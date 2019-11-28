package com.queue.demo.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.queue.demo.domain.QueueComplaint;
import com.queue.demo.domain.QueueStoCode;
import com.queue.demo.domain.QueueStoReserve;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Mapper
@Component(value = "QueueHomeMyBatisDao")
public interface QueueHomeMyBatisDao extends BaseMapper<QueueStoReserve> {
    String getSupplierName(@Param("supplierCode") String supplierCode);
    String getQueueStoReserveId(@Param("thisDate") String thisDate);

    void saveStoreReserveInfo(QueueStoReserve queueStoReserve);

    QueueStoReserve getNewStoReserveInfoById(@Param("stoReserveId") String stoReserveId);

    List<Map> getSortInfo(@Param("reserveArrivalTime") String reserveArrivalTime, @Param("dischargePort") String dischargePort);

    void cancelReserveOrder(@Param("stoReserveId") String stoReserveId);

    List<QueueStoReserve> getStoReserveListByTel(@Param("driverTel") String driverTel);

    void saveQueueStoCodeInfo(QueueStoCode queueStoCode);

    String getQueueStoCode(@Param("thisDate") String thisDate);

    void changeQueueStoReserveState(@Param("stoReserveId") String stoReserveId, @Param("stoReserveState") String stoReserveState,
                                    @Param("queueCode") String queueCode, @Param("arrivalTime") String arrivalTime);

    QueueStoCode getQueueStoCodeInfo(@Param("stoReserveId") String stoReserveId);

    List<QueueStoCode> getQueueStoCodeInfoList(@Param("thisDate") String thisDate);

    List<QueueStoCode> getCodeInfoByTel(@Param("driverTel") String driverTel);

    void finishOrder(@Param("stoReserveId") String stoReserveId);

    List<QueueStoReserve> getStoReserveByState(@Param("state") String state);

    String getQueueOrder(@Param("companyName") String companyName);

    int panduanTel(@Param("driverTel") String driverTel);

    void saveComplaintInfo(QueueComplaint queueComplaint);

}
