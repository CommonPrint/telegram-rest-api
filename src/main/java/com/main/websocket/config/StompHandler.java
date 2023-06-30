package com.main.websocket.config;

import com.main.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import java.util.Objects;

//import org.springframework.integration.config.GlobalChannelInterceptor;

@Component
@RequiredArgsConstructor
public class StompHandler implements ChannelInterceptor {
    private final JwtUtils jwtTokenProvider;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        System.out.println("Channel: " + channel.toString());
        System.out.println("message: " + message);
        System.out.println("헤더 : " + message.getHeaders());

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            System.out.println("Validate connect true? " + jwtTokenProvider.validateJwtToken(Objects.requireNonNull(accessor.getFirstNativeHeader("Authorization")).substring(7)));
        }

        return message;
    }
}