package org.example.shoppingmall.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewRequestDTO {

    private Long reviewId;  // 수정/삭제에서 사용
    private Long productId; // 리뷰 작성/redirect 용

    private int rating;
    private String content;
}
