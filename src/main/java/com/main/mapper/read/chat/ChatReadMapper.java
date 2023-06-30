package com.main.mapper.read.chat;

import com.main.dto.read.chat.ChatReadDto;
import com.main.dto.read.general.MessageRecipientReadDto;
import com.main.dto.read.general.UserChatReadDto;
import com.main.dto.read.message.MessageReadDto;
import com.main.dto.read.user.UserReadDto;
import com.main.entity.chat.Chat;
import com.main.entity.chat.ChatType;
import com.main.entity.general.UserChat;
import com.main.entity.message.Message;
import com.main.entity.user.User;
import com.main.mapper.Mapper;
import com.main.mapper.read.general.MessageRecipientReadMapper;
import com.main.mapper.read.general.UserChatReadMapper;
import com.main.mapper.read.message.MessageReadMapper;
import com.main.mapper.read.user.UserReadMapper;
import com.main.repository.chat.ChatTypeRepository;
import com.main.repository.general.UserChatRepository;
import com.main.repository.message.MessageRepository;
import com.main.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class ChatReadMapper implements Mapper<Chat, ChatReadDto>  {

    private final UserReadMapper userReadMapper;

    private final MessageReadMapper messageReadMapper;

    private final MessageRepository messageRepository;
    private final UserChatReadMapper userChatReadMapper;

    private final UserChatRepository userChatRepository;


    @Override
    public ChatReadDto map(Chat object) {

        UserReadDto creator = Optional.ofNullable(object.getCreator())
                .map(userReadMapper::map)
                .orElse(null);

        // Пользователи одного и того же чата
//        List<UserReadDto> users = new ArrayList<>();
//
//        object.getUserChats().forEach((userChat) -> {
//
//            if(object.getId() == userChat.getChat().getId()) {
//
//                UserReadDto user = Optional.ofNullable(userChat.getUser())
//                        .map(userReadMapper::map)
//                        .orElse(null);
//
//                users.add(user);
//            }
//
//        });

        // Пользователи одного и того же чата
        List<UserChatReadDto> userChats = userChatRepository.findAllByChatId(object.getId())
                                            .stream()
                                            .map(userChatReadMapper::map)
                                            .toList();

//        object.getUserChats().forEach((userChat) -> {
//
//            if(object.getId() == userChat.getChat().getId()) {
//
//                Optional<UserReadDto> user = userRepository.findById(userChat.getUser().getId())
//                                                            .map(userReadMapper::map);
//
//                userChats.add(
//                    user.get()
//                );
//            }
//
//        });


        // Сообщения одного и того же чата
//        List<MessageReadDto> messages = new ArrayList<>();

//        // Соберем Сообщения одного и того же чата
//        object.getMessages().forEach((msg) -> {
//
//            MessageReadDto messageReadDto = Optional.ofNullable(msg)
//                    .map(messageReadMapper::map)
//                    .orElse(null);
//
//            messages.add(messageReadDto);
//        });

        List<MessageReadDto> messages = messageRepository.findAllByChatId(object.getId())
                .stream()
                .map(messageReadMapper::map)
                .toList();

        return new ChatReadDto(
                object.getId(),
                object.getName(),
                object.getCreateTime(),
                creator,
                object.getType(),
                userChats,
//                users,
                messages
        );
    }


}
