package com.behrouz.server.repository;

import com.behrouz.server.model.account.OperatorEntity;
import com.behrouz.server.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.repository
 * Project server
 * 24 September 2018 11:14
 **/
@Repository
public interface OperatorRepository extends BaseRepository<OperatorEntity> {


    OperatorEntity findFirstByUsernameAndBannedIsFalse( String username );

    OperatorEntity findFirstByUsernameAndDeletedIsFalse(String username);
}
