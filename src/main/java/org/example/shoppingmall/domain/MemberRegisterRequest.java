package org.example.shoppingmall.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberRegisterRequest {
    private String username;
    private String password;
    private String email;
    private String phone;
}
