package com.example.demo.user.controller;

import com.example.demo.user.service.UserDomainObject;
import com.example.demo.user.service.UserService;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping
public class UserController {

    private final UserService userService;

    public UserController(@NonNull final UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/users")
    public ResponseEntity<List<JsonUserDomainResponse>> getAllUsers() {
        List<UserDomainObject> userDomainObjects = userService.getAllUsers();
        List<JsonUserDomainResponse> jsonResponse = userDomainObjects.stream().map(JsonUserDomainResponse::toJson).collect(Collectors.toList());
        return ResponseEntity.ok().body(jsonResponse);
    }

    @PostMapping("/api/create/user")
    public ResponseEntity<JsonUserDomainResponse> newUser(@RequestBody @NonNull final JsonUpsertUserDomainRequest request) {
        final UserDomainObject userDomainObject = userService.save(UserControllerHelper.toCreateUserRequest(request));
        return ResponseEntity.ok().body(JsonUserDomainResponse.toJson(userDomainObject));
    }

    @PutMapping("/api/user/{id}")
    public ResponseEntity<JsonUserDomainResponse> updateUser(@RequestBody @NonNull final JsonUpsertUserDomainRequest request, @PathVariable Long id) {
        final UserDomainObject userDomainObject = userService.update(UserControllerHelper.toUserDomainObject(request, id));
        return ResponseEntity.ok().body(JsonUserDomainResponse.toJson(userDomainObject));
    }

}
