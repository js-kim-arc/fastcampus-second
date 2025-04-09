package com.fastcampus.projectboard.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@DisplayName("View 컨트롤러 인증 테스트- 진행중")
@WebMvcTest
public class AuthControllerTest {
    private MockMvc mvc;

    public AuthControllerTest(@Autowired MockMvc mvc){this.mvc=mvc;}
    @DisplayName("로그인 페이지 - 정상 호출 ")
    @Test
    void givenNothing_whenTryToLogin_thenReturnsLoginPage() throws Exception {
        //given
        //when
        mvc.perform(get("/login"))
           .andExpect(status().isOk())
           .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
        //Then

    }


}
