package datastorage;

import com.fasterxml.jackson.databind.node.ObjectNode;
import shop.Product;

public interface StorageStrategy {
    void addCategory(String categoryName);

    void removeCategory(String categoryName);

    void addClient(String consumerUsername, int consumerBalance);

    void addProduct(String productName, String productCategory, int productQuantity,
                    int productPrice, int productMaxQuantity);
    
    void addProduct(String productName, String productCategory, String productQuantityString,
                     String productPriceString, String productMaxQuantityString);


    Product getProduct(String productName);
    
    void removeProduct(String productName);

    void removeProduct(String productName, String categoryName);
    
    void showAll();
    
    void showByCategory(String categoryName);
    
    void showCategories();
    
    void showProduct(String productName);

    void switchPrintStrategy(String strategyName, String ...args);

    void doTransaction(String consumerUsername, String productName, String productQuantityString);

    void undoTransaction(String consumerUsername,
                         String productName, String productQuantityString);

    void incrementProductQuantity(String productName, String addedQuantity);

    void decrementProductQuantity(String productName, String substractedQuantity);

    boolean hasCategory(String categoryName);
    
    boolean hasProduct(String productName, String categoryName);
    
    boolean noSuchProduct(String productName);
    
    boolean noSuchConsumer(String consumerName);

    void write(String message);

    void closeConnections();

    String getPrintStrategy();

    ObjectNode toObjectNode();
}
