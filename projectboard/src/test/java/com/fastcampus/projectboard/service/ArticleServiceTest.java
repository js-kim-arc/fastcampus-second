package com.fastcampus.projectboard.service;

import com.fastcampus.projectboard.domain.Article;
import com.fastcampus.projectboard.domain.Hashtag;
import com.fastcampus.projectboard.domain.UserAccount;
import com.fastcampus.projectboard.domain.type.SearchType;
import com.fastcampus.projectboard.dto.*;
import com.fastcampus.projectboard.repository.ArticleRepository;
import com.fastcampus.projectboard.repository.HashtagRepository;
import com.fastcampus.projectboard.repository.UserAccountRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;


@DisplayName("비즈니스 로직 - 게시글 비즈니스 로직 테스트")
@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    @InjectMocks private ArticleService sut;
    @Mock private ArticleRepository articleRepository;
    @Mock private UserAccountRepository userAccountRepository;
    @Mock private HashtagRepository hashtagRepository;
    @Mock private HashtagService hashtagService;

    @DisplayName("searchAtricle-게시글 search 용 - 리스트를 반납한다. ")
    @Test
    void givenSearchParameters_whenSearchingArticlesthenReturnslists(){
        //우선 하나만으로 테스트 ㄱㄱ 검색어 tdd 기반
        //given
        SearchType searchType = SearchType.TITLE;
        String searchKeyword = "title";
        Pageable pageable = Pageable.ofSize(20);
        given(articleRepository.findByTitleContaining(searchKeyword, pageable)).willReturn(Page.empty());

        //when
        Page<ArticleDto> articles=sut.searchArticles(searchType,searchKeyword,pageable);//제목,본문, id, 닉네임, 해시테그 --->> ㅇㅇ

        //then
        assertThat(articles).isNotNull();
    }

    @DisplayName("getArtilceWithComment-게시글을 get용 - 어떤 개인이 쓴 게시글을 반환한다. ")
    @Test
    void givenGetOrdering_whenGetArticles_thenReturns(){
        //우선 하나만으로 테스트 ㄱㄱ 검색어 tdd 기반
        //given
        Long articleId=1L;
        Article article = createArticle();
        given(articleRepository.findById(articleId)).willReturn(Optional.of(article));

        //when
        ArticleWithCommentsDto dto=sut.getArticleWithComment(articleId);

        //then
        assertThat(dto).hasFieldOrPropertyWithValue("title",article.getTitle())
                .hasFieldOrPropertyWithValue("content",article.getContent())
                .hasFieldOrPropertyWithValue("hashtag",article.getHashtags().stream()
                                                              .map(HashtagDto::from)
                        .collect(Collectors.toUnmodifiableSet()));
    }

    //해시태그 검색용->> 아직은 전부 articleService로 구성한 기능들임 ㅇㅇ
    @DisplayName("검색어 없이 게시글 해시태그 검색하면, 빈페이지 반환")
    @Test
    void givenNoSearchParameter_whenRequestingArticlesHashtagSearch_thenReturnsArticlesHashtagView() throws Exception {
        //given
        Pageable pageable=Pageable.ofSize(20);

        //when
        Page<ArticleDto> articles=sut.searchArticlesViaHashtag(null,pageable);

        //Then
        assertThat(articles).isEqualTo(Page.empty(pageable));

    }

    @DisplayName("검색어 있을 때 게시글 해시태그 검색하면, 게시글 페이지를 반환한다.")
    @Test
    void givenSearchParameter_whenRequestingArticlesHashtagSearch_thenReturnsArticlesPage() throws Exception {
        //given
        String hashtag="#java";
        Pageable pageable=Pageable.ofSize(20);
        given(articleRepository.findByHashtagNames(List.of(hashtag),pageable)).willReturn();

        //when
        Page<ArticleDto> articles=sut.searchArticlesViaHashtag(hashtag,pageable);

        //Then
        assertThat(articles).isEqualTo(Page.empty(pageable));
        then(articleRepository).should().findByHashtagNames(List.of(hashtag),pageable);

    }

    @DisplayName("해시태그를 조회하면, 유니크 해시태그 리스트를 반환한다.")
    @Test
    void givenNothing_whenCalling_thenReturnsHashtags() {
        // Given
        Article article = createArticle();
        List<String> expectedHashtags = List.of("java", "spring", "boot");
        given(hashtagRepository.findAllHashtagNames()).willReturn(expectedHashtags);

        // When
        List<String> actualHashtags = sut.getHashtags();

        // Then
        assertThat(actualHashtags).isEqualTo(expectedHashtags);
        then(hashtagRepository).should().findAllHashtagNames();
    }




    @DisplayName("게시글을 save용 - 어떤 개인이 쓴 게시글을 반환한다. ")
    @Test
    void givenSearchParameters_whenSearchingArticles_thenReturns(){
        //우선 하나만으로 테스트 ㄱㄱ 검색어 tdd 기반
        //given
        ArticleDto dto=createArticleDto();
        given(articleRepository.save(any(Article.class))).willReturn(null);

        //when
        sut.saveArticle(dto);

        //then
        then(articleRepository).should().save(any(Article.class));
    }

    @DisplayName("게시글 업데이트 용- 게시글을 수정한다.게시글의 id와 수정정보를 입력")
    @Test
    void givenArticleIdAndModifiedInfo_whenUpdateArticle_thenReturnsModifiesArticle(){
        //우선 하나만으로 테스트 ㄱㄱ 검색어 tdd 기반
        //given
        Article article=createArticle();
        ArticleDto dto=createArticleDto("새 타이틀","새 내용 ");
        given(articleRepository.getReferenceById(dto.id())).willReturn(article);

        //when
        sut.updateArticle(dto);

        //then
        assertThat(article)
                .hasFieldOrPropertyWithValue("title",dto.title())
                .hasFieldOrPropertyWithValue("content",dto.content());
        then(articleRepository).should().getReferenceById(dto.id());

    }

    @DisplayName("게시글 삭제용- 게시글을 삭제한다.")
    @Test
    void givenArticeId_whenDeleteArticle_thenReturnsDeleteArticle(){
        //우선 하나만으로 테스트 ㄱㄱ 검색어 tdd 기반
        //given
        willDoNothing().given(articleRepository).delete(any(Article.class));

        //when
        sut.deleteArticle(1L,"uno");

        //then
        then(articleRepository).should().delete(any(Article.class));
    }

    private UserAccount createUserAccount() {
        return createUserAccount("uno");
    }

    private UserAccount createUserAccount(String userId) {
        return UserAccount.of(
                userId,
                "password",
                "uno@email.com",
                "Uno",
                null
                             );
    }
    private Article createArticle() {
        return createArticle(1L);
    }

    private Article createArticle(Long id) {
        Article article = Article.of(
                createUserAccount(),
                "title",
                "content"
                                    );
        article.addHashtags(Set.of(
                createHashtag(1L, "java"),
                createHashtag(2L, "spring")
                                  ));
        ReflectionTestUtils.setField(article, "id", id);

        return article;
    }

    private Hashtag createHashtag(String hashtagName) {
        return createHashtag(1L, hashtagName);
    }

    private Hashtag createHashtag(Long id, String hashtagName) {
        Hashtag hashtag = Hashtag.of(hashtagName);
        ReflectionTestUtils.setField(hashtag, "id", id);

        return hashtag;
    }
    private HashtagDto createHashtagDto() {
        return HashtagDto.of("java");
    }

    private ArticleDto createArticleDto() {
        return createArticleDto("title", "content");
    }

    private ArticleDto createArticleDto(String title, String content) {
        return ArticleDto.of(
                1L,
                createUserAccountDto(),
                title,
                content,
                null,
                LocalDateTime.now(),
                "Uno",
                LocalDateTime.now(),
                "Uno");
    }

    private UserAccountDto createUserAccountDto() {
        return UserAccountDto.of(
                "uno",
                "password",
                "uno@mail.com",
                "Uno",
                "This is memo",
                LocalDateTime.now(),
                "uno",
                LocalDateTime.now(),
                "uno"
                                );
    }


}