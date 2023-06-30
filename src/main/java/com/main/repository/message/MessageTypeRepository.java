package com.main.repository.message;


import com.main.entity.message.MessageType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageTypeRepository extends JpaRepository<MessageType, Long> {

    @Query("SELECT mt FROM MessageType mt WHERE mt.name = :name")
    MessageType findByName(@Param("name") String name);

}
