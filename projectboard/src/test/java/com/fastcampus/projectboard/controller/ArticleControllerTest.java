package com.fastcampus.projectboard.controller;

import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@DisplayName("View 컨트롤러 - 게시글 ")
@WebMvcTest
@AutoConfigureMockMvc
class ArticleControllerTest {
    private final MockMvc mvc;

    ArticleControllerTest(@Autowired MockMvc mvc)
    {
        this.mvc = mvc;
    }

    @DisplayName("[view][Get]- 게시글 리스트 페이지 - 정상 호출 ")
    @Test
    void givenNothing_whenRequestingArticlesView_thenReturnsArticlesView() throws Exception {
        //given
        //when
        mvc.perform(get("/articles"))
           .andExpect(status().isOk())
           .andExpect(content().contentType(MediaType.TEXT_HTML))
           .andExpect(model().attributeExists("articles"));
        //Then

    }

    @DisplayName("[view][Get]- 게시글 상세 페이지 테스트 - 정상 호출 ")
    @Test
    void givenNothing_whenRequestingArticlesDetailView_thenReturnsArticlesDetailPage() throws Exception {
        //given

        //when
        mvc.perform(get("/articles/1"))
           .andExpect(status().isOk())
           .andExpect(content().contentType(MediaType.TEXT_HTML))
           .andExpect(model().attributeExists("articles/1"));
        //Then

    }

    @DisplayName("[view][Get]- 게시글 검색 페이지 테스트 - 정상 호출 ")
    @Test
    void givenNothing_whenRequestingArticlesSearchView_thenReturnsArticlesSearchView() throws Exception {
        //given

        //when
        mvc.perform(get("/articles/search"))
           .andExpect(status().isOk())
           .andExpect(content().contentType(MediaType.TEXT_HTML))
           .andExpect(model().attributeExists("articles/1"));
        //Then

    }

    @DisplayName("[view][Get]- 게시글 해시태그 검색 페이지 테스트 - 정상 호출 ")
    @Test
    void givenNothing_whenRequestingArticlesHashtagSearchView_thenReturnsArticlesHashtagView() throws Exception {
        //given

        //when
        mvc.perform(get("/articles/search-hashtag"))
           .andExpect(status().isOk())
           .andExpect(content().contentType(MediaType.TEXT_HTML))
           .andExpect(model().attributeExists("articles/1"));
        //Then

    }



}

