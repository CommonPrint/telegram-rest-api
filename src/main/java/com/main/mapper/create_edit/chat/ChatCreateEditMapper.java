package com.main.mapper.create_edit.chat;

import com.main.dto.create_edit.chat.ChatCreateEditDto;
import com.main.dto.create_edit.user.UserCreateEditDto;
import com.main.dto.read.general.UserChatReadDto;
import com.main.entity.chat.Chat;
import com.main.entity.chat.ChatType;
import com.main.entity.general.UserChat;
import com.main.entity.message.Message;
import com.main.entity.user.User;
import com.main.mapper.Mapper;
import com.main.mapper.read.general.UserChatReadMapper;
import com.main.repository.chat.ChatRepository;
import com.main.repository.chat.ChatTypeRepository;
import com.main.repository.general.UserChatRepository;
import com.main.repository.message.MessageRepository;
import com.main.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ChatCreateEditMapper implements Mapper<ChatCreateEditDto, Chat> {

    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final UserChatRepository userChatRepository;
    private final ChatTypeRepository chatTypeRepository;
    private final ChatRepository chatRepository;

    private UserRepository userRepository1;


    @Override
    public Chat map(ChatCreateEditDto object) {
        Chat chat = new Chat();

        copy(object, chat);

        return chat;
    }


    @Override
    public Chat map(ChatCreateEditDto fromObject, Chat toObject) {
        copy(fromObject, toObject);

        return toObject;
    }


    // Вставка данных
    public void copy(ChatCreateEditDto object, Chat chat) {
        // Если id-чата есть, то значит это будет редактирование чата
        if(chat.getId() != null) {
            System.out.println("Chat: " + chat);
            System.out.println("ChatEdit: " + object);

            chat.setUserChats(getUserChats(object.getUserChats()));
            chat.setMessages(getMessages(object.getMessages()));
        }

        else {
            chat.setName(object.getName());
            chat.setType(getChatType(object.getType()));
            chat.setCreateTime(object.getCreateTime());
            chat.setCreator(getCreatorChat(object.getCreatorId()));
        }

    }


    // Пользователи чата UserChat
    public List<UserChat> getUserChats(List<UserChatReadDto> userChats) {
        List<UserChat> userChatList = new ArrayList<>();

        userChats.forEach(userChatDto -> {
            userChatList.add(
                    userChatRepository.findById(userChatDto.getId()).orElse(null)
            );
        });

        return userChatList;
    }


    // Тип чата
    public ChatType getChatType(String type) {
        return chatTypeRepository.findByName(type);
    }



    // Создатель чата
    public User getCreatorChat(Long creatorId) {
        return userRepository.findById(creatorId).orElse(null);
    }



    // Получим список сообщений
    public List<Message> getMessages(List<Long> messages) {

        List<Message> messageSet = new ArrayList<>();

        messages.forEach(msgId -> {
            messageSet.add(
                    messageRepository.findById(msgId).orElse(null)
            );
        });

        return messageSet;

    }//getMessages(List<Long> messages)



//    public List<User> getUsers(List<Long> users) {
//
//        List<User> userSet = new ArrayList<>();
//
//        users.forEach(userId -> {
//            userSet.add(
//                    userRepository.findById(userId).orElse(null)
//            );
//        });
//
//        return userSet;
//
//    }




}
