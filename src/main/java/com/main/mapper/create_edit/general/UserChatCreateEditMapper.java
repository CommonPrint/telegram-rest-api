package com.main.mapper.create_edit.general;

import com.main.dto.create_edit.general.MessageRecipientCreateEditDto;
import com.main.dto.create_edit.general.UserChatCreateEditDto;
import com.main.entity.chat.Chat;
import com.main.entity.general.MessageRecipient;
import com.main.entity.general.UserChat;
import com.main.entity.message.Message;
import com.main.entity.user.User;
import com.main.mapper.Mapper;
import com.main.repository.chat.ChatRepository;
import com.main.repository.message.MessageRepository;
import com.main.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class UserChatCreateEditMapper implements Mapper<UserChatCreateEditDto, UserChat> {

    private final UserRepository userRepository;
    private final ChatRepository chatRepository;


    @Override
    public UserChat map(UserChatCreateEditDto object) {

        UserChat userChat = new UserChat();

        copy(object, userChat);

        return userChat;

    }

    @Override
    public UserChat map(UserChatCreateEditDto fromObject, UserChat toObject) {
        copy(fromObject, toObject);

        return toObject;
    }


    // Вставка данных
    public void copy(UserChatCreateEditDto object, UserChat userChat) {

        userChat.setUser(getUser(object.getUserId()));
        userChat.setChat(getChat(object.getChatId()));

    }


    // Получит пользователя
    public User getUser(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public Chat getChat(Long chatId) {
        return chatRepository.findById(chatId).orElse(null);
    }

}
