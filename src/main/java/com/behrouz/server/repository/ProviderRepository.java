package com.behrouz.server.repository;

import com.behrouz.server.model.account.ProviderEntity;
import com.behrouz.server.repository.base.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.repository
 * Project server
 * 23 September 2018 08:54
 **/
@Repository
public interface ProviderRepository extends BaseRepository<ProviderEntity>, JpaSpecificationExecutor<ProviderEntity> {

    ProviderEntity findFirstByIdAndDeletedIsFalseAndBannedIsFalse(long id );


//    // TODO CHECK (ProviderCategoryEntity deleted)
//    @Query(
//            "SELECT distinct pp.provider FROM ProductProviderEntity pp " +
//            "INNER JOIN  ProductEntity p  ON  pp.product = p.id  AND p.deleted = FALSE  " +
//                "WHERE pp.deleted = false " +
//                "AND ( :productCategoryId = 0 OR p.category.id = :productCategoryId ) " +
//                "AND pp.deleted = false " +
////                "AND pp.customized = false " +
//                "AND pp.provider.active = true " +
////                "AND (:providerCategoryId = 0 OR pp.provider.id IN " +
////                    "(SELECT pc.provider.id FROM ProviderCategoryEntity pc " +
////                        "WHERE pc.deleted = false " +
////                        "AND pc.category.id = :providerCategoryId))" +
//                    "AND (:regionIds is null OR pp.provider.id IN " +
//                    "(SELECT pr.provider.id FROM ProviderRegionEntity pr " +
//                        "WHERE pr.deleted = false " +
//                        "AND pr.region.id in (:regionIds) ) )"
//    )
//    Page<ProviderEntity> findAllByProductProviderCategory(
//            @Param( "providerCategoryId" ) Long providerCategoryId,
//            @Param( "productCategoryId" ) Long productCategoryId,
//            @Param( "regionIds" ) List<Long> regionIds,
//            Pageable pageable
//    );


    Page<ProviderEntity> findAllByDeletedIsFalseAndBannedIsFalseAndActiveIsTrue( Pageable pageable );

    List<ProviderEntity> findAllByDeletedIsFalseAndBannedIsFalseAndActiveIsTrue();

    ProviderEntity findFirstById( long id );

    Page<ProviderEntity> findAllByDeletedIsFalseAndBannedIsFalseAndActiveIsFalse( Pageable pageable );

    List<ProviderEntity> findFirst10ByDeletedIsFalseAndBannedIsFalseAndActiveIsTrueOrderByIdDesc ();

    ProviderEntity findFirstByIdAndDeletedIsFalseAndBannedIsFalseAndActiveIsTrue ( long id );

    ProviderEntity findFirstByIdAndDeletedIsFalse(long id);
}
