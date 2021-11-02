package services;

import entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> getProducts();

    List<Product> getProducts(int categoryId);

    void addProduct(Product product);

    void increment(int productId, int quantity);

    void decrement(int productId, int quantity);

    Product getProduct(int productId);

    Product getProduct(String name);

    void deleteProduct(int productId);
}
