package com.main.repository.user;


import com.main.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.username = :username")
    Optional<User> findByUsername(@Param("username") String username);

    @Query("SELECT u FROM User u WHERE lower(u.username) LIKE %:username%")
    List<User> findAllByUsername(@Param("username") String username);

    @Modifying
    @Query("UPDATE User u SET u.online = :online, u.lastActive = :lastActive WHERE u.id = :userId")
    int updateOnlineUser(@Param("userId") Long userId,
                         @Param("online") boolean online,
                         @Param("lastActive") Instant lastActive);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}