package com.gmail.dedmikash.market.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CommonController {
    private static final Logger logger = LoggerFactory.getLogger(CommonController.class);

    @GetMapping("/login")
    public String login() {
        logger.debug("Login page accessed");
        return "/login";
    }

    @GetMapping("/")
    public String slash() {
        logger.debug("Slash page accessed -> redirected to login page");
        return "redirect:/login";
    }
}
