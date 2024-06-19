package edu.mdc.capstone.amplify.controllers;

import edu.mdc.capstone.amplify.models.User;
import edu.mdc.capstone.amplify.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/getall")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
    @GetMapping("/getuser/{userId}")
    public User getUser(@PathVariable Long userId){
        return userService.findUser(userId);
    }
}
