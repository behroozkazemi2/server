package com.behrouz.server.repository;

import com.behrouz.server.model.product.ProductTagEntity;
import com.behrouz.server.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.repository
 * Project server
 * 24 September 2018 10:07
 **/
@Repository
public interface ProductTagRepository extends BaseRepository<ProductTagEntity> {


    List<ProductTagEntity> findAllByDeletedIsFalse();

    List<ProductTagEntity> findAllByIdInAndDeletedIsFalse( List<Long> tagIds );

    ProductTagEntity findFirstByIdAndDeletedIsFalse(long tagId);


    List<ProductTagEntity> findAllByProductIdAndDeletedIsFalse(long id);
}
