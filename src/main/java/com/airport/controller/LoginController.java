package com.airport.controller;

import com.airport.model.entities.UserEntity;
import com.airport.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {
    private UserService userService;

    @Autowired
    LoginController(UserService userService) {
        this.userService = userService;
    }

    @CrossOrigin
    @RequestMapping("/user/new")
    public void addUser(@RequestParam String login,
                        @RequestParam String password) {

        UserEntity userEntity = new UserEntity(login, password);
        userService.add(userEntity);
    }

    @CrossOrigin
    @RequestMapping("user/password")
    public void changePassword(@RequestParam String login,
                               @RequestParam String password) {
        userService.changePassword(login, password);
    }
}
