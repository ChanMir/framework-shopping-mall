package org.example.shoppingmall.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.example.shoppingmall.domain.Member;
import org.example.shoppingmall.domain.Role;


import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class MemberDto {
    private Long id;
    private String username;
    private String email;
    private String phone;
    private Role role;
    private LocalDateTime createdAt;

    public static MemberDto from(Member member) {
        return MemberDto.builder()
                .id(member.getId())
                .username(member.getUsername())
                .email(member.getEmail())
                .phone(member.getPhone())
                .role(member.getRole())
                .createdAt(member.getCreatedAt())
                .build();
    }

}
