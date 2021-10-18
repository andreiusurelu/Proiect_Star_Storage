package main_components;

import entity.Category;
import entity.Product;

public interface Receiver {
    void write(String message);

    int addNewCategory(String categoryName);
    void undoAddNewCategory(int ID);

    int addNewProduct(String productName, String productCategory, String quantity,
                       String price, String maxQuantity);
    int addNewProduct(Product product);
    void undoAddNewProduct(int productID);

    void doTransaction(String consumerUsername, String productName, String quantity);
    void undoTransaction(String consumerUsername, String productName, String quantity);

    void close();

    void showAll();
    void showByCategory(String categoryName);
    void showCategories();
    void showDisplayMode();
    void showProduct(String productName);

    Product removeProduct(String productName);

    void replenish(String productName, String quantity);
    void undoReplenish(String productName, String quantity);

    void switchDisplayMode(String url);
    String getDisplayMode();

    void exportData(String fileType, String filePath);
}
