package com.gmail.dedmikash.market.service.converter.impl;

import com.gmail.dedmikash.market.repository.model.Profile;
import com.gmail.dedmikash.market.repository.model.Role;
import com.gmail.dedmikash.market.repository.model.User;
import com.gmail.dedmikash.market.service.converter.ProfileConverter;
import com.gmail.dedmikash.market.service.converter.UserConverter;
import com.gmail.dedmikash.market.service.model.ProfileDTO;
import com.gmail.dedmikash.market.service.model.RoleDTO;
import com.gmail.dedmikash.market.service.model.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class UserConverterImpl implements UserConverter {
    private final ProfileConverter profileConverter;

    public UserConverterImpl(ProfileConverter profileConverter) {
        this.profileConverter = profileConverter;
    }

    @Override
    public UserDTO toDTO(User user) {
        if (user != null) {
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
                roleDTO.setId(user.getRole().getId());
            }
            userDTO.setRoleDTO(roleDTO);
            ProfileDTO profileDTO = new ProfileDTO();
            if (user.getProfile() != null) {
                profileDTO = profileConverter.toDTO(user.getProfile());
            }
            userDTO.setProfileDTO(profileDTO);
            userDTO.setBlocked(user.isBlocked());
            return userDTO;
        } else return null;
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
            role.setId(userDTO.getRoleDTO().getId());
        }
        user.setRole(role);
        Profile profile = new Profile();
        if (userDTO.getProfileDTO() != null) {
            profile = profileConverter.fromDTO(userDTO.getProfileDTO());
        }
        user.setProfile(profile);
        user.setBlocked(userDTO.isBlocked());
        return user;
    }
}
