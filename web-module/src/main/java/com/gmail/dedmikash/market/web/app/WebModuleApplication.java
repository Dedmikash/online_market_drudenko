package com.gmail.dedmikash.market.web.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(
        scanBasePackages = "com.gmail.dedmikash.market.*",
        exclude = UserDetailsServiceAutoConfiguration.class
)
@EntityScan(basePackages = "com.gmail.dedmikash.market.repository.*")
public class WebModuleApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebModuleApplication.class, args);
    }
}
