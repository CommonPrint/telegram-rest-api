package com.main.dto.create_edit.message;

import com.main.dto.read.general.MessageRecipientReadDto;
import com.main.entity.chat.Chat;
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
public class MessageCreateEditDto {

    private Long id;

    private String content;

    private Instant createTime;

    private Long creatorId; // creatorId - это id пользователя

    private String type;

    private Long chatId;

    private List<Long> recipients;
//    private List<MessageRecipientReadDto> recipients;

    private String extension;

}
