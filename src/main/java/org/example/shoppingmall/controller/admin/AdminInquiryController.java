package org.example.shoppingmall.controller.admin;

import lombok.RequiredArgsConstructor;
import org.example.shoppingmall.Service.InquiryService;
import org.example.shoppingmall.domain.Inquiry;
import org.example.shoppingmall.repository.InquiryRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/inquiries")
public class AdminInquiryController {

    private final InquiryService inquiryService;

    @GetMapping("")
    public String inquiryList(Model model) {
        model.addAttribute("inquiries", inquiryService.findAll());
        return "admin/inquiry-list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("inquiry", inquiryService.findById(id));
        return "admin/inquiry-detail";
    }

    @PostMapping("/{id}/answer")
    public String answer(@PathVariable Long id, @RequestParam String answer) {
        inquiryService.answerInquiry(id, answer);
        return "redirect:/admin/inquiries";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        inquiryService.delete(id);
        return "redirect:/admin/inquiries";
    }
}
