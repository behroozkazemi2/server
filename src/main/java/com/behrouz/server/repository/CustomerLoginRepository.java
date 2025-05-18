package com.behrouz.server.repository;

import com.behrouz.server.model.account.CustomerLoginEntity;
import com.behrouz.server.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.repository
 * Project server
 * 22 September 2018 09:51
 **/
@Repository
public interface CustomerLoginRepository extends BaseRepository<CustomerLoginEntity> {


    CustomerLoginEntity findFirstByAccount_IdAndLogoutIsFalseAndDeletedIsFalseOrderByIdDesc( long customerId );
}
