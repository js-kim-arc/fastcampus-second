package com.fastcampus.projectboard.repository.querydsl;

import com.fastcampus.projectboard.domain.QHashtag;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class HashtagRepositoryCustomImpl implements HashtagRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<String> findAllHashtagNames() {
        QHashtag hashtag=QHashtag.hashtag;

        return jpaQueryFactory.select(hashtag.hashtagName)
                .from(hashtag)
                .fetch();
    }
}
