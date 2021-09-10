package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import datastorage.DataStructureStorageStrategy;

import java.io.File;
import java.io.IOException;

public class InputLoader implements VariableException{

    private final String inputPath;

    public InputLoader(final String inputPath) {
        this.inputPath = inputPath;
    }
    public void readInput(DataStructureStorageStrategy storage){


        int balance;
        try {
            ObjectMapper map = new ObjectMapper();
            final ObjectNode jsonObject = map.readValue(new File(inputPath), ObjectNode.class);
            ArrayNode jsonStocks = (ArrayNode) jsonObject.get(Constants.STOCK);
            ArrayNode jsonConsumers = (ArrayNode) jsonObject.get(Constants.CLIENTS);


            if (jsonStocks == null) {
                throw new MissingStructureError("No stock!");
            }
            if (jsonConsumers == null) {
                throw new MissingStructureError("No clients!");
            }

            for (JsonNode jsonStock : jsonStocks) {
                String category = jsonStock.get(Constants.CATEGORY).toString().replace("\"", "");
                if (Constants.isIncorrectName(category)) {
                    throw new InvalidInputValueError("Category " + category + " is invalid!");
                }
                if (!storage.hasCategory(category)) {
                    storage.addCategory(category);
                }

                String name = jsonStock.get(Constants.NAME).toString().replace("\"", "");
                if (Constants.isIncorrectName(name)) {
                    throw new InvalidInputValueError("Name " + name + " is invalid!");
                }
                if (storage.hasProduct(name, category)) {
                    throw new OverrideInputError("Product " + name + "of category " + category +
                            " already exists!");
                }
                String quantityString = jsonStock.get(Constants.QUANTITY).toString().replace("\"", "");
                if (Constants.isIncorrectNumber(quantityString)) {
                    throw new InvalidInputValueError("Quantity " + quantityString + " is invalid!");
                }
                String priceString = jsonStock.get(Constants.PRICE).toString().replace("\"", "");
                if (Constants.isIncorrectNumber(priceString)) {
                    throw new InvalidInputValueError("Price " + priceString + " is invalid!");
                }
                String maxQuantityString = jsonStock.get(Constants.MAXQUANTITY).toString();
                if (Constants.isIncorrectNumber(maxQuantityString)) {
                    throw new InvalidInputValueError("maxQuantity " + maxQuantityString + " is invalid!");
                }
                storage.addProduct(name, category, quantityString, priceString, maxQuantityString);
            }
            for (Object jsonConsumer : jsonConsumers) {
                String username = ((ObjectNode) jsonConsumer).get(Constants.USERNAME).toString()
                        .replace("\"", "");
                if (Constants.isIncorrectName(username)) {
                    throw new InvalidInputValueError("Consumer's username " + username + " is invalid!");
                }
                String balanceString = ((ObjectNode) jsonConsumer).get(Constants.BALANCE).toString()
                        .replace("\"", "");
                if (Constants.isIncorrectNumber(balanceString)) {
                    throw new InvalidInputValueError("Consumer's balance " + balanceString + " is invalid!");
                }
                balance = Integer.parseInt(balanceString);
                storage.addClient(username, balance);
            }
        }

        catch (IOException e) {
            System.out.println(e.toString());
        }
    }
}
