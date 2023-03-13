package com.example.demo.User;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


@Component
public class UserModelAssembler implements RepresentationModelAssembler<userDomain, EntityModel<userDomain>> {
    @Override
    public EntityModel<userDomain> toModel(userDomain user){
        return EntityModel.of(user,
                linkTo(methodOn(UserController.class).one(user.getId())).withSelfRel(),
                linkTo(methodOn(UserController.class).all()).withRel("user"));
    }
}
