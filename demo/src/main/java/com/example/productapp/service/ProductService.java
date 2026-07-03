package com.example.productapp.service;

import com.example.productapp.model.Product;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service // tầng xử lý logic dữ liệu
public class ProductService {

    private final List<Product> products = new ArrayList<>();

    public ProductService() {
        products.add(new Product(1, "Laptop", 15000000));
        products.add(new Product(2, "Mouse", 250000));
    }

    // Lấy toàn bộ danh sách sản phẩm
    public List<Product> getAllProducts() {
        return products;
    }

    // Thêm mới sản phẩm
    public void addProduct(Product product) {
        product.setId(products.size() + 1);
        products.add(product);
    }

    // Xem chi tiết theo ID
    public Product getProductById(int id) {
        return products.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    //Xóa sản phẩm theo ID
    public void deleteProduct(int id) {
        products.removeIf(p -> p.getId() == id);
    }

    //Tìm kiếm sản phẩm theo tên (không phân biệt hoa thường)
    public List<Product> searchProductsByName(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return products;
        }
        return products.stream()
                .filter(p -> p.getName().toLowerCase().contains(keyword.toLowerCase().trim()))
                .collect(Collectors.toList());
    }
}