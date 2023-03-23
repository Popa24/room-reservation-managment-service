package com.example.demo.user.service;

import com.example.demo.user.repository.UserRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @NonNull
    final UserRepository userRepository;

    public UserService(@NonNull final UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @NonNull
    public UserDomainObject save(@NonNull final CreateUserDomainObjectRequest createUserDomainObjectRequest){
       return userRepository.save(createUserDomainObjectRequest);
    }
}
