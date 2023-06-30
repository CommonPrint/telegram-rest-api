package com.main.service.user;


import com.main.dto.create_edit.user.UserCreateEditDto;
import com.main.dto.read.message.MessageReadDto;
import com.main.dto.read.user.UserActivity;
import com.main.dto.read.user.UserReadDto;
import com.main.entity.chat.Chat;
import com.main.mapper.create_edit.general.UserChatCreateEditMapper;
import com.main.mapper.create_edit.user.UserCreateEditMapper;
import com.main.mapper.read.general.UserChatReadMapper;
import com.main.mapper.read.user.UserReadMapper;
import com.main.repository.chat.ChatRepository;
import com.main.repository.general.UserChatRepository;
import com.main.repository.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.time.Instant;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserReadMapper userReadMapper;
    private final UserCreateEditMapper userCreateEditMapper;


    private final ChatRepository chatRepository;


    private final UserChatRepository userChatRepository;
//    private final UserChatCreateEditMapper userChatCreateEditMapper;
//    private final UserChatReadMapper userChatReadMapper;


    // Изменить этот метод
    public List<UserReadDto> findAll() {
        return userRepository.findAll()
                .stream()
                .map(userReadMapper::map)
                .toList();
    }


    public List<UserReadDto> findAllByUsername(String username) {
        return userRepository.findAllByUsername(username.toLowerCase())
                .stream()
                .map(userReadMapper::map)
                .toList();
    }


    public Optional<UserReadDto> findById(Long id) {
        return userRepository.findById(id)
                .map(userReadMapper::map);
    }

    @Transactional
    public UserReadDto create(UserCreateEditDto userDto) {

        return Optional.of(userDto)
                .map(dto -> {
                    return userCreateEditMapper.map(dto);
                })
                .map(userRepository::save)
                .map(userReadMapper::map)
                .orElseThrow();
    }


    @Transactional
    public Optional<UserReadDto> update(Long id, UserCreateEditDto userDto) {
        return userRepository.findById(id)
                        .map(entity -> {
                            return userCreateEditMapper.map(userDto, entity);
                        })
                        .map(userRepository::saveAndFlush)
                        .map(userReadMapper::map);

    }

    @Transactional
    public Boolean updateOnlineUser(Long id, UserActivity userActivity) {

        if(userRepository.findById(id) != null) {

            if (userActivity.isOnline() == false) {
                userActivity.setLastActive(Instant.ofEpochMilli(System.currentTimeMillis()));
            } else {
                userActivity.setLastActive(null);
            }

            if(userRepository.updateOnlineUser(id, userActivity.isOnline(), userActivity.getLastActive()) == 1) {
                return true;
            }
        }

        return false;
    }


    @Transactional
    public boolean delete(Long id) {

        return userRepository.findById(id)
                .map(entity -> {
                    List<Chat> userCreateChats = chatRepository.findByUserId(id);

                    userCreateChats.forEach(userCreateChat -> {
                       userCreateChat.setCreator(null);
                       chatRepository.saveAndFlush(userCreateChat);
                    });

                    userRepository.delete(entity);
                    userRepository.flush();
                    // Удалим записи из "user_chats" с удаленным чатом по id
//                    userChatRepository.deleteByChatId(id);
                    System.out.println("UserChat is delete? " + userChatRepository.findAllByUserId(id));
                    return true;
                })
                .orElse(false);

    }

}
