package com.koral.vister.mapper;
import com.koral.vister.common.entity.RedPacket;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author baomidou
 * @since 2023-09-02
 */
@Mapper
public interface RedPacketMapper{
    /**
     * 获取红包信息
     *
     * @param id
     * @return
     */
    RedPacket getRedPacket(Long id);

    /**
     * 扣减抢红包数
     *
     * @param id
     * @return
     */
    int decreaseRedPacket(Long id);

    int decreaseRedPacketForVersion(Long redPacketId, Integer version);
}
