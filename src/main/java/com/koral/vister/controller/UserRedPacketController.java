package com.koral.vister.controller;

import com.koral.vister.service.UserRedPacketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class UserRedPacketController {

    @Autowired
    private UserRedPacketService userRedPacketService;

    @PostMapping("/userRedPacket/grapRedPacket")
//    @CrossOrigin
    public Map<String, Object> grapRedPacket(Long redPacketId, Long userId) {
        log.info("[[抢红包]]");
        // 抢红包
        int result = userRedPacketService.grapRedPacket(redPacketId, userId);
        HashMap<String, Object> retMap = new HashMap<>();
        boolean flag = result > 0;
        retMap.put("success", flag);
        retMap.put("message", flag ? "抢红包成功" : "抢红包失败");
        return retMap;
    }

    /**
     * 乐观锁
     *
     * @param redPacketId
     * @param userId
     * @return
     */
    @PostMapping("/userRedPacket/grapRedPacketPositiveLock")
    public Map<String, Object> grapRedPacketPositiveLock(Long redPacketId, Long userId) {
        log.info("乐观锁自旋抢红包");
        int result = userRedPacketService.grapRedPacketForVersion(redPacketId, userId);
        HashMap<String, Object> retMap = new HashMap<>();
        boolean flag = result > 0;
        retMap.put("success", flag);
        retMap.put("message", flag ? "抢红包成功" : "抢红包失败");
        return retMap;
    }

    @PostMapping("/userRedPacket/grapRedPacketByRedis")
    public Map<String, Object> grapRedPacketByRedis(Long redPacketId, Long userId) {
        log.info("redis抢红包入口");
        Long result = userRedPacketService.grapRedPacketByRedis(redPacketId, userId);
        HashMap<String, Object> retMap = new HashMap<>();
        boolean flag = result > 0;
        retMap.put("success", flag);
        retMap.put("message", flag ? "抢红包成功" : "抢红包失败");
        return retMap;
    }
}
