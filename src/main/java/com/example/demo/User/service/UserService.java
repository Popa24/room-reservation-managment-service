package com.example.demo.user.service;

import com.example.demo.user.repository.UserRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    @NonNull
    final UserRepository userRepository;

    public UserService(@NonNull final UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @NonNull
    public UserDomainObject save(@NonNull final CreateUserDomainObjectRequest createUserDomainObjectRequest) {
        if (isEmailInUse(createUserDomainObjectRequest.getEmail())) {
            throw new IllegalStateException("Email already in use");
        }
        return userRepository.save(createUserDomainObjectRequest);
    }
    @NonNull
    public boolean isEmailInUse(String email) {
        return findByEmail(email) != null;
    }

    public String getPassword(Long userId) {
        return userRepository.getPassword(userId);
    }


    public UserInfoDto getUserInfo(Integer userId) {

        UserDomainObject userDomainObject = userRepository.getById(userId);

        return UserInfoDto.builder()
                .user_id(userId)
                .email(userDomainObject.getEmail())
                .build();
    }
    @NonNull
    public UserDomainObject getById(@NonNull final  Integer id){
        return userRepository.getById(id);
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
