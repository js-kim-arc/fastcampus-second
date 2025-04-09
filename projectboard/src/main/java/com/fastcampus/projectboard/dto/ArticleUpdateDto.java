package com.fastcampus.projectboard.dto;

import com.fastcampus.projectboard.domain.Article;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link Article}
 */
public record ArticleUpdateDto(
        String title,
        String content,
        String hashtag
) implements Serializable {
    public static ArticleUpdateDto of(
                                      String title,
                                      String content,
                                      String hashtag){
        return new ArticleUpdateDto(title, content,hashtag);
    }

}