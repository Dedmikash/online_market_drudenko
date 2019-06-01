package com.gmail.dedmikash.market.service.impl;

import com.gmail.dedmikash.market.repository.RoleRepository;
import com.gmail.dedmikash.market.repository.UserRepository;
import com.gmail.dedmikash.market.repository.model.User;
import com.gmail.dedmikash.market.service.UserService;
import com.gmail.dedmikash.market.service.converter.UserConverter;
import com.gmail.dedmikash.market.service.model.UserDTO;
import com.gmail.dedmikash.market.service.util.RandomService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceUnitTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserConverter userConverter;
    @Mock
    private RandomService randomService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private JavaMailSender javaMailSender;
    private UserService userService;

    @Before
    public void init() {
        userService = new UserServiceImpl(userConverter, userRepository, randomService,
                passwordEncoder, roleRepository, javaMailSender);
    }

    @Test
    public void shouldReturnUserByUsername() {
        User user = new User();
        user.setName("testname");
        user.setSurname("testsurname");
        user.setUsername("testusername");
        when(userRepository.findNonDeletedByUsername("testusername")).thenReturn(user);
        UserDTO userDTO = new UserDTO();
        userDTO.setName("testname");
        userDTO.setSurname("testsurname");
        userDTO.setUsername("testusername");
        when(userConverter.toDTO(user)).thenReturn(userDTO);
        Assert.assertEquals(userDTO, userService.readByUsername("testusername"));
    }

    @Test
    public void shouldReturnNullIfNullWhenReadUserByUsername() {
        when(userRepository.findNonDeletedByUsername("testusername")).thenReturn(null);
        Assert.assertNull(userService.readByUsername("testusername"));
    }

    @Test
    public void shouldReturnRightUserDTOsListWhenGetUserBatch() {
        User firstUser = new User();
        firstUser.setUsername("test1");
        User secondUser = new User();
        secondUser.setUsername("test2");
        List<User> userList = Arrays.asList(firstUser, secondUser);
        when(userRepository.findPage(5)).thenReturn(userList);
        UserDTO firstUserDTO = new UserDTO();
        firstUserDTO.setUsername("test1");
        UserDTO secondUserDTO = new UserDTO();
        secondUserDTO.setUsername("test2");
        when(userConverter.toDTO(firstUser)).thenReturn(firstUserDTO);
        when(userConverter.toDTO(secondUser)).thenReturn(secondUserDTO);
        Assert.assertEquals("test1", userService.getUsers(5).getList().get(0).getUsername());
        Assert.assertEquals("test2", userService.getUsers(5).getList().get(1).getUsername());
    }
}
