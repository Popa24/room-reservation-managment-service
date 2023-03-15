package com.example.demo.User;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.methodOn;

@RestController
public class UserController {
    private final UserRepository userRepository;
    private final UserModelAssembler modelAssembler;
    UserController(UserRepository userRepository, UserModelAssembler modelAssembler){
        this.userRepository=userRepository;
        this.modelAssembler=modelAssembler;
    }
    @GetMapping("user")
    CollectionModel<EntityModel<userDomain>> all(){
       List<EntityModel<userDomain>> users=userRepository.findAll().stream().map(modelAssembler::toModel).collect(Collectors.toList());
       return CollectionModel.of(users,
               linkTo(methodOn(UserController.class).all()).withSelfRel());
    }
    @PostMapping("user")
    ResponseEntity <?> newUser(@RequestBody userDomain newUser){
        EntityModel<userDomain> entityModel=modelAssembler.toModel(userRepository.save(newUser));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }
    @GetMapping("user/{id}")
    EntityModel<userDomain> one(@PathVariable Long id)  {
        userDomain user= userRepository.findById(id).orElseThrow(()-> new UserNotFoundException(id));
     return modelAssembler.toModel(user);
    }
    @PutMapping("user/{id}")
    ResponseEntity<?> replaceEmployee(@RequestBody userDomain newUser, @PathVariable Long id) {

        userDomain updatedUser = userRepository.findById(id) //
                .map(user -> {
                    user.setName(newUser.getName());
                    user.setSurname(newUser.getSurname());
                    user.setEmail(newUser.getEmail());
                    return userRepository.save(user);
                }) //
                .orElseGet(() -> {
                    newUser.setId(id);
                    return userRepository.save(newUser);
                });

        EntityModel<userDomain> entityModel = modelAssembler.toModel(updatedUser);

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @DeleteMapping("user/{id}")
   ResponseEntity<?> deleteUser(@PathVariable Long id){
        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
