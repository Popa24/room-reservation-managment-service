package com.example.demo.user.service;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class UserInfoDto {
    @NonNull
    int user_id;
    @NonNull
    String email;
}
