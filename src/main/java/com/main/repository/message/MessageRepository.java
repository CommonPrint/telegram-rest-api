package com.main.repository.message;


import com.main.entity.message.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("SELECT m FROM Message m ORDER BY m.createTime DESC")
    Page<Message> findAll(Pageable pageable);

    @Query("SELECT m FROM Message m ORDER BY m.id ASC")
    List<Message> findAll();

    @Query("SELECT m FROM Message m WHERE m.chat.id = :chatId ORDER BY m.id ASC")
    List<Message> findAllByChatId(@Param("chatId") Long chatId);
    

//    @Modifying(clearAutomatically = true)
//    @Query("UPDATE Message ms SET ms.readable = true WHERE ms.id = :messageId")
//    Integer updateReadMessage(@Param("messageId") Long messageId);

    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM Message ms WHERE ms.chat.id = :chatId")
    void deleteByChatId(@Param("chatId") Long chatId);

}
