package com.behrouz.server.rest.response;


import com.behrouz.server.rest.request.IdName;
import com.behrouz.server.model.ticket.TicketEntity;
import com.behrouz.server.utils.PersianNumberText;
import com.behrouz.server.utils.date.PersianDateUtil;

public class TicketDetailRestResponse {

    private String trackingCode;
    private long ticketId;
    private String projectName;
    private String ticketSubject;
    private String insertDateStr;
    private long accountId;
    private boolean closed;
    private IdName importance;
    private boolean read;
    private String lastTicketMessageDateStr;



    public TicketDetailRestResponse(String trackingCode, long ticketId, String projectName, String ticketSubject, String insertDateStr, boolean closed) {
        this.trackingCode = trackingCode;
        this.ticketId = ticketId;
        this.projectName = projectName;
        this.ticketSubject = ticketSubject;
        this.insertDateStr = insertDateStr;
        this.closed = closed;
    }

    ;


    public TicketDetailRestResponse(String trackingCode, long ticketId, String projectName, String ticketSubject, String insertDateStr, boolean closed, IdName importance) {
        this.trackingCode = trackingCode;
        this.ticketId = ticketId;
        this.projectName = projectName;
        this.ticketSubject = ticketSubject;
        this.insertDateStr = insertDateStr;
        this.closed = closed;
        this.importance = importance;
    }


    public TicketDetailRestResponse(String trackingCode, long ticketId, String projectName, String ticketSubject, String insertDateStr, boolean closed, IdName importance, boolean read, String  lastTicketMessageDateStr)  {
        this.trackingCode = trackingCode;
        this.ticketId = ticketId;
        this.projectName = projectName;
        this.ticketSubject = ticketSubject;
        this.insertDateStr = insertDateStr;
        this.closed = closed;
        this.importance = importance;
        this.read = read;
        this.lastTicketMessageDateStr = lastTicketMessageDateStr;
    }

    ;


    public TicketDetailRestResponse(String projectName, String ticketSubject, String insertDateStr, long accountId, long ticketId) {
        this.projectName = projectName;
        this.ticketSubject = ticketSubject;
        this.insertDateStr = insertDateStr;
        this.accountId = accountId;
        this.ticketId = ticketId;
    }

    public TicketDetailRestResponse(TicketEntity ticket) {
        this(
                ticket.getTrackingCode(),
                ticket.getId(),
                "",
                ticket.getSubject(),
                PersianNumberText.toPersianNumber(PersianDateUtil.getPersianDate(ticket.getInsertDate())),
                ticket.isClosed(),
                new IdName(ticket.getTicketImportance().getId(), ticket.getTicketImportance().getName())
        );

        this.accountId = ticket.getAccount().getId();
    }

    public TicketDetailRestResponse() {
    }

    public long getTicketId() {
        return ticketId;
    }

    public void setTicketId(long ticketId) {
        this.ticketId = ticketId;
    }


    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }


    public String getTicketSubject() {
        return ticketSubject;
    }

    public void setTicketSubject(String ticketSubject) {
        this.ticketSubject = ticketSubject;
    }


    public String getInsertDateStr() {
        return insertDateStr;
    }

    public void setInsertDateStr(String insertDateStr) {
        this.insertDateStr = insertDateStr;
    }


    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public String getTrackingCode() {
        return trackingCode;
    }

    public void setTrackingCode(String trackingCode) {
        this.trackingCode = trackingCode;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }


    public IdName getImportance() {
        return importance;
    }

    public void setImportance(IdName importance) {
        this.importance = importance;
    }

    public boolean isRead() {
        return read;
    }
    public void setRead(boolean read) {
        this.read = read;
    }

    public String getLastTicketMessageDateStr() {
        return lastTicketMessageDateStr;
    }
    public void setLastTicketMessageDateStr(String lastTicketMessageDateStr) {
        this.lastTicketMessageDateStr = lastTicketMessageDateStr;
    }
}