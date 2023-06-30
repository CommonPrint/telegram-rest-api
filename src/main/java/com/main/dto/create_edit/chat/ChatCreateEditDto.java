package com.main.dto.create_edit.chat;

import com.main.dto.read.general.UserChatReadDto;
import com.main.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatCreateEditDto {

    private Long id;

    private String name;

    private Instant createTime;

    private Long creatorId; // creatorId - это id пользователя

    private String type; // Тип чата

    private List<Long> users; // Послужит для сущности UserChat

    private List<Long> messages;

    private List<UserChatReadDto> userChats;

}
