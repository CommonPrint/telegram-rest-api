package com.main.service.message;


import com.main.dto.create_edit.general.MessageRecipientCreateEditDto;
import com.main.dto.create_edit.message.MessageCreateEditDto;
import com.main.dto.create_edit.user.UserCreateEditDto;
import com.main.dto.read.general.MessageRecipientReadDto;
import com.main.dto.read.general.UserChatReadDto;
import com.main.dto.read.message.MessageReadDto;
import com.main.dto.read.user.UserReadDto;
import com.main.entity.general.MessageRecipient;
import com.main.entity.user.User;
import com.main.mapper.create_edit.general.MessageRecipientCreateEditMapper;
import com.main.mapper.create_edit.message.MessageCreateEditMapper;
import com.main.mapper.create_edit.user.UserCreateEditMapper;
import com.main.mapper.read.general.MessageRecipientReadMapper;
import com.main.mapper.read.message.MessageReadMapper;
import com.main.mapper.read.user.UserReadMapper;
import com.main.repository.general.MessageRecipientRepository;
import com.main.repository.message.MessageRepository;
import com.main.repository.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final MessageReadMapper messageReadMapper;
    private final MessageCreateEditMapper messageCreateEditMapper;

    private final MessageRecipientRepository messageRecipientRepository;

    private final MessageRecipientCreateEditMapper messageRecipientCreateEditMapper;
    private final MessageRecipientReadMapper messageRecipientReadMapper;


    //  Сообщения с пагинацией
    public Page<MessageReadDto> findAllPageable(Pageable page) {
        return this.messageRepository.findAll(page)
                .map(messageReadMapper::map);
    }//findAll()



    public List<MessageReadDto> findAll() {
        return messageRepository.findAll()
                .stream()
                .map(messageReadMapper::map)
                .toList();
    }

    public Optional<MessageReadDto> findById(Long id) {
        return messageRepository.findById(id)
                .map(messageReadMapper::map);
    }

    @Transactional
    public MessageReadDto create(MessageCreateEditDto messageDto) {

        MessageReadDto messageReadDto = Optional.of(messageDto)
                .map(dto -> {
                    return messageCreateEditMapper.map(dto);
                })
                .map(messageRepository::save)
                .map(messageReadMapper::map)
                .orElseThrow();

        if(messageReadDto != null) {

            List<MessageRecipientReadDto> messageRecipientList = new ArrayList<>();

            // Создадим и внесем записи для таблицы "message_recipient"
            messageDto.getRecipients().forEach((recipientId) -> {
                MessageRecipientCreateEditDto messageRecipientDto = new MessageRecipientCreateEditDto();
                messageRecipientDto.setMessageId(messageReadDto.getId());
                messageRecipientDto.setRecipientId(recipientId);
                messageRecipientDto.setRead(false);
                messageRecipientDto.setReadingTime(null);

                MessageRecipientReadDto messageRecipientReadDto = Optional.of(messageRecipientDto)
                        .map(dto -> {
                            return messageRecipientCreateEditMapper.map(dto);
                        })
                        .map(messageRecipientRepository::save)
                        .map(messageRecipientReadMapper::map)
                        .orElseThrow();

                messageRecipientList.add(messageRecipientReadDto);

            });

            // Установим id-шники получателей сообщения
            messageReadDto.setRecipients(messageRecipientList);
        }

        return messageReadDto;
    }


    @Transactional
    public Optional<MessageReadDto> update(Long id, MessageCreateEditDto messageDto) {
        return messageRepository.findById(id)
                .map(entity -> {
                    System.out.println("Message find: " + entity);
                    System.out.println("Message dto find: " + messageDto);
                    return messageCreateEditMapper.map(messageDto, entity);
                })
                .map(messageRepository::saveAndFlush)
                .map(messageReadMapper::map);

    }


    @Transactional
    public boolean delete(Long id) {

        return messageRepository.findById(id)
                .map(entity -> {

                    messageRepository.delete(entity);
                    messageRepository.flush();
                    // Удалим записи из "message_recipients" с удаляемым сообщением по id
                    System.out.println("MessageRecipient is delete? " + messageRecipientRepository.findAllByMessageId(id));

                    return true;
                })
                .orElse(false);

    }
    
}
