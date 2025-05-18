package com.behrouz.server.repository.bill;

import com.behrouz.server.model.bill.BillBillStatusEntity;
import com.behrouz.server.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.repository
 * Project server
 * 11 October 2018 09:11
 **/
@Repository
public interface BillBillStatusRepository extends BaseRepository<BillBillStatusEntity> {

    BillBillStatusEntity findFirstByBill_IdAndDeletedIsFalseOrderByIdDesc(long billId);

    List<BillBillStatusEntity> findAllByBill_IdAndDeletedIsFalse(long id);
}
