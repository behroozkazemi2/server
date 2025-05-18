package com.behrouz.server.niazPardazandeh;

import com.behrouz.server.values.NiazPardazandehSmsValues;

public class SmsData {
    private String userName;
    private String password;
    private String fromNumber;
    private String toNumbers;
    private String messageContent;
    private boolean isFlash;
    private int sendDelay;

    public SmsData() {
        this.userName = NiazPardazandehSmsValues.USER_NAME;
        this.password = NiazPardazandehSmsValues.PASSWORD;
        this.fromNumber = NiazPardazandehSmsValues.FROM_NUMBER;
        this.isFlash = NiazPardazandehSmsValues.FLASH;
        this.sendDelay = NiazPardazandehSmsValues.SEND_DELAY;
    }

    public SmsData(String userName, String password, String fromNumber, boolean isFlash, int sendDelay) {
        this.userName = userName;
        this.password = password;
        this.fromNumber = fromNumber;
        this.isFlash = isFlash;
        this.sendDelay = sendDelay;
    }

    public SmsData(String userName, String password, String fromNumber, String toNumbers, String messageContent, boolean isFlash, int sendDelay) {
        this.userName = userName;
        this.password = password;
        this.fromNumber = fromNumber;
        this.toNumbers = toNumbers;
        this.messageContent = messageContent;
        this.isFlash = isFlash;
        this.sendDelay = sendDelay;
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getFromNumber() {
        return fromNumber;
    }
    public void setFromNumber(String fromNumber) {
        this.fromNumber = fromNumber;
    }

    public String getToNumbers() {
        return toNumbers;
    }
    public void setToNumbers(String toNumbers) {
        this.toNumbers = toNumbers;
    }

    public String getMessageContent() {
        return messageContent;
    }
    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public boolean isFlash() {
        return isFlash;
    }
    public void setFlash(boolean flash) {
        isFlash = flash;
    }

    public int getSendDelay() {
        return sendDelay;
    }
    public void setSendDelay(int sendDelay) {
        this.sendDelay = sendDelay;
    }

    @Override
    public String toString() {
        return "SmsData{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", fromNumber='" + fromNumber + '\'' +
                ", toNumbers='" + toNumbers + '\'' +
                ", messageContent='" + messageContent + '\'' +
                ", isFlash=" + isFlash +
                ", sendDelay=" + sendDelay +
                '}';
    }


    // Getters and setters
}