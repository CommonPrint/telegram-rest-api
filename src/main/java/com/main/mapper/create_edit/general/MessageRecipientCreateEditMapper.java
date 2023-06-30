package com.main.mapper.create_edit.general;

import com.main.dto.create_edit.general.MessageRecipientCreateEditDto;
import com.main.dto.create_edit.message.MessageCreateEditDto;
import com.main.entity.general.MessageRecipient;
import com.main.entity.message.Message;
import com.main.entity.user.User;
import com.main.mapper.Mapper;
import com.main.repository.general.MessageRecipientRepository;
import com.main.repository.message.MessageRepository;
import com.main.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageRecipientCreateEditMapper implements Mapper<MessageRecipientCreateEditDto, MessageRecipient>  {

    private final UserRepository userRepository;
    private final MessageRepository messageRepository;

    @Override
    public MessageRecipient map(MessageRecipientCreateEditDto object) {

        MessageRecipient messageRecipient = new MessageRecipient();

        copy(object, messageRecipient);

        return messageRecipient;

    }

    @Override
    public MessageRecipient map(MessageRecipientCreateEditDto fromObject, MessageRecipient toObject) {
        copy(fromObject, toObject);

        return toObject;
    }


    // Вставка данных
    public void copy(MessageRecipientCreateEditDto object, MessageRecipient messageRecipient) {

        messageRecipient.setRecipient(getUser(object.getRecipientId())); // setRecipient() устанавливает получателя сообщения
        messageRecipient.setMessage(getMessage(object.getMessageId()));
        messageRecipient.setReadingTime(object.getReadingTime());
        messageRecipient.setRead(object.isRead());

    }


    public User getUser(Long recipientId) {
        return userRepository.findById(recipientId).orElse(null);
    }

    public Message getMessage(Long messageId) {
        return messageRepository.findById(messageId).orElse(null);
    }


}
