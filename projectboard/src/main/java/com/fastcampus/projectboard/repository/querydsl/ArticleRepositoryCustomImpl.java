package com.fastcampus.projectboard.repository.querydsl;

import com.fastcampus.projectboard.domain.Article;
import com.fastcampus.projectboard.domain.QArticle;
import com.fastcampus.projectboard.domain.QHashtag;
import com.querydsl.core.QueryFactory;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class ArticleRepositoryCustomImpl implements ArticleRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    public List<String> findAllDistinctHashtags(){
        QArticle article=QArticle.article;
        return jpaQueryFactory
                .select(article.hashtags.any().hashtagName)
                .from(article)
                .distinct()
                .fetch();

    }

    @Override
    public Page<Article> findByHashtagNames(Collection<String> hashtagNames, Pageable pageable) {
        QHashtag hashtag = QHashtag.hashtag;
        QArticle article = QArticle.article;

        JPQLQuery<Article> query = jpaQueryFactory.
                selectFrom(article)
                .innerJoin(article.hashtags, hashtag)
                .where(hashtag.hashtagName.in(hashtagNames));

        List<Article> articles = jpaQueryFactory.
                selectFrom(article)
                .innerJoin(article.hashtags,hashtag)
                .where(hashtag.hashtagName.in(hashtagNames))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        long total=query.fetchCount();

        return new PageImpl<>(articles, pageable, total);
    }
}
