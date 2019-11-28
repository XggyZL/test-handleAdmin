package com.queue.demo.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity(name = "QUEUE_STO_RESERVE")
public class QueueStoReserve {
    @Id
    @GeneratedValue
    private String stoReserveId; //预约订单Id
    @Column
    private String companyName; //工厂编号
    @Column
    private String queueCode;  //排队码
    @Column
    private String supplierCode;
    @Column
    private String supplierName;
    @Column
    private String dischargePort;  //卸货口
    @Column
    private String driverTel;   //司机手机号
    @Column
    private String carNum;  //车牌号
    @Column
    private String reserveArrivalTime;  //预计到达时间
    @Column
    private String stoReserveState; //已预约，
    @Column
    private String arrivalTime;  //实际到达时间
    @Column
    private String orderTime;  //提交时间
    @Column
    private String nextQueueCode;  //下一个【排队中】排队码
    @Column
    private String finishTime;

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getNextQueueCode() {
        return nextQueueCode;
    }

    public void setNextQueueCode(String nextQueueCode) {
        this.nextQueueCode = nextQueueCode;
    }

    public String getCarNum() {
        return carNum;
    }

    public void setCarNum(String carNum) {
        this.carNum = carNum;
    }

    public String getStoReserveId() {
        return stoReserveId;
    }

    public void setStoReserveId(String stoReserveId) {
        this.stoReserveId = stoReserveId;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
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

    public String getReserveArrivalTime() {
        return reserveArrivalTime;
    }

    public void setReserveArrivalTime(String reserveArrivalTime) {
        this.reserveArrivalTime = reserveArrivalTime;
    }

    public String getStoReserveState() {
        return stoReserveState;
    }

    public void setStoReserveState(String stoReserveState) {
        this.stoReserveState = stoReserveState;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getQueueCode() {
        return queueCode;
    }

    public void setQueueCode(String queueCode) {
        this.queueCode = queueCode;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
}
