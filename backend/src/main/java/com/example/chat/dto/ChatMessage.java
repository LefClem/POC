package com.example.chat.dto;

import com.example.chat.model.ConversationType;
import com.example.chat.model.ConversationStatus;

public class ChatMessage {
    private String content;
    private ConversationType type;
    private ConversationStatus status;
    private Integer userId;

    // Constructeur par défaut
    public ChatMessage() {
    }

    // Getters
    public String getContent() {
        return content;
    }

    public ConversationType getType() {
        return type;
    }

    public ConversationStatus getStatus() {
        return status;
    }

    public Integer getUserId() {
        return userId;
    }

    // Setters
    public void setContent(String content) {
        this.content = content;
    }

    public void setType(ConversationType type) {
        this.type = type;
    }

    public void setStatus(ConversationStatus status) {
        this.status = status;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "content='" + content + '\'' +
                ", type=" + type +
                ", status=" + status +
                ", userId=" + userId +
                '}';
    }
}
