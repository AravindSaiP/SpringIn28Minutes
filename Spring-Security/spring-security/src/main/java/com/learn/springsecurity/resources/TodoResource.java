package com.learn.springsecurity.resources;

import jakarta.annotation.security.RolesAllowed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TodoResource {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private static final List<Todo> TODOS_LIST =
            List.of(
                    new Todo("admin", "Get AWS Certified"),
                    new Todo("admin", "Get MicroServices Certified"),
                    new Todo("user", "Learn AWS")
            );

    @GetMapping("/todos")
    public List<Todo> retrieveAllTodos() {
        return TODOS_LIST;
    }

    @GetMapping("/users/{username}/todos")
    @PreAuthorize("hasRole('ADMIN') and #username == authentication.name") //Only who has ROLE as ADMIN and {username} that is sent in URL should match with logged-in username
    @PostAuthorize("returnObject.name == 'admin'") //It checks the return object i.e. TODOS_LIST.get(0).name == admin or not
    @RolesAllowed({"ADMIN","USER"}) //It allows only ADMIN and USER to access this  is a jsr250 annotation to make this available we need to put jsr250Enabled = true in @EnableMethodSecurity
    @Secured({"ROLE_ADMIN","ROLE_USER"}) //Here the check is on Authorities. Roles = ADMIN, USER Authorities = ROLE_ADMIN, ROLE_USER this is another way similar to  jsr250 annotation called as secured annotation. We need to put securedEnabled = true in @EnableMethodSecurity

    public Todo retrieveTodosForSpecificUser(@PathVariable String username) {
        return TODOS_LIST.get(0);
    }

    @PostMapping("/users/{username}/todos")
    public void createTodoForSpecificUser(@PathVariable String username
            , @RequestBody Todo todo) {
        logger.info("Create {} for {}", todo, username);
    }
}

record Todo(String name, String course){};
