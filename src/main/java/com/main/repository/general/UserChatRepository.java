package com.main.repository.general;

import com.main.entity.general.UserChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserChatRepository extends JpaRepository<UserChat, Long> {



    // Метод будет брать информацию о чатах для конкретного Пользователя
    @Query("SELECT uc FROM UserChat uc WHERE uc.user.id = :userId")
    List<UserChat> findAllByUserId(@Param("userId") Long userId);

    @Query("SELECT uc FROM UserChat uc WHERE uc.user.id = :userId")
    Optional<UserChat> findByUserId(@Param("userId") Long userId);


    // Метод будет брать информацию о пользователях для конкретного Чата
    @Query("SELECT uc FROM UserChat uc WHERE uc.chat.id = :chatId")
    List<UserChat> findAllByChatId(@Param("chatId") Long chatId);

    @Query("SELECT uc FROM UserChat uc WHERE uc.chat.id = :chatId")
    Optional<UserChat> findByChatId(@Param("chatId") Long chatId);

    @Modifying
    @Query("DELETE FROM UserChat uc WHERE uc.chat.id = :chatId")
    int deleteByChatId(@Param("chatId") Long chatId);

}
