package com.gmail.dedmikash.market.service;

import com.gmail.dedmikash.market.service.model.UserDTO;
import com.gmail.dedmikash.market.service.model.assembly.UsersWithPagesAndRoles;

import java.util.Map;

public interface UserService {
    void add(UserDTO userDTO);

    UserDTO readByUsername(String username);

    UsersWithPagesAndRoles getUsers(int page);

    void deleteUsersByIds(Long[] ids);

    void changeUsersPasswordsByUsernames(String[] usernames);

    void changeUsersRolesById(Map<Long, String> changes);
}
