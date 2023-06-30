package com.main.dto.create_edit.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.main.dto.read.general.UserChatReadDto;
import com.main.entity.general.UserChat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateEditDto {

    private Long id;

    @NotBlank
    @Size(max = 20)
    private String username;

    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(max = 120)
    private String password;

    private String avatar;

    private boolean online;

    private Instant lastActive;

    private List<String> roles;
    private List<Long> chats; // будет принимать id-чатов

    private List<UserChatReadDto> userChats; // будет содержать более подробную информацию о чатах пользователя

}
