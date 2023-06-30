package com.main.dto.read.message;

import com.main.dto.read.chat.ChatReadDto;
import com.main.dto.read.general.MessageRecipientReadDto;
import com.main.dto.read.user.UserReadDto;
import com.main.entity.general.MessageRecipient;
import com.main.entity.message.MessageType;
import com.main.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageReadDto {

    private Long id;

    private String content;

    private Instant createTime;

    private UserReadDto creator;

    private String type;

    private Long chat;

//    private List<Long> recipients;
    private List<MessageRecipientReadDto> recipients;
//    private List<UserReadDto> recipients;

    private String extension;

}
