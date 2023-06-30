package com.main.controller.user;


import com.main.dto.create_edit.user.UserCreateEditDto;
import com.main.dto.read.user.UserActivity;
import com.main.dto.read.user.UserReadDto;
import com.main.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public UserReadDto findById(@PathVariable("id") Long id) {
        return userService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }


    @GetMapping
    public ResponseEntity<List<UserReadDto>> findAll() {
        List<UserReadDto> users = userService.findAll();

        return new ResponseEntity<List<UserReadDto>>(users, HttpStatus.OK);
    }


    @GetMapping("/search/{username}")
    public ResponseEntity<List<UserReadDto>> findAll(@PathVariable("username") String username) {
        List<UserReadDto> users = userService.findAllByUsername(username);

        return new ResponseEntity<List<UserReadDto>>(users, HttpStatus.OK);
    }


//    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseStatus(HttpStatus.CREATED)
//    public UserReadDto create(@RequestBody UserCreateEditDto user) {
//        return userService.create(user);
//    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Optional<UserReadDto> update(@PathVariable("id") Long id, @RequestBody UserCreateEditDto user) {

        return userService.update(id, user);
    }

    @PutMapping(value = "/online/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Boolean updateOnlineUser(@PathVariable("id") Long id, @RequestBody UserActivity userActivity) {
        return userService.updateOnlineUser(id, userActivity);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {

        return userService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

}
