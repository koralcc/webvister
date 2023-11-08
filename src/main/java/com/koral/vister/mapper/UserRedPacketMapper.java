package com.koral.vister.mapper;

import com.koral.vister.common.entity.UserRedPacket;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author koral
 * @since 2023-09-02
 */
@Mapper
public interface UserRedPacketMapper {
    int grapRedPacket(UserRedPacket userRedPacket);

}