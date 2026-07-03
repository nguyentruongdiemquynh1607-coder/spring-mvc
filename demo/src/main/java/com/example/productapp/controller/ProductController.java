package com.example.productapp.controller;

import com.example.productapp.model.Product;
import com.example.productapp.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    // Tiêm (Inject) ProductService vào để dùng chung dữ liệu
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    //Nhận thêm tham số "keyword" để tìm kiếm
    @GetMapping
    public String listProducts(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<Product> list;
        if (keyword != null) {
            list = productService.searchProductsByName(keyword);
        } else {
            list = productService.getAllProducts();
        }
        model.addAttribute("products", list);
        model.addAttribute("keyword", keyword); // Gửi lại từ khóa ra giao diện để hiển thị ở ô tìm kiếm
        return "products";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("product", new Product());
        return "add-product";
    }

    @PostMapping("/add")
    public String addProduct(@Valid @ModelAttribute Product product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "add-product";
        }
        productService.addProduct(product);
        return "redirect:/products";
    }

    @GetMapping("/{id}")
    public String productDetail(@PathVariable int id, Model model) {
        Product product = productService.getProductById(id); // Gọi sang Service
        model.addAttribute("product", product);
        return "product-detail";
    }

    //Xóa sản phẩm dựa vào ID truyền từ nút bấm
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable int id) {
        productService.deleteProduct(id); // Gọi sang Service xử lý xóa
        return "redirect:/products";
    }
}