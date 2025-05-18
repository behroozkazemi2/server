package com.behrouz.server.repository;

import com.behrouz.server.model.product.ProductProviderEntity;
import com.behrouz.server.repository.base.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.repository
 * Project server
 * 24 September 2018 15:13
 **/
@Repository
public interface ProductProviderRepository extends BaseRepository<ProductProviderEntity>, JpaSpecificationExecutor<ProductProviderEntity> {

    ProductProviderEntity findFirstByIdAndDeletedIsFalse( long id );

    @Query(
            "SELECT pp FROM ProductProviderEntity pp " +
                    "INNER JOIN ProductEntity p ON pp.product.id = p.id AND p.deleted = false " +
                    "WHERE pp.deleted = false " +
//                    "AND pp.customized = false " + todo
                    "AND pp.provider.banned = false " +
                    "AND pp.provider.deleted = false " +
                    "AND pp.provider.active = true " +
                    "AND pp.provider.id = :providerId " +
                    "AND ( :productCategoryId = 0 or p.category.id = :productCategoryId )"
//                    + "ORDER BY pp.productProviderOrder "
    )
    Page<ProductProviderEntity> findAllProductByProvider(
            @Param( "productCategoryId" ) long productCategoryId,
            @Param( "providerId" ) long providerId,
            Pageable pageable

    );


//    Page<ProductProviderEntity> findAllByProvider_IdAndDeletedIsFalseAndProductProviderExistenceIsTrueAndCustomizedIsFalse ( long providerId, Pageable pageable );
//    Page<ProductProviderEntity> findAllByProvider_IdAndDeletedIsFalseAndProductProviderExistenceIsFalseAndCustomizedIsFalse ( long providerId, Pageable pageable );

//
//    @Query(
//            "SELECT pp FROM ProductProviderEntity pp " +
//                    "INNER JOIN ProductEntity p ON p.id = pp.product.id AND p.deleted= FALSE  " +
//                    "WHERE pp.deleted = false " +
//                    "AND pp.productProviderExistence = :existence " +
//                    "AND pp.deleted = false " +
////                    "AND pp.customized = false " + todo
//                    "AND pp.provider.active = true " +
//                    "AND pp.provider.deleted = false " +
//                    "AND pp.provider.banned = false " +
//                    "AND pp.provider.id = :providerId " +
//                    "AND pp.name like concat( '%',:name,'%' )"
//    )
////    Page<ProductProviderEntity> findAllSearch(
//            @Param( "name" ) String name,
//            @Param( "providerId" ) int providerId,
//            @Param( "existence" ) boolean existence,
//            Pageable pageable
//    );

    List<ProductProviderEntity> findAllByDeletedIsFalseAndProductProviderExistenceIsTrue();
//    List<ProductProviderEntity> findAllByDeletedIsFalseAndProductProviderExistenceIsTrueAndCustomizedIsFalse();

    //    List<ProductProviderEntity> findFirst18ByDeletedIsFalseAndProductProviderExistenceIsTrueAndCustomizedIsFalse();
    List<ProductProviderEntity> findFirst18ByDeletedIsFalseAndProductProviderExistenceIsTrue();

//    List<ProductProviderEntity> findAllByProvider_IdAndDeletedIsFalseAndCustomizedIsFalse ( int providerId );


//    ProductProviderEntity findFirstByIdAndDeletedIsFalseAndCustomizedIsTrue ( int productProviderId );

    Page<ProductProviderEntity> findAllByProvider_IdAndDeletedIsFalseAndProductProviderExistenceIsTrue(long providerId, Pageable of);

    List<ProductProviderEntity> findAllByProvider_IdAndDeletedIsFalse(long providerId);

    List<ProductProviderEntity> findAllByProduct_IdAndDeletedIsFalse(long id);

    List<ProductProviderEntity> findFirstByIdInAndDeletedIsFalse(List<Long> ids);

    ProductProviderEntity findFistByProduct_IdAndProviderIdAndDeletedIsFalse(long productId, long providerId);
}
