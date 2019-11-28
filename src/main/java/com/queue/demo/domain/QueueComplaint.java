package com.queue.demo.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity(name= "QUEUE_COMPLAINT")
public class QueueComplaint {
    @Id
    @GeneratedValue
    private String complaintId;  //排队码
    @Column
    private String driverTel;
    @Column
    private String driverContent;
    @Column
    private String submitTime;

    public String getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(String complaintId) {
        this.complaintId = complaintId;
    }

    public String getDriverTel() {
        return driverTel;
    }

    public void setDriverTel(String driverTel) {
        this.driverTel = driverTel;
    }

    public String getDriverContent() {
        return driverContent;
    }

    public void setDriverContent(String driverContent) {
        this.driverContent = driverContent;
    }

    public String getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(String submitTime) {
        this.submitTime = submitTime;
    }
}
