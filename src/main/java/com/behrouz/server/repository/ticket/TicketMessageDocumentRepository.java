package com.behrouz.server.repository.ticket;

import com.behrouz.server.model.ticket.TicketMessageDocumentEntity;
import com.behrouz.server.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketMessageDocumentRepository extends BaseRepository<TicketMessageDocumentEntity> {

    List<TicketMessageDocumentEntity> findAllByTicketMessage_IdAndDeletedIsFalse(long ticketMsgId);
}
