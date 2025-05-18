package com.behrouz.server.repository.bank;


import com.behrouz.server.model.bank.BankInfoEntity;
import com.behrouz.server.repository.base.BaseRepository;

public interface BankInfoRepository extends BaseRepository<BankInfoEntity> {

    BankInfoEntity findFirstByDeletedIsFalseAndAccount_Id(long id);

}
