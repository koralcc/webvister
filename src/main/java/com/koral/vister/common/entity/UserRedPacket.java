package com.koral.vister.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;

/**
 * <p>
 * 
 * </p>
 *
 * @author koral
 * @since 2023-09-02
 */
@TableName("T_USER_RED_PACKET")
@ApiModel(value = "UserRedPacket对象", description = "")
public class UserRedPacket implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long redPacketId;

    private Long userId;

    private BigDecimal amount;

    private LocalDateTime grabTime;

    private String note;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRedPacketId() {
        return redPacketId;
    }

    public void setRedPacketId(Long redPacketId) {
        this.redPacketId = redPacketId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getGrabTime() {
        return grabTime;
    }

    public void setGrabTime(LocalDateTime grabTime) {
        this.grabTime = grabTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "UserRedPacket{" +
            "id = " + id +
            ", redPacketId = " + redPacketId +
            ", userId = " + userId +
            ", amount = " + amount +
            ", grabTime = " + grabTime +
            ", note = " + note +
        "}";
    }
}
