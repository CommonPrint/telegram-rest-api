package com.main.service.chat;


import com.main.dto.create_edit.chat.ChatCreateEditDto;
import com.main.dto.create_edit.general.UserChatCreateEditDto;
import com.main.dto.read.chat.ChatReadDto;
import com.main.dto.read.general.UserChatReadDto;
import com.main.entity.message.Message;
import com.main.mapper.create_edit.chat.ChatCreateEditMapper;
import com.main.mapper.create_edit.general.UserChatCreateEditMapper;
import com.main.mapper.read.chat.ChatReadMapper;
import com.main.mapper.read.general.UserChatReadMapper;
import com.main.repository.chat.ChatRepository;

import com.main.repository.general.UserChatRepository;
import com.main.repository.message.MessageRepository;
import com.main.service.message.MessageService;
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
public class ChatService {

    private final ChatRepository chatRepository;
    private final ChatReadMapper chatReadMapper;
    private final ChatCreateEditMapper chatCreateEditMapper;

    private final UserChatRepository userChatRepository;
    private final UserChatReadMapper userChatReadMapper;
    private final UserChatCreateEditMapper userChatCreateEditMapper;

    private final MessageService messageService;
    private final MessageRepository messageRepository;

    //  Чаты с пагинацией
    public Page<ChatReadDto> findAllPageable(Pageable page) {
        return chatRepository.findAll(page)
                .map(chatReadMapper::map);
    }//findAll()

    public List<ChatReadDto> findAll() {
        return chatRepository.findAll()
                .stream()
                .map(chatReadMapper::map)
                .toList();
    }

    public Optional<ChatReadDto> findById(Long id) {
        return chatRepository.findById(id)
                .map(chatReadMapper::map);
    }

    @Transactional
    public ChatReadDto create(ChatCreateEditDto chatDto) {

        ChatReadDto chatReadDto = Optional.of(chatDto)
                .map(dto -> {
                    return chatCreateEditMapper.map(dto);
                })
                .map(chatRepository::save)
                .map(chatReadMapper::map)
                .orElseThrow();

        if(chatReadDto != null) {

            List<UserChatReadDto> userChatList = new ArrayList<>();

            // Создадим и внесем записи для таблицы "user_chats"
            chatDto.getUsers().forEach((userId) -> {
                UserChatCreateEditDto userChatCreateEditDto = new UserChatCreateEditDto();
                userChatCreateEditDto.setChatId(chatReadDto.getId());
                userChatCreateEditDto.setUserId(userId);

                UserChatReadDto userChatReadDto = Optional.of(userChatCreateEditDto)
                        .map(dto -> {
                            return userChatCreateEditMapper.map(dto);
                        })
                        .map(userChatRepository::save)
                        .map(userChatReadMapper::map)
                        .orElseThrow();

                userChatList.add(userChatReadDto);

            });

            // Установим id-шники пользователей чата
//            chatReadDto.setUserChats(chatDto.getUserChats());
            chatReadDto.setUserChats(userChatList);
        }

        return chatReadDto;

    }//create(ChatCreateEditDto chatDto)


    @Transactional
    public Optional<ChatReadDto> update(Long id, ChatCreateEditDto chatDto) {

        return chatRepository.findById(id)
                .map(entity -> {
                    System.out.println("Entity: " + entity);
                    System.out.println("Entity userChats: " + entity.getUserChats());
                    return chatCreateEditMapper.map(chatDto, entity);
                })
                .map(chatRepository::saveAndFlush)
                .map(chatReadMapper::map);

    }


    @Transactional
    public boolean delete(Long id) {

        return chatRepository.findById(id)
                .map(entity -> {

                    // Удалим все сообщения чата
                    List<Message> messages = messageRepository.findAllByChatId(id);

                    messages.forEach((msg) -> {
                        messageService.delete(msg.getId());
                    });

                    // Удалим сам чат
                    chatRepository.delete(entity);
                    chatRepository.flush();

                    return true;
                })
                .orElse(false);

    }

}
