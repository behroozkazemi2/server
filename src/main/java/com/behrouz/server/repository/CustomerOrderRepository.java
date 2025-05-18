package com.behrouz.server.repository;

import com.behrouz.server.model.order.CustomerOrderEntity;
import com.behrouz.server.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.repository
 * Project server
 * 06 October 2018 14:30
 **/
@Repository
public interface CustomerOrderRepository extends BaseRepository<CustomerOrderEntity> {

    CustomerOrderEntity findFirstByCustomer_IdAndProductProvider_IdAndProductProvider_DeletedIsFalse(
            long customerId, long productProviderId
    );
    List<CustomerOrderEntity> findAllByCustomer_IdAndDeletedIsFalse(long customerId );
    CustomerOrderEntity findFirstByCustomer_IdAndProductProvider_IdAndDeletedIsFalse(long customerId, long productId );

//    CustomerOrderEntity findFirstByCustomer_IdAndSpecialProduct_IdAndDeletedIsFalse(int customerId, long id);

}
