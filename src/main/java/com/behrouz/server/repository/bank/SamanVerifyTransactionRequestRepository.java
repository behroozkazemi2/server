package com.behrouz.server.repository.bank;


import com.behrouz.server.model.bank.SamanVerifyTransactionRequestEntity;
import com.behrouz.server.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SamanVerifyTransactionRequestRepository extends BaseRepository<SamanVerifyTransactionRequestEntity> {
    SamanVerifyTransactionRequestEntity findFirstByRefNum(String refNum);
}
