package com.main.mapper.read.general;

import com.main.dto.read.chat.ChatReadDto;
import com.main.dto.read.general.MessageRecipientReadDto;
import com.main.dto.read.message.MessageReadDto;
import com.main.dto.read.user.UserReadDto;
import com.main.entity.general.MessageRecipient;
import com.main.mapper.Mapper;
import com.main.mapper.read.chat.ChatReadMapper;
import com.main.mapper.read.message.MessageReadMapper;
import com.main.mapper.read.user.UserReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MessageRecipientReadMapper implements Mapper<MessageRecipient, MessageRecipientReadDto> {

//    private final UserReadMapper userReadMapper;
//
//    private final MessageReadMapper messageReadMapper;

    @Override
    public MessageRecipientReadDto map(MessageRecipient object) {

//        UserReadDto user = Optional.ofNullable(object.getUser())
//                .map(userReadMapper::map)
//                .orElse(null);
//
//        MessageReadDto message = Optional.ofNullable(object.getMessage())
//                .map(messageReadMapper::map)
//                .orElse(null);

//        return new MessageRecipientReadDto(
//                object.getId(),
//                message.getId(),
//                user.getId(),
//                object.isRead(),
//                object.getReadingTime()
//        );


        return new MessageRecipientReadDto(
          object.getId(),
          object.getMessage().getId(),
          object.getUser().getId(),
          object.isRead(),
          object.getReadingTime()
        );
    }
}
