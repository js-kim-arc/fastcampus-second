package com.fastcampus.projectboard.repository.querydsl;

import com.fastcampus.projectboard.domain.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;

public interface ArticleRepositoryCustom {

    Page<Article> findByHashtagNames(Collection<String> hashtagNames, Pageable pageable);

    @Deprecated
    List<String> findAllDistinctHashtags();
}
