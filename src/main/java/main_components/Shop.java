package main_components;

import dataprocessing.PrintStrategy;
import dataprocessing.PrintStrategyFactory;
import entity.Category;
import entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import parsers.Parser;
import parsers.ParserFactory;
import utils.Constants;
import utils.Utils;

import javax.persistence.*;


@Component
public class Shop implements Receiver {
    private static final Logger logger = LogManager.getLogger(Shop.class);
    private static final EntityManagerFactory factory = Persistence
            .createEntityManagerFactory("entity");
    private PrintStrategy output;

    @Autowired
    public Shop() {
        output = PrintStrategyFactory.createStrategy("CONSOLE");
    }

    public String getDisplayMode() {
        return output.getDisplayMode();
    }

    public void showDisplayMode() {
        String displayMode = output.getDisplayMode();
        write(displayMode);
    }

    public void write(String message) {
        output.print(message);
        logger.info(message);
    }

    public int addNewCategory(String categoryName) {
        int ID = -1;
        String check = Utils.checkCategoryConstraints(categoryName);
        if (check != null) {
            logger.error(check);
            return ID;
        }
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            Category category = new Category();
            category.setName(categoryName);

            entityManager.persist(category);
            ID = category.getId();
            transaction.commit();
            write("Category added.");
        }
        catch (Exception e) {
            logger.error("An exception occurred: ", e);
            if (transaction != null) {
                transaction.rollback();
            }
        }
        finally {
            entityManager.close();
        }
        return ID;
    }

    public void undoAddNewCategory(int ID) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = null;
        Category category;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            Query query = entityManager.createNamedQuery("Category.deleteEntry");
            query.setParameter("id", ID);
            query.executeUpdate();

            transaction.commit();
        }
        catch (Exception e) {
            logger.error("An exception occurred: ", e);
            if (transaction != null) {
                transaction.rollback();
            }
        }

        finally {
            entityManager.close();
        }
    }
    public int addNewProduct(String productName, String productCategory,
                              String quantity, String price, String maxQuantity) {
        int ID = -1;
        String check = Utils.checkProductConstraints(productName, productCategory, quantity, price, maxQuantity);
        if (check != null) {
            logger.error(check);
            return ID;
        }
        int productQuantity = Integer.parseInt(quantity);
        int productPrice = Integer.parseInt(price);
        int productMaxQuantity = Integer.parseInt(maxQuantity);

        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            TypedQuery<Category> query = entityManager.createNamedQuery("Category.findByName", Category.class);
            query.setParameter("categoryName", productCategory);
            Category category = query.getSingleResult();

            Product product = new Product();
            product.setName(productName);
            product.setCategory(category);
            product.setQuantity(productQuantity);
            product.setPrice(productPrice);
            product.setMaxQuantity(productMaxQuantity);


            entityManager.persist(product);
            ID = product.getId();
            transaction.commit();

            write(productQuantity + " " + productName + " have been added to "
                    + productCategory + " category.");
        }
        catch (Exception e) {
            logger.error("An exception occurred: ", e);
            if (transaction != null) {
                transaction.rollback();
            }
        }
        finally {
            entityManager.close();
        }
        return ID;
    }

    public int addNewProduct(Product product) {

        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = null;
        int ID = -1;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            entityManager.persist(product);

            ID = product.getId();

            write(product.getQuantity() + " " + product.getName() + " have been added to "
                    + product.getCategory() + " category.");

            transaction.commit();
        }
        catch (Exception e) {
            logger.error("An exception occurred: ", e);
            if (transaction != null) {
                transaction.rollback();
            }
        }
        finally {
            entityManager.close();
        }
        return ID;
    }

    public void undoAddNewProduct(int productID) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = null;

        try {

            transaction = entityManager.getTransaction();
            transaction.begin();

            Query query = entityManager.createNamedQuery("Product.deleteEntry");
            query.setParameter("id", productID);
            query.executeUpdate();

            transaction.commit();
        }
        catch (Exception e) {
            logger.error("An exception occurred: ", e);
            if (transaction != null) {
                transaction.rollback();
            }
        }
        finally {
            entityManager.close();
        }
    }


    public void doTransaction(String consumerUsername, String productName, String productQuantityString) {

        String check = Utils.checkBuyConstraints(consumerUsername, productName, productQuantityString);
        if (check != null) {
            logger.error(check);
            return;
        }

        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            int quantity = Integer.parseInt(productQuantityString);

            Query productTypedQuery = entityManager
                    .createNamedQuery("Product.decrement");
            productTypedQuery.setParameter("name", productName);
            productTypedQuery.setParameter("quantity", quantity);
            productTypedQuery.executeUpdate();

            TypedQuery<Integer> priceQuery = entityManager.createNamedQuery("Product.fetchPriceByName",
                    Integer.class);
            priceQuery.setParameter("name", productName);
            int price = priceQuery.getSingleResult();

            Query consumerQuery = entityManager.createNamedQuery("Consumer.buyProduct");
            consumerQuery.setParameter("quantity", quantity);
            consumerQuery.setParameter("price", price);
            consumerQuery.setParameter("username", consumerUsername);
            consumerQuery.executeUpdate();

            transaction.commit();

            write("Consumer " + consumerUsername + " successfully bought " +
                    productQuantityString + " " + productName);
        }
        catch (Exception e) {
            logger.error("An exception occurred: ", e);
            if (transaction != null) {
                transaction.rollback();
            }
        }
        finally {
            entityManager.close();
        }
    }

    public void undoTransaction(String consumerUsername, String productName, String productQuantityString) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            int quantity = Integer.parseInt(productQuantityString);
            Query productQuery = entityManager.createNamedQuery("Product.increment");
            productQuery.setParameter("quantity", quantity);
            productQuery.setParameter("name", productName);
            productQuery.executeUpdate();

            TypedQuery<Integer> priceQuery = entityManager.createNamedQuery("Product.fetchPriceByName",
                    Integer.class);
            priceQuery.setParameter("name", productName);
            int price = priceQuery.getSingleResult();

            Query consumerQuery = entityManager.createNamedQuery("Consumer.undoBuyProduct");
            consumerQuery.setParameter("quantity", quantity);
            consumerQuery.setParameter("username", consumerUsername);
            consumerQuery.setParameter("price", price);
            productQuery.executeUpdate();

            transaction.commit();
        }
        catch (Exception e) {
            logger.error("An exception occurred: ", e);
            if (transaction != null) {
                transaction.rollback();
            }
        }

        finally {
            entityManager.close();
        }
    }

    public void close() {
        factory.close();
        output.close();
    }

    public void showCategories() {
        EntityManager entityManager = factory.createEntityManager();
        try {
            TypedQuery<String> query = entityManager
                    .createNamedQuery("Category.fetchEntriesNameOnly", String.class);
            String resultString = String.join(", ", query.getResultList());
            write(resultString);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }
        finally {
            entityManager.close();
        }
    }


    public void showProduct(String productName) {
        if (Constants.isIncorrectName(productName)) {
            logger.error("Invalid name");
            return;
        }
        EntityManager entityManager = factory.createEntityManager();
        try {
            TypedQuery<Product> query = entityManager.createNamedQuery("Product.fetchByName", Product.class);
            query.setParameter("productName", productName);
            String resultString = query.getSingleResult().toString();
            write(resultString);
        }
        catch (Exception e) {
            logger.error("An exception was thrown: ", e);
        }
        finally {
            entityManager.close();
        }
    }

    public void showAll() {
        EntityManager entityManager = factory.createEntityManager();
        try {
            TypedQuery<Product> query = entityManager.createNamedQuery("Product.fetchEntries", Product.class);
            List<Product> products = query.getResultList();
            int i = 1, size = products.size();
            while (i <= size) {
                Product product = products.get(i - 1);
                write(i + " " + product.getName() + " " + product.getQuantity() + " " +
                        product.getCategory() + " " + product.getPrice());
                i++;
            }
        } catch (Exception e) {
            logger.error("An exception has been thrown: ", e);
        } finally {
            entityManager.close();
        }
    }

    public void showByCategory(String productCategory) {
        if (Constants.isIncorrectName(productCategory)) {
            logger.error("Incorrect product category name: " + productCategory);
            return;
        }
        EntityManager entityManager = factory.createEntityManager();
        try {
            TypedQuery<Product> query = entityManager
                    .createNamedQuery("Product.fetchByCategoryName", Product.class);
            query.setParameter("categoryName", productCategory);
            List<Product> products = query.getResultList();
            int i = 1, size = products.size();
            while (i <= size) {
                Product product = products.get(i-1);
                write(i + " " + product);
                i++;
            }
        }
        catch (Exception e) {
            logger.error("An exception has been thrown: " , e);
        }
        finally {
            entityManager.close();
        }
    }

    public Product removeProduct(String productName) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = null;
        Product product = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            TypedQuery<Product> productQuery = entityManager
                    .createNamedQuery("Product.fetchByName", Product.class);
            productQuery.setParameter("productName", productName);
            product = productQuery.getSingleResult();

            int quantity = product.getQuantity();
            if (quantity > 0) {
                product = null;
                throw new Exception("Cannot remove product, quantity is not 0: " + quantity);
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
        return product;
    }

    public void replenish(String productName, String quantityString) {
        String check = Utils.checkReplenishConstraints(productName, quantityString);
        if (check != null) {
            logger.error(check);
            return;
        }
        int quantity = Integer.parseInt(quantityString);
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            Query query = entityManager.createNamedQuery("Product.increment");
            query.setParameter("quantity", quantity);
            query.setParameter("name", productName);
            query.executeUpdate();

            transaction.commit();
        }
        catch (Exception e) {
            logger.error("An exception occurred: ", e);
            if (transaction != null) {
                transaction.rollback();
            }
        }
        finally {
            entityManager.close();
        }
    }

    public void undoReplenish(String productName, String quantityString) {
        int quantity = Integer.parseInt(quantityString);
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            Query query = entityManager.createNamedQuery("Product.decrement");
            query.setParameter("name", productName);
            query.setParameter("quantity", quantity);
            query.executeUpdate();
            transaction.commit();
        }
        catch (Exception e) {
            logger.error("An exception occurred: ", e);
            if (transaction != null) {
                transaction.rollback();
            }
        }
        finally {
            entityManager.close();
        }
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
            logger.error("Couldn't fetch a valid parser of type: " + fileType);
            return;
        }

        parser.setUpFile(filePath);
        parser.writeToFile(this);
        parser.closeFile();
    }
}
