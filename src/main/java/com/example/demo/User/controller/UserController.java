package com.example.demo.user.controller;

import com.example.demo.user.service.UserDomainObject;
import com.example.demo.user.service.UserService;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/user")
public class UserController {

    private final UserService userService;

    public UserController(@NonNull final UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<JsonUserDomainResponse>> getAllUsers() {
        List<UserDomainObject> userDomainObjects = userService.getAllUsers();
        List<JsonUserDomainResponse> jsonResponse = userDomainObjects.stream().map(UserControllerHelper::toJson).collect(Collectors.toList());
        return ResponseEntity.ok().body(jsonResponse);
    }

    @PostMapping("/create")
    public ResponseEntity<JsonUserDomainResponse> newUser(@RequestBody @NonNull final JsonUpsertUserDomainRequest request) {
        final UserDomainObject userDomainObject = userService.save(UserControllerHelper.toCreateUserRequest(request));
        return ResponseEntity.ok().body(UserControllerHelper.toJson(userDomainObject));
    }

    @PutMapping("update/{id}")
    public ResponseEntity<JsonUserDomainResponse> updateUser(@RequestBody @NonNull final JsonUpsertUserDomainRequest request, @PathVariable Long id) {
        final UserDomainObject userDomainObject = userService.update(UserControllerHelper.toUserDomainObject(request, id));
        return ResponseEntity.ok().body(UserControllerHelper.toJson(userDomainObject));
    }

}
