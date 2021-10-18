package parsers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entity.Category;
import entity.Consumer;
import entity.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import main_components.Receiver;
import utils.Constants;

import javax.persistence.EntityManager;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.List;

public class JSONParser implements Parser{
    private FileWriter jsonFile;
    private static final Logger logger = LogManager.getLogger(JSONParser.class);

    @Override
    public void setUpFile(String filePath) {
        if (Constants.isPathValid(filePath) && filePath.endsWith(".json")) {
            try {
                jsonFile = new FileWriter(new File(filePath));
            } catch (IOException e) {
                logger.error(e);
            } catch (NullPointerException e) {
                logger.error("Null pointer exception thrown: ", e);
            }
        }
        else {
            logger.error("Invalid path, check to see if it " +
                    "indicates a file or if it is written correctly");
            return;
        }
    }

    @Override
    public void writeToFile(Receiver receiver) {
        EntityManager entityManager = factory.createEntityManager();
        try {
            List<Category> categoryList = entityManager.createNamedQuery("Category.fetchEntries").getResultList();
            List<Consumer> consumerList = entityManager.createNamedQuery("Consumer.fetchEntries").getResultList();

            if (consumerList.isEmpty()) {
                logger.error("Consumer list is empty!");
                return;
            }

            if (categoryList.isEmpty()) {
                logger.error("Category list is empty!");
                return;
            }

            ObjectMapper mapper = new ObjectMapper();
            ObjectNode main = mapper.createObjectNode();

            try {
                Field mapField = main.getClass().getDeclaredField("_children");
                mapField.setAccessible(true);
                mapField.set(main, new LinkedHashMap<>());
                mapField.setAccessible(false);
            }
            catch (NoSuchFieldException | IllegalAccessException e) {
                logger.error("An exception occured: ", e);
                return;
            }

            ArrayNode stockArray = mapper.createArrayNode();
            ArrayNode consumerArray = mapper.createArrayNode();

            for (Category category : categoryList) {
                List<Product> productList = entityManager.createNamedQuery("Product.fetchByCategory")
                        .setParameter("category", category).getResultList();
                for (Product product : productList) {
                    ObjectNode productObj = mapper.createObjectNode();
                    productObj.put(Constants.CATEGORY, category.getName());
                    productObj.put(Constants.NAME, product.getName());
                    productObj.put(Constants.QUANTITY, product.getQuantity());
                    productObj.put(Constants.PRICE, product.getPrice());
                    productObj.put(Constants.MAXQUANTITY, product.getMaxQuantity());
                    stockArray.add(productObj);
                }
            }

            for (Consumer consumer : consumerList) {
                ObjectNode consumerObj = mapper.createObjectNode();
                consumerObj.put(Constants.USERNAME, consumer.getConsumerUsername());
                consumerObj.put(Constants.BALANCE, consumer.getConsumerBalance());
                consumerArray.add(consumerObj);
            }

            main.put(Constants.STOCK, stockArray);
            main.put(Constants.CLIENTS, consumerArray);

            jsonFile.write(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(main));
            //jsonFile.close();
        }
        catch (Exception e) {
            logger.error("An exception occured: ", e);
        }
        finally {
            entityManager.close();
        }
    }

    @Override
    public void closeFile() {
        try {
            jsonFile.close();
        }
        catch (IOException e) {
            logger.error("Failed to close file!");
        }
    }
}
