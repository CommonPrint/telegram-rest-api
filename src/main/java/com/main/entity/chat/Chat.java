package com.main.entity.chat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.main.entity.BaseEntity;
import com.main.entity.general.UserChat;
import com.main.entity.message.Message;
import com.main.entity.user.User;
import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "chats")
@Data
@Builder
@ToString(exclude = {"messages", "userChats"})
@EqualsAndHashCode(exclude = {"messages", "userChats"})
@NoArgsConstructor
@AllArgsConstructor
public class Chat implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Instant createTime;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "creator_id")
    private User creator;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "chat_type_id")
    private ChatType type;

    @JsonBackReference
    @Builder.Default
    @OneToMany(mappedBy = "chat", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserChat> userChats = new ArrayList<>();

    @JsonBackReference
    @Builder.Default
    @OneToMany(
//            mappedBy = "chat",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL) // Параметр [mappedBy] указывает на [имя поля] в [классе] на который мы ссылаемся. В нашем случае это поле называется "chat" в классе "User" (Часто встречается на практике)
    @JoinColumn(name="message_id") // Чтобы использовать @JoinColumn нужно, чтобы у @OneToMany не было параметра "mappedBy"
    private List<Message> messages = new ArrayList<>();

}
