package com.gmail.dedmikash.market.web.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackages = "com.gmail.dedmikash.market.web.controller")
public class AppExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(AppExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public String defaultErrorHandler(Exception e, Model model) {
        logger.error(e.getMessage(), e);
        model.addAttribute("exception", e);
        return "/error/500";
    }
}
