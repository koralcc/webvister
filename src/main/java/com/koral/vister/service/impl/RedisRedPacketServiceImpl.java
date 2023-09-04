package com.koral.vister.service.impl;

import com.koral.vister.common.entity.UserRedPacket;
import com.koral.vister.service.RedisRedPacketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class RedisRedPacketServiceImpl implements RedisRedPacketService {
    private static final String PREFIX = "red_packet_list_";
    // 每次取出1000条，避免一次取出消耗太多内存
    private static final int TIME_SIZE = 1000;
    @Autowired
    private RedisTemplate<String, String> redisTemplate = null;

    @Autowired
    private DataSource dataSource = null;


    @Override
    @Async
    public void saveUserRedPacketByRedis(Long redPacketId, Double unitAmount) {
        log.info("开始保存数据");
        Long start = System.currentTimeMillis();
        // 获取列表操作对象
        BoundListOperations ops = redisTemplate.boundListOps(PREFIX + redPacketId);
        Long size = ops.size();
        Long times = size % TIME_SIZE == 0 ? size / TIME_SIZE : size / TIME_SIZE + 1;
        int count = 0;
        ArrayList<UserRedPacket> userRedPacketList = new ArrayList<>(TIME_SIZE);
        for (int i = 0; i < times; i++) {
            // 获取至多TIME_SIZE个抢红包信息
            List userIdList = null;
            if (i == 0) {
                userIdList = ops.range(i * TIME_SIZE, (i + 1) * TIME_SIZE);
            } else {
                userIdList = ops.range(i * TIME_SIZE + 1, (i + 1) * TIME_SIZE + 1);
            }
            userRedPacketList.clear();
            // 保存红包信息
            for (int j = 0; j < userIdList.size(); j++) {
                String args = userIdList.get(j).toString();
                String[] arr = args.split("-");
                String userIdStr = arr[0];
                String timeStr = arr[1];
                long userId = Long.parseLong(userIdStr);
                long time = Long.parseLong(timeStr);
                //生成抢红包信息
                UserRedPacket userRedPacket = new UserRedPacket();
                userRedPacket.setRedPacketId(redPacketId);
                userRedPacket.setUserId(userId);
                userRedPacket.setAmount(BigDecimal.valueOf(unitAmount));
                userRedPacket.setGrabTime(new Timestamp(time).toLocalDateTime());
                userRedPacket.setNote("redis抢红包" + redPacketId);
                userRedPacketList.add(userRedPacket);
            }

            //插入抢红包信息
            count += executebatch(userRedPacketList);

        }

        //删除redis列表
        redisTemplate.delete(PREFIX + redPacketId);
        long end = System.currentTimeMillis();
        log.info("保存数据结束，耗时{}毫秒，共{}条记录被保存", (end - start), count);
    }

    /**
     * 使用JDBC批量处理redis缓存数据
     *
     * @param userRedPacketList
     * @return
     */
    private int executebatch(ArrayList<UserRedPacket> userRedPacketList) {
        Connection conn = null;
        Statement stmt = null;
        int[] count = null;
        try {
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            for (UserRedPacket userRedPacket : userRedPacketList) {
                String sql1 = "update T_RED_PACKET set stock = stock - 1 where id =" + userRedPacket.getRedPacketId();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String sql2 = "insert into T_USER_RED_PACKET(red_packet_id,user_id,amount,grab_time,note) values("
                        + userRedPacket.getRedPacketId() + ","
                        + userRedPacket.getUserId() + ","
                        + userRedPacket.getAmount() + ","
//                        + "'" +  df.format(userRedPacket.getGrabTime()) + "'" + ","
                        + "'" + DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(userRedPacket.getGrabTime()) + "'" + ","
                        + "'" + userRedPacket.getNote() + "'"
                        + ")";
                stmt.addBatch(sql1);
                stmt.addBatch(sql2);
            }
            // 执行批量
            count = stmt.executeBatch();
            // 提交事务
            conn.commit();
        } catch (SQLException e) {
            /**
             * 错误处理逻辑
             */
            log.info("抢红包批量执行程序错误:" + e);
            throw new RuntimeException("抢红包批量执行程序错误");
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return count.length / 2;
    }
}
