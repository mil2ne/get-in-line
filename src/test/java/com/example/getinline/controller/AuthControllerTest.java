package com.example.getinline.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    private final MockMvc mvc;

    public AuthControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @DisplayName("[view][GET] 로그인 페이지")
    @Test
    void givenNothing_whenRequestingLogin_thenReturnsLoginPage() throws Exception {
        // Given

        // When & Then
        mvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(content().string(containsString("This is login page.")))
                .andExpect(view().name("auth/login"));
    }

    @DisplayName("[view][GET] 회원가입 페이지")
    @Test
    void givenNothing_whenRequestingSignUpPage_thenReturnsSignUpPages() throws Exception {
        // Given

        // When & Then
        mvc.perform(get("/sign-up"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(content().string(containsString("This is sign-up page.")))
                .andExpect(view().name("auth/sign-up"));
    }
}