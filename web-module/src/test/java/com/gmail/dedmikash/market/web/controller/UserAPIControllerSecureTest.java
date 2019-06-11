package com.gmail.dedmikash.market.web.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.dedmikash.market.service.model.RoleDTO;
import com.gmail.dedmikash.market.service.model.UserDTO;
import com.gmail.dedmikash.market.web.app.WebModuleApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.gmail.dedmikash.market.web.constant.RolesConstants.ADMIN;
import static com.gmail.dedmikash.market.web.constant.RolesConstants.CUSTOMER;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = WebModuleApplication.class)
public class UserAPIControllerSecureTest {
    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;
    private UserDTO userDTO = new UserDTO();

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
        userDTO.setUsername("username@gmail.com");
        userDTO.setPassword("password");
        userDTO.setName("test");
        userDTO.setSurname("testov");
        userDTO.setPatronymic("testovich");
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setName(CUSTOMER);
        userDTO.setRoleDTO(roleDTO);

    }

    @WithMockUser(authorities = ADMIN)
    @Test
    public void saveUserShouldSucceedWith201CodeForAdmin() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        byte[] jsonQuery = mapper.writeValueAsBytes(userDTO);

        mvc.perform(post("/api/v1/users")
                .contentType(APPLICATION_JSON)
                .content(jsonQuery));
    }

    @WithMockUser(authorities = CUSTOMER)
    @Test
    public void saveUserShouldSucceedWith201CodeForUser() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        byte[] jsonQuery = mapper.writeValueAsBytes(userDTO);

        mvc.perform(post("/api/v1/users")
                .contentType(APPLICATION_JSON)
                .content(jsonQuery))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/403"));
    }
}
