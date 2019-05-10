package com.gmail.dedmikash.market.web.controller;

import com.gmail.dedmikash.market.service.RoleService;
import com.gmail.dedmikash.market.service.UserService;
import com.gmail.dedmikash.market.service.model.RoleDTO;
import com.gmail.dedmikash.market.service.model.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final RoleService roleService;

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String getUsers(@RequestParam(name = "page", required = false) Integer page, Model model) {
        if (page == null) {
            page = 1;
        }
        List<UserDTO> userDTOList = userService.getUsersBatch(page);
        List<RoleDTO> roleDTOList = roleService.getRoles();
        model.addAttribute("users", userDTOList);
        model.addAttribute("roles", roleDTOList);
        model.addAttribute("pages", userService.getCountOfUsersPages());
        logger.info("Getting users {}, page {}", userDTOList, page);
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
        Map<Long, String> validChanges = new HashMap<>();
        for (String change : changes) {
            String[] elements = change.split(",");
            if (!elements[1].equals(elements[2])) {
                validChanges.put(Long.parseLong(elements[0]), elements[2]);
            }
        }
        userService.changeUsersRolesById(validChanges);
        logger.info("Changing roles of users with ids: {} ", validChanges.keySet());
        return "redirect:/users";
    }

    @PostMapping("/change_pass")
    public String changePasswords(@RequestParam(value = "change_pass", required = false) String[] usernames) {
        if (usernames == null) {
            return "redirect:/users";
        }
        userService.changeUsersPasswordsByUsernames(usernames);
        logger.info("Changing passwords of users with usernames: {} ", Arrays.toString(usernames));
        return "redirect:/users";
    }

    @GetMapping("/add")
    public String addUser(UserDTO userDTO, Model model) {
        List<RoleDTO> roleDTOList = roleService.getRoles();
        model.addAttribute("roles", roleDTOList);
        return "usersadd";
    }

    @PostMapping("/add")
    public String addUser(
            @Valid UserDTO userDTO,
            BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            List<RoleDTO> roleDTOList = roleService.getRoles();
            model.addAttribute("roles", roleDTOList);
            logNotValidUserAdding(result);
            return "usersadd";
        } else {
            if (userService.readByUsername(userDTO.getUsername()) != null) {
                logger.info("Attempt to create user with same email: {}", userDTO.getUsername());
                List<RoleDTO> roleDTOList = roleService.getRoles();
                model.addAttribute("roles", roleDTOList);
                model.addAttribute("duplicate", "User with such email already exists in DB");
                return "usersadd";
            }
            logger.info("Trying to add user: {} {} {} {} {}", userDTO.getSurname(), userDTO.getName(),
                    userDTO.getPatronymic(), userDTO.getUsername(), userDTO.getRoleDTO().getName());
            userService.add(userDTO);
            return "redirect:/users";
        }
    }

    private void logNotValidUserAdding(BindingResult result) {
        logger.info("Not valid user adding: {}", result.getFieldErrors().stream()
                .map(fieldError -> fieldError.getField().concat(" : ")
                        .concat(Objects.requireNonNull(fieldError.getRejectedValue()).toString())
                        .concat(" - ").concat(Objects.requireNonNull(fieldError.getDefaultMessage())))
                .collect(Collectors.toList()).toString());
    }
}
