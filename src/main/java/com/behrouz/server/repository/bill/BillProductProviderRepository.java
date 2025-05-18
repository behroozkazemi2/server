package com.behrouz.server.repository.bill;

import com.behrouz.server.model.bill.BillProductProviderEntity;
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
public interface BillProductProviderRepository extends BaseRepository<BillProductProviderEntity> {


    List<BillProductProviderEntity> findAllByBill_IdAndDeletedIsFalse(long billId );

    List<BillProductProviderEntity> findAllByBill_IdAndDeletedIsFalseOrderById(long id);
}
