package com.queue.demo.domain;

import lombok.Data;
import org.omg.CORBA.PRIVATE_MEMBER;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity(name = "QUEUE_STO_CODE")
public class QueueStoCode {
    @Id
    @GeneratedValue
    private String queueCode;  //排队码
    @Column
    private String stoReserveId; //预约订单Id
    @Column
    private String dischargePort;  //卸货口
    @Column
    private String driverTel;   //司机手机号
    @Column
    private String arrivalTime;  //实际到达时间
    @Column
    private String queueState; //已到达，已完成

    public String getQueueCode() {
        return queueCode;
    }

    public void setQueueCode(String queueCode) {
        this.queueCode = queueCode;
    }

    public String getStoReserveId() {
        return stoReserveId;
    }

    public void setStoReserveId(String stoReserveId) {
        this.stoReserveId = stoReserveId;
    }

    public String getDischargePort() {
        return dischargePort;
    }

    public void setDischargePort(String dischargePort) {
        this.dischargePort = dischargePort;
    }

    public String getDriverTel() {
        return driverTel;
    }

    public void setDriverTel(String driverTel) {
        this.driverTel = driverTel;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getQueueState() {
        return queueState;
    }

    public void setQueueState(String queueState) {
        this.queueState = queueState;
    }
}
