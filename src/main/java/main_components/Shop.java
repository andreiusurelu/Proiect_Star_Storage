package main_components;

import dataprocessing.PrintStrategy;
import dataprocessing.PrintStrategyFactory;
import entity.Category;
import entity.Product;
import entity.Consumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import parsers.Parser;
import parsers.ParserFactory;
import utils.Constants;

import javax.persistence.*;


@Component
public class Shop implements Receiver {
    private static final Logger logger = LogManager.getLogger(Shop.class);
    private static final EntityManagerFactory factory = Persistence
            .createEntityManagerFactory("entity");
    private PrintStrategy output = PrintStrategyFactory.createStrategy("CONSOLE");

    @Autowired
    public Shop() {
        //de implementat bean-urile ca interfete (sa renunt la clase si sa am interfete)
        //de folosit JPA
//        try (InputStream input = new FileInputStream("src/main/resources/config.properties")){
//            Properties prop = new Properties();
//            prop.load(input);
//            Class.forName(prop.getProperty("driverClass"));
//            connection = DriverManager.getConnection(prop.getProperty("url"),
//                    prop.getProperty("username"), prop.getProperty("password"));
//            connection.setAutoCommit(false);
//        }
//        catch (FileNotFoundException e) {
//            logger.error("Couldn't find the file in the specified path");
//        }
//        catch (IOException e) {
//            logger.error("Error while attempting to open file");
//        }
//        catch (IllegalArgumentException e) {
//            logger.error("Argument is not a file");
//        }
//        catch (ClassNotFoundException e) {
//            logger.error("Incorrect driver class for JDBC");
//        }
//        catch (SQLException e) {
//            logger.error("SQLException: " + e.getLocalizedMessage() +
//                    "\n SQLState: " + e.getSQLState() +
//                    "\n VendorError: " + e.getErrorCode());
//        }
    }

    public String getDisplayMode() {
        return output.getDisplayMode();
    }

    public void showDisplayMode() {
        String displayMode = output.getDisplayMode();
        output.print(displayMode);
        logger.info(displayMode);
    }

    public void write(String message) {
        output.print(message);
        logger.info(message);
    }

    public void addNewCategory(String categoryName) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            Category category = new Category();
            category.setName(categoryName);

            entityManager.persist(category);

            transaction.commit();
            output.print("Category added.");
            logger.info("Category added.");
        }
        catch (EntityExistsException e) {
            logger.warn("Category " + categoryName + " already exists!");
            if (transaction != null) {
                transaction.rollback();
            }
        }
        catch (IllegalArgumentException e) {
            logger.warn("The instance couldn't be added, check syntax and entity type!", e);
            if (transaction != null) {
                transaction.rollback();
            }
        }
        catch (RollbackException e) {
            logger.warn("Failed to commit!", e);
            if (transaction != null) {
                transaction.rollback();
            }
        }
        catch (IllegalStateException e) {
            logger.warn("Incorrect transaction state at: " + e.getLocalizedMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        }
        catch (PersistenceException e) {
            logger.error("An exception regarding persistence was thrown: ", e);
            if (transaction != null) {
                transaction.rollback();
            }
        }
        finally {
            entityManager.close();
        }
//        String update = "INSERT INTO categories(categoryName) VALUES (?)";
//        try (PreparedStatement preparedStatement = connection.prepareStatement(update)) {
//            preparedStatement.setString(1, categoryName);
//            preparedStatement.executeUpdate();
//            connection.commit();
//            logger.info("Category added.");
//            output.print("Category added.");
//        }
//        catch (SQLException ex) {
//            logger.warn("SQLException: " + ex.getLocalizedMessage() +
//                    "\n SQLState: " + ex.getSQLState() +
//                    "\n VendorError: " + ex.getErrorCode());
//            if (connection != null) {
//                try {
//                    logger.info("Transaction is being rolled back...");
//                    output.print("Transaction is being rolled back...");
//                    connection.rollback();
//                }
//                catch (SQLException e) {
//                    logger.error("SQLException: " + e.getLocalizedMessage() +
//                            "\n SQLState: " + e.getSQLState() +
//                            "\n VendorError: " + e.getErrorCode());
//                }
//            }
//        }
    }

    public void undoAddNewCategory(String categoryName) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = null;
        Category category;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            Query query = entityManager.createNamedQuery("Category.findByName");
            query.setParameter("categoryName", categoryName);
            category = (Category) query.getSingleResult();

            entityManager.remove(category);
            transaction.commit();
        }
        catch (IllegalArgumentException e) {
            logger.error(e.getStackTrace());
            if (transaction != null) {
                transaction.rollback();
            }
        }
        catch (NoResultException e) {
            logger.error("Couldn't fetch a result.");
            if (transaction != null) {
                transaction.rollback();
            }
        }
        catch (NonUniqueResultException e) {
            logger.fatal("Multiple results found, check database!");
            if (transaction != null) {
                transaction.rollback();
            }
        }

        finally {
            entityManager.close();
        }
//        String update = "DELETE FROM categories WHERE categoryName = ?";
//        try (PreparedStatement preparedStatement = connection.prepareStatement(update)){
//            preparedStatement.setString(1, categoryName);
//            preparedStatement.executeUpdate();
//            connection.commit();
//        }
//        catch (SQLException ex) {
//            logger.warn("SQLException: " + ex.getLocalizedMessage() +
//                    "\n SQLState: " + ex.getSQLState() +
//                    "\n VendorError: " + ex.getErrorCode());
//            if (connection != null) {
//                try {
//                    logger.info("Transaction is being rolled back...");
//                    output.print("Transaction is being rolled back...");
//                    connection.rollback();
//                }
//                catch (SQLException e) {
//                    logger.error("SQLException: " + e.getLocalizedMessage() +
//                            "\n SQLState: " + e.getSQLState() +
//                            "\n VendorError: " + e.getErrorCode());
//                }
//            }
//        }
    }

    public void addNewProduct(String productName, String productCategory,
                              String quantity, String price, String maxQuantity) {
        if (Constants.isIncorrectName(productName)) {
            logger.warn("Invalid product name: " + productName);
            return;
        }
        if (Constants.isIncorrectName(productCategory)) {
            logger.warn("Invalid product category: " + productCategory);
            return;
        }
        if (Constants.isIncorrectNumber(quantity)) {
            logger.warn("Invalid product quantity: " + quantity);
            return;
        }
        if (Constants.isIncorrectNumber(price)) {
            logger.warn("Invalid product price: " + price);
            return;
        }
        if (Constants.isIncorrectNumber(maxQuantity)) {
            logger.warn("Invalid max quantity: " + maxQuantity);
            return;
        }
        int productQuantity = Integer.parseInt(quantity);
        int productPrice = Integer.parseInt(price);
        int productMaxQuantity = Integer.parseInt(maxQuantity);

        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            Query query = entityManager.createNamedQuery("Category.findByName");
            query.setParameter("categoryName", productCategory);
            Category category = (Category) query.getSingleResult();

            Product product = new Product();
            product.setName(productName);
            product.setCategory(category);
            product.setQuantity(productQuantity);
            product.setPrice(productPrice);
            product.setMaxQuantity(productMaxQuantity);

            logger.info(productQuantity + " " + productName + " have been added to "
                    + productCategory + " category.");
            output.print(productQuantity + " " + productName + " have been added to "
                    + productCategory + " category.");

            entityManager.persist(product);
            transaction.commit();
        }
        catch (EntityExistsException e) {
            logger.warn("Product " + productName + " already exists!");
            if (transaction != null) {
                transaction.rollback();
            }
        }
        catch (IllegalArgumentException e) {
            logger.warn("The instance couldn't be added, check syntax and entity type!");
            if (transaction != null) {
                transaction.rollback();
            }
        }
        catch (RollbackException e) {
            logger.warn("Failed to commit!");
            if (transaction != null) {
                transaction.rollback();
            }
        }
        catch (IllegalStateException e) {
            logger.warn("Incorrect transaction state at: " + e.getLocalizedMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        }
        catch (PersistenceException e) {
            logger.error("A persistence exception appeared: ", e);
            if (transaction != null) {
                transaction.rollback();
            }
        }
        finally {
            entityManager.close();
        }
//        String updateString = "INSERT INTO products(productName, productCategory," +
//                "productQuantity, productPrice, productMaxQuantity) VALUES (?, ?, ?, ?, ?)";
//        try (PreparedStatement preparedStatement = connection.prepareStatement(updateString)) {
//            preparedStatement.setString(1, productName);
//            preparedStatement.setString(2, productCategory);
//            preparedStatement.setInt(3, productQuantity);
//            preparedStatement.setInt(4, productPrice);
//            preparedStatement.setInt(5, productMaxQuantity);
//            preparedStatement.executeUpdate();
//            connection.commit();
//            logger.info(productQuantity + " " + productName + " have been added to "
//                    + productCategory + " category.");
//            output.print(productQuantity + " " + productName + " have been added to "
//                    + productCategory + " category.");
//        }
//        catch (SQLException ex) {
//            logger.warn("SQLException: " + ex.getLocalizedMessage() +
//                    "\n SQLState: " + ex.getSQLState() +
//                    "\n VendorError: " + ex.getErrorCode());
//            if (connection != null) {
//                try {
//                    logger.info("Transaction is being rolled back");
//                    output.print("Transaction is being rolled back");
//                    connection.rollback();
//                }
//                catch (SQLException e) {
//                    logger.error("SQLException: " + e.getLocalizedMessage() +
//                            "\n SQLState: " + e.getSQLState() +
//                            "\n VendorError: " + e.getErrorCode());
//                }
//            }
//        }
    }

    public void addNewProduct(String productName, Category productCategory,
                              int productQuantity, int productPrice, int productMaxQuantity) {

        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            Product product = new Product();
            product.setName(productName);
            product.setCategory(productCategory);
            product.setQuantity(productQuantity);
            product.setPrice(productPrice);
            product.setMaxQuantity(productMaxQuantity);

            entityManager.persist(product);

            logger.info(productQuantity + " " + productName + " have been added to "
                    + productCategory + " category.");
            output.print(productQuantity + " " + productName + " have been added to "
                    + productCategory + " category.");

            transaction.commit();
        }
        catch (EntityExistsException e) {
            logger.warn("Product " + productName + " already exists!");
            if (transaction != null) {
                transaction.rollback();
            }
        }
        catch (IllegalArgumentException e) {
            logger.warn("The instance couldn't be added, check syntax and entity type!");
            if (transaction != null) {
                transaction.rollback();
            }
        }
        catch (RollbackException e) {
            logger.warn("Failed to commit!");
            if (transaction != null) {
                transaction.rollback();
            }
        }
        catch (IllegalStateException e) {
            logger.warn("Incorrect transaction state at: " + e.getLocalizedMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        }
        finally {
            entityManager.close();
        }
//        String updateString = "INSERT INTO products(productName, productCategory," +
//                "productQuantity, productPrice, productMaxQuantity) VALUES (?, ?, ?, ?, ?)";
//        try (PreparedStatement preparedStatement = connection.prepareStatement(updateString)) {
//            preparedStatement.setString(1, productName);
//            preparedStatement.setString(2, productCategory);
//            preparedStatement.setInt(3, productQuantity);
//            preparedStatement.setInt(4, productPrice);
//            preparedStatement.setInt(5, productMaxQuantity);
//            preparedStatement.executeUpdate();
//            connection.commit();
//        }
//        catch (SQLException ex) {
//            logger.warn("SQLException: " + ex.getLocalizedMessage() +
//                    "\n SQLState: " + ex.getSQLState() +
//                    "\n VendorError: " + ex.getErrorCode());
//            if (connection != null) {
//                try {
//                    logger.info("Transaction is being rolled back...");
//                    output.print("Transaction is being rolled back...");
//                    connection.rollback();
//                }
//                catch (SQLException e) {
//                    logger.error("SQLException: " + e.getLocalizedMessage() +
//                            "\n SQLState: " + e.getSQLState() +
//                            "\n VendorError: " + e.getErrorCode());
//                }
//            }
//        }
    }

    public void undoAddNewProduct(String productName, String productCategory, String quantityString,
                                  String priceString, String maxQuantityString) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = null;

        try {
            int quantity = Integer.parseInt(quantityString);
            int price = Integer.parseInt(priceString);
            int maxQuantity = Integer.parseInt(maxQuantityString);

            transaction = entityManager.getTransaction();
            transaction.begin();

            Query categoryQuery = entityManager.createNamedQuery("Category.findByName");
            categoryQuery.setParameter("categoryName", productCategory);
            Category category = (Category) categoryQuery.getSingleResult();

            Query query = entityManager.createNamedQuery("Product.findByFields");
            query.setParameter("productName", productName);
            query.setParameter("productCategory", category);
            query.setParameter("productQuantity", quantity);
            query.setParameter("productPrice", price);
            query.setParameter("productMaxQuantity", maxQuantity);

            Product product = (Product) query.getSingleResult();
            entityManager.remove(product);

            transaction.commit();
        }
        catch (IllegalArgumentException e) {
            logger.error(e.getStackTrace());
            if (transaction != null) {
                transaction.rollback();
            }
        }
        catch (NoResultException e) {
            logger.error("Couldn't fetch a result.");
            if (transaction != null) {
                transaction.rollback();
            }
        }
        catch (NonUniqueResultException e) {
            logger.fatal("Multiple results found, check database!");
            if (transaction != null) {
                transaction.rollback();
            }
        }

        finally {
            entityManager.close();
        }
//        String updateString = "DELETE FROM products WHERE productName = ? AND productCategory = ? " +
//                "AND productQuantity = ? AND productPrice = ? AND productMaxQuantity = ?";
//        try (PreparedStatement preparedStatement = connection.prepareStatement(updateString)) {
//            preparedStatement.setString(1, productName);
//            preparedStatement.setString(2, productCategory);
//            preparedStatement.setInt(3, Integer.parseInt(quantityString));
//            preparedStatement.setInt(4, Integer.parseInt(priceString));
//            preparedStatement.setInt(5, Integer.parseInt(maxQuantityString));
//            preparedStatement.executeUpdate();
//            connection.commit();
//        }
//        catch (SQLException ex) {
//            logger.warn("SQLException: " + ex.getLocalizedMessage() +
//                    "\n SQLState: " + ex.getSQLState() +
//                    "\n VendorError: " + ex.getErrorCode());
//            if (connection != null) {
//                try {
//                    logger.info("Transaction is being rolled back...");
//                    output.print("Transaction is being rolled back...");
//                    connection.rollback();
//                }
//                catch (SQLException e) {
//                    logger.error("SQLException: " + e.getLocalizedMessage() +
//                            "\n SQLState: " + e.getSQLState() +
//                            "\n VendorError: " + e.getErrorCode());
//                }
//            }
//        }
    }

    public Product fetchProductByName(String productName) {
        EntityManager entityManager = factory.createEntityManager();
        Product product = null;

        try {
            Query query = entityManager.createNamedQuery("Product.findByName");
            query.setParameter("productName", productName);
            product = (Product) query.getSingleResult();

        }
        catch (IllegalArgumentException e) {
            logger.warn("Check syntax!");
        }
        catch (NonUniqueResultException e) {
            logger.fatal("Multiple results detected, check database!");
        }
        finally {
            entityManager.close();
        }
        return product;
//        Product product = null;
//        String queryString = "SELECT productName, productQuantity, " +
//                "productCategory, productPrice, productMaxQuantity FROM products WHERE productName = ?";
//
//        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)){
//            preparedStatement.setString(1, productName);
//            ResultSet res = preparedStatement.executeQuery();
//            if (res.next()) {
//                product = new Product(res.getString("productName"),
//                        res.getString("productCategory"),
//                        res.getInt("productQuantity"),
//                        res.getInt("productPrice"),
//                        res.getInt("productMaxQuantity"));
//                res.close();
//
//            }
//        }
//        catch (SQLException ex) {
//            logger.warn("SQLException: " + ex.getLocalizedMessage() +
//                    "\n SQLState: " + ex.getSQLState() +
//                    "\n VendorError: " + ex.getErrorCode());
//            if (connection != null) {
//                try {
//                    logger.info("Transaction is being rolled back...");
//                    output.print("Transaction is being rolled back...");
//                    connection.rollback();
//                }
//                catch (SQLException e) {
//                    logger.error("SQLException: " + e.getLocalizedMessage() +
//                            "\n SQLState: " + e.getSQLState() +
//                            "\n VendorError: " + e.getErrorCode());
//                }
//            }
//        }
//        finally {
//            return product;
//        }
    }

    public void doTransaction(String consumerUsername, String productName, String productQuantityString) {

        if (Constants.isIncorrectName(consumerUsername)) {
            logger.warn("Incorrect consumer name");
            return;
        }
        if (Constants.isIncorrectName(productName)) {
            logger.warn("Incorrect product name");
            return;
        }
        if (Constants.isIncorrectNumber(productQuantityString)) {
            logger.warn("Invalid product quantity");
            return;
        }

        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            int quantity = Integer.parseInt(productQuantityString);

            Query productQuery = entityManager.createNamedQuery("Product.findByName");
            productQuery.setParameter("productName", productName);
            Product product = (Product) productQuery.getSingleResult();

            Query consumerQuery = entityManager.createNamedQuery("Consumer.findByName");
            consumerQuery.setParameter("consumerUsername", consumerUsername);
            Consumer consumer = (Consumer) consumerQuery.getSingleResult();

            int price = product.getPrice();

            if (quantity > product.getQuantity()) {
                logger.warn("Cannot buy product " + productName + " due to quantity.");
                transaction.rollback();
            }

            if (quantity * price > consumer.getConsumerBalance()) {
                logger.warn("Consumer " + consumerUsername + " can't buy product due to insufficient funds.");
                transaction.rollback();
            }

            else {
                product.decrement(quantity);
                consumer.decrement(quantity * price);
                entityManager.merge(product);
                entityManager.merge(consumer);
                transaction.commit();
            }
        }
        catch (NonUniqueResultException e) {
            logger.fatal("Multiple results detected, check database! Trace: ", e);
            if (transaction != null) {
                transaction.rollback();
            }
        }

        catch (RollbackException e) {
            logger.error("Couldn't commit, check causes: ", e);
        }

        catch (NoResultException e) {
            logger.error("No result was found at: ", e);
            if (transaction != null) {
                transaction.rollback();
            }
        }
        finally {
            entityManager.close();
        }
//        String balanceString = "SELECT consumerBalance FROM consumers WHERE consumerUsername = ?";
//        String priceString = "SELECT productPrice FROM products WHERE productName = ?";
//        String updateProductsString = "UPDATE products SET productQuantity = productQuantity - ? WHERE productName = " +
//                "?";
//        String updateConsumersString = "UPDATE consumers SET consumerBalance = consumerBalance - ? * ? WHERE " +
//                "consumerUsername = ?";
//        try (PreparedStatement balanceStatement = connection.prepareStatement(balanceString);
//             PreparedStatement priceStatement = connection.prepareStatement(priceString)) {
//            balanceStatement.setString(1, consumerUsername);
//            ResultSet res = balanceStatement.executeQuery();
//            if (res.next()) {
//                balance = res.getInt(1);
//                if (balance <= 0) {
//                    logger.warn("Insufficient balance.");
//                    return;
//                }
//            }
//            else {
//                logger.warn("Couldn't obtain a result.");
//                return;
//            }
//            if (Constants.isIncorrectNumber(productQuantityString)) {
//                logger.warn(new VariableException.InvalidCommandValueException("Incorrect quantity: "
//                        + productQuantityString));
//                return;
//            }
//            quantity = Integer.parseInt(productQuantityString);
//
//            priceStatement.setString(1, productName);
//            res = priceStatement.executeQuery();
//            if (res.next()) {
//                price = res.getInt(1);
//                if (price * quantity > balance) {
//                    logger.warn("Insufficient balance to perform transaction for user: " + consumerUsername);
//                    return;
//                }
//            }
//            else {
//                logger.warn("Couldn't obtain a result.");
//                return;
//            }
//            try (PreparedStatement productStatement = connection.prepareStatement(updateProductsString);
//                 PreparedStatement consumerStatement = connection.prepareStatement(updateConsumersString)) {
//                productStatement.setInt(1, quantity);
//                productStatement.setString(2, productName);
//                productStatement.executeUpdate();
//
//                consumerStatement.setInt(1, quantity);
//                consumerStatement.setInt(2, price);
//                consumerStatement.setString(3, consumerUsername);
//                consumerStatement.executeUpdate();
//                logger.info("User " + consumerUsername + " has bought " + productQuantityString + " "
//                        + productName + ".");
//                output.print("User " + consumerUsername + " has bought " + productQuantityString + " "
//                        + productName + ".");
//
//                connection.commit();
//            }
//            catch (SQLException ex) {
//                logger.warn("SQLException: " + ex.getLocalizedMessage() +
//                        "\n SQLState: " + ex.getSQLState() +
//                        "\n VendorError: " + ex.getErrorCode());
//                if (connection != null) {
//                    try {
//                        logger.info("Transaction is being rolled back...");
//                        output.print("Transaction is being rolled back...");
//                        connection.rollback();
//                    }
//                    catch (SQLException e) {
//                        logger.error("SQLException: " + e.getLocalizedMessage() +
//                                "\n SQLState: " + e.getSQLState() +
//                                "\n VendorError: " + e.getErrorCode());
//                    }
//                }
//            }
//        }
//        catch (SQLException ex) {
//            logger.warn("SQLException: " + ex.getLocalizedMessage() +
//                    "\n SQLState: " + ex.getSQLState() +
//                    "\n VendorError: " + ex.getErrorCode());
//            if (connection != null) {
//                try {
//                    logger.info("Transaction is being rolled back...");
//                    output.print("Transaction is being rolled back...");
//                    connection.rollback();
//                }
//                catch (SQLException e) {
//                    logger.error("SQLException: " + e.getLocalizedMessage() +
//                            "\n SQLState: " + e.getSQLState() +
//                            "\n VendorError: " + e.getErrorCode());
//                }
//            }
//        }
    }

    public void undoTransaction(String consumerUsername, String productName, String productQuantityString) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            int quantity = Integer.parseInt(productQuantityString);

            Query productQuery = entityManager.createNamedQuery("Product.findByName");
            productQuery.setParameter("productName", productName);
            Product product = (Product) productQuery.getSingleResult();

            Query consumerQuery = entityManager.createNamedQuery("Consumer.findByName");
            consumerQuery.setParameter("consumerUsername", consumerUsername);
            Consumer consumer = (Consumer) consumerQuery.getSingleResult();

            int price = product.getPrice();

            product.increment(quantity);
            consumer.increment(quantity * price);

            entityManager.merge(product);
            entityManager.merge(consumer);

            transaction.commit();
        }

        catch (NonUniqueResultException e) {
            logger.fatal("Multiple results detected, check database!");
            if (transaction != null) {
                transaction.rollback();
            }
        }

        catch (NoResultException e) {
            logger.error("No result was found at: ", e);
            if (transaction != null) {
                transaction.rollback();
            }
        }

        catch (IllegalArgumentException e) {
            logger.warn("Illegal argument exception");
            if (transaction != null) {
                transaction.rollback();
            }
        }

        finally {
            entityManager.close();
        }
//        String updateProductsString = "UPDATE products SET productQuantity = productQuantity + ? WHERE productName = ?";
//        String updateConsumersString = "UPDATE consumers SET consumerBalance = consumerBalance + ? * " +
//                "(SELECT productPrice FROM products WHERE productName = ?) WHERE consumerUsername = ?";
//        try (PreparedStatement productStatement = connection.prepareStatement(updateProductsString);
//             PreparedStatement consumerStatement = connection.prepareStatement(updateConsumersString)) {
//            int quantity = Integer.parseInt(productQuantityString);
//            productStatement.setInt(1, quantity);
//            productStatement.setString(2, productName);
//            productStatement.executeUpdate();
//
//            consumerStatement.setInt(1, quantity);
//            consumerStatement.setString(2, productName);
//            consumerStatement.setString(3, consumerUsername);
//            consumerStatement.executeUpdate();
//
//            connection.commit();
//        }
//        catch (SQLException ex) {
//            logger.warn("SQLException: " + ex.getLocalizedMessage() +
//                    "\n SQLState: " + ex.getSQLState() +
//                    "\n VendorError: " + ex.getErrorCode());
//            if (connection != null) {
//                try {
//                    output.print("Transaction is being rolled back");
//                    connection.rollback();
//                }
//                catch (SQLException e) {
//                    logger.error("SQLException: " + e.getLocalizedMessage() +
//                            "\n SQLState: " + e.getSQLState() +
//                            "\n VendorError: " + e.getErrorCode());
//                }
//            }
//        }
    }

    public void close() {
        factory.close();
        output.close();
        //connection.close();
    }

    public void showCategories() {
        EntityManager entityManager = factory.createEntityManager();
        try {
            Query query = entityManager.createNamedQuery("Category.printEntriesName");
            String resultString = String.join(", ", query.getResultList());
            output.print(resultString);
            logger.info(resultString);
        }
        catch (Exception e) {
            logger.warn(e.getMessage());
        }
        finally {
            entityManager.close();
        }
//        String queryString = "SELECT LISTAGG(categoryName, ',')" +
//                " WITHIN GROUP (ORDER BY categoryID)" +
//                "  FROM categories";
//        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString);
//             ResultSet res = preparedStatement.executeQuery()){
//
//            if (res.next()) {
//                output.print(res.getString(1));
//                logger.info(res.getString(1));
//            }
//        }
//        catch (SQLException ex) {
//            logger.warn("SQLException: " + ex.getLocalizedMessage() +
//                    "\n SQLState: " + ex.getSQLState() +
//                    "\n VendorError: " + ex.getErrorCode());
//        }
    }

//    public boolean noSuchProduct(String productName) {
//        boolean result = false;
//        String queryString = "SELECT * FROM products WHERE " +
//                "productName = ?";
//        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)){
//            preparedStatement.setString(1, productName);
//            ResultSet res = preparedStatement.executeQuery();
//            result = !res.next();
//            res.close();
//        }
//        catch (SQLException ex) {
//            logger.warn("SQLException: " + ex.getLocalizedMessage() +
//                    "\n SQLState: " + ex.getSQLState() +
//                    "\n VendorError: " + ex.getErrorCode());
//        }
//        finally {
//            return result;
//        }
//    }

    public void showProduct(String productName) {
        if (Constants.isIncorrectName(productName)) {
            logger.warn("Invalid name");
            return;
        }
        EntityManager entityManager = factory.createEntityManager();
        try {
            Query query = entityManager.createNamedQuery("Product.findByName");
            query.setParameter("productName", productName);
            String resultString = query.getSingleResult().toString();
            output.print(resultString);
            //logger.info(resultString);
        }
        catch (Exception e) {
            logger.error("An exception was thrown: ", e);
        }
        finally {
            entityManager.close();
        }
//        String queryString = "SELECT productName, productQuantity, " +
//                "productPrice FROM products WHERE productName = ?";
//        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)){
//            preparedStatement.setString(1, productName);
//            ResultSet res = preparedStatement.executeQuery();
//            if (res.next()) {
//                output.print(res.getString("productName") + " " +
//                        res.getInt("productQuantity") +
//                        " " + res.getInt("productPrice"));
//                logger.info(res.getString("productName") + " " +
//                        res.getInt("productQuantity") +
//                        " " + res.getInt("productPrice"));
//            }
//            else {
//                logger.warn("Couldn't fetch a result");
//            }
//            res.close();
//        }
//        catch (SQLException ex) {
//            logger.warn("SQLException: " + ex.getLocalizedMessage() +
//                    "\n SQLState: " + ex.getSQLState() +
//                    "\n VendorError: " + ex.getErrorCode());
//        }
    }

    public void showAll() {
        EntityManager entityManager = factory.createEntityManager();
        try {
            Query query = entityManager.createNamedQuery("Product.printEntries");
            List products = query.getResultList();
            int i = 1, size = products.size();
            while (i <= size) {
                Product product = (Product) products.get(i-1);
                output.print(i + " " +  product.getName() + " " + product.getQuantity() + " " +
                        product.getCategory() + " " + product.getPrice());
                logger.info(i + " " + product.getName() + " " + product.getQuantity() + " " +
                        product.getCategory() + " " + product.getPrice());
                i++;
            }
        }
        catch (Exception e) {
            logger.error("An exception has been thrown: ", e);
        }
        finally {
            entityManager.close();
        }
//        String queryString = "SELECT ROW_NUMBER() OVER(ORDER BY productID) " +
//                "AS row_num, productName, productQuantity, productCategory, " +
//                "productPrice FROM products";
//        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString);
//             ResultSet res = preparedStatement.executeQuery()) {
//            while(res.next()) {
//                output.print(res.getInt("row_num")
//                        + " " + res.getString("productName")
//                        + " " + res.getInt("productQuantity")
//                        + " " + res.getString("productCategory"));
//                logger.info(res.getInt("row_num")
//                        + " " + res.getString("productName")
//                        + " " + res.getInt("productQuantity")
//                        + " " + res.getString("productCategory"));
//
//            }
//        }
//        catch (SQLException ex) {
//            logger.warn("SQLException: " + ex.getLocalizedMessage() +
//                    "\n SQLState: " + ex.getSQLState() +
//                    "\n VendorError: " + ex.getErrorCode());
//        }
    }

//    public boolean noSuchCategory(String categoryName) {
//        boolean result = true;
//        String queryString = "SELECT * FROM categories WHERE " +
//                "categoryName = ?";
//        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {
//            preparedStatement.setString(1, categoryName);
//            ResultSet res = preparedStatement.executeQuery();
//            result = !res.next();
//            res.close();
//        }
//        catch (SQLException ex) {
//            logger.warn("SQLException: " + ex.getLocalizedMessage() +
//                    "\n SQLState: " + ex.getSQLState() +
//                    "\n VendorError: " + ex.getErrorCode());
//        }
//        finally {
//            return result;
//        }
//    }

    public void showByCategory(String productCategory) {
        EntityManager entityManager = factory.createEntityManager();
        try {
            Query query = entityManager.createNamedQuery("Product.printByCategoryName");
            query.setParameter("categoryName", productCategory);
            List<Product> products = query.getResultList();
            int i = 1, size = products.size();
            while (i <= size) {
                Product product = products.get(i-1);
                output.print(i + " " + product);
                logger.info(i + " " + product);
                i++;
            }
        }
        catch (Exception e) {
            logger.error("An exception has been thrown: " , e);
        }
        finally {
            entityManager.close();
        }
//        String queryString = "SELECT ROW_NUMBER() OVER(ORDER BY productID )" +
//                "AS row_num, productName, productQuantity, productPrice FROM products WHERE " +
//                "productCategory = ?";
//        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {
//            preparedStatement.setString(1, productCategory);
//            ResultSet res = preparedStatement.executeQuery();
//            while(res.next()) {
//                output.print(res.getInt("row_num") + " " +
//                        res.getString("productName") + " " +
//                        res.getInt("productQuantity") + " " +
//                        res.getInt("productPrice"));
//                logger.info(res.getInt("row_num") + " " +
//                        res.getString("productName") + " " +
//                        res.getInt("productQuantity") + " " +
//                        res.getInt("productPrice"));
//            }
//            res.close();
//        }
//        catch (SQLException ex) {
//            logger.warn("SQLException: " + ex.getLocalizedMessage() +
//                    "\n SQLState: " + ex.getSQLState() +
//                    "\n VendorError: " + ex.getErrorCode());
//        }
    }

    public void removeProduct(String productName) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            Query query = entityManager.createNamedQuery("Product.findByName");
            query.setParameter("productName", productName);
            Product product = (Product) query.getSingleResult();
            if (product.getQuantity() != 0) {
                logger.warn("Cannot remove product " + productName + ". Quantity is: " + product.getQuantity());
                transaction.rollback();
                return;
            }

            entityManager.remove(product);
            transaction.commit();
        }
        catch (Exception e) {
            logger.error("An exception was thrown: ", e);
            if (transaction != null) {
                transaction.rollback();
            }
        }
        finally {
            entityManager.close();
        }
//        String updateString = "DELETE FROM products WHERE productName = ? AND productQuantity = 0";
//        try (PreparedStatement preparedStatement = connection.prepareStatement(updateString)) {
//            preparedStatement.setString(1, productName);
//            preparedStatement.executeUpdate();
//            connection.commit();
//            logger.info("Product " + productName + " successfully removed.");
//            output.print("Product successfully removed.");
//        }
//        catch (SQLException ex) {
//            logger.warn("SQLException: " + ex.getLocalizedMessage() +
//                    "\n SQLState: " + ex.getSQLState() +
//                    "\n VendorError: " + ex.getErrorCode());
//            if (connection != null) {
//                try {
//                    logger.info("Transaction is being rolled back...");
//                    output.print("Transaction is being rolled back");
//                    connection.rollback();
//                }
//                catch (SQLException e) {
//                    logger.error("SQLException: " + e.getLocalizedMessage() +
//                            "\n SQLState: " + e.getSQLState() +
//                            "\n VendorError: " + e.getErrorCode());
//                }
//            }
//        }
    }

    public void replenish(String productName, String quantityString) {
        if (Constants.isIncorrectName(productName)) {
            logger.warn("Invalid name: " + productName);
            return;
        }
        if (Constants.isIncorrectNumber(quantityString)) {
            logger.warn("Invalid quantity: " + quantityString);
            return;
        }
        int quantity = Integer.parseInt(quantityString);
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            Query query = entityManager.createNamedQuery("Product.findByName");
            query.setParameter("productName", productName);
            Product product = (Product) query.getSingleResult();

            if (product.getQuantity() + quantity > product.getMaxQuantity()) {
                logger.warn("Can't replenish product, the added quantity would exceed max quantity stock.");
                transaction.rollback();
                return;
            }

            product.increment(quantity);
            entityManager.merge(product);
            transaction.commit();
        }
        catch (Exception e) {
            logger.error("An exception occured: ", e);
            if (transaction != null) {
                transaction.rollback();
            }
        }
        finally {
            entityManager.close();
        }
//        String updateString = "UPDATE products SET productQuantity = productQuantity + ? WHERE productName = ?";
//        int quantity = Integer.parseInt(quantityString);
//        try (PreparedStatement preparedStatement = connection.prepareStatement(updateString)) {
//            preparedStatement.setInt(1, quantity);
//            preparedStatement.setString(2, productName);
//            preparedStatement.executeUpdate();
//            connection.commit();
//            output.print("Product " + productName + " replenished.");
//            logger.info("Product " + productName + " replenished.");
//        }
//        catch (SQLException ex) {
//            logger.warn("SQLException: " + ex.getLocalizedMessage() +
//                    "\n SQLState: " + ex.getSQLState() +
//                    "\n VendorError: " + ex.getErrorCode());
//        }
    }

    public void undoReplenish(String productName, String quantityString) {
        if (Constants.isIncorrectName(productName)) {
            logger.error("Invalid name: " + productName);
            return;
        }
        if (Constants.isIncorrectNumber(quantityString)) {
            logger.error("Invalid quantity: " + quantityString);
            return;
        }
        int quantity = Integer.parseInt(quantityString);
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            Query query = entityManager.createNamedQuery("Product.findByName");
            query.setParameter("productName", productName);
            Product product = (Product) query.getSingleResult();

            product.decrement(quantity);
            entityManager.merge(product);
            transaction.commit();
        }
        catch (Exception e) {
            logger.error("An exception occured: ", e);
            if (transaction != null) {
                transaction.rollback();
            }
        }
        finally {
            entityManager.close();
        }
//        String updateString = "UPDATE products SET productQuantity = productQuantity - ? WHERE productName = ?";
//        int quantity = Integer.parseInt(quantityString);
//        try (PreparedStatement preparedStatement = connection.prepareStatement(updateString)) {
//            preparedStatement.setInt(1, quantity);
//            preparedStatement.setString(2, productName);
//            preparedStatement.executeUpdate();
//            connection.commit();
//        }
//        catch (SQLException ex) {
//            logger.warn("SQLException: " + ex.getLocalizedMessage() +
//                    "\n SQLState: " + ex.getSQLState() +
//                    "\n VendorError: " + ex.getErrorCode());
//            if (connection != null) {
//                try {
//                    logger.info("Transaction is being rolled back");
//                    output.print("Transaction is being rolled back");
//                    connection.rollback();
//                }
//                catch (SQLException e) {
//                    logger.error("SQLException: " + e.getLocalizedMessage() +
//                            "\n SQLState: " + e.getSQLState() +
//                            "\n VendorError: " + e.getErrorCode());
//                }
//            }
//        }
    }


    public void switchDisplayMode(String url) {
        PrintStrategy printStrategy = PrintStrategyFactory
                .createStrategy(url);
        if (printStrategy == null) {
            logger.error("Invalid print strategy");
            return;
        }
        output.close();
        output = printStrategy;
    }

    public void exportData(String fileType, String filePath) {
        Parser parser = ParserFactory.createParser(fileType);
        if (parser == null) {
            logger.warn("Couldn't fetch a valid parser of type: " + fileType);
            return;
        }

        parser.setUpFile(filePath);
        parser.writeToFile(this);
        parser.closeFile();
    }
}
