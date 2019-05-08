package com.gmail.dedmikash.market.web.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("test")
public class CommonControllerTest {
    private MockMvc mockMvc;

    @Before
    public void init() {
        CommonController commonController = new CommonController();
        mockMvc = MockMvcBuilders.standaloneSetup(commonController).build();
    }

    @Test
    public void shouldGetLoginPageForLoginUrl() throws Exception {
        mockMvc.perform(get("/login.html"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/login"));
    }

    @Test
    public void shouldGetLoginPageForSlashUrl() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }
}
