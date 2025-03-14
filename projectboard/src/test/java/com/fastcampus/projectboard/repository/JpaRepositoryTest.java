package com.fastcampus.projectboard.repository;

import com.fastcampus.projectboard.config.JpaConfig;
import com.fastcampus.projectboard.domain.Article;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.*;



@ActiveProfiles("testdb")
@DisplayName("jpa 연결 테스트 좀 해보겠습니다.")
@Import(JpaConfig.class)
@DataJpaTest
class JpaRepositoryTest {

    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;

    public JpaRepositoryTest(@Autowired ArticleRepository articleRepository, @Autowired ArticleCommentRepository articleCommentRepository) {
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
    }

    @DisplayName("select 테스트")
    @Test
    void givenTestData_whenSelecting_thenWorksFine() {

        //given

        //when
        List<Article> articles = articleRepository.findAll();
        //then
        assertThat(articles)
                .isNotNull()
                .hasSize(1000);

    }


    @DisplayName("insert 테스트")
    @Test
    void givenTestData_whenInsert_thenWorksFine() {

        //given
        long previousCount = articleRepository.count();

        //when
        Article savedArticle = articleRepository.save(Article.of("new article", "new content", "nwe hashtag"));

        //then
        assertThat(articleRepository.count()).isEqualTo(previousCount+1);

    }


    @DisplayName("update 테스트")
    @Test
    void givenTestData_whenupdate_thenWorksFine() {

        //given
        Article article=articleRepository.findById(1L).orElseThrow();
        String updateHashtag="#springboot";
        article.setHashtag(updateHashtag);

        //when
        Article savedArticle = articleRepository.saveAndFlush(article);

        //then
        assertThat(savedArticle).hasFieldOrPropertyWithValue("hashtag",updateHashtag);
    }

    @DisplayName("delete 테스트")
    @Test
    void givenTestData_whenudelete_thenWorksFine() {

        //given
        Article article=articleRepository.findById(1L).orElseThrow();
        long previousArticleCount=articleRepository.count();
        long previousArticleCommentCount=articleCommentRepository.count();
        long deletedCommentsSize=article.getArticleComments().size();

        //when
        articleRepository.delete(article);

        //then
        assertThat(articleRepository.count()).isEqualTo(previousArticleCount-1);
        assertThat(articleCommentRepository.count()).isEqualTo(previousArticleCommentCount-deletedCommentsSize);

    }



}