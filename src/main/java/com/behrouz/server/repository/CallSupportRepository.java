package com.behrouz.server.repository;

import com.behrouz.server.model.CallSupportEntity;
import com.behrouz.server.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by: Reza
 * Company: reza
 * Package: com.behrouz.server.repository
 * Project Name: behta-server
 * 10 December 2018
 **/
public interface CallSupportRepository extends BaseRepository<CallSupportEntity>, JpaSpecificationExecutor<CallSupportEntity> {

    CallSupportEntity findFirstByIdAndDeletedIsFalse( int id );
}
