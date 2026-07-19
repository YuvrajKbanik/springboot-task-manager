package com.codewithraj.store.controller;

import com.codewithraj.store.entity.User;
import com.codewithraj.store.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login(){

        return "login";

    }

    @GetMapping("/register")
    public String registerPage(Model model){

        model.addAttribute("user",new User());

        return "register";

    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user,
                               Model model){

        if(userService.usernameExists(user.getUsername())){

            model.addAttribute("error",
                    "Username already exists");

            return "register";

        }

        userService.registerUser(user);

        return "redirect:/login";

    }

}