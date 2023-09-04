package com.koral.vister.service;

public interface UserRedPacketService {
    /**
     * 保存抢红包信息
     * @param redPacketId
     * @param userId
     * @return
     */
    public int grapRedPacket(Long redPacketId, Long userId);

    public int grapRedPacketForVersion(Long redPacketId, Long userId);

    Long grapRedPacketByRedis(Long redPacketId, Long userId);
}
