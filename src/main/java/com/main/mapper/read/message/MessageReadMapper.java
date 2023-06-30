package com.main.mapper.read.message;


import com.main.dto.read.general.MessageRecipientReadDto;
import com.main.dto.read.message.MessageReadDto;
import com.main.dto.read.user.UserReadDto;
import com.main.entity.message.Message;
import com.main.mapper.Mapper;
import com.main.mapper.read.general.MessageRecipientReadMapper;
import com.main.mapper.read.user.UserReadMapper;
import com.main.repository.general.MessageRecipientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MessageReadMapper implements Mapper<Message, MessageReadDto> {

    private final UserReadMapper userReadMapper;
    private final MessageRecipientReadMapper messageRecipientReadMapper;
    private final MessageRecipientRepository messageRecipientRepository;



    @Override
    public MessageReadDto map(Message object) {

        UserReadDto user = Optional.ofNullable(object.getCreator())
                .map(userReadMapper::map)
                .orElse(null);


        // Пользователи одного и того же чата
        List<MessageRecipientReadDto> messageRecipients = messageRecipientRepository.findAllByMessageId(object.getId())
                .stream()
                .map(messageRecipientReadMapper::map)
                .toList();

        return new MessageReadDto(
                object.getId(),
                object.getContent(),
                object.getCreateTime(),
                user,
                object.getType().getName(),
                object.getChat().getId(),
                messageRecipients,
                object.getExtension()
        );
    }


}
