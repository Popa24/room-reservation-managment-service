package com.example.demo.user.controller;

import com.example.demo.user.service.UserDomainObject;
import com.example.demo.user.service.UserService;
import lombok.NonNull;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
public class UserController {

    private final UserService userService;

    UserController(@NonNull final UserService userService) {

        this.userService = userService;
    }


    @PostMapping("user")
    public ResponseEntity<JsonUserDomainResponse> newUser(@RequestBody @NonNull final JsonUpsertUserDomainRequest request) {
        final UserDomainObject userDomainObject= userService.save(UserControllerHelper.toCreateUserRequest(request));
        return ResponseEntity.ok().body(JsonUserDomainResponse.toJson(userDomainObject));
    }
    @PutMapping("user/{id}")
    public ResponseEntity<JsonUserDomainResponse> updateUser(@RequestBody @NonNull final JsonUpsertUserDomainRequest request, @PathVariable Long id) {
        final UserDomainObject userDomainObject = userService.update(UserControllerHelper.toUserDomainObject(request,id));
        return ResponseEntity.ok().body(JsonUserDomainResponse.toJson(userDomainObject));
    }


}


    //    @GetMapping("user")
//    CollectionModel<EntityModel<userDomain>> all() {
//        List<EntityModel<userDomain>> users = userRepository.findAll().stream().map(modelAssembler::toModel).collect(Collectors.toList());
//        return CollectionModel.of(users,
//                linkTo(methodOn(UserController.class).all()).withSelfRel());
//    }


//    @GetMapping("user/{id}")
//    EntityModel<userDomain> one(@PathVariable Long id) {
//        userDomain user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
//        return modelAssembler.toModel(user);
//    }
//
//    @PutMapping("user/{id}")
//    ResponseEntity<?> replaceEmployee(@RequestBody userDomain newUser, @PathVariable Long id) {
//
//        userDomain updatedUser = userRepository.findById(id) //
//                .map(user -> {
//                    user.setName(newUser.getName());
//                    user.setSurname(newUser.getSurname());
//                    user.setEmail(newUser.getEmail());
//                    return userRepository.save(user);
//                }) //
//                .orElseGet(() -> {
//                    newUser.setId(id);
//                    return userRepository.save(newUser);
//                });
//
//        EntityModel<userDomain> entityModel = modelAssembler.toModel(updatedUser);
//
//        return ResponseEntity //
//                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
//                .body(entityModel);
//    }
//
//    @DeleteMapping("user/{id}")
//    ResponseEntity<?> deleteUser(@PathVariable Long id) {
//        userRepository.deleteById(id);
//        return ResponseEntity.noContent().build();
//    }

