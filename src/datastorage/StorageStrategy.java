package datastorage;

import com.fasterxml.jackson.databind.node.ObjectNode;

public interface StorageStrategy {
    void addCategory(String categoryName);

    void addClient(String consumerUsername, int consumerBalance);
    
     void addProduct(String productName, String productCategory, String productQuantityString,
                     String productPriceString, String productMaxQuantityString);
    
     void removeProduct(String productName);
    
     void showAll();
    
     void showByCategory(String categoryName);
    
     void showCategories();
    
     void showProduct(String productName);

     void switchPrintStrategy(String strategyName, String ...args);

     void doTransaction(String consumerUsername, String productName, String productQuantityString);

     void incrementProductQuantity(String productName, String addedQuantity);

     boolean hasCategory(String categoryName);
    
     boolean hasProduct(String productName, String categoryName);
    
     boolean noSuchProduct(String productName);
    
     boolean noSuchConsumer(String consumerName);

     void write(String message);

     void closeConnections();

     String getPrintStrategy();

     ObjectNode toObjectNode();
}
