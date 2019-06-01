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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("test")
public class ErrorControllerTest {
    private MockMvc mockMvc;

    @Before
    public void init() {
        ErrorController errorController = new ErrorController();
        mockMvc = MockMvcBuilders.standaloneSetup(errorController).build();
    }

    @Test
    public void shouldGet403PageFor403Url() throws Exception {
        mockMvc.perform(get("error/403.html"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("error/403"));
    }
}
