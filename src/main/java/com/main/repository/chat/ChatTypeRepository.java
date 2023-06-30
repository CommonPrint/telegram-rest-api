package com.main.repository.chat;

import com.main.entity.chat.ChatType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatTypeRepository extends JpaRepository<ChatType, Long> {

    @Query("SELECT ct FROM ChatType ct WHERE ct.name = :name")
    ChatType findByName(@Param("name") String name);

}
