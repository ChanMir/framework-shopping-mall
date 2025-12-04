package org.example.shoppingmall.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InquiryReplyRequestDTO {
    private Long inquiryId;  // 어떤 문의글에 달리는지
    private Long replyId;    // 수정 시 사용
    private String content;
}
