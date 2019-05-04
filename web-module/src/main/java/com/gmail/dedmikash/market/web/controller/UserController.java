package com.gmail.dedmikash.market.web.controller;

import com.gmail.dedmikash.market.service.UserService;
import com.gmail.dedmikash.market.service.model.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;

@Controller
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/{page}")
    public String getUsers(@PathVariable("page") int page, Model model) {
        List<UserDTO> userDTOList = userService.getUsersBatch(page);
        model.addAttribute("users", userDTOList);
        model.addAttribute("pages", userService.countPages());
        logger.info("Getting users {}, page {}", userDTOList, page);
        return "users";
    }

    @PostMapping("/delete_users")
    public String deleteUsers(@RequestParam(value = "delete", required = false) Long[] ids) {
        if (ids == null) {
            return "redirect:/users/1";
        }
        logger.info("Deleting users with ids: {} ", Arrays.toString(ids));
        userService.deleteUsersByIds(ids);
        return "redirect:/users/1";
    }

}
