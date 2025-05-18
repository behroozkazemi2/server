package com.behrouz.server.repository;

import com.behrouz.server.model.account.CustomerEntity;
import com.behrouz.server.repository.base.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.repository
 * Project server
 * 15 September 2018 11:05
 **/
@Repository
public interface CustomerRepository extends BaseRepository<CustomerEntity>, JpaSpecificationExecutor<CustomerEntity> {

    boolean existsByMobileAndBannedIsFalse( String mobile );

    CustomerEntity findFirstByMobileAndBannedIsFalseAndDeletedIsFalse ( String mobile );

    CustomerEntity findFirstByInvitingCodeAndBannedIsFalseAndDeletedIsFalse ( String invitingCode );

    CustomerEntity findFirstByIdAndBannedIsFalseAndDeletedIsFalse ( long customerId );

    Page<CustomerEntity> findAllByBannedIsFalseAndDeletedIsFalse (Pageable pageable);

    Page<CustomerEntity> findAllByBannedIsTrueAndDeletedIsFalse (Pageable pageable);


    @Query(
            "SELECT c FROM CustomerEntity c " +
                    "WHERE c.banned = :statusIsBanned " +
                    "AND ( c.firstName like concat( '%',:name,'%' ) OR c.lastName like concat( '%',:name,'%' ) )" +
                    "AND c.mobile like concat( '%',:mobile,'%' )"
    )
    Page<CustomerEntity> findAllSearch(
            @Param( "name" ) String name,
            @Param( "mobile" ) String mobile,
            @Param( "statusIsBanned" ) boolean statusIsBanned, // Ban
            Pageable pageable
    );

    CustomerEntity findFirstById(int id);

}
