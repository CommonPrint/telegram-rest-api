package com.main.dto.read.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.main.dto.read.chat.ChatReadDto;
import com.main.dto.read.general.UserChatReadDto;
import com.main.entity.user.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserReadDto {

    private Long id;

    private String username;

    private String email;

    @JsonIgnore
    @Size(max = 120)
    private String password;

    private String avatar;

    private boolean online;
    private Instant lastActive;

//    private Set<Long> chats;

//    private Set<ChatReadDto> chats;

    private List<Role> role;

    private List<UserChatReadDto> userChats; // будет хранить всю необходимую информацию о чатах пользователя

}
