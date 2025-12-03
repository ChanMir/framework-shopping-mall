package org.example.shoppingmall.controller.admin;

import lombok.RequiredArgsConstructor;
import org.example.shoppingmall.domain.Member;
import org.example.shoppingmall.repository.MemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/members")
@RequiredArgsConstructor
public class AdminMemberController {

    private final MemberRepository memberRepository;

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("members", memberRepository.findAll());
        return "admin/member-list";
    }

    @PostMapping("/toggle/{id}")
    public String toggleActive(@PathVariable Long id) {
        Member m = memberRepository.findById(id).orElseThrow();

        m.setActive(!m.isActive()); // true → false, false → true 자동 토글
        memberRepository.save(m);

        return "redirect:/admin/members";
    }


    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        memberRepository.deleteById(id);
        return "redirect:/admin/members";
    }
}
