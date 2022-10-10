package com.jojoldu.webservice.web;

import com.jojoldu.webservice.config.auth.SecurityConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.core.Is.is;
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = HelloController.class,
            excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
        classes = SecurityConfig.class)
            })
public class HelloControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @WithMockUser(roles = "USER")
    public void hello_return() throws Exception{
        String hello = "hello";
        mvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(hello));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void hello_dto_return() throws Exception{
        String name = "hello";
        int amount = 1000;

        mvc.perform(get("/hello/dto")
                                .param("name",name)
                                .param("amount",String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is(name)))
                .andExpect(jsonPath("$.amount", is(amount)));
    }
}
