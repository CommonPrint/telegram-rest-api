package com.main.dto.create_edit.general;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserChatCreateEditDto {

    private Long id;

    private Long userId;

    private Long chatId;

}
