package com.behrouz.server.repository.bill;

import com.behrouz.server.model.bill.BillEntity;
import com.behrouz.server.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.repository
 * Project server
 * 10 October 2018 13:08
 **/
@Repository
public interface BillRepository extends BaseRepository<BillEntity> {



//    Page<BillEntity> findAllByCustomer_IdAndStatus_IdNotOrderByIdDesc ( int customerId, int exceptionStatus, Pageable pageable );

    BillEntity findFirstByIdAndDeletedIsFalse ( long billId );

//    BillEntity findFirstByCustomer_IdOrderByIdDescAndDeletedIsFalse ( long customerId );

    long countAllByCustomer_IdAndOffCode_CodeAndDeletedIsFalse ( long customerId, String offCode );

    long countAllByCustomer_IdAndOffCode_IdAndDeletedIsFalse(long customerId, long id);

    BillEntity findFirstByCustomer_IdOrderByIdDesc(long customerId);

//    List<BillEntity> findAllByCustomer_IdAndStatus_IdNotAndOffCode_Code ( int customerId, int id, String offCode );
}
