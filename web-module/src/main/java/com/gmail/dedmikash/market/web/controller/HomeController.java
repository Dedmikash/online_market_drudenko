package com.gmail.dedmikash.market.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @GetMapping("/login")
    public String login() {
        logger.info("Login page accessed");
        return "/login";
    }

    @GetMapping("/")
    public String slash() {
        logger.info("Slash page accessed -> redirected to login page");
        return "redirect:/login";
    }
}
