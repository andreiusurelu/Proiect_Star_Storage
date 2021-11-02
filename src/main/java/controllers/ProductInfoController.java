package controllers;

import entity.Category;
import entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import services.CategoryService;
import services.ProductService;

import java.util.List;

@RestController
public class ProductInfoController {
    private CategoryService categoryService;
    private ProductService productService;

    @Autowired
    private void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Autowired
    private void setProductService(ProductService productService) {
        this.productService = productService;
    }
    //PRINT PRODUCTS ALL
    @GetMapping("/products")
    private List<Product> getAllProducts() {
        return productService.getProducts();
    }
    //PRINT PRODUCTS CATEGORY {CATEGORY_NAME}
    @GetMapping("products/category/{categoryId}")
    private List<Product> getProductsByCategory(@PathVariable Integer categoryId) {
        return productService.getProducts(categoryId);
    }
    //PRINT PRODUCTS {PRODUCT_NAME}
    @GetMapping("products/name/{productName}")
    private Product getProduct(@PathVariable String productName) {
        return productService.getProduct(productName);
    }
    //PRINT CATEGORIES
    @GetMapping("/categories")
    private List<Category> getCategories() {
        return categoryService.getCategories();
    }
}
