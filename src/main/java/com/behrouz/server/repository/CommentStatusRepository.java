package com.behrouz.server.repository;

import com.behrouz.server.model.CommentStatusEntity;
import com.behrouz.server.repository.base.BaseNameRepository;

/**
 * Created by: Reza
 * Company: reza
 * Package: com.behrouz.server.repository
 * Project Name: behta-server
 * 13 December 2018
 **/
public interface CommentStatusRepository extends BaseNameRepository<CommentStatusEntity> {

    CommentStatusEntity findFirstByIdAndDeletedIsFalse ( long id );

}
