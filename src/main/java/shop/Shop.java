package shop;

import com.fasterxml.jackson.databind.node.ObjectNode;
import datastorage.StorageStrategy;
import datastorage.StorageStrategyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Shop {
    private final StorageStrategy storage;
    @Autowired
    public Shop() {
        storage = StorageStrategyFactory
                .createStrategy("src/main/resources/config.properties");
    }

    public String getDisplayMode() {
        return storage.getPrintStrategy();
    }

    public void showDisplayMode() {
        storage.write(storage.getPrintStrategy());
    }

    public void write(String message) {
        storage.write(message);
    }

    public void addNewCategory(String categoryName) {
        storage.addCategory(categoryName);
    }

    public void undoAddNewCategory(String categoryName) {
        storage.removeCategory(categoryName);
    }

    public void addNewProduct(String productName, String categoryName,
                              String quantity, String price, String maxQuantity) {
        storage.addProduct(productName, categoryName, quantity, price, maxQuantity);
    }

    public void addNewProduct(String productName, String categoryName,
                              int quantity, int price, int maxQuantity) {
        storage.addProduct(productName, categoryName, quantity, price, maxQuantity);
    }

    public void undoAddNewProduct(String productName, String categoryName) {

    }

    public Product fetchProductByName(String productName) {
        return storage.getProduct(productName);
    }

    public void doTransaction(String username, String productName, String quantity) {
        storage.doTransaction(username, productName, quantity);
    }

    public void undoTransaction(String username, String productName, String quantity) {
        storage.undoTransaction(username, productName, quantity);
    }

    public void close() {
        storage.closeConnections();
    }

    public void showCategories() {
        storage.showCategories();
    }

    public boolean noSuchProduct(String productName) {
        return storage.noSuchProduct(productName);
    }

    public void showProduct(String productName) {
        storage.showProduct(productName);
    }

    public void showAll() {
        storage.showAll();
    }

    public boolean noSuchCategory(String categoryName) {
        return !storage.hasCategory(categoryName);
    }

    public void showByCategory(String categoryName) {
        storage.showByCategory(categoryName);
    }

    public void removeProduct(String productName) {
        storage.removeProduct(productName);
    }

    public void replenish(String productName, String quantity) {
        storage.incrementProductQuantity(productName, quantity);
    }

    public void undoReplenish(String productName, String quantity) {
        storage.decrementProductQuantity(productName, quantity);
    }


    public void switchDisplayMode(String strategyName, String ...args) {
        storage.switchPrintStrategy(strategyName, args);
    }

    public ObjectNode toObjectNode() {
        return storage.toObjectNode();
    }
}
