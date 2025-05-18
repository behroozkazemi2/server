package com.behrouz.server.repository;

import com.behrouz.server.model.account.AccountEntity;
import com.behrouz.server.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by: Reza
 * Company: reza
 * Package: com.behrouz.server.repository
 * Project Name: behta-server
 * 06 July 2020
 **/

@Repository
public interface AccountRepository extends BaseRepository<AccountEntity> {

    AccountEntity findFirstByMobileAndBannedIsFalseAndDeletedIsFalse( String mobile );

    boolean existsByMobileAndBannedIsFalseAndDeletedIsFalse(String mobile);

}