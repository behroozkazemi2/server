package com.behrouz.server.repository;

import com.behrouz.server.model.CandidateTimeEntity;
import com.behrouz.server.repository.base.BaseNameRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by: HapiKZM
 * Project Name: behta-server
 * 09 July 2020
 **/

@Repository
public interface CandidateTimeRepository extends BaseNameRepository<CandidateTimeEntity> {

    CandidateTimeEntity findAllByIdAndDeletedIsFalse (long id);

    CandidateTimeEntity findFirstByIdAndDeletedIsFalse (long timeId);
}
