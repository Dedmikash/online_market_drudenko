package com.gmail.dedmikash.market.web.controller;

import com.gmail.dedmikash.market.web.app.WebModuleApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.gmail.dedmikash.market.web.constant.RolesConstants.ADMIN;
import static com.gmail.dedmikash.market.web.constant.RolesConstants.CUSTOMER;
import static com.gmail.dedmikash.market.web.constant.RolesConstants.SALE;
import static com.gmail.dedmikash.market.web.constant.RolesConstants.SECURE_API;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebModuleApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class UserControllerSecureIntegrationTest {
    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;

    @Before
    public void init() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @WithMockUser
    @Test
    public void shouldRedirectOn403PageForUsersPageForUser() throws Exception {
        mvc.perform(get("/users"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/403"));
    }

    @WithMockUser(authorities = ADMIN)
    @Test
    public void shouldSucceedForUsersPageForAdministrator() throws Exception {
        mvc.perform(get("/users"))
                .andExpect(status().isOk());
    }

    @WithMockUser(authorities = SALE)
    @Test
    public void shouldRedirectOn403PageForUsersPageForSaleUser() throws Exception {
        mvc.perform(get("/users"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/403"));
    }

    @WithMockUser(authorities = CUSTOMER)
    @Test
    public void shouldRedirectOn403PageForUsersPageForCustomerUser() throws Exception {
        mvc.perform(get("/users"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/403"));
    }

    @WithMockUser(authorities = SECURE_API)
    @Test
    public void shouldRedirectOn403PageForUsersPageForSecureApiUser() throws Exception {
        mvc.perform(get("/users"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/403"));
    }

    @WithMockUser
    @Test
    public void shouldRedirectOn403PageForDeleteUsersPageForUser() throws Exception {
        mvc.perform(post("/users/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/403"));
    }

    @WithMockUser(authorities = ADMIN)
    @Test
    public void shouldRedirectOnUsersPageForDeleteUsersPageForAdministrator() throws Exception {
        mvc.perform(post("/users/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users"));
    }

    @WithMockUser(authorities = SALE)
    @Test
    public void shouldRedirectOn403PageForDeleteUsersPageForSaleUser() throws Exception {
        mvc.perform(post("/users/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/403"));
    }

    @WithMockUser(authorities = CUSTOMER)
    @Test
    public void shouldRedirectOn403PageForDeleteUsersPageForCustomerUser() throws Exception {
        mvc.perform(post("/users/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/403"));
    }

    @WithMockUser(authorities = SECURE_API)
    @Test
    public void shouldRedirectOn403PageForDeleteUsersPageForSecureApiUser() throws Exception {
        mvc.perform(post("/users/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/403"));
    }

    @WithMockUser
    @Test
    public void shouldRedirectOn403PageForChangeRolePageForUser() throws Exception {
        mvc.perform(post("/users/change_roles"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/403"));
    }

    @WithMockUser(authorities = ADMIN)
    @Test
    public void shouldRedirectOnUsersPageForChangeRolePageForAdministrator() throws Exception {
        mvc.perform(post("/users/change_roles"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users"));
    }

    @WithMockUser(authorities = SALE)
    @Test
    public void shouldRedirectOn403PageForChangeRolePageForSaleUser() throws Exception {
        mvc.perform(post("/users/change_roles"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/403"));
    }

    @WithMockUser(authorities = CUSTOMER)
    @Test
    public void shouldRedirectOn403PageForChangeRolePageForCustomerUser() throws Exception {
        mvc.perform(post("/users/change_roles"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/403"));
    }

    @WithMockUser(authorities = SECURE_API)
    @Test
    public void shouldRedirectOn403PageForChangeRolePageForSecureApiUser() throws Exception {
        mvc.perform(post("/users/change_roles"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/403"));
    }

    @WithMockUser
    @Test
    public void shouldRedirectOn403PageForChangePasswordPageForUser() throws Exception {
        mvc.perform(post("/users/change_pass"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/403"));
    }

    @WithMockUser(authorities = ADMIN)
    @Test
    public void shouldRedirectOnUsersPageForChangePasswordPageForAdministrator() throws Exception {
        mvc.perform(post("/users/change_pass"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users"));
    }

    @WithMockUser(authorities = SALE)
    @Test
    public void shouldRedirectOn403PageForChangePasswordPageForSaleUser() throws Exception {
        mvc.perform(post("/users/change_pass"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/403"));
    }

    @WithMockUser(authorities = CUSTOMER)
    @Test
    public void shouldRedirectOn403PageForChangePasswordPageForCustomerUser() throws Exception {
        mvc.perform(post("/users/change_pass"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/403"));
    }

    @WithMockUser(authorities = SECURE_API)
    @Test
    public void shouldRedirectOn403PageForChangePasswordPageForSecureApiUser() throws Exception {
        mvc.perform(post("/users/change_pass"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/403"));
    }

    @WithMockUser
    @Test
    public void shouldRedirectOn403PageForAddUserPageForUserForPostMethod() throws Exception {
        mvc.perform(post("/users/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/403"));
    }

    @WithMockUser(authorities = SALE)
    @Test
    public void shouldRedirectOn403PageForAddUserPageForSaleUserForPostMethod() throws Exception {
        mvc.perform(post("/users/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/403"));
    }

    @WithMockUser(authorities = CUSTOMER)
    @Test
    public void shouldRedirectOn403PageForAddUserPageForCustomerUserForPostMethod() throws Exception {
        mvc.perform(post("/users/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/403"));
    }

    @WithMockUser(authorities = SECURE_API)
    @Test
    public void shouldRedirectOn403PageForAddUserPageForSecureApiUserForPostMethod() throws Exception {
        mvc.perform(post("/users/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/403"));
    }

    @WithMockUser
    @Test
    public void shouldRedirectOn403PageForAddUserPageForUserForGetMethod() throws Exception {
        mvc.perform(get("/users/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/403"));
    }

    @WithMockUser(authorities = ADMIN)
    @Test
    public void shouldSucceedForAddUserPageForAdministratorForGetMethod() throws Exception {
        mvc.perform(get("/users/new"))
                .andExpect(status().isOk());
    }

    @WithMockUser(authorities = SALE)
    @Test
    public void shouldRedirectOn403PageForAddUserPageForSaleUserForGetMethod() throws Exception {
        mvc.perform(get("/users/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/403"));
    }

    @WithMockUser(authorities = CUSTOMER)
    @Test
    public void shouldRedirectOn403PageForAddUserPageForCustomerUserForGetMethod() throws Exception {
        mvc.perform(get("/users/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/403"));
    }

    @WithMockUser(authorities = SECURE_API)
    @Test
    public void shouldRedirectOn403PageForAddUserPageForSecureApiUserForGetMethod() throws Exception {
        mvc.perform(get("/users/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/403"));
    }
}