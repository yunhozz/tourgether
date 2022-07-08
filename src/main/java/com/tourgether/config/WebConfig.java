package com.tourgether.config;

import com.tourgether.domain.chat.service.ChatHandler;
import com.tourgether.ui.login.LoginArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import java.util.List;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer, WebSocketConfigurer {

    private final LoginArgumentResolver loginArgumentResolver;
    private final ChatHandler chatHandler;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginArgumentResolver);
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chatHandler, "/chat").setAllowedOriginPatterns("*").withSockJS();
    }
}
