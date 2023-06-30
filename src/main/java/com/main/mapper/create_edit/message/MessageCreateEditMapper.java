package com.main.mapper.create_edit.message;

import com.main.dto.create_edit.message.MessageCreateEditDto;

import com.main.entity.chat.Chat;

import com.main.entity.general.MessageRecipient;
import com.main.entity.message.Message;
import com.main.entity.message.MessageType;
import com.main.entity.user.User;

import com.main.mapper.Mapper;
import com.main.mapper.create_edit.general.MessageRecipientCreateEditMapper;

import com.main.repository.chat.ChatRepository;
import com.main.repository.general.MessageRecipientRepository;
import com.main.repository.message.MessageTypeRepository;
import com.main.repository.user.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.awt.desktop.SystemEventListener;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MessageCreateEditMapper implements Mapper<MessageCreateEditDto, Message>  {

    private final UserRepository userRepository;
    private final ChatRepository chatRepository;
    private final MessageTypeRepository messageTypeRepository;
    private final MessageRecipientRepository messageRecipientRepository;

    private final MessageRecipientCreateEditMapper messageRecipientCreateEditMapper;


    // Метод для создания объекта
    @Override
    public Message map(MessageCreateEditDto object) {
        Message message = new Message();

        copy(object, message);

        return message;
    }

    // Метод для редактирования объекта
    @Override
    public Message map(MessageCreateEditDto fromObject, Message toObject) {
        copy(fromObject, toObject);

        return toObject;
    }


    public void copy(MessageCreateEditDto object, Message message) {

        message.setContent(object.getContent());
        message.setCreator(getCreatorMessage(object.getCreatorId()));
        message.setType(getMessageType(object.getType()));
        message.setChat(getChat(object.getChatId()));
        message.setExtension(object.getExtension());

        if(object.getCreateTime() != null) {
            message.setCreateTime(object.getCreateTime());
        }

        else {
            message.setCreateTime(Instant.ofEpochMilli(System.currentTimeMillis()));
        }

//        if (object.getId() != null) {
//            message.setRecipients(getMessageRecipients(object.getRecipients()));
//        }

    }

    // Автор сообщения
    public User getCreatorMessage(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    // Тип сообщения
    public MessageType getMessageType(String type) {
        return messageTypeRepository.findByName(type);
    }


    // Чат сообщения
    public Chat getChat(Long chatId) {
        return chatRepository.findById(chatId).orElse(null);
    }


    // Получить всех получателей сообщения через [id]
//    public List<MessageRecipient> getMessageRecipients(List<Long> recipients) {
//
//        List<MessageRecipient> messageRecipientsList = new ArrayList<>();
//
//        recipients.forEach(recipientId -> {
//
//            messageRecipientsList.add(
//                    messageRecipientRepository.findById(recipientId).orElse(null)
//            );
//
//        });
//
//        return messageRecipientsList;
//
//    }

}
