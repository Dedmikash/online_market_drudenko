package com.gmail.dedmikash.market.service;

import com.gmail.dedmikash.market.service.model.PageDTO;
import com.gmail.dedmikash.market.service.model.UserDTO;

import java.util.Map;

public interface UserService {
    void add(UserDTO userDTO);

    UserDTO readByUsername(String username);

    PageDTO<UserDTO> getUsers(int page);

    void deleteUsersByIds(Long[] ids);

    void changeUsersPasswordsByUsernames(String[] usernames);

    void changeUsersRolesById(Map<Long, String> changes);
}
