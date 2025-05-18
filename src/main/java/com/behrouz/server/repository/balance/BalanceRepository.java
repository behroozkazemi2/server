package com.behrouz.server.repository.balance;

import com.behrouz.server.model.balance.BalanceEntity;
import com.behrouz.server.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by: Reza
 * Company: reza
 * Package: com.behrouz.server.repository
 * Project Name: behta-server
 * 09 December 2018
 **/

@Repository
public interface BalanceRepository extends BaseRepository<BalanceEntity> {

    BalanceEntity findFirstByAccount_IdAndDeletedIsFalseOrderByIdDesc(long customerId );

}
