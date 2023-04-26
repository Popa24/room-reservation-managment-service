package com.example.demo.user.service;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class CreateUserDomainObjectRequest {
    @NonNull
    String name;
    @NonNull
    String surname;
    @NonNull
    String email;
    @NonNull
    String password;
}
