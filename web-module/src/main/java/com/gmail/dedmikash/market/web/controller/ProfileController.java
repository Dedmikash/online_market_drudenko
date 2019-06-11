package com.gmail.dedmikash.market.web.controller;

import com.gmail.dedmikash.market.service.UserService;
import com.gmail.dedmikash.market.service.model.AppUserPrincipal;
import com.gmail.dedmikash.market.service.model.UserDTO;
import com.gmail.dedmikash.market.web.validator.ProfileValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);
    private final UserService userService;
    private final ProfileValidator profileValidator;

    public ProfileController(UserService userService,
                             ProfileValidator profileValidator) {
        this.userService = userService;
        this.profileValidator = profileValidator;
    }

    @GetMapping
    public String getUsers(UserDTO userDTO, Model model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long id = ((AppUserPrincipal) userDetails).getId();
        model.addAttribute("user", userService.getUserById(id));
        logger.info("Got user with id: {} ", id);
        return "profile";
    }

    @PostMapping
    public String changeInfo(@ModelAttribute UserDTO userDTO,
                             @RequestParam(value = "old_password", required = false) String oldPassword,
                             @RequestParam(value = "new_password", required = false) String newPassword,
                             BindingResult result,
                             Model model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long id = ((AppUserPrincipal) userDetails).getId();
        userDTO.setId(id);
        profileValidator.validate(userDTO, result);
        model.addAttribute("user", userDTO);
        if (result.hasErrors()) {
            return "profile";
        }
        switch (userService.updateUserProfileAndPassword(userDTO, oldPassword, newPassword)) {
            case 1:
                model.addAttribute("password", "Password successfully changed");
                return "profile";
            case 0:
                model.addAttribute("password", "Wrong password");
                return "profile";
            case -1:
                return "profile";
            default:
                return "#";
        }
    }
}
