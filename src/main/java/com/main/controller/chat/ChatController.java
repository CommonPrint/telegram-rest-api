package com.main.controller.chat;

import com.main.dto.create_edit.chat.ChatCreateEditDto;
import com.main.dto.read.chat.ChatReadDto;
import com.main.service.chat.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/chats")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;


    @GetMapping
    public ResponseEntity<List<ChatReadDto>> findAll() {
        List<ChatReadDto> chats = chatService.findAll();

        return new ResponseEntity<List<ChatReadDto>>(chats, HttpStatus.OK);
    }


    @GetMapping("/filter")
    public ResponseEntity<Map<String, Object>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        List<ChatReadDto> chats = chatService.findAll();
        Pageable paging = PageRequest.of(page, size);

        Page<ChatReadDto> pageChats = this.chatService.findAllPageable(paging);

        chats = pageChats.getContent();

        Map<String, Object> response = new HashMap<>();
        response.put("chats", chats);
        response.put("currentPage", pageChats.getNumber());
        response.put("totalItems", pageChats.getTotalElements());
        response.put("totalPages", pageChats.getTotalPages());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ChatReadDto findById(@PathVariable("id") Long id) {
        return chatService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ChatReadDto create(@RequestBody ChatCreateEditDto chat) {
        return chatService.create(chat);
    }


    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Optional<ChatReadDto> update(@PathVariable("id") Long id, @RequestBody ChatCreateEditDto chat) {
        return chatService.update(id, chat);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {

        return chatService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();

    }

}
