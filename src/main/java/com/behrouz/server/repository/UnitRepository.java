package com.behrouz.server.repository;

import com.behrouz.server.model.global.UnitEntity;
import com.behrouz.server.repository.base.BaseNameRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.repository
 * Project server
 * 16 September 2018 11:57
 **/
@Repository
public interface UnitRepository extends BaseNameRepository<UnitEntity> {

    List<UnitEntity> findAllByIdInAndDeletedIsFalse( List<Long> productUnitIds );

    List<UnitEntity> findAllByDeletedIsFalse();

    UnitEntity findFirstByIdAndDeletedIsFalse(long id);
}
