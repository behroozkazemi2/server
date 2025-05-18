package com.behrouz.server.repository;

import com.behrouz.server.model.OffCodeEntity;
import com.behrouz.server.repository.base.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by: Reza
 * Company: reza
 * Package: com.behrouz.server.repository
 * Project Name: behta-server
 * 20 January 2019
 **/
public interface OffCodeRepository extends BaseRepository<OffCodeEntity>, JpaSpecificationExecutor<OffCodeEntity> {


    @Query(
            value = "SELECT oc FROM OffCodeEntity oc " +
                    "WHERE ( oc.customer IS NULL OR oc.customer.id = :customerId ) " +
                    "AND oc.deleted = false "
    )
    List<OffCodeEntity> findAllForCustomerIdAndDeletedIsFalse (
            @Param( "customerId" ) long customerId
    );


    @Query(
            value = "SELECT oc FROM OffCodeEntity oc " +
                    "WHERE oc.code = :offCode " +
                    "AND oc.deleted = false " +
                    "AND ( oc.productProvider.id IN :productProviderIds ) " +
                    "AND ( oc.provider.id IN :providerIds ) " +
                    "AND ( oc.customer.id = :customerId ) "
    )
    OffCodeEntity findFirstByCodeForCustomer (
            @Param( "productProviderIds" ) List < Integer > productProviderIds,
            @Param( "providerIds" ) List < Integer > providerIds,
            @Param( "customerId" ) long customerId,
            @Param( "offCode" ) String offCode
    );


    OffCodeEntity findFirstByCodeAndDeletedIsFalse ( String usedOffCode );


    //TODO CHECK THIS
    @Query(
            value = "select oc from OffCodeEntity oc " +
                    "where oc.deleted = false " +
                    "and DATE(oc.expireDate) >= DATE(current_date) "
    )
    Page<OffCodeEntity> findAllValidOffCodes ( Pageable pageable );


    //TODO CHECK THIS
    @Query(
            value = "select oc from OffCodeEntity oc " +
                    "where oc.deleted = false " +
                    "or DATE(oc.startDate) > DATE(current_date) " +
                    "or DATE(oc.expireDate) < DATE(current_date) "
    )
    Page<OffCodeEntity> findAllInValidOffCodes ( Pageable pageable );


    OffCodeEntity findFirstByIdAndDeletedIsFalse ( long id );


    List<OffCodeEntity> findAllByCustomer_IdAndDeletedIsFalse(long customerId);

    OffCodeEntity findFirstByProductProvider_IdAndDeletedIsFalseAndProductProvider_DeletedIsFalseOrderByIdDesc(long id);

    OffCodeEntity findFirstByProductProvider_IdAndDeletedIsFalseAndProductProvider_DeletedIsFalse(long id);

    List<OffCodeEntity> findAllByProvider_IdAndProductProvider_DeletedIsFalseAndDeletedIsFalse(long providerId);
    List<OffCodeEntity> findAllByCustomer_IdOrForAllIsTrueAndDeletedIsFalse(long customerId);

}
