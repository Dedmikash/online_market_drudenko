package com.gmail.dedmikash.market.web.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.dedmikash.market.service.UserService;
import com.gmail.dedmikash.market.service.model.RoleDTO;
import com.gmail.dedmikash.market.service.model.UserDTO;
import com.gmail.dedmikash.market.web.rest.UserAPIController;
import com.gmail.dedmikash.market.web.validator.UserValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.gmail.dedmikash.market.web.constant.RolesConstants.CUSTOMER;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class UserAPIControllerUnitTest {
    private MockMvc mockMvc;
    @Mock
    private UserService userService;
    @Mock
    private UserValidator userValidator;

    @Before
    public void init() {
        UserAPIController userAPIController = new UserAPIController(userService, userValidator);
        mockMvc = MockMvcBuilders.standaloneSetup(userAPIController).build();
    }

    @Test
    public void saveUserShouldSucceedWith201Code() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("username");
        userDTO.setPassword("password");
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setName(CUSTOMER);
        userDTO.setRoleDTO(roleDTO);
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        byte[] jsonQuery = mapper.writeValueAsBytes(userDTO);

        mockMvc.perform(post("/api/v1/users")
                .contentType(APPLICATION_JSON)
                .content(jsonQuery))
                .andExpect(status().isCreated());
    }
}
