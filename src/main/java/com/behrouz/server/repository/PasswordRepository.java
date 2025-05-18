package com.behrouz.server.repository;

import com.behrouz.server.model.account.PasswordEntity;
import com.behrouz.server.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.repository
 * Project server
 * 24 September 2018 11:04
 **/
@Repository
public interface PasswordRepository extends BaseRepository<PasswordEntity> {

    PasswordEntity findFirstByOperator_IdAndDeletedIsFalseOrderByIdDesc( long operatorId );


}
