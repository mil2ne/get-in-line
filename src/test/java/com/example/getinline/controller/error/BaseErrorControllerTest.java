package com.example.getinline.controller.error;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BaseErrorController.class)
class BaseErrorControllerTest {

    private final MockMvc mvc;

    public BaseErrorControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @Test
    void 베이스에러_컨트롤러_페이지없음_정상에러_테스트() throws Exception {
        // Given

        // When
        mvc.perform(get("/wrong-uri"))
                .andExpect(status().isNotFound())
                .andDo(print());

        // Then
    }

}