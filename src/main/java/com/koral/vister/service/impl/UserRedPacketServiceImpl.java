package com.koral.vister.service.impl;

import com.koral.vister.common.entity.RedPacket;
import com.koral.vister.common.entity.UserRedPacket;
import com.koral.vister.mapper.RedPacketMapper;
import com.koral.vister.mapper.UserRedPacketMapper;
import com.koral.vister.service.RedisRedPacketService;
import com.koral.vister.service.UserRedPacketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;

@Service
@Slf4j
public class UserRedPacketServiceImpl implements UserRedPacketService {
    @Autowired
    private UserRedPacketMapper userRedPacketMapper;
    @Autowired
    private RedPacketMapper redPacketMapper;
    // 失败
    private static final int FAILED = 0;

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RedisRedPacketService redisRedPacketService = null;

    // Lua脚本
    String script = "local listKey = 'red_packet_list_'..KEYS[1] \n"
            + "local redPacket='red_packet_'..KEYS[1] \n"
            + "local stock = tonumber(redis.call('hget',redPacket,'stock')) \n"
            + "if stock <=0 then return 0 end \n"
            + "stock = stock - 1 \n"
            + "redis.call('hset',redPacket,'stock',tostring(stock)) \n"
            + "redis.call('rpush',listKey,ARGV[1]) \n"
            + "if stock == 0 then return 2 end \n"
            + "return 1 \n";
    //在缓存Lua脚本后，使用该变量保存Redis返回大的32位的SHA1编码，使用它去执行缓存Lua脚本
    String sha1 = null;

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public int grapRedPacket(Long redPacketId, Long userId) {
        // 获取红包信息
        RedPacket redPacket = redPacketMapper.getRedPacket(redPacketId);
        // 当红包库存大于0时
        if (redPacket.getStock() > 0) {
            redPacketMapper.decreaseRedPacket(redPacketId);
            UserRedPacket userRedPacket = new UserRedPacket();
            userRedPacket.setRedPacketId(redPacketId);
            userRedPacket.setUserId(userId);
            userRedPacket.setAmount(redPacket.getUnitAmount());
            userRedPacket.setNote("抢红包" + redPacketId);

            int result = userRedPacketMapper.grapRedPacket(userRedPacket);
            return result;
        }
        // 失败返回
        return FAILED;
    }

    @Override
    public int grapRedPacketForVersion(Long redPacketId, Long userId) {
        for (int i = 0; i < 3; i++) {
            RedPacket redPacket = redPacketMapper.getRedPacket(redPacketId);
            if (redPacket.getStock() > 0) {
                int update = redPacketMapper.decreaseRedPacketForVersion(redPacketId, redPacket.getVersion());
                if (update == 0) {
                    continue;
                }
                UserRedPacket userRedPacket = new UserRedPacket();
                userRedPacket.setRedPacketId(redPacketId);
                userRedPacket.setUserId(userId);
                userRedPacket.setAmount(redPacket.getUnitAmount());
                userRedPacket.setNote("抢红包" + redPacketId);
                int result = userRedPacketMapper.grapRedPacket(userRedPacket);
                return result;
            } else {
                return FAILED;
            }
        }
        return FAILED;
    }

    @Override
    public Long grapRedPacketByRedis(Long redPacketId, Long userId) {
        // 当前抢红包用户和日期信息
        String args = userId + "-" + System.currentTimeMillis();
        Long result = null;
        //获取底层Redis操作对象
        Jedis jedis = (Jedis) redisTemplate.getConnectionFactory().getConnection().getNativeConnection();
        try {
            if (sha1 == null) {
                sha1 = jedis.scriptLoad(script);
            }
            // 执行脚本，返回结果
            Object res = jedis.evalsha(sha1, 1, redPacketId + "", args);
            result = (Long) res;
            // 返回2时为最后一个红包，此时将红包信息通过异步保存到数据据库中
            if (result==2) {
                // 获取单个最小金额
                String unitAmountStr = jedis.hget("red_packet_" + redPacketId, "unit_amount");
                //触发保存数据库操作
                double unitAmount = Double.parseDouble(unitAmountStr);
                log.info("thread_name=" + Thread.currentThread().getName());
                redisRedPacketService.saveUserRedPacketByRedis(redPacketId, unitAmount);
            }
        } finally {
            // 确保jedis顺利关闭
            if (jedis != null && jedis.isConnected()) {
                jedis.close();
            }
        }
        return result;
    }
}
