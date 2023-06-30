package com.main.dto.read.chat;

import com.main.dto.create_edit.user.UserCreateEditDto;
import com.main.dto.read.general.UserChatReadDto;
import com.main.dto.read.message.MessageReadDto;
import com.main.dto.read.user.UserReadDto;
import com.main.entity.chat.ChatType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatReadDto {

    private Long id;

    private String name;

    private Instant createTime;

    private UserReadDto creator; // creatorId - это id пользователя

    private ChatType type; // Тип чата


//    private List<Long> users;

//    private List<UserReadDto> users; // Послужит для сущности UserChat
    private List<UserChatReadDto> userChats; // будет хранить всю необходимую информацию о чатах пользователя
    private List<MessageReadDto> messages;

}
