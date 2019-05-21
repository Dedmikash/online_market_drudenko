package com.gmail.dedmikash.market.service.impl;

import com.gmail.dedmikash.market.repository.RoleRepository;
import com.gmail.dedmikash.market.repository.UserRepository;
import com.gmail.dedmikash.market.repository.model.User;
import com.gmail.dedmikash.market.service.UserService;
import com.gmail.dedmikash.market.service.converter.UserConverter;
import com.gmail.dedmikash.market.service.model.PageDTO;
import com.gmail.dedmikash.market.service.model.UserDTO;
import com.gmail.dedmikash.market.service.util.RandomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserConverter userConverter;
    private final UserRepository userRepository;
    private final RandomService randomService;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserConverter userConverter,
                           UserRepository userRepository,
                           RandomService randomService,
                           PasswordEncoder passwordEncoder,
                           RoleRepository roleRepository) {
        this.userConverter = userConverter;
        this.userRepository = userRepository;
        this.randomService = randomService;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public void saveUser(UserDTO userDTO) {
        User user = userConverter.fromDTO(userDTO);
        String password = randomService.getNewPassword();
        String hashedPassword = passwordEncoder.encode(password);
        user.setPassword(hashedPassword);
        user.getProfile().setAddress("-");
        user.getProfile().setTelephone("-");
        userRepository.create(user);
    }

    @Override
    @Transactional
    public UserDTO readByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return null;
        }
        return userConverter.toDTO(user);
    }

    @Override
    @Transactional
    public PageDTO<UserDTO> getUsers(int page) {
        PageDTO<UserDTO> users = new PageDTO<>();
        List<UserDTO> userDTOS = getPageOfUsers(page);
        users.setList(userDTOS);
        users.setCountOfPages(userRepository.getCountOfPages());
        return users;
    }

    @Override
    @Transactional
    public void deleteUsersByIds(Long[] ids) {
        for (Long id : ids) {
            userRepository.delete(userRepository.findById(id));
        }
    }

    @Override
    @Transactional
    public void changeUsersPasswordsByUsernames(Long[] ids) { //TODO email sending
        Map<String, String> emails = new HashMap<>();
        Map<Long, String> newPasswords = new HashMap<>();
        for (Long id : ids) {
            emails.put(userRepository.findById(id).getUsername(), randomService.getNewPassword());
            newPasswords.put(id, randomService.getNewPassword());
        }
        for (Map.Entry<Long, String> entry : newPasswords.entrySet()) {
            User user = userRepository.findById(entry.getKey());
            user.setPassword(passwordEncoder.encode(entry.getValue()));
            userRepository.update(user);
        }
        for (Map.Entry<String, String> entry : emails.entrySet()) {
            logger.info("Password of user with username: {} - was changed to: {}. Email has been sent."
                    , entry.getKey(), entry.getValue());
        }
    }

    @Override
    @Transactional
    public void changeUsersRolesById(Map<Long, Long> changes) {
        for (Map.Entry<Long, Long> change : changes.entrySet()) {
            User user = userRepository.findById(change.getKey());
            user.setRole(roleRepository.findById(change.getValue()));
            userRepository.update(user);
        }
    }

    @Override
    @Transactional
    public UserDTO getUserById(Long id) {
        return userConverter.toDTO(userRepository.findById(id));
    }

    @Override
    @Transactional
    public int updateUserProfileAndPassword(UserDTO userDTO,
                                            String oldPassword,
                                            String newPassword) {
        User user = userRepository.findById(userDTO.getId());
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.getProfile().setAddress(userDTO.getProfileDTO().getAddress());
        user.getProfile().setTelephone(userDTO.getProfileDTO().getTelephone());
        return getUpdatingProfileSuccessCode(oldPassword, newPassword, user);
    }

    private int getUpdatingProfileSuccessCode(String oldPassword, String newPassword, User user) {
        if (!newPassword.equals("") || !oldPassword.equals("")) {
            if (passwordEncoder.matches(oldPassword, user.getPassword())) {
                user.setPassword(passwordEncoder.encode(newPassword));
                userRepository.update(user);
                return 1;
            } else {
                userRepository.update(user);
                return 0;
            }
        } else {
            userRepository.update(user);
            return -1;
        }
    }

    private List<UserDTO> getPageOfUsers(int page) {
        return userRepository.findPage(page)
                .stream()
                .map(userConverter::toDTO)
                .collect(Collectors.toList());
    }
}
