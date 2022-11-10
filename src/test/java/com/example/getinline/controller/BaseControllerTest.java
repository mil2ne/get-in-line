package com.example.getinline.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@WebMvcTest
class BaseControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void 베이스컨트롤러_루트페이지_정상호출테스트() throws Exception {
        // Given

        // When
        mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(content().string(containsString("This is root page.")))
                .andExpect(view().name("index"))
                .andDo(print());

        // Then
    }

    @Test
    void 베이스컨트롤러_루트페이지_정상호출테스트_두번째방법() throws Exception {
        // Given

        // When
        ResultActions result = mvc.perform(get("/"));


        // Then
        result
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(content().string(containsString("This is root page.")))
                .andExpect(view().name("index"))
                .andDo(print());
    }
}