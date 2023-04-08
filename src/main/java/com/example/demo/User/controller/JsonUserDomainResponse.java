package com.example.demo.user.controller;

import com.example.demo.user.service.UserDomainObject;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonUserDomainResponse {
    @JsonProperty("id")
    Long id;
    @JsonProperty("name")
    String name;
    @JsonProperty("surname")
    String surname;
    @JsonProperty("email")
    String email;
    @JsonProperty("password")
    String password;
    @JsonProperty("role")
    String role;

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
