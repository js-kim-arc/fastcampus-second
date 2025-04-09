package com.fastcampus.projectboard.controller;

import com.fastcampus.projectboard.config.SecurityConfig;
import com.fastcampus.projectboard.domain.Article;
import com.fastcampus.projectboard.domain.type.SearchType;
import com.fastcampus.projectboard.dto.ArticleWithCommentsDto;
import com.fastcampus.projectboard.dto.HashtagDto;
import com.fastcampus.projectboard.dto.UserAccountDto;
import com.fastcampus.projectboard.service.ArticleService;
import com.fastcampus.projectboard.service.PaginationService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@DisplayName("View 컨트롤러 - 게시글 ")
@WebMvcTest(ArticleController.class)
@Import({SecurityConfig.class,FormDataEncoder.class})
@AutoConfigureMockMvc
class ArticleControllerTest {

    private final MockMvc mvc;


    @MockitoBean
    private ArticleService articleService;
    @MockitoBean
    private PaginationService paginationService;

    ArticleControllerTest(@Autowired MockMvc mvc)
    {
        this.mvc = mvc;
    }

    @DisplayName("[view][Get]- 게시글 리스트 페이지 - 정상 호출 ")
    @Test
    void givenNothing_whenRequestingArticlesView_thenReturnsArticlesView() throws Exception {
        //given

        given(articleService.searchArticles(eq(null),eq(null),any(Pageable.class))).willReturn(Page.empty());

        //when
        mvc.perform(get("/articles"))
           .andExpect(status().isOk())
           .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
           .andExpect(view().name("articles/index"))
           .andExpect(model().attributeExists("articles"))
           .andExpect(model().attributeExists("paginationBarNumbers"))
           .andExpect(model().attributeExists("searchTypes"));
        then(articleService).should().searchArticles(eq(null),eq(null),any(Pageable.class));
        then(paginationService).should().getPaginationBarNumbers(anyInt(),anyInt());

        //Then

    }
    @Disabled("구현중")
    @DisplayName("[view][Get][상세]- 게시글 상세 페이지 테스트 - 정상 호출 ")
    @Test
    void givenSearchKeyword_whenRequestingArticlesDetailView_thenReturnsArticlesDetailPage() throws Exception {
        //given
        SearchType searchType = SearchType.TITLE;
        String searchValue = "title";
        Long articleId=1L;
        given(articleService.searchArticles(eq(searchType),eq(searchValue),any(Pageable.class))).willReturn(Page.empty());
        given(paginationService.getPaginationBarNumbers(anyInt(), anyInt())).willReturn(List.of(0, 1, 2, 3, 4));

        //when
        mvc.perform(get("/articles").queryParam("searchType", searchType.name())
           .queryParam("searchValue", searchValue))
           .andExpect(status().isOk())
           .andExpect(content().contentType(MediaType.TEXT_HTML))
           .andExpect(view().name("articles/detail"))
           .andExpect(model().attributeExists("articles"))
           .andExpect(model().attributeExists("articleComment"));
        //Then
        then(articleService).should().getArticle(articleId);

    }

    @Disabled("구현중")
    @DisplayName("[view][Get]- 게시글 검색 전용 페이지 테스트 - 정상 호출 ")
    @Test
    void givenNothing_whenRequestingArticlesSearchView_thenReturnsArticlesSearchView() throws Exception {
        //given

        //when
        mvc.perform(get("/articles/search"))
           .andExpect(status().isOk())
           .andExpect(content().contentType(MediaType.TEXT_HTML))
           .andExpect(view().name("articles/search"))
           .andExpect(model().attributeExists("articles/1"));
        //Then

    }

    @Disabled("구현중")
    @DisplayName("[view][Get]- 게시글 해시태그 검색 페이지 테스트 - 정상 호출 ")
    @Test
    void givenNothing_whenRequestingArticlesHashtagSearchView_thenReturnsArticlesHashtagView() throws Exception {
        //given

        //when
        mvc.perform(get("/articles/search-hashtag"))
           .andExpect(status().isOk())
           .andExpect(content().contentType(MediaType.TEXT_HTML))
           .andExpect(view().name("articles/hashtag"))
           .andExpect(model().attributeExists("articles/1"));
        //Then

    }

    // 해시태그 검색 관련 테스트



    private ArticleWithCommentsDto createArticleWithCommentsDto() {
        return ArticleWithCommentsDto.of(
                1L,
                createUserAccountDto(),
                Set.of(),
                "title",
                "content",
                Set.of(HashtagDto.of("java")),
                LocalDateTime.now(),
                "uno",
                LocalDateTime.now(),
                "uno"
                                        );
    }

    private UserAccountDto createUserAccountDto() {
        return UserAccountDto.of(
                "uno",
                "pw",
                "uno@mail.com",
                "Uno",
                "memo",
                LocalDateTime.now(),
                "uno",
                LocalDateTime.now(),
                "uno"
                                );
    }






}

