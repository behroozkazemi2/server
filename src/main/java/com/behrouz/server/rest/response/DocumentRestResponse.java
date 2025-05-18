package com.behrouz.server.rest.response;


import com.behrouz.server.model.Document.DocumentEntity;

public class DocumentRestResponse {

    private String name;
    private String path;
    private long type;
    private long id;
    private long size;

    public DocumentRestResponse(DocumentEntity doc) {
        this.id = doc.getId();
        this.name = doc.getName();
        this.path = doc.getPath();
        this.type = doc.getType();
        this.size = doc.getSize();
    }
    public DocumentRestResponse(String name, String path, long id) {
        this.name = name;
        this.path = path;
        this.id = id;
    }

    public String getName() {
        return name;
    }
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

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public long getSize() {
        return size;
    }
    public void setSize(long size) {
        this.size = size;
    }
}
