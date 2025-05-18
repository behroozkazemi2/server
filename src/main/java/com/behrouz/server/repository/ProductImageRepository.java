package com.behrouz.server.repository;

import com.behrouz.server.model.product.ProductImageEntity;
import com.behrouz.server.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.repository
 * Project server
 * 06 October 2018 11:08
 **/
@Repository
public interface ProductImageRepository extends BaseRepository<ProductImageEntity> {

    List<ProductImageEntity> findAllByProduct_IdAndProduct_DeletedIsFalseAndDeletedIsFalse (long productProviderId );

    ProductImageEntity findFirstByImage_Id (long imageId );

    List<ProductImageEntity> findAllByProduct_DeletedIsFalse();

    List<ProductImageEntity> findAllByProduct_Id (long productProviderId );

    ProductImageEntity findFirstByProduct_Id (long productProviderId );

}
