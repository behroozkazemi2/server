package com.behrouz.server.repository.global;

import com.behrouz.server.model.global.RegionEntity;
import com.behrouz.server.repository.base.BaseNameRepository;

import java.util.List;

public interface RegionRepository extends BaseNameRepository<RegionEntity> {

    RegionEntity findFirstByIdAndDeletedIsFalse(long id);


    List<RegionEntity> findAllByIdInAndDeletedIsFalse(List<Long> regionIds);

    List<RegionEntity> findAllByDeletedIsFalse();
}
