package com.behrouz.server.repository;

import com.behrouz.server.model.ApplicationVersionEntity;
import com.behrouz.server.repository.base.BaseNameRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.repository
 * Project server
 * 22 September 2018 09:38
 **/
@Repository
public interface ApplicationVersionRepository extends BaseNameRepository<ApplicationVersionEntity> {
    ApplicationVersionEntity findFirstByIdAndDeletedIsFalse ( long versionId );
}
