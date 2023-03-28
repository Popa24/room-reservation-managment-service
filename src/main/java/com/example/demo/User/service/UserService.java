package com.example.demo.user.service;

import com.example.demo.user.repository.UserRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public String getPassword(Long userId) {
        return userRepository.getPassword(userId);
    }

    @NonNull
    public UserDomainObject update(@NonNull final UserDomainObject userDomainObject) {
        return userRepository.update(userDomainObject);
    }

    public UserDomainObject findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public List<UserDomainObject> getAllUsers() {
        return userRepository.getAllUsers();
    }

}
