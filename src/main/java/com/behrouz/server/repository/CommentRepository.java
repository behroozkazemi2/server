package com.behrouz.server.repository;

import com.behrouz.server.model.CommentEntity;
import com.behrouz.server.repository.base.BaseRepository;

import java.util.List;

/**
 * Created by: Reza
 * Company: reza
 * Package: com.behrouz.server.repository
 * Project Name: behta-server
 * 13 December 2018
 **/
public interface CommentRepository extends BaseRepository<CommentEntity> {


    CommentEntity findFirstByIdAndStatus_IdAndDeletedIsFalse  ( long id, long statusId );

    List<CommentEntity> findAllByProductProvider_IdAndStatus_IdAndDeletedIsFalse  ( long productProviderId, long statusId );

}
