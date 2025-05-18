package com.behrouz.server.repository;

import com.behrouz.server.model.product.ProductProviderPriceEntity;
import com.behrouz.server.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.repository
 * Project server
 * 06 October 2018 10:50
 **/
@Repository
public interface ProductPriceRepository extends BaseRepository<ProductProviderPriceEntity> {

    ProductProviderPriceEntity findFirstByDeletedIsFalseAndProductProvider_IdAndProductProvider_DeletedIsFalseOrderByIdDesc(long productProviderId );

    List<ProductProviderPriceEntity> findAllByDeletedIsFalseAndProductProvider_Provider_IdAndProductProvider_DeletedIsFalseOrderByIdDesc(long providerId );

    ProductProviderPriceEntity findFirstByDeletedIsFalseAndProductProvider_IdOrderByIdDesc(long productProviderId );

    List<ProductProviderPriceEntity> findAllByProductProvider_IdInAndDeletedIsFalse(List<Long> pIds);
}
