package com.example.RESTAPI.SocialMediaApplication.User.service;

import com.example.RESTAPI.SocialMediaApplication.User.entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Component
public class UserDaoService {

    private static ArrayList<User> users = new ArrayList<>();
    private static int usersCount = 0;

    static {
        users.add(new User(++usersCount,"user1", LocalDate.now().minusYears(30)));
        users.add(new User(++usersCount,"user2", LocalDate.now().minusYears(30)));
        users.add(new User(++usersCount,"user3", LocalDate.now().minusYears(30)));
    }

    public List<User> findAll(){
        return users;
    }

    public User findOne(int id){
        Predicate<User> predicate = user -> user.getId().equals(id);
        //User user = users.stream().filter(predicate).findFirst().get();
        User user = users.stream().filter(predicate).findFirst().orElse(null);
        return user;
    }

    public void deleteById(int id){
        Predicate<User> predicate = user -> user.getId().equals(id);
        users.removeIf(predicate);
    }

    public User save(User user){
        user.setId(++usersCount);
        users.add(user);
        return user;
    }
}
