package com.gmail.dedmikash.market.service;

import com.gmail.dedmikash.market.service.model.UserDTO;

import java.util.List;
import java.util.Map;

public interface UserService {
    void add(UserDTO userDTO);

    UserDTO readByUsername(String username);

    List<UserDTO> getUsersBatch(int page);

    int countPages();

    void deleteUsersByIds(Long[] ids);

    void changeUsersPasswordsByUsernames(String[] usernames);

    void changeUsersRolesById(Map<Long, String> changes);
}
