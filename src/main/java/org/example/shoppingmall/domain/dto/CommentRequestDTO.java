package org.example.shoppingmall.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDTO {

    private Long reviewId;   // 댓글이 달린 리뷰 ID
    private Long commentId;  // 수정할 때 사용 (작성때는 null)

    private String content;
}
