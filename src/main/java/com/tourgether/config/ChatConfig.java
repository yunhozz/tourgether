package com.tourgether.config;

import com.tourgether.domain.chat.service.ChatHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocket
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class ChatConfig implements WebSocketConfigurer, WebSocketMessageBrokerConfigurer {

    private final ChatHandler chatHandler;

    /**
     * 웹소켓을 이용한 채팅
     */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chatHandler, "/ws/chat").setAllowedOriginPatterns("*").withSockJS();
    }

    /**
     * STOMP 를 이용한 채팅
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/stomp-chat").setAllowedOriginPatterns("*").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/publish"); // queue : 1대 1, topic : 1대 다
        registry.enableSimpleBroker("/subscribe");
    }
}
