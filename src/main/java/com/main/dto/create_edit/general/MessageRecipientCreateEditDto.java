package com.main.dto.create_edit.general;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageRecipientCreateEditDto {

    private Long id;

    private Long messageId;

    private Long recipientId;

    private boolean isRead;

    private Instant readingTime;

}
