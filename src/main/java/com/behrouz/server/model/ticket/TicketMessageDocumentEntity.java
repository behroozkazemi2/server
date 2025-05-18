package com.behrouz.server.model.ticket;


import com.behrouz.server.model.base.BaseEntity;
import com.behrouz.server.model.Document.DocumentEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ticket_message_document", schema = "public")
public class TicketMessageDocumentEntity extends BaseEntity {


    private TicketMessageEntity ticketMessage;

    private DocumentEntity document;


    public TicketMessageDocumentEntity() {
    }

    public TicketMessageDocumentEntity(TicketMessageEntity ticketMessage, DocumentEntity document) {
        this.ticketMessage = ticketMessage;
        this.document = document;
    }

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "ticket_message_id", nullable = false)
    public TicketMessageEntity getTicketMessage() {
        return ticketMessage;
    }
    public void setTicketMessage(TicketMessageEntity ticketMessage) {
        this.ticketMessage = ticketMessage;
    }



    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "document_id", nullable = false)
    public DocumentEntity getDocument() {
        return document;
    }
    public void setDocument(DocumentEntity document) {
        this.document = document;
    }
}
