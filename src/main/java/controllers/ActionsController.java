package controllers;

import entity.Category;
import entity.Consumer;
import entity.Product;
import forms.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import services.CategoryService;
import services.ConsumerService;
import services.ProductService;

@RestController
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
public class ActionsController {
    private static final Logger logger = LogManager.getLogger(ActionsController.class);
    private CategoryService categoryService;
    private ConsumerService consumerService;
    private ProductService productService;
    @Autowired
    private void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @Autowired
    private void setConsumerService(ConsumerService consumerService) {
        this.consumerService = consumerService;
    }
    @Autowired
    private void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/categories")
    private Category addNewCategory(@RequestBody CategoryForm categoryForm) {
        Category category = new Category();
        category.setName(categoryForm.getName());
        categoryService.addCategory(category);

        return categoryService.getCategory(category.getName());
    }

    @PostMapping("/products")
    private Product addNewProduct(@RequestBody ProductForm productForm) {
        Product product = new Product();
        product.setName(productForm.getName());
        product.setCategory(categoryService.getCategory(productForm.getCategoryId()));
        product.setQuantity(productForm.getQuantity());
        product.setPrice(productForm.getPrice());
        product.setMaxQuantity(productForm.getMaxQuantity());

        productService.addProduct(product);

        return productService.getProduct(product.getId());
    }

    @PostMapping("/consumers")
    private Consumer addNewConsumer(@RequestBody ConsumerForm consumerForm) {
        Consumer consumer = new Consumer();
        consumer.setConsumerUsername(consumerForm.getName());
        consumer.setConsumerBalance(consumerForm.getBalance());
        consumerService.addConsumer(consumer);

        return consumerService.getConsumer(consumer.getID());
    }

    @PutMapping("/buy")
    private void buy(@RequestBody BuyForm buyForm) {
        int productId = buyForm.getProductId(), quantity = buyForm.getQuantity();
        if (quantity <= 0) {
            logger.error("Invalid quantity.");
        }
        else {
            Product product = productService.getProduct(productId);
            int price = product.getPrice();
            productService.decrement(productId, quantity);
            consumerService.decrement(buyForm.getConsumerId(), price * quantity);
        }
    }

    @PutMapping("/replenish")
    private void replenish(@RequestBody ReplenishForm replenishForm) {
        if (replenishForm.getQuantity() <= 0) {
            logger.error("Invalid quantity.");
        }
        else {
            productService.increment(replenishForm.getProductId(), replenishForm.getQuantity());
        }
    }

    @DeleteMapping("/products/{productId}")
    private void remove(@PathVariable Integer productId) {
        Product product = productService.getProduct(productId);
        if (product.getQuantity() == 0) {
            productService.deleteProduct(productId);
        }
        else {
            logger.error("Cannot remove product, quantity is 0.");
        }
    }
}
