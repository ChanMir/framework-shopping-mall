package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class ShoppingMallController {

    @GetMapping("/")
    public String main_page() {
        return "mainpage";
    }
    @GetMapping("/login")
    public String login_page() {
        return "login";
    }
    @GetMapping("/register")
    public String register_page() {
        return "register";
    }
    @GetMapping("/product")
    public String product_page() {
        return "product";
    }

    // 레파지토리
    // 서비스
    // 컨트롤러
}
