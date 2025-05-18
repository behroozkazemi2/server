package com.behrouz.server.repository.ticket;


import com.behrouz.server.model.ticket.TicketEntity;
import com.behrouz.server.repository.base.BaseRepository;

public interface TicketRepository extends BaseRepository<TicketEntity> {
    TicketEntity findFirstByTrackingCodeAndDeletedIsFalse(String trackingCode);
}
