package com.behrouz.server.repository.ticket;

import com.behrouz.server.model.ticket.TicketImportanceEntity;
import com.behrouz.server.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketImportanceRepository extends BaseRepository<TicketImportanceEntity> {


    List<TicketImportanceEntity> findAllByNameLikeAndDeletedIsFalse(String name );

}
