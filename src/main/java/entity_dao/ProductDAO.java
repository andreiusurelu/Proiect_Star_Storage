package entity_dao;

import entity.Product;

import java.util.List;

public interface ProductDAO {
    List<Product> getProducts();
    List<Product> getProducts(int categoryId);

    void addProduct(Product product);
    void deleteProduct(int id);

    Product getProduct(int id);
    Product getProduct(String name);

    void increment(int id, int quantity);
    void decrement(int id, int quantity);
}
