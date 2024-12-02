package com.ssafy.chat.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.chat.chatservice.ChatService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
//import 생략....

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSockChatHandler extends TextWebSocketHandler {

    private final ChatService simpleService; // SimpleService 주입

    // 연결 성공 시 호출
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("WebSocket 연결 성공: {}", session.getId());
        
        // 클라이언트에 초기 메시지 전송
        String welcomeMessage = "무엇을 도와드릴까요?";
        session.sendMessage(new TextMessage(welcomeMessage));
    }
    
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload {}", payload);  // 요청 받은 메시지 로깅

        // SimpleService의 getResponse 메서드 호출
        String responseMessage = simpleService.getResponse(payload);

        // 응답 메시지를 TextMessage로 변환하여 클라이언트에 전송
        TextMessage textMessage = new TextMessage(responseMessage);
        session.sendMessage(textMessage);
    }
}