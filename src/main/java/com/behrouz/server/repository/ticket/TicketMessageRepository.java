package com.behrouz.server.repository.ticket;


import com.behrouz.server.model.ticket.TicketMessageEntity;
import com.behrouz.server.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketMessageRepository extends BaseRepository<TicketMessageEntity> {

    List<TicketMessageEntity> findAllByTicket_IdInAndDeletedIsFalse(List<Long> ticketId);



}
