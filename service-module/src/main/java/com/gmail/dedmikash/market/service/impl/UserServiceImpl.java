package com.gmail.dedmikash.market.service.impl;

import com.gmail.dedmikash.market.repository.RoleRepository;
import com.gmail.dedmikash.market.repository.UserRepository;
import com.gmail.dedmikash.market.repository.model.User;
import com.gmail.dedmikash.market.service.UserService;
import com.gmail.dedmikash.market.service.converter.UserConverter;
import com.gmail.dedmikash.market.service.model.PageDTO;
import com.gmail.dedmikash.market.service.model.UserDTO;
import com.gmail.dedmikash.market.service.util.RandomService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserConverter userConverter;
    private final UserRepository userRepository;
    private final RandomService randomService;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final JavaMailSender javaMailSender;

    public UserServiceImpl(UserConverter userConverter,
                           UserRepository userRepository,
                           RandomService randomService,
                           PasswordEncoder passwordEncoder,
                           RoleRepository roleRepository,
                           JavaMailSender javaMailSender) {
        this.userConverter = userConverter;
        this.userRepository = userRepository;
        this.randomService = randomService;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.javaMailSender = javaMailSender;
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
        user.getProfile().setUser(user);
        userRepository.create(user);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getUsername());
        message.setSubject("Welcome!");
        message.setText("Welcome to our site! Your password: " + password + ".");
        javaMailSender.send(message);
    }

    @Override
    @Transactional
    public UserDTO readByUsername(String username) {
        User user = userRepository.findNonDeletedByUsername(username);
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
        users.setCountOfPages(userRepository.getCountOfNonDeletedPages());
        return users;
    }

    @Override
    @Transactional
    public void deleteUsersByIds(Long[] ids) {
        for (Long id : ids) {
            User user = userRepository.findNonDeletedById(id);
            if (user != null && !user.isDeleted()) {
                userRepository.delete(user);
            }
        }
    }

    @Override
    @Transactional
    public void changeUsersPasswordsById(Long[] ids) {
        for (Long id : ids) {
            String password = randomService.getNewPassword();
            User user = userRepository.findNonDeletedById(id);
            user.setPassword(passwordEncoder.encode(password));
            userRepository.update(user);
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(user.getUsername());
            message.setSubject("Changing password");
            message.setText("Your new password: " + password + ".");
            javaMailSender.send(message);
        }
    }

    @Override
    @Transactional
    public void changeUsersRolesById(Map<Long, Long> changes) {
        for (Map.Entry<Long, Long> change : changes.entrySet()) {
            User user = userRepository.findNonDeletedById(change.getKey());
            user.setRole(roleRepository.findById(change.getValue()));
            userRepository.update(user);
        }
    }

    @Override
    @Transactional
    public UserDTO getUserById(Long id) {
        return userConverter.toDTO(userRepository.findNonDeletedById(id));
    }

    @Override
    @Transactional
    public int updateUserProfileAndPassword(UserDTO userDTO,
                                            String oldPassword,
                                            String newPassword) {
        User user = userRepository.findNonDeletedById(userDTO.getId());
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
        return userRepository.findNonDeletedPage(page)
                .stream()
                .map(userConverter::toDTO)
                .collect(Collectors.toList());
    }
}
