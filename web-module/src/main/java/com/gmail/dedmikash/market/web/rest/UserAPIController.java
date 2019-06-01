package com.gmail.dedmikash.market.web.rest;

import com.gmail.dedmikash.market.service.UserService;
import com.gmail.dedmikash.market.service.model.UserDTO;
import com.gmail.dedmikash.market.web.validator.UserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserAPIController {
    private static final Logger logger = LoggerFactory.getLogger(UserAPIController.class);
    private final UserService userService;
    private final UserValidator userValidator;

    public UserAPIController(UserService userService,
                             UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @PostMapping
    @SuppressWarnings(value = "unchecked")
    public ResponseEntity saveUser(@RequestBody UserDTO userDTO,
                                   BindingResult result) {
        userValidator.validate(userDTO, result);
        if (result.hasErrors()) {
            return new ResponseEntity(result.toString(), HttpStatus.BAD_REQUEST);
        }
        userService.saveUser(userDTO);
        logger.info("Added user: {} - with REST API", userDTO.getUsername());
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @SuppressWarnings(value = "unchecked")
    public ResponseEntity getUser(@PathVariable("id") Long id) {
        UserDTO userDTO = userService.getUserById(id);
        if (userDTO != null) {
            logger.info("User with id: {} - was shown with REST API", id);
            return new ResponseEntity(userDTO, HttpStatus.OK);
        } else {
            logger.info("User with id: {} - wasn't shown with REST API. No such article or it was soft deleted", id);
            return new ResponseEntity("No user with such id in DB or it was soft deleted", HttpStatus.NOT_FOUND);
        }
    }
}
