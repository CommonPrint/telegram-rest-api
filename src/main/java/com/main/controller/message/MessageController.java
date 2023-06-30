package com.main.controller.message;

import lombok.RequiredArgsConstructor;

import com.main.dto.create_edit.message.MessageCreateEditDto;
import com.main.dto.read.message.MessageReadDto;
import com.main.service.message.MessageService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    @GetMapping
    public ResponseEntity<List<MessageReadDto>> findAll() {
        List<MessageReadDto> messages = messageService.findAll();

        return new ResponseEntity<List<MessageReadDto>>(messages, HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<Map<String, Object>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        List<MessageReadDto> messages = messageService.findAll();
        Pageable paging = PageRequest.of(page, size);

        Page<MessageReadDto> pageProducts = this.messageService.findAllPageable(paging);

        messages = pageProducts.getContent();

        Map<String, Object> response = new HashMap<>();
        response.put("messages", messages);
        response.put("currentPage", pageProducts.getNumber());
        response.put("totalItems", pageProducts.getTotalElements());
        response.put("totalPages", pageProducts.getTotalPages());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public MessageReadDto findById(@PathVariable("id") Long id) {
        return messageService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }



    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public MessageReadDto create(@RequestBody MessageCreateEditDto message) {
        message.setCreateTime(Instant.ofEpochMilli(System.currentTimeMillis()));
        return messageService.create(message);
    }


    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Optional<MessageReadDto> update(@PathVariable("id") Long id, @RequestBody MessageCreateEditDto message) {
        return messageService.update(id, message);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {

        return messageService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();

    }

}
