package com.chatlogs.OfBusinessAssignment.Entities;

public class ChatUserMessageIdPair {
    public ChatLog chatLog;
    public String messageId;

    public ChatUserMessageIdPair(ChatLog chatLog, String messageId){
        this.chatLog = chatLog;
        this.messageId = messageId;
    }
}
