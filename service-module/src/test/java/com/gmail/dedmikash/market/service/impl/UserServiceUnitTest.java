package com.gmail.dedmikash.market.service.impl;

import com.gmail.dedmikash.market.repository.UserRepository;
import com.gmail.dedmikash.market.repository.exception.StatementException;
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
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Connection;
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
    private Connection connection;
    @Mock
    private RandomService randomService;
    @Mock
    private PasswordEncoder passwordEncoder;
    private UserService userService;

    @Before
    public void init() {
        userService = new UserServiceImpl(userConverter, userRepository, randomService, passwordEncoder);
    }

    @Test
    public void shouldReturnUserByUsername() throws StatementException {
        when(userRepository.getConnection()).thenReturn(connection);
        User user = new User();
        user.setName("testname");
        user.setSurname("testsurname");
        user.setUsername("testusername");
        when(userRepository.readByUsername(connection, "testusername")).thenReturn(user);
        UserDTO userDTO = new UserDTO();
        userDTO.setName("testname");
        userDTO.setSurname("testsurname");
        userDTO.setUsername("testusername");
        when(userConverter.toDTO(user)).thenReturn(userDTO);
        Assert.assertEquals(userDTO, userService.readByUsername("testusername"));
    }

    @Test
    public void shouldReturnNullIfNullWhenReadUserByUsername() throws StatementException {
        when(userRepository.getConnection()).thenReturn(connection);
        when(userRepository.readByUsername(connection, "testusername")).thenReturn(null);
        Assert.assertNull(userService.readByUsername("testusername"));
    }

    @Test
    public void shouldReturnRightUserDTOsListWhenGetUserBatch() throws StatementException {
        when(userRepository.getConnection()).thenReturn(connection);
        User firstUser = new User();
        firstUser.setUsername("test1");
        User secondUser = new User();
        secondUser.setUsername("test2");
        List<User> userList = Arrays.asList(firstUser, secondUser);
        when(userRepository.getUsers(connection, 5)).thenReturn(userList);
        UserDTO firstUserDTO = new UserDTO();
        firstUserDTO.setUsername("test1");
        UserDTO secondUserDTO = new UserDTO();
        secondUserDTO.setUsername("test2");
        when(userConverter.toDTO(firstUser)).thenReturn(firstUserDTO);
        when(userConverter.toDTO(secondUser)).thenReturn(secondUserDTO);
        Assert.assertEquals("test1", userService.getUsersBatch(5).get(0).getUsername());
        Assert.assertEquals("test2", userService.getUsersBatch(5).get(1).getUsername());
    }
}
