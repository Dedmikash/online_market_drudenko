package com.gmail.dedmikash.market.service;

import com.gmail.dedmikash.market.service.model.UserDTO;

import java.util.List;

public interface UserService {
    void add(UserDTO userDTO);

    UserDTO readByUsername(String username);

    List<UserDTO> getUsersBatch(int page);

    int countPages();
}
