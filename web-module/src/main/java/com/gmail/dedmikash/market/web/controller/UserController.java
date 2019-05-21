package com.gmail.dedmikash.market.web.controller;

import com.gmail.dedmikash.market.service.RoleService;
import com.gmail.dedmikash.market.service.UserService;
import com.gmail.dedmikash.market.service.model.PageDTO;
import com.gmail.dedmikash.market.service.model.RoleDTO;
import com.gmail.dedmikash.market.service.model.UserDTO;
import com.gmail.dedmikash.market.web.validator.UserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final RoleService roleService;
    private final UserValidator userValidator;

    public UserController(UserService userService,
                          RoleService roleService,
                          UserValidator userValidator) {
        this.userService = userService;
        this.roleService = roleService;
        this.userValidator = userValidator;
    }

    @GetMapping
    public String getUsers(@RequestParam(name = "page", defaultValue = "1") Integer page, Model model) {
        PageDTO<UserDTO> users = userService.getUsers(page);
        model.addAttribute("users", users.getList());
        model.addAttribute("pages", users.getCountOfPages());
        List<RoleDTO> roles = roleService.getRoles();
        model.addAttribute("roles", roles);
        logger.info("Getting users {}, page {}", users.getList(), page);
        return "users";
    }

    @PostMapping("/delete")
    public String deleteUsers(@RequestParam(value = "delete", required = false) Long[] ids) {
        if (ids == null) {
            return "redirect:/users";
        }
        logger.info("Deleting users with ids: {} ", Arrays.toString(ids));
        userService.deleteUsersByIds(ids);
        return "redirect:/users";
    }

    @PostMapping("/change_roles")
    public String changeRoles(@RequestParam(value = "change_role", required = false) String[] changes) {
        if (changes == null) {
            return "redirect:/users";
        }
        Map<Long, Long> validChanges = new HashMap<>();
        for (String change : changes) {
            String[] elements = change.split(",");
            if (!elements[1].equals(elements[2])) {
                validChanges.put(Long.parseLong(elements[0]), Long.parseLong(elements[2]));
            }
        }
        userService.changeUsersRolesById(validChanges);
        logger.info("Changing roles of users with ids: {} ", validChanges.keySet());
        return "redirect:/users";
    }

    @PostMapping("/change_pass")
    public String changePasswords(@RequestParam(value = "change_pass", required = false) Long[] ids) {
        if (ids == null) {
            return "redirect:/users";
        }
        userService.changeUsersPasswordsByUsernames(ids);
        logger.info("Changing passwords of users with ids: {} ", Arrays.toString(ids));
        return "redirect:/users";
    }

    @GetMapping("/new")
    public String addUser(UserDTO userDTO, Model model) {
        List<RoleDTO> roleDTOList = roleService.getRoles();
        model.addAttribute("roles", roleDTOList);
        return "usersadd";
    }

    @PostMapping("/new")
    public String addUser(
            @ModelAttribute UserDTO userDTO,
            BindingResult result,
            Model model) {
        userValidator.validate(userDTO, result);
        if (result.hasErrors()) {
            List<RoleDTO> roleDTOList = roleService.getRoles();
            model.addAttribute("roles", roleDTOList);
            return "usersadd";
        }
        logger.info("Trying to add user: {} {} {} {} {}", userDTO.getSurname(), userDTO.getName(),
                userDTO.getPatronymic(), userDTO.getUsername(), userDTO.getRoleDTO().getId());
        userService.saveUser(userDTO);
        return "redirect:/users";
    }
}
