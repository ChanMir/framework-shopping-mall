package org.example.shoppingmall.controller.admin;

import lombok.RequiredArgsConstructor;
import org.example.shoppingmall.Service.ProductService;
import org.example.shoppingmall.domain.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/products")
public class AdminProductController {

    private final ProductService productService;
    @GetMapping("")
    public String productList(Model model) {
        model.addAttribute("products", productService.findAll());
        return "admin/product-list";
    }

    // 상품 등록 폼
    @GetMapping("/add")
    public String addForm() {
        return "admin/product-add";
    }

    // 상품 등록 처리
    @PostMapping("/add")
    public String addProduct(
            @RequestParam String name,
            @RequestParam BigDecimal price,
            @RequestParam Integer stock,
            @RequestParam String description,
            @RequestParam("imageFile") MultipartFile imageFile
    ) throws IOException {

        if (imageFile.isEmpty()) {
            throw new IllegalArgumentException("이미지 파일이 필요합니다.");
        }

        // ⭐ 프로젝트의 절대 경로 얻기
        String projectPath = System.getProperty("user.dir");

        // ⭐ 항상 프로젝트 안의 uploads/img 에 저장됨
        String uploadDir = projectPath + "/uploads/img/";

        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();

        // 파일명 생성
        String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();

        // 파일 저장
        File dest = new File(uploadDir + fileName);
        imageFile.transferTo(dest);

        // DB 저장용 product
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setStock(stock);
        product.setDescription(description);
        product.setImageUrl(fileName);

        productService.save(product);

        return "redirect:/admin/products";
    }


    @GetMapping("/edit/{id}")
    public String editProduct(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.findById(id));
        return "admin/product-edit";
    }

    @PostMapping("/edit")
    public String updateProduct(@ModelAttribute Product product) {
        productService.save(product);
        return "redirect:/admin/products";
    }

    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.delete(id);
        return "redirect:/admin/products";
    }
}
