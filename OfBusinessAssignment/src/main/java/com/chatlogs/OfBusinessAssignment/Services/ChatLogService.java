package com.chatlogs.OfBusinessAssignment.Services;

import com.chatlogs.OfBusinessAssignment.Entities.ChatLog;
import com.chatlogs.OfBusinessAssignment.Entities.ChatUserMessageIdPair;
import com.chatlogs.OfBusinessAssignment.Entities.ChatUserPair;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class ChatLogService {

    public List<ChatUserMessageIdPair> getChatsByUserIdinMap(String id) {
        List<ChatUserMessageIdPair> matchingList = chatMessageIdMap.get(id);
        return matchingList;
    }

    public void sortChatsTimeMap(List<ChatUserMessageIdPair> userChats) {
        Collections.sort(userChats, new Comparator<ChatUserMessageIdPair>() {
            @Override
            public int compare( ChatUserMessageIdPair o1, ChatUserMessageIdPair o2) {
                return (int) (o1.chatLog.getTimestamp() - o2.chatLog.getTimestamp());
            }
        });
    }

    private static Map<String, List<ChatLog>> userToChatMap = new HashMap<>();

    public ChatUserPair addChatsToUser(String userId, ChatLog chat) {
        userToChatMap.putIfAbsent(userId, new ArrayList<>());
        userToChatMap.get(userId).add(chat);

        return new ChatUserPair(userId, chat);
    }

    public void deleteUserChats(String userId) {
        chatMessageIdMap.get(userId).clear();
        chatMessageIdMap.put(userId, new ArrayList<>());
    }

    public Map<String, List<ChatUserMessageIdPair>> getAllChatsMap() {
        return chatMessageIdMap;
    }

    private static Map<String, List<ChatUserMessageIdPair>> chatMessageIdMap = new HashMap<>();

    public ChatUserMessageIdPair createMap(String userId, ChatLog chatLog) {
        chatLog.setSent(true);
        if(chatLog.getTimestamp()==0) {
            chatLog.setTimestamp(getEpochtimeSeconds());
        }
        String uniqueMessageId = userId + String.valueOf(chatLog.getTimestamp());
        chatMessageIdMap.putIfAbsent(userId, new ArrayList<>());

        ChatUserMessageIdPair msggPair = new ChatUserMessageIdPair(chatLog, uniqueMessageId);
        chatMessageIdMap.get(userId).add(msggPair);
        return msggPair;
    }

    public void deleteChatsWithMessageId(String userId, String messageId) {
        List<ChatUserMessageIdPair> chatmssgList = chatMessageIdMap.get(userId);
        for(int index = 0; index<chatmssgList.size(); index++) {
            if (chatmssgList.get(index).messageId.equals(messageId)) {
                chatmssgList.remove(chatmssgList.get(index));
            }
        }
    }

    public long getEpochtimeSeconds(){
        LocalDateTime localDateTime = LocalDateTime.now();

        // Convert LocalDateTime to Instant
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();

        // Get the number of seconds since the Unix epoch
        return instant.getEpochSecond();
    }

    public List<ChatUserMessageIdPair> limitChatsSize(List<ChatUserMessageIdPair> userChatList, int limit) {
        return userChatList.stream().limit(limit).collect(Collectors.toList());
    }

    public List<ChatUserMessageIdPair> paginateOnMessageId(List<ChatUserMessageIdPair> userChatList, String pageId) {
        List<ChatUserMessageIdPair> pageIdSearchList = new ArrayList<>();
        for(int index = 0; index<userChatList.size(); index++) {
            String msggId = userChatList.get(index).messageId;
            if(isStringStartsWith(msggId, pageId)) {
                pageIdSearchList.add(userChatList.get(index));
            }
        }
        return pageIdSearchList;
    }

    public boolean isStringStartsWith(String mainString, String searchString) {
        char[] mainChars = mainString.toCharArray();
        char[] searchChars = searchString.toCharArray();

        if (mainChars.length < searchChars.length) {
            return false;
        }

        return IntStream.range(0, searchChars.length)
                .allMatch(i -> mainChars[i] == searchChars[i]);
    }

}
