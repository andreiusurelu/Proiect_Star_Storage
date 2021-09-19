package datastorage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import dataprocessing.PrintStrategy;
import dataprocessing.PrintStrategyFactory;
import shop.Consumer;
import shop.Product;
import utils.Constants;
import utils.InputLoader;
import utils.VariableException;

import java.lang.reflect.Field;
import java.util.*;

public class DataStructureStorageStrategy implements StorageStrategy{
    private final Map<String, Map<String, Product>> products = new HashMap<>();
    private final List<Consumer> consumers = new ArrayList<>();
    private PrintStrategy output = PrintStrategyFactory.createStrategy("CONSOLE");

    public DataStructureStorageStrategy(String filePath) {
        InputLoader loader = new InputLoader(filePath);
        loader.readInput(this);
    }

    public Product getProduct(String productName) {
        for (String category : products.keySet()) {
            if (products.get(category).containsKey(productName)) {
                return products.get(category).get(productName);
            }
        }
        return null;
    }

    private Consumer getConsumer(String consumerName) {
        for (Consumer c : consumers) {
            if (c.getUsername().equals(consumerName)) {
                return c;
            }
        }
        return null;
    }

    @Override
    public void addCategory(String categoryName) {
        try {
            if (Constants.isIncorrectName(categoryName)) {
                throw new VariableException.InvalidCommandValueException("Invalid name.");
            }
            if (products.containsKey(categoryName)) {
                throw new VariableException.OverrideCommandException("Category "
                        + categoryName + " already exists.");
            }
            products.put(categoryName, new HashMap<>());
            write("Category added.");
        }
        catch (VariableException.InvalidCommandValueException
                | VariableException.OverrideCommandException ex) {
            write(ex.getMessage());
        }
    }

    @Override
    public void removeCategory(String categoryName) {
        products.get(categoryName).clear();
        products.remove(categoryName);
    }

    @Override
    public void addClient(String consumerUsername, int consumerBalance) {
        consumers.add(new Consumer(consumerUsername, consumerBalance));
    }

    public void addProduct(String productName, String productCategory,
                           int quantity, int price, int maxQuantity) {
        products.get(productCategory).put(productName, new Product(productName, productCategory,
                quantity, price, maxQuantity));
    }

    @Override
    public void addProduct(String productName, String productCategory,
                           String productQuantityString, String productPriceString,
                           String productMaxQuantityString) {
        try {
            if (Constants.isIncorrectName(productName)) {
                throw new VariableException.InvalidCommandValueException("Invalid product name");
            }
            if (Constants.isIncorrectName(productCategory)) {
                throw new VariableException.InvalidCommandValueException("Invalid category name");
            }
            if (Constants.isIncorrectNumber(productQuantityString)) {
                throw new VariableException.InvalidCommandValueException("Invalid quantity");
            }
            if (Constants.isIncorrectNumber(productPriceString)) {
                throw new VariableException.InvalidCommandValueException("Invalid price");
            }
            if (Constants.isIncorrectNumber(productMaxQuantityString)) {
                throw new VariableException.InvalidCommandValueException("Invalid maxQuantity");
            }
            if (hasProduct(productName, productCategory)) {
                throw new VariableException.OverrideCommandException("Product already exists");
            }

            products.get(productCategory).put(productName,
                    new Product(productName, productCategory,
                            Integer.parseInt(productQuantityString),
                            Integer.parseInt(productPriceString),
                            Integer.parseInt(productMaxQuantityString)));
            write(productQuantityString + " " + productName + " have been added to " + productCategory + " category");
        }
        catch(VariableException.InvalidCommandValueException
                | VariableException.OverrideCommandException ex) {
            write(ex.getMessage());
        }
    }


    @Override
    public void removeProduct(String productName) {
        try {
            for (String category : products.keySet()) {
                if (products.get(category).containsKey(productName)) {
                    if (products.get(category).get(productName).getQuantity() == 0) {
                        products.get(category).remove(productName);
                        output.print("Product successfully removed.");
                        return;
                    }
                    else {
                        throw new VariableException.InvalidCommandValueException("Cannot remove product.");
                    }
                }
            }
        }
        catch (VariableException.InvalidCommandValueException e) {
            write(e.getMessage());
        }
    }

    @Override
    public void removeProduct(String productName, String categoryName) {
        products.get(categoryName).remove(productName);
    }

    @Override
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

    @Override
    public void showByCategory(String categoryName) {
        int i = 1;
        for (Product product : products.get(categoryName).values()) {
            output.print(i + " " + product);
            i++;
        }
    }

    @Override
    public void showCategories() {
        String categories = String.join(",", products.keySet());
        output.print(categories);
    }

    @Override
    public void showProduct(String productName) {
        for (String category : products.keySet()) {
            if (products.get(category).containsKey(productName)) {
                output.print(products.get(category).get(productName).toString());
                return;
            }
        }
    }

    @Override
    public boolean hasCategory(String categoryName) {
        return products.containsKey(categoryName);
    }

    @Override
    public boolean noSuchConsumer(String consumerName) {
        for (Consumer c : consumers) {
            if (c.getUsername().equals(consumerName)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean hasProduct(String productName, String categoryName) {
        return products.get(categoryName).containsKey(productName);
    }

    @Override
    public boolean noSuchProduct(String productName) {
        if (products.isEmpty()) {
            return true;
        }

        for (String category : products.keySet()) {
            if (products.get(category).containsKey(productName)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void switchPrintStrategy(String strategyName, String ...args) {
        try {
            PrintStrategy printStrategy = PrintStrategyFactory
                    .createStrategy(strategyName, args);
            if (printStrategy == null) {
                throw new VariableException
                        .InvalidCommandValueException("Failed to fetch a valid print strategy");
            }
            output.close();
            output = printStrategy;
        }
        catch (VariableException.InvalidCommandValueException ex) {
            output.print(ex.getMessage());
        }
    }

    @Override
    public void incrementProductQuantity(String productName, String addedQuantityString) {
        try {
            Product p = getProduct(productName);
            if (p == null) {
                throw new VariableException.InvalidCommandValueException("Invalid product name");
            }
            if (Constants.isIncorrectNumber(addedQuantityString)) {
                throw new VariableException.InvalidCommandValueException("Invalid quantity");
            }
            int addedQuantity = Integer.parseInt(addedQuantityString);
            if (p.getQuantity() + addedQuantity > p.getMaxQuantity()) {
                throw new VariableException.InvalidCommandValueException("Quantity is too large");
            }
            p.increment(addedQuantity);
            output.print("Product " + productName + " replenished.");
        }
        catch (VariableException.InvalidCommandValueException ex) {
            output.print(ex.getMessage());
        }
    }

    @Override
    public void decrementProductQuantity(String productName, String substractedQuantity) {
        try {
            Product p = getProduct(productName);
            if (p == null) {
                throw new VariableException.InvalidCommandValueException("Invalid product name");
            }
            if (substractedQuantity == null) {
                throw new VariableException.InvalidCommandValueException("Invalid quantity value");
            }
            int sub = Integer.parseInt(substractedQuantity);
            p.decrement(sub);
        }
        catch (VariableException.InvalidCommandValueException ex) {
            output.print(ex.getMessage());
        }
    }

    @Override
    public void doTransaction(String consumerUsername, String productName,
                              String productQuantityString) {
        try {
            if (Constants.isIncorrectName(consumerUsername)) {
                throw new VariableException.InvalidCommandValueException("Invalid username");
            }
            if (noSuchConsumer(consumerUsername)) {
                throw new VariableException.InvalidCommandValueException("Consumer does not exist");
            }
            if (Constants.isIncorrectName(productName)) {
                throw new VariableException.InvalidCommandValueException("Invalid product name");
            }
            if (noSuchProduct(productName)) {
                throw new VariableException.InvalidCommandValueException("Product does not exist");
            }
            if (Constants.isIncorrectNumber(productQuantityString)) {
                throw new VariableException.InvalidCommandValueException("Invalid quantity");
            }
            int quantity = Integer.parseInt(productQuantityString);
            Consumer c = getConsumer(consumerUsername);
            Product p = getProduct(productName);

            if (p == null) {
                throw new VariableException.InvalidCommandValueException("Failed to fetch product");
            }
            if (c == null) {
                throw new VariableException.InvalidCommandValueException("Failed to fetch consumer");
            }

            if (p.getQuantity() < quantity) {
                output.print("User " + consumerUsername + " cannot buy " + quantity + " "
                        + productName + " because there" +
                        " is only " + p.getQuantity() + " " + productName + " left.");
                return;
            }
            if (c.getBalance() < quantity * p.getPrice()) {
                output.print("User " + consumerUsername + " cannot buy " + quantity + " " + productName +
                        " because the current balance is too low: " + c.getBalance() + ".");
                return;
            }
            c.decrement(quantity * p.getPrice());
            p.decrement(quantity);
            output.print("User " + consumerUsername + " has bought " +
                    quantity + " " + productName + ".");
        }
        catch (VariableException.InvalidCommandValueException ex) {
            output.print(ex.getMessage());
        }
    }

    @Override
    public void undoTransaction(String consumerUsername, String productName,
                                String productQuantityString) {
        try {
            int quantity = Integer.parseInt(productQuantityString);
            Consumer c = getConsumer(consumerUsername);
            Product p = getProduct(productName);
            if (c == null) {
                throw new VariableException.InvalidCommandValueException("Failed to fetch " +
                        "consumer " + consumerUsername);
            }
            if (p == null) {
                throw new VariableException.InvalidCommandValueException("Failed to fetch " +
                        "product " + productName);
            }
            c.increment(quantity * p.getPrice());
            p.increment(quantity);
        }
        catch (VariableException.InvalidCommandValueException ex) {
            output.print(ex.getMessage());
        }
    }

    @Override
    public void write(String message) {
        output.print(message);
    }

    @Override
    public void closeConnections() {
        output.close();
    }

    @SuppressWarnings("deprecation")
    public ObjectNode toObjectNode() {
        ObjectMapper map = new ObjectMapper();
        ObjectNode main = map.createObjectNode();
        try {
            Field mapField = main.getClass().getDeclaredField("_children");
            mapField.setAccessible(true);
            mapField.set(main, new LinkedHashMap<>());
            mapField.setAccessible(false);
        }
        catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        ArrayNode stockArray = map.createArrayNode();
        ArrayNode consumerArray = map.createArrayNode();

        for (String category : products.keySet()) {
            for (Product product : products.get(category).values()) {
                ObjectNode productObj = map.createObjectNode();
                productObj.put(Constants.CATEGORY, category);
                productObj.put(Constants.NAME, product.getName());
                productObj.put(Constants.QUANTITY, product.getQuantity());
                productObj.put(Constants.PRICE, product.getPrice());
                productObj.put(Constants.MAXQUANTITY, product.getMaxQuantity());
                stockArray.add(productObj);
            }
        }

        for (Consumer consumer : consumers) {
            ObjectNode consumerObj = map.createObjectNode();
            consumerObj.put(Constants.USERNAME, consumer.getUsername());
            consumerObj.put(Constants.BALANCE, consumer.getBalance());
            consumerArray.add(consumerObj);
        }

        main.put(Constants.STOCK, stockArray);
        main.put(Constants.CLIENTS, consumerArray);
        return main;
    }

    @Override
    public String getPrintStrategy() {
        return output.getDisplayMode();
    }
}
