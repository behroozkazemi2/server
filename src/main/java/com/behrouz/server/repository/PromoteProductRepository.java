package com.behrouz.server.repository;

import com.behrouz.server.model.product.PromoteProductEntity;
import com.behrouz.server.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.repository
 * Project server
 **/
@Repository
public interface PromoteProductRepository extends BaseRepository<PromoteProductEntity> {

    PromoteProductEntity findFirstByIdAndDeletedIsFalse(long id);

    List<PromoteProductEntity> findAllByPromote_IdAndProductProvider_DeletedIsFalse(long promoteId);

    List<PromoteProductEntity> findAllByPromote_IdAndProductProvider_DeletedIsFalseAndDeletedIsFalse(long promoteId);
}
