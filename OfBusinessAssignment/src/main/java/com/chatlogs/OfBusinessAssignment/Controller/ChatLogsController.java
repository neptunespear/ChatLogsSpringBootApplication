package com.chatlogs.OfBusinessAssignment.Controller;

import com.chatlogs.OfBusinessAssignment.Entities.ChatLog;
import com.chatlogs.OfBusinessAssignment.Entities.ChatUserMessageIdPair;
import com.chatlogs.OfBusinessAssignment.Entities.ChatUserPair;
import com.chatlogs.OfBusinessAssignment.Services.ChatLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class ChatLogsController {

    @Autowired
    private ChatLogService chatLogService;

    //Post All chats by User ID
    @PostMapping("/chatlogs/{userId}")
    public ResponseEntity<ChatUserMessageIdPair> addUserChats(@RequestBody ChatLog userChat, @PathVariable("userId") String userId) {
        ChatUserPair pair = this.chatLogService.addChatsToUser(userId, userChat);
        System.out.println(pair);
        ChatUserMessageIdPair messageIdPair = this.chatLogService.createMap(pair.userId, pair.chatLog);
        return ResponseEntity.of(Optional.of(messageIdPair));
    }

    //Get All chats by UserId
    @GetMapping("/chatlogs/{userId}")
    public ResponseEntity<List<ChatUserMessageIdPair>> getChatsByuserId(
            @PathVariable("userId") String userId,
            @RequestParam(value = "limit", required = false, defaultValue = "10") int limit,
            @RequestParam(value = "pageId", required = false, defaultValue = "") String pageId){
        List<ChatUserMessageIdPair> userChatList = this.chatLogService.getChatsByUserIdinMap(userId);
        if(userChatList.size()<=0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        this.chatLogService.sortChatsTimeMap(userChatList);
        List<ChatUserMessageIdPair> userChatLimitlist = this.chatLogService.limitChatsSize(userChatList, limit);
        List<ChatUserMessageIdPair> pageIdSearchList = this.chatLogService.paginateOnMessageId(userChatList, pageId);

        if(pageId.length()!=0){
            return ResponseEntity.of(Optional.of(pageIdSearchList));
        }

        return ResponseEntity.of(Optional.of(userChatLimitlist));
    }

    //Get All Chatlogs
    @GetMapping("/chatlogs")
    public Map<String, List<ChatUserMessageIdPair>> getAllChats(){
        return this.chatLogService.getAllChatsMap();
    }

    //Delete all chats by UserId
    @DeleteMapping("/chatlogs/{userId}")
    public ResponseEntity<Void> deleteChatsOfuser(@PathVariable("userId") String userId){
        try {
            this.chatLogService.deleteUserChats(userId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //Delete chatlogs with specific userId and messageId
    @DeleteMapping("/chatlogs/{userId}/{messageId}")
    public ResponseEntity<Void> deleteChatsWithMessageId(@PathVariable("userId") String userId,
                                                         @PathVariable("messageId") String messageId) {
        try{
            this.chatLogService.deleteChatsWithMessageId(userId, messageId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
