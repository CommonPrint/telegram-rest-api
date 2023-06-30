package com.main.mapper.create_edit.user;


import com.main.dto.create_edit.user.UserCreateEditDto;
import com.main.dto.read.general.UserChatReadDto;
import com.main.entity.general.UserChat;
import com.main.entity.user.User;
import com.main.mapper.Mapper;
import com.main.repository.general.UserChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserCreateEditMapper implements Mapper<UserCreateEditDto, User> {

    private final PasswordEncoder encoder;
    private final UserChatRepository userChatRepository;

    @Override
    public User map(UserCreateEditDto object) {
        User user = new User();

        copy(object, user);

        return user;
    }


    @Override
    public User map(UserCreateEditDto fromObject, User toObject) {
        copy(fromObject, toObject);

        return toObject;
    }


    // Вставка данных
    public void copy(UserCreateEditDto object, User user) {
        user.setUsername(object.getUsername());
        user.setEmail(object.getEmail());
        user.setPassword(encoder.encode(object.getPassword()));
        user.setAvatar(object.getAvatar());
        user.setOnline(object.isOnline());
        user.setLastActive(object.getLastActive());

        // Если пользователь уже был создан, значит будет редактирование профиля
//        if(object.getId() != null) {
//            user.setUserChats(getUserChats(object.getUserChats()));
//        }
    }

    public List<UserChat> getUserChats(List<UserChatReadDto> userChats) {
        List<UserChat> userChatList = new ArrayList<>();

        userChats.forEach(userChatDto -> {
            userChatList.add(
                    userChatRepository.findById(userChatDto.getId()).orElse(null)
            );
        });

        return userChatList;
    }


}
