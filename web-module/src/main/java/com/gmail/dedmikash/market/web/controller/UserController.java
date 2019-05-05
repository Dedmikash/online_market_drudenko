package com.gmail.dedmikash.market.web.controller;

import com.gmail.dedmikash.market.service.RoleService;
import com.gmail.dedmikash.market.service.UserService;
import com.gmail.dedmikash.market.service.model.RoleDTO;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final RoleService roleService;

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/users/{page}")
    public String getUsers(@PathVariable("page") int page, Model model) {
        List<UserDTO> userDTOList = userService.getUsersBatch(page);
        List<RoleDTO> roleDTOList = roleService.getRoles();
        model.addAttribute("users", userDTOList);
        model.addAttribute("roles", roleDTOList);
        model.addAttribute("pages", userService.countPages());
        logger.info("Getting users {}, page {}", userDTOList, page);
        return "users";
    }

    @PostMapping("/users/delete_users")
    public String deleteUsers(@RequestParam(value = "delete", required = false) Long[] ids) {
        if (ids == null) {
            return "redirect:/users/1";
        }
        logger.info("Deleting users with ids: {} ", Arrays.toString(ids));
        userService.deleteUsersByIds(ids);
        return "redirect:/users/1";
    }

    @PostMapping("/users/change_roles")
    public String changeRoles(@RequestParam(value = "change_role", required = false) String[] changes) {
        if (changes == null) {
            return "redirect:/users/1";
        }
        Map<Long, String> validChanges = new HashMap<>();
        for (String change : changes) {
            String[] elements = change.split(",");
            if (!elements[1].equals(elements[2])) {
                validChanges.put(Long.parseLong(elements[0]), elements[2]);
            }
        }
        userService.changeUsersRolesById(validChanges);
        logger.info("Changing roles of users with ids:: {} ", validChanges.keySet());
        return "redirect:/users/1";
    }

    @PostMapping("/users/change_pass")
    public String changePasswords(@RequestParam(value = "change_pass", required = false) String[] usernames) {
        if (usernames == null) {
            return "redirect:/users/1";
        }
        userService.changeUsersPasswordsByUsernames(usernames);
        logger.info("Changing passwords of users with usernames: {} ", Arrays.toString(usernames));
        return "redirect:/users/1";
    }
}
