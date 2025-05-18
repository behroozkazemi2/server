package com.behrouz.server.repository.bank;


import com.behrouz.server.model.bank.BankTransactionEntity;
import com.behrouz.server.repository.base.BaseRepository;

public interface BankTransactionRepository extends BaseRepository<BankTransactionEntity> {



    BankTransactionEntity findFirstByToken(String token);


}
