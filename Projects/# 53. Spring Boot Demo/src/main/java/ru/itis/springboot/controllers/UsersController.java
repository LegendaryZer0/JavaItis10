package ru.itis.springboot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.itis.springboot.dto.UserDto;
import ru.itis.springboot.services.UsersService;

import java.util.List;

@Controller
public class UsersController {
    @Autowired
    private UsersService usersService;

    @GetMapping(value = "/users")
    public String getUsersPage(Model model) {
        List<UserDto> users = usersService.getAllUsers();
        model.addAttribute("users", users);

        return "users";
    }
}
