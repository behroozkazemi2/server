package com.behrouz.server.api.customer.response;

import com.behrouz.server.model.MessageEntity;
import com.behrouz.server.utils.LocalDateTimeUtil;

import java.util.Date;

/**
 * Created by: Reza
 * Company: reza
 * Package: com.behrouz.server.api.customer.response
 * Project Name: behta-server
 * 19 January 2019
 **/
public class InboxMessageResponse {

    private long messageId;

    private String subject;

    private String content;

    private Date insertDate;

    private boolean read;


    public InboxMessageResponse ( MessageEntity message ) {

        this.messageId = message.getId();
        this.subject = message.getSubject();
        this.content = message.getContent();
        this.insertDate = LocalDateTimeUtil.localDateTimeToDate(message.getInsertDate());
        this.read = message.isRead();

    }



    public long getMessageId() {
        return messageId;
    }
    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }


    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }


    public String getContent () {
        return content;
    }
    public void setContent ( String content ) {
        this.content = content;
    }


    public Date getInsertDate() {
        return insertDate;
    }
    public void setInsertDate(Date insertDate) {
        this.insertDate = insertDate;
    }


    public boolean isRead() {
        return read;
    }
    public void setRead(boolean read) {
        this.read = read;
    }

}
