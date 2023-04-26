package com.example.demo.user.controller;

import com.example.demo.user.service.CreateUserDomainObjectRequest;
import com.example.demo.user.service.UserDomainObject;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserControllerHelper {
    public @NonNull CreateUserDomainObjectRequest toCreateUserRequest(
            @NonNull final JsonUpsertUserDomainRequest jsonRequest) {

        return CreateUserDomainObjectRequest.builder()
                .name(jsonRequest.getName())
                .surname(jsonRequest.getSurname())
                .email(jsonRequest.getEmail())
                .password(jsonRequest.getPassword())
                .build();
    }

    public @NonNull UserDomainObject toUserDomainObject(@NonNull final JsonUpsertUserDomainRequest jsonUpsertUserDomainRequest, Long id) {
        return UserDomainObject.builder()
                .id(id)
                .name(jsonUpsertUserDomainRequest.getName())
                .surname(jsonUpsertUserDomainRequest.getSurname())
                .email(jsonUpsertUserDomainRequest.getEmail())
                .password(jsonUpsertUserDomainRequest.getPassword())
                .roles(jsonUpsertUserDomainRequest.getRoles())
                .build();
    }

    @NonNull
    public static JsonUserDomainResponse toJson(@NonNull final UserDomainObject userDomainObject) {
        return JsonUserDomainResponse.builder()
                .id(userDomainObject.getId())
                .name(userDomainObject.getName())
                .surname(userDomainObject.getSurname())
                .email(userDomainObject.getEmail())
                .password(userDomainObject.getPassword())
                .role(userDomainObject.getRoles())
                .build();
    }
}
