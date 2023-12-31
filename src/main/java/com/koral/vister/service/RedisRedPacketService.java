package com.koral.vister.service;

public interface RedisRedPacketService {

    /**
     * 保存redis抢红包列表
     *
     * @param redPacketId
     * @param unitAmount
     */
    public void saveUserRedPacketByRedis(Long redPacketId, Double unitAmount);
}
