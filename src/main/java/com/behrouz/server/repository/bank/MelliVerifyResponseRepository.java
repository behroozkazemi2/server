package com.behrouz.server.repository.bank;


import com.behrouz.server.model.bank.melli.MelliVerifyResponseEntity;
import com.behrouz.server.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MelliVerifyResponseRepository extends BaseRepository<MelliVerifyResponseEntity> {

    MelliVerifyResponseEntity findFirstByBankTransaction_Id(long id);


}
