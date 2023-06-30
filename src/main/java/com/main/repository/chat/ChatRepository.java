package com.main.repository.chat;

import com.main.entity.chat.Chat;
import com.main.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    @Query("SELECT c FROM Chat c WHERE c.creator.id = :userId")
    List<Chat> findByUserId(@Param("userId") Long userId);

//    @Modifying
//    @Query("DELETE FROM Chat c WHERE c.id = :chatId")
//    int deleteByChatId(@Param("chatId") Long chatId);

}
