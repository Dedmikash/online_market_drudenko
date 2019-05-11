package com.gmail.dedmikash.market.service.util.impl;

import com.gmail.dedmikash.market.service.util.RandomService;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RandomServiceImpl implements RandomService {
    private final Random random = new Random();

    @Override
    public String getNewPassword() {
        StringBuilder newPassword = new StringBuilder();
        for (int i = 0; i < 20; i++) {
            newPassword.append((char) (random.nextInt(90 - 65 + 1) + 65));
        }
        return newPassword.toString();
    }
}
