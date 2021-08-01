package shop;


import dataprocessing.PrintStrategy;
import dataprocessing.PrintStrategyFactory;

import java.util.*;

public class Shop {
    private final Map<String, Map<String, Product>> products = new HashMap<>();
    private final List<Consumer> consumers = new ArrayList<>();
    public PrintStrategy output = PrintStrategyFactory.createStrategy("CONSOLE");
    private static Shop instance = null;

    private Shop() {
    }

    public static Shop getInstance() {
        if (instance == null) {
            instance = new Shop();
        }
        return instance;
    }

    public void addClient(Consumer consumer) {
        consumers.add(consumer);
    }

    public void addCategory(String category){
        products.put(category, new HashMap<>());
    }

    public void addProduct(String category, String productName, Product product){
        products.get(category).put(productName, product);
    }

    public void removeProduct(String name){
        for (String category : products.keySet()) {
            if (products.get(category).containsKey(name)) {
                products.get(category).remove(name);
                return;
            }
        }
    }

    public void showAll() {
        int i = 1;
        for (String category : products.keySet()) {
            for (Product product : products.get(category).values()) {
                output.print(i + " " + product.getName() + " " + product.getQuantity() + " " +
                        category + " " + product.getPrice());
                i++;
            }
        }
    }

    public void showByCategory(String category){
        int i = 1;
        for (Product product : products.get(category).values()) {
            output.print(i + " " + product);
            i++;
        }
    }

    public void showCategories() {
        String categories = String.join(",", products.keySet());
        output.print(categories);
    }

    public void showProduct(String product) {
        for (String category : products.keySet()) {
            if (products.get(category).containsKey(product)) {
                output.print(products.get(category).get(product).toString());
            }
        }
    }

    public void showDisplayMode() {
        output.print(output.getDisplayMode());
    }

    public boolean hasCategory(String category) {
        return products.containsKey(category);
    }
    public boolean hasProduct(String category, String product) {
        return products.get(category).containsKey(product);
    }
    public boolean noSuchProduct(String product) {

        if (products.isEmpty()) {
            return true;
        }

        for (String category : products.keySet()) {
            if (products.get(category).containsKey(product)) {
                return false;
            }
        }
        return true;
    }
    public boolean hasConsumer(String username) {
        for (Consumer c : consumers) {
            if (c.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public Consumer getConsumer(String username) {
        for (Consumer c : consumers) {
            if (c.getUsername().equals(username)) {
                return c;
            }
        }
        return null;
    }

    public Product getProduct(String name) {
        for (String category : products.keySet()) {
            if (products.get(category).containsKey(name)) {
                return products.get(category).get(name);
            }
        }
        return null;
    }

    public List<Consumer> getConsumers() {
        return consumers;
    }

    public Map<String, Map<String, Product>> getProducts() {
        return products;
    }

    public void write(String message) {
        output.print(message);
    }
}
