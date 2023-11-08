package com.chatlogs.OfBusinessAssignment.Entities;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ChatUserPair {
    @Size(max = 15, message = "User ID must be less than 16 characters")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "User ID must be alphanumeric")
    public String userId;
    public ChatLog chatLog;

    public ChatUserPair(String userId, ChatLog chatLog){
        this.userId = userId;
        this.chatLog = chatLog;
    }
}
