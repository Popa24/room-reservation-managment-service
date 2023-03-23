package com.example.demo.user.service;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class UserDomainObject {
    @NonNull
    Long id;
    @NonNull
    String name;
    @NonNull
    String surname;
    @NonNull
    String email;
    @NonNull
    String password;
    @NonNull
    String roles;
}
