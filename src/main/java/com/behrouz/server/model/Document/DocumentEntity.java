package com.behrouz.server.model.Document;


import com.behrouz.server.model.base.BaseIdNameEntity;
import com.behrouz.server.model.ticket.TicketMessageDocumentEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "document", schema = "public")
public class DocumentEntity extends BaseIdNameEntity {
    private String path;
    private long type;
    private long size;


    public DocumentEntity() {
    }

    public DocumentEntity(long type, String path, String name, long size) {
        this.type = type;
        this.path = path;
        this.name = name;
        this.size = size;
    }


    @Override
    public String getName() {
        return name;
    }
    @Override
    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }


    public long getType() {
        return type;
    }
    public void setType(long type) {
        this.type = type;
    }


    public long getSize() {
        return size;
    }
    public void setSize(long size) {
        this.size = size;
    }



    @OneToMany(mappedBy = "document")
    @JsonManagedReference
    private Set<TicketMessageDocumentEntity> ticketMessageDocumnet;




}
