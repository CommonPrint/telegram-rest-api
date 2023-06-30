package com.main.entity.message;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.main.entity.BaseEntity;
import com.main.entity.chat.Chat;
import com.main.entity.general.MessageRecipient;
import com.main.entity.user.User;
import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "messages")
@Data
@Builder
@ToString(exclude = {"recipients"})
@NoArgsConstructor
@AllArgsConstructor
public class Message implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 5000)
    private String content;

    private Instant createTime;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "creator_id")
    private User creator;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "message_type_id")
    private MessageType type;

//    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    private Chat chat;

    @JsonBackReference
    @Builder.Default
    @OneToMany(mappedBy = "message", cascade = CascadeType.ALL)
    private List<MessageRecipient> recipients = new ArrayList<>();

    private String extension; // Расширение будет, если тип сообщения будет как картинка или аудио-файл, иначе пустое значение


}
