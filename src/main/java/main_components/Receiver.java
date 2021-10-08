package main_components;

import entity.Category;
import entity.Product;

public interface Receiver {
    void write(String message);

    void addNewCategory(String categoryName);
    void undoAddNewCategory(String categoryName);

    void addNewProduct(String productName, String productCategory, String quantity,
                       String price, String maxQuantity);
    void addNewProduct(String productName, Category productCategory, int quantity,
                       int price, int maxQuantity);
    void undoAddNewProduct(String productName, String productCategory, String quantity,
                           String price, String maxQuantity);

    Product fetchProductByName(String productName);

    void doTransaction(String consumerUsername, String productName, String quantity);
    void undoTransaction(String consumerUsername, String productName, String quantity);

    void close();

    void showAll();
    void showByCategory(String categoryName);
    void showCategories();
    void showDisplayMode();
    void showProduct(String productName);

    void removeProduct(String productName);

    void replenish(String productName, String quantity);
    void undoReplenish(String productName, String quantity);

    void switchDisplayMode(String url);
    String getDisplayMode();

    void exportData(String fileType, String filePath);
}
