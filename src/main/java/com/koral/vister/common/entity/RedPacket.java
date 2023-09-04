package com.koral.vister.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 *
 * </p>
 *
 * @author baomidou
 * @since 2023-09-02
 */
@TableName("T_RED_PACKET")
@ApiModel(value = "RedPacket对象", description = "")
public class RedPacket implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("发红包用户")
    private Long userId;

    @ApiModelProperty("红包金额")
    private BigDecimal amount;

    @ApiModelProperty("发红包时间")
    private LocalDateTime sendDate;

    @ApiModelProperty("小红包总数")
    private Integer total;

    @ApiModelProperty("单个小红包金额")
    private BigDecimal unitAmount;

    @ApiModelProperty("剩余小红包个数")
    private Integer stock;

    @ApiModelProperty("版本")
    private Integer version;

    @ApiModelProperty("备注")
    private String note;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getSendDate() {
        return sendDate;
    }

    public void setSendDate(LocalDateTime sendDate) {
        this.sendDate = sendDate;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public BigDecimal getUnitAmount() {
        return unitAmount;
    }

    public void setUnitAmount(BigDecimal unitAmount) {
        this.unitAmount = unitAmount;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "RedPacket{" +
            "id = " + id +
            ", userId = " + userId +
            ", amount = " + amount +
            ", sendDate = " + sendDate +
            ", total = " + total +
            ", unitAmount = " + unitAmount +
            ", stock = " + stock +
            ", version = " + version +
            ", note = " + note +
        "}";
    }
}
