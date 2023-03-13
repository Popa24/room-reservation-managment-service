package com.example.demo.User;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    private final UserRepository userRepository;
    UserController(UserRepository userRepository){
        this.userRepository=userRepository;
    }
    @GetMapping("user")
    List<userDomain> all(){
        return userRepository.findAll();
    }
    @PostMapping("user")
    userDomain newUser(@RequestBody userDomain newUser){
        return userRepository.save(newUser);
    }
    @GetMapping("user/{id}")
    userDomain one(@PathVariable Long id)  {
        return userRepository.findById(id).orElseThrow(()-> new UserNotFoundException(id));
    }
    @PostMapping("user/{id}")
    userDomain replaceUser(@RequestBody userDomain newUser,@PathVariable Long id){
        return userRepository.findById(id).map(
                user -> {
                    user.setName(newUser.getName());
                    user.setEmail(newUser.getEmail());
                    user.setSurname(newUser.getSurname());
                    user.setPassword(newUser.getPassword());
                    return userRepository.save(user);
                })
                .orElseGet(
                        ()->{
                            newUser.setId(id);
                            return userRepository.save(newUser);
                        }
                );
    }

    @DeleteMapping("user/{id}")
    void deleteUser(@PathVariable Long id){
        userRepository.deleteById(id);
    }
}
