package com.main.entity.general;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.main.entity.BaseEntity;
import com.main.entity.chat.Chat;
import com.main.entity.message.Message;
import com.main.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "messages_recipients")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MessageRecipient  implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "message_id")
    private Message message;

    private boolean isRead;

    private Instant readingTime;

    public void setRecipient(User user) {
        this.user = user;
        this.user.getRecipients().add(this);
    }

    public void setMessage(Message message) {
        this.message = message;
        this.message.getRecipients().add(this);
    }

}
