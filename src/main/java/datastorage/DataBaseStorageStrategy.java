package datastorage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import dataprocessing.PrintStrategy;
import dataprocessing.PrintStrategyFactory;
import shop.Product;
import utils.Constants;
import utils.VariableException;

import java.io.IOException;
import java.sql.*;

public class DataBaseStorageStrategy implements StorageStrategy{
    private Statement statement;
    private Connection connection;
    private PrintStrategy output = PrintStrategyFactory.createStrategy("CONSOLE");
    public DataBaseStorageStrategy(String url, String name, String password) {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection(url, name, password);
            statement = connection.createStatement();
        }
        catch(ClassNotFoundException ex) {
            output.print("SQLException: " + ex.getLocalizedMessage());
        }
        catch(SQLException ex){
            output.print("SQLException: " + ex.getLocalizedMessage());
            output.print("SQLState: " + ex.getSQLState());
            output.print("VendorError: " + ex.getErrorCode());
        }
    }

    @Override
    public void addCategory(String categoryName) {
        try (ResultSet res = statement.executeQuery("INSERT INTO categories(categoryName) VALUES" +
                "('" + categoryName + "')")){
            write("Category added.");
        }
        catch (SQLException ex) {
            output.print("SQLException: " + ex.getMessage());
            output.print("SQLState: " + ex.getSQLState());
            output.print("VendorError: " + ex.getErrorCode());
        }
    }

    @Override
    public void removeCategory(String categoryName) {
        try {
            statement.executeQuery("DELETE FROM " +
                    "categories WHERE categoryName = '" + categoryName + "'");
        }
        catch (SQLException ex) {
            output.print("SQLException: " + ex.getMessage());
            output.print("SQLState: " + ex.getSQLState());
            output.print("VendorError: " + ex.getErrorCode());
        }
    }

    @Override
    public void addClient(String consumerUsername, int consumerBalance) {
        try (ResultSet res = statement.executeQuery("INSERT INTO consumers(consumerUsername, consumerBalance) " +
                "VALUES ('" + consumerUsername + "', " + consumerBalance + ")")) {
            output.print("Client added.");
        }
        catch (SQLException ex) {
            output.print("SQLException: " + ex.getMessage());
            output.print("SQLState: " + ex.getSQLState());
            output.print("VendorError: " + ex.getErrorCode());
        }
    }

    @Override
    public void addProduct(String productName, String productCategory, int productQuantity,
                           int productPrice, int productMaxQuantity) {
        try {
            statement.executeQuery("INSERT INTO products(productName, productCategory," +
                    "productQuantity, productPrice, productMaxQuantity) VALUES ('" + productName +
                    "','" + productCategory + "'," + productQuantity + ","
                    + productPrice + "," + productMaxQuantity + ")");
        }
        catch (SQLException ex) {
            output.print("SQLException: " + ex.getMessage());
            output.print("SQLState: " + ex.getSQLState());
            output.print("VendorError: " + ex.getErrorCode());
        }
    }

    @Override
    public void addProduct(String productName, String productCategory, String productQuantityString,
                           String productPriceString, String productMaxQuantityString) {
        try {
            statement.executeQuery("INSERT INTO products(productName, productCategory," +
                    "productQuantity, productPrice, productMaxQuantity) VALUES ('" + productName +
                    "','" + productCategory + "'," + productQuantityString + ","
                    + productPriceString + "," + productMaxQuantityString + ")");
            output.print(productQuantityString + " " + productName + " have been added to "
                    + productCategory + " category.");
        }
        catch (SQLException ex) {
            output.print("SQLException: " + ex.getMessage());
            output.print("SQLState: " + ex.getSQLState());
            output.print("VendorError: " + ex.getErrorCode());
        }
    }

    public Product getProduct(String productName) {
        try (ResultSet res = statement.executeQuery("SELECT productName, productQuantity, " +
                "productCategory, productPrice, productMaxQuantity FROM products WHERE productName = '"
                + productName + "'")){
            if (res.next()) {
                return new Product(res.getString("productName"),
                        res.getString("productCategory"),
                        res.getInt("productQuantity"),
                                res.getInt("productPrice"),
                                res.getInt("productMaxQuantity"));
            }
        }
        catch (SQLException ex) {
            output.print("SQLException: " + ex.getMessage());
            output.print("SQLState: " + ex.getSQLState());
            output.print("VendorError: " + ex.getErrorCode());
        }
        return null;
    }

    @Override
    public void removeProduct(String productName) {
        try {
            statement.executeQuery("DELETE FROM products WHERE productName = '" + productName +
                    "' AND productQuantity = 0");
            output.print("Product successfully removed.");
        }
        catch (SQLException ex) {
            output.print("SQLException: " + ex.getMessage());
            output.print("SQLState: " + ex.getSQLState());
            output.print("VendorError: " + ex.getErrorCode());
        }
    }

    @Override
    public void removeProduct(String productName, String categoryName) {
        try {
            statement.executeQuery("DELETE FROM products WHERE productName = '" + productName
            + "' AND productCategory = '" + categoryName + "'");
        }
        catch (SQLException ex) {
            output.print("SQLException: " + ex.getMessage());
            output.print("SQLState: " + ex.getSQLState());
            output.print("VendorError: " + ex.getErrorCode());
        }
    }

    @Override
    public void showAll() {
        try (ResultSet res = statement.executeQuery("SELECT ROW_NUMBER() OVER(ORDER BY productID) " +
                "AS row_num, productName, productQuantity, productCategory, " +
                "productPrice FROM products")) {

            while(res.next()) {
                output.print(res.getInt("row_num")
                        + " " + res.getString("productName")
                        + " " + res.getInt("productQuantity")
                        + " " + res.getString("productCategory"));

            }
        }
        catch (SQLException ex) {
            output.print("SQLException: " + ex.getMessage());
            output.print("SQLState: " + ex.getSQLState());
            output.print("VendorError: " + ex.getErrorCode());
        }
    }

    @Override
    public void showByCategory(String categoryName) {
        try (ResultSet res = statement.executeQuery("SELECT ROW_NUMBER() OVER(ORDER BY productID )" +
                "AS row_num, productName, productQuantity, productPrice FROM products WHERE " +
                "productCategory = '" + categoryName + "'")) {

            while(res.next()) {
                output.print(res.getInt("row_num") + " " +
                        res.getString("productName") + " " +
                        res.getInt("productQuantity") + " " +
                        res.getInt("productPrice"));
            }
        }
        catch (SQLException ex) {
            output.print("SQLException: " + ex.getMessage());
            output.print("SQLState: " + ex.getSQLState());
            output.print("VendorError: " + ex.getErrorCode());
        }
    }

    @Override
    public void showCategories() {
        try (ResultSet res = statement.executeQuery("SELECT LISTAGG(categoryName, ',')" +
                " WITHIN GROUP (ORDER BY categoryID)" +
                "  FROM categories")){

            if (res.next()) {
                output.print(res.getString(1));
            }
        }
        catch (SQLException ex) {
            output.print("SQLException: " + ex.getMessage());
            output.print("SQLState: " + ex.getSQLState());
            output.print("VendorError: " + ex.getErrorCode());
        }
    }

    @Override
    public void showProduct(String productName) {

        try (ResultSet res = statement.executeQuery("SELECT productName, productQuantity, " +
                "productPrice FROM products WHERE productName = '" + productName + "'")){
            if (res.next()) {
                output.print(res.getString("productName") + " " +
                        res.getInt("productQuantity") +
                        " " + res.getInt("productPrice"));
            }
        }
        catch (SQLException ex) {
            output.print("SQLException: " + ex.getMessage());
            output.print("SQLState: " + ex.getSQLState());
            output.print("VendorError: " + ex.getErrorCode());
        }
    }

    @Override
    public boolean hasCategory(String categoryName) {
        try (ResultSet res = statement.executeQuery("SELECT COUNT(1) FROM categories WHERE " +
                "categoryName = '" + categoryName + "'")) {

            if (res.next()) {
                return res.getBoolean(1);
            }
        }
        catch (SQLException ex) {
            output.print("SQLException: " + ex.getMessage());
            output.print("SQLState: " + ex.getSQLState());
            output.print("VendorError: " + ex.getErrorCode());
        }
        return false;
    }

    @Override
    public boolean noSuchConsumer(String consumerName) {
        try (ResultSet res = statement.executeQuery("SELECT COUNT(1) FROM consumers WHERE " +
                "consumerUsername = '" + consumerName + "'")){
            if (res.next()) {
                return res.getBoolean(1);
            }
        }
        catch (SQLException ex) {
            output.print("SQLException: " + ex.getMessage());
            output.print("SQLState: " + ex.getSQLState());
            output.print("VendorError: " + ex.getErrorCode());
        }
        return false;
    }

    @Override
    public boolean hasProduct(String productName, String categoryName) {
        try (ResultSet res = statement.executeQuery("SELECT COUNT(1) FROM categories WHERE " +
                "categoryName = '" + categoryName + "' AND productName = '" + productName + "'")) {
            if (res.next()) {
                return res.getBoolean(1);
            }
        }
        catch (SQLException ex) {
            output.print("SQLException: " + ex.getMessage());
            output.print("SQLState: " + ex.getSQLState());
            output.print("VendorError: " + ex.getErrorCode());
        }
        return false;
    }

    @Override
    public boolean noSuchProduct(String productName) {
        try (ResultSet res = statement.executeQuery("SELECT * FROM products WHERE " +
                "productName = '" + productName + "'")){
            return !res.next();
        }
        catch (SQLException ex) {
            output.print("SQLException: " + ex.getMessage());
            output.print("SQLState: " + ex.getSQLState());
            output.print("VendorError: " + ex.getErrorCode());
            return false;
        }
    }

    @Override
    public void switchPrintStrategy(String strategyName, String... args) {
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

    public String getPrintStrategy() {
        return output.getDisplayMode();
    }

    @Override
    public void incrementProductQuantity(String productName, String addedQuantity) {
        try {
            statement.executeQuery("UPDATE products SET productQuantity =" +
                    "productQuantity + " + addedQuantity +
                    " WHERE productName = '" + productName + "'");
            write("Product " + productName + " replenished.");
        }
        catch (SQLException ex) {
            write("SQLException: " + ex.getMessage());
            write("SQLState: " + ex.getSQLState());
            write("VendorError: " + ex.getErrorCode());
        }
    }

    @Override
    public void decrementProductQuantity(String productName, String substractedQuantity) {
        try {
            statement.executeQuery("UPDATE products SET productQuantity =" +
                    "productQuantity - " + substractedQuantity + " WHERE productName = '" +
                    productName + "'");
        }
        catch (SQLException ex) {
            write("SQLException: " + ex.getMessage());
            write("SQLState: " + ex.getSQLState());
            write("VendorError: " + ex.getErrorCode());
        }
    }

    @Override
    public void doTransaction(String consumerUsername, String productName, String productQuantityString) {
        int x;
        try (ResultSet res = statement.executeQuery("SELECT consumerBalance FROM consumers " +
                "WHERE consumerUsername = '" + consumerUsername + "'")) {

            if (res.next()) {
                x = res.getInt(1);
                if (x <= 0) {
                    throw new VariableException.InvalidCommandValueException("Insufficient funds");
                }
            }
            if (Constants.isIncorrectNumber(productQuantityString)) {
                throw new VariableException.InvalidCommandValueException("Invalid quantity");
            }
            statement.executeQuery("UPDATE products SET productQuantity = productQuantity - " +
                    productQuantityString + " WHERE productName = '" + productName + "'");
            statement.executeQuery("UPDATE consumers SET consumerBalance = consumerBalance - " +
                    productQuantityString + " * (SELECT productPrice FROM products WHERE productName = '" +
                    productName + "')");
            write("User " + consumerUsername + " has bought " + productQuantityString + " " + productName + ".");
        }
        catch (SQLException ex) {
            write("SQLException: " + ex.getMessage());
            write("SQLState: " + ex.getSQLState());
            write("VendorError: " + ex.getErrorCode());
        }
        catch (VariableException.InvalidCommandValueException ex) {
            write(ex.getMessage());
        }
    }

    @Override
    public void undoTransaction(String consumerUsername, String productName,
                                String productQuantityString) {
        try {
            statement.executeQuery("UPDATE products SET productQuantity = productQuantity + " +
                    productQuantityString + " WHERE productName = '" + productName + "'");
            statement.executeQuery("UPDATE consumers SET consumerBalance = consumerBalance + " +
                    productQuantityString + " * (SELECT productPrice FROM products WHERE productName = '" +
                    productName + "')");
        }
        catch (SQLException ex) {
            write("SQLException: " + ex.getMessage());
            write("SQLState: " + ex.getSQLState());
            write("VendorError: " + ex.getErrorCode());
        }
    }

    @Override
    public void write(String message) {
        output.print(message);
    }

    @Override
    public void closeConnections() {
        try {
            output.close();
            connection.close();
        }
        catch (SQLException ex) {
            write("SQLException: " + ex.getMessage());
            write("SQLState: " + ex.getSQLState());
            write("VendorError: " + ex.getErrorCode());
        }
    }

    public ObjectNode toObjectNode() {
        try (ResultSet res = statement.executeQuery("SELECT JSON_OBJECT (\n" +
                "         KEY 'stock' VALUE (\n" +
                "                SELECT JSON_ARRAYAGG(\n" +
                "                    JSON_OBJECT (\n" +
                "                        KEY 'category' VALUE p.productCategory,\n" +
                "                        KEY 'name' VALUE p.productName,\n" +
                "                        KEY 'quantity' VALUE p.productQuantity,\n" +
                "                        KEY 'price' VALUE p.productPrice,\n" +
                "                        KEY 'maxQuantity' VALUE productMaxQuantity\n" +
                "                    )\n" +
                "                )\n" +
                "                FROM products p\n" +
                "            ),\n" +
                "        KEY 'clients' VALUE (\n" +
                "                SELECT JSON_ARRAYAGG(\n" +
                "                    JSON_OBJECT (\n" +
                "                        KEY 'username' VALUE c.consumerUsername,\n" +
                "                        KEY 'balance' VALUE c.consumerBalance\n" +
                "                    )\n" +
                "                )\n" +
                "                FROM consumers c\n" +
                "        )\n" +
                "       ) AS products_data\n" +
                "FROM   SYS.dual")) {

            if (res.next()) {
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readValue(res.getString(1), ObjectNode.class);
            }
            else {
                return null;
            }
        } catch (SQLException | IOException ex) {
            write(ex.getMessage());
            return null;
        }
    }
}
