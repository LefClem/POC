package com.example.chat.handler;

import com.example.chat.dto.ChatMessage;
import com.example.chat.model.Conversation;
import com.example.chat.repository.ConversationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {
    private final Set<WebSocketSession> sessions = Collections.synchronizedSet(new HashSet<>());
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private ConversationRepository conversationRepository;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
        System.out.println("[WebSocket] Connexion établie : " + session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session);
        System.out.println("[WebSocket] Connexion fermée : " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        System.out.println("[WebSocket] Message reçu : " + message.getPayload());
        try {
            ChatMessage chatMessage = objectMapper.readValue(message.getPayload(), ChatMessage.class);
            System.out.println("[WebSocket] Message parsé : " + chatMessage);

            Conversation conversation = new Conversation();
            conversation.setContent(chatMessage.getContent());
            conversation.setSendingDate(LocalDateTime.now());
            conversation.setType(chatMessage.getType());
            conversation.setStatus(chatMessage.getStatus());
            conversation.setUserId(chatMessage.getUserId());

            conversationRepository.save(conversation);
            System.out.println("[WebSocket] Message sauvegardé en BDD");

            String broadcast = objectMapper.writeValueAsString(chatMessage);
            synchronized (sessions) {
                for (WebSocketSession ws : sessions) {
                    if (ws.isOpen()) {
                        ws.sendMessage(new TextMessage(broadcast));
                    }
                }
            }
            System.out.println("[WebSocket] Message diffusé à tous les clients");

        } catch (InvalidFormatException e) {
            String error = "{\"error\":\"Invalid enum value in message. Use type: CLIENT/SUPPORT, status: SENT/RECEIVED\"}";
            session.sendMessage(new TextMessage(error));
            System.out.println("[WebSocket][Erreur] Enum invalide : " + e.getMessage());
        } catch (Exception e) {
            String error = "{\"error\":\"Message processing error: " + e.getMessage().replace("\"", "'") + "\"}";
            session.sendMessage(new TextMessage(error));
            System.out.println("[WebSocket][Erreur] Traitement message : " + e.getMessage());
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.out.println("[WebSocket][Erreur de transport] Session: " + session.getId() + ", Exception: "
                + exception.getMessage());
        exception.printStackTrace();
    }
}
