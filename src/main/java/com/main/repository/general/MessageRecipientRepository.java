package com.main.repository.general;

import com.main.entity.general.MessageRecipient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRecipientRepository extends JpaRepository<MessageRecipient, Long> {

    @Query("SELECT mr FROM MessageRecipient mr WHERE mr.message.id = :messageId")
    List<MessageRecipient> findAllByMessageId(@Param("messageId") Long messageId);

//    @Query("SELECT mr FROM MessageRecipient mr WHERE mr.user.id = :recipientId")
//    List<MessageRecipient> findByRecipientId(@Param("recipientId") Long recipientId);

}
