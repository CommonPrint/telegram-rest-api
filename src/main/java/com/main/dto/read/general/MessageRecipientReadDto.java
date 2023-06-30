package com.main.dto.read.general;

import com.main.dto.read.chat.ChatReadDto;
import com.main.dto.read.message.MessageReadDto;
import com.main.dto.read.user.UserReadDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageRecipientReadDto {

    private Long id;

    private Long messageId;

    private Long userId;

    private boolean isRead;

    private Instant readingTime;

}
