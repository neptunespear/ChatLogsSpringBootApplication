package com.chatlogs.OfBusinessAssignment.Entities;

import jakarta.validation.constraints.NotBlank;
import jdk.jfr.Timestamp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.bind.DefaultValue;

public class ChatLog {

    @NotBlank(message = "Message should not be empty")
    private String message;
    @Value("time:0")
    private long timestamp;
    private boolean isSent;

    public ChatLog(String message, long timestamp, boolean isSent) {
        this.message = message;
        this.timestamp = timestamp;
        this.isSent = isSent;
    }

    @Override
    public String toString() {
        return "ChatLog{" +
                "message='" + message + '\'' +
                ", timestamp=" + timestamp +
                ", isSent=" + isSent +
                '}';
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isSent() {
        return isSent;
    }

    public void setSent(boolean sent) {
        isSent = sent;
    }


}
