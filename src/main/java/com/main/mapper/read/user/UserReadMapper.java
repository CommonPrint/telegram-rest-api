package com.main.mapper.read.user;


import com.main.dto.read.chat.ChatReadDto;
import com.main.dto.read.general.UserChatReadDto;
import com.main.dto.read.user.UserReadDto;
import com.main.entity.user.User;
import com.main.mapper.Mapper;
import com.main.mapper.read.chat.ChatReadMapper;
import com.main.mapper.read.general.UserChatReadMapper;
import com.main.repository.chat.ChatRepository;
import com.main.repository.general.UserChatRepository;
import com.main.repository.user.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class UserReadMapper implements Mapper<User, UserReadDto> {

//    private final ChatRepository chatRepository;
//    private final ChatReadMapper chatReadMapper;

    private final RoleRepository roleRepository;
    private final UserChatRepository userChatRepository;
    private final UserChatReadMapper userChatReadMapper;


    @Override
    public UserReadDto map(User object) {

        // Добавим список чатов, в которых присутсвует пользователь
//        Set<ChatReadDto> chats = new HashSet<>();
        List<UserChatReadDto> userChats = new ArrayList<>();

        System.out.println("UserChats from userReadMapper: " + object.getUserChats());

        object.getUserChats().forEach(userChatDto -> {
            System.out.println("UserChatDto: " + userChatDto);
            if(object.getId() == userChatDto.getUser().getId()) {
                userChats.add(
                        userChatRepository.findById(userChatDto.getId())
                                .map(userChatReadMapper::map).orElse(null)
//                        userChatRepository.findByUserId(userChatDto.getUser().getId())
//                                .map(userChatReadMapper::map).orElse(null)
                );
            }

        });

        return new UserReadDto(
                object.getId(),
                object.getUsername(),
                object.getEmail(),
                object.getPassword(),
                object.getAvatar(),
                object.isOnline(),
                object.getLastActive(),
                object.getRoles(),
                userChats
        );
    }

}
