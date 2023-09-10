package com.example.RESTAPI.SocialMediaApplication.User.controller;

import com.example.RESTAPI.SocialMediaApplication.User.entity.Post;
import com.example.RESTAPI.SocialMediaApplication.User.entity.User;
import com.example.RESTAPI.SocialMediaApplication.User.Exception.UserNotFoundException;
import com.example.RESTAPI.SocialMediaApplication.User.repository.UserRepository;
import com.example.RESTAPI.SocialMediaApplication.User.service.UserDaoService;
import jakarta.validation.Valid;
import org.springframework.context.MessageSource;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class UserJpaResource {

    private UserDaoService service;
    private MessageSource messageSource;

    private UserRepository repository;

    public UserJpaResource(UserDaoService service, MessageSource messageSource, UserRepository repository) {
        this.service = service;
        this.messageSource = messageSource;
        this.repository = repository;
    }

    //http://localhost:8080/jpa/users
    @GetMapping("/jpa/users")
    public List<User> findAll(){
        return repository.findAll();
    }


    //http://localhost:8080/jpa/users/10001
    @GetMapping("jpa/users/{id}")
    public EntityModel<User> retriveUser(@PathVariable int id){
        Optional<User> user = repository.findById(id);
        if(user.isEmpty())
            throw new UserNotFoundException("id: "+id);

        EntityModel<User> entityModel = EntityModel.of(user.get());
        WebMvcLinkBuilder link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findAll());
        entityModel.add(link.withRel("all-users"));
        return entityModel;
    }

    @DeleteMapping("/jpa/users/{id}")
    public void deleteUser(@PathVariable int id){
        repository.deleteById(id);
    }

    @PostMapping("/jpa/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){
        User savedUser = repository.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/jpa/users/{id}/posts")
    public List<Post> retrievePostsForUser(@PathVariable int id) {
        Optional<User> user = repository.findById(id);

        if(user.isEmpty())
            throw new UserNotFoundException("id:"+id);

        return user.get().getPosts();

    }


}
