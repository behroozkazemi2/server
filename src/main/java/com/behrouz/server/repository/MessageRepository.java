package com.behrouz.server.repository;

import com.behrouz.server.model.MessageEntity;
import com.behrouz.server.repository.base.BaseRepository;

import java.util.List;

/**
 * Created by: Reza
 * Company: reza
 * Package: com.behrouz.server.repository
 * Project Name: behta-server
 * 19 January 2019
 **/
public interface MessageRepository extends BaseRepository<MessageEntity> {


    List<MessageEntity> findAllByCustomer_IdAndDeletedIsFalse ( long customerId );
}
