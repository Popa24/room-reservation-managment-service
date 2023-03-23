package com.example.demo.user.controller;

import com.example.demo.user.service.CreateUserDomainObjectRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonCreateUserDomainRequest {
    @JsonProperty("name")
    String name;
    @JsonProperty("surname")
    String surname;
    @JsonProperty("email")
    String email;
    @JsonProperty("password")
    String password;
    @NonNull
    public CreateUserDomainObjectRequest toDomainObject(){
        return CreateUserDomainObjectRequest.builder()
                .name(getName())
                .surname(getSurname())
                .email(getEmail())
                .password(getPassword())
                .build();
    }
}
