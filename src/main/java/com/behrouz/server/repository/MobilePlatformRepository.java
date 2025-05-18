package com.behrouz.server.repository;

import com.behrouz.server.model.MobilePlatformEntity;
import com.behrouz.server.repository.base.BaseNameRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.repository
 * Project server
 * 22 September 2018 09:45
 **/

@Repository
public interface MobilePlatformRepository extends BaseNameRepository<MobilePlatformEntity> {


    MobilePlatformEntity findFirstByIdAndDeletedIsFalse( long platformId );
}
