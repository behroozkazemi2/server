package com.behrouz.server.repository;

import com.behrouz.server.model.OrderStatusEntity;
import com.behrouz.server.repository.base.BaseNameRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.repository
 * Project server
 * 06 October 2018 14:37
 **/
@Repository
public interface OrderStatusRepository extends BaseNameRepository<OrderStatusEntity> {


    OrderStatusEntity findFirstByIdAndDeletedIsFalse( long id );
}
