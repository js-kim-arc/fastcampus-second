package com.fastcampus.projectboard.service;

import com.fastcampus.projectboard.domain.Article;
import com.fastcampus.projectboard.dto.ArticleCommentDto;
import com.fastcampus.projectboard.repository.ArticleCommentRepository;
import com.fastcampus.projectboard.repository.ArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@DisplayName("비즈니스 로직 - 댓글 로직 테스트")
@ExtendWith(MockitoExtension.class)
class ArticleCommentServiceTest {
    @InjectMocks private ArticleCommentService sut;

    @Mock private ArticleCommentRepository articleCommentRepository;
    @Mock private ArticleRepository articleRepository;


    @DisplayName("댓글 리스트 조회- ")
    @Test
    void givenArticleId_whenSearchingComments_thenReturnsComments(){
        //given
        Long articleId=1L;
        given(articleRepository.findById(articleId)).willReturn(
                Optional.of(Article.of(null,"content","#java")));
        //when
        List<ArticleCommentDto> articleComments=sut.searchArticleComment(1L);

        //then
        assertThat(articleComments).isNotNull();
        then(articleRepository).should().findById(articleId);
    }

}