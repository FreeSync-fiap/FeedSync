package br.com.feedsync.FeedSync.controller;


import br.com.feedsync.FeedSync.dto.UserRequest;
import br.com.feedsync.FeedSync.dto.UserResponse;
import br.com.feedsync.FeedSync.entity.User;
import br.com.feedsync.FeedSync.mapper.UserMapper;
import br.com.feedsync.FeedSync.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;
    private final UserMapper mapper;

    public UserController(UserService service, UserMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(
            @Validated @RequestBody UserRequest user
    ) {
        User saved = service.create(mapper.toEntity(user));
        return ResponseEntity.ok(mapper.toResponse(saved));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findUserById(
            @PathVariable String id) {
        var result = service.findUserById(id);
        return ResponseEntity.ok(mapper.toResponse(result));
    }


    @GetMapping
    ResponseEntity<List<UserResponse>> findAllUsers(
            @RequestParam(required = false) Boolean active
    ) {
        var result = service.findAll(active);
        return ResponseEntity.ok(mapper.toResponseList(result));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(
            @PathVariable String id
    ) {
        service.deleteUserById(id);
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{userId}/enroll/{courseId}")
    public ResponseEntity<Void> enrollUser(
            @PathVariable String userId,
            @PathVariable String courseId
    ) {
        service.enrollUserInCourse(userId, courseId);
        return ResponseEntity.noContent().build();
    }

}
