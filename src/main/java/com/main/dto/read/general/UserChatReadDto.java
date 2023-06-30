package com.main.dto.read.general;

import com.main.dto.read.chat.ChatReadDto;
import com.main.dto.read.user.UserReadDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserChatReadDto {

    private Long id;

    private Long userId;
    private Long chatId;

//    private UserReadDto user;
//
//    private ChatReadDto chat;

}
