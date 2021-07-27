package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import shop.Consumer;
import shop.Product;
import shop.Shop;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;


public class Writer {
    private FileWriter jsonFile;

    public void setJsonFile(String path) throws VariableException.InvalidCommandValueException {
        try {
            if (!Constants.isPathValid(path) || !path.endsWith(".json")) {
                throw new VariableException
                        .InvalidCommandValueException("Invalid path, check to see if it " +
                        "indicates a file or if it is written correctly");
            }
            jsonFile = new FileWriter(path);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("deprecation")
    public ObjectNode writeFile(Shop shop){
        ObjectMapper map = new ObjectMapper();
        ObjectNode main = map.createObjectNode();

        try {
            //schimb tipul de map la LinkedHashMap ca sa pastrez ordinea adaugarii
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

        for (String category : shop.getProducts().keySet()) {
            for (Product product : shop.getProducts().get(category).values()) {
                ObjectNode productObj = map.createObjectNode();
                productObj.put(Constants.CATEGORY, category);
                productObj.put(Constants.NAME, product.getName());
                productObj.put(Constants.QUANTITY, product.getQuantity());
                productObj.put(Constants.PRICE, product.getPrice());
                productObj.put(Constants.MAXQUANTITY, product.getMaxQuantity());
                stockArray.add(productObj);
            }
        }

        for (Consumer consumer : shop.getConsumers()) {
            ObjectNode consumerObj = map.createObjectNode();
            consumerObj.put(Constants.USERNAME, consumer.getUsername());
            consumerObj.put(Constants.BALANCE, consumer.getBalance());
            consumerArray.add(consumerObj);
        }

        main.put(Constants.STOCK, stockArray);
        main.put(Constants.CLIENTS, consumerArray);
        return main;
    }

    public void closeJSON(final ObjectNode object) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            jsonFile.write(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object));
            jsonFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
