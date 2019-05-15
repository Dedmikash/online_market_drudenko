package com.gmail.dedmikash.market.service.converter.impl;

import com.gmail.dedmikash.market.repository.model.Role;
import com.gmail.dedmikash.market.repository.model.User;
import com.gmail.dedmikash.market.service.converter.UserConverter;
import com.gmail.dedmikash.market.service.model.RoleDTO;
import com.gmail.dedmikash.market.service.model.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class UserConverterImpl implements UserConverter {
    @Override
    public UserDTO toDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        userDTO.setName(user.getName());
        userDTO.setSurname(user.getSurname());
        userDTO.setPatronymic(user.getPatronymic());
        RoleDTO roleDTO = new RoleDTO();
        if (user.getRole() != null) {
            roleDTO.setName(user.getRole().getName());
        }
        userDTO.setRoleDTO(roleDTO);
        userDTO.setBlocked(user.isBlocked());
        userDTO.setDeleted(user.isDeleted());
        return userDTO;
    }

    @Override
    public User fromDTO(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setSurname(userDTO.getSurname());
        user.setName(userDTO.getName());
        user.setPatronymic(userDTO.getPatronymic());
        Role role = new Role();
        if (userDTO.getRoleDTO() != null) {
            role.setName(userDTO.getRoleDTO().getName());
        }
        user.setRole(role);
        user.setBlocked(userDTO.isBlocked());
        user.setDeleted(userDTO.isDeleted());
        return user;
    }
}
