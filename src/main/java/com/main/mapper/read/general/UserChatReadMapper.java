package com.main.mapper.read.general;

import com.main.dto.read.chat.ChatReadDto;
import com.main.dto.read.general.UserChatReadDto;
import com.main.dto.read.message.MessageReadDto;
import com.main.dto.read.user.UserReadDto;
import com.main.entity.chat.Chat;
import com.main.entity.general.UserChat;
import com.main.entity.user.User;
import com.main.mapper.Mapper;
import com.main.mapper.read.chat.ChatReadMapper;
import com.main.mapper.read.message.MessageReadMapper;
import com.main.mapper.read.user.UserReadMapper;

import com.main.repository.chat.ChatRepository;
import com.main.repository.user.UserRepository;
import com.main.repository.message.MessageRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserChatReadMapper implements Mapper<UserChat, UserChatReadDto> {

//    private final UserReadMapper userReadMapper;
//    private final UserRepository userRepository;

//    private final ChatReadMapper chatReadMapper;
//    private final ChatRepository chatRepository;



    // Метод сработает после создания объекта UserChat
    @Override
    public UserChatReadDto map(UserChat object) {
//        UserReadDto user = Optional.ofNullable(object.getUser())
//                .map(userReadMapper::map)
//                .orElse(null);
//
//        ChatReadDto chat = Optional.ofNullable(object.getChat())
//                .map(chatReadMapper::map)
//                .orElse(null);

        return new UserChatReadDto(
                object.getId(),
                object.getUser().getId(),
                object.getChat().getId()
        );

    }



    // Метод сработает во время редактирования объекта UserChat
    @Override
    public UserChatReadDto map(UserChat fromObject, UserChatReadDto toObject) {
        return Mapper.super.map(fromObject, toObject);
    }

}
