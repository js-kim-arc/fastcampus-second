package com.fastcampus.projectboard.repository;

import com.fastcampus.projectboard.domain.Article;
import com.fastcampus.projectboard.domain.QArticle;
import com.fastcampus.projectboard.repository.querydsl.ArticleRepositoryCustom;
import com.querydsl.core.types.dsl.DateExpression;
import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.nio.channels.FileChannel;
import java.util.List;

@RepositoryRestResource
public interface ArticleRepository extends
        JpaRepository<Article, Long>,
        ArticleRepositoryCustom,
        QuerydslPredicateExecutor<Article>,
        QuerydslBinderCustomizer<QArticle> {


    @Override
    default void customize(QuerydslBindings bindings, QArticle root) {
        bindings.excludeUnlistedProperties(true);
        bindings.including(root.title,root.createdAt,root.createdBy);
        // 기본 바인딩 설정 (예: 문자열은 대소문자 무시 검색)
        bindings.bind(root.title).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.createdAt).first((path, value) -> path.eq(value));
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase);
    }


    Page<Article> findByTitleContaining(String title, Pageable pageable);

    Page<Article> findByContentContaining(String searchKeyword, Pageable pageable);

    Page<Article> findByUserAccount_UserIdContaining(String searchKeyword, Pageable pageable);

    Page<Article> findByUserAccount_NicknameContaining(String searchKeyword, Pageable pageable);


    void deleteByIdAndUserAccount_UserId(long articleId, String userId);
}