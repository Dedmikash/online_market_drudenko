package com.gmail.dedmikash.market.service.impl;

import com.gmail.dedmikash.market.repository.RoleRepository;
import com.gmail.dedmikash.market.repository.exception.StatementException;
import com.gmail.dedmikash.market.repository.model.Role;
import com.gmail.dedmikash.market.service.RoleService;
import com.gmail.dedmikash.market.service.converter.RoleConverter;
import com.gmail.dedmikash.market.service.model.RoleDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RoleServiceUnitTest {
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private RoleConverter roleConverter;
    @Mock
    private Connection connection;
    private RoleService roleService;
    private Role firstRole = new Role(1L, "test1");
    private Role secondRole = new Role(2L, "test2");
    private List<Role> roleList = Arrays.asList(firstRole, secondRole);

    @Before
    public void init() {
        roleService = new RoleServiceImpl(roleRepository, roleConverter);
    }

    @Test
    public void shouldReturnRightRoleDTOsListWhenGetRoles() throws StatementException {
        when(roleRepository.getConnection()).thenReturn(connection);
        when(roleRepository.findAll()).thenReturn(roleList);
        when(roleConverter.toDTO(firstRole)).thenReturn(new RoleDTO(firstRole.getName()));
        when(roleConverter.toDTO(secondRole)).thenReturn(new RoleDTO(secondRole.getName()));
        Assert.assertEquals("test1", roleService.getRoles().get(0).getName());
        Assert.assertEquals("test2", roleService.getRoles().get(1).getName());
    }
}
