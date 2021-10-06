package parsers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import main_components.Shop;
import utils.Constants;

import java.io.FileWriter;
import java.io.IOException;

public class XMLParser implements Parser{
    private FileWriter xmlFile;
    private static final Logger logger = LogManager.getLogger(XMLParser.class);

    @Override
    public void setUpFile(String filePath) {
        if (Constants.isPathValid(filePath) || !filePath.endsWith(".xml")) {
            logger.warn("Invalid path, check to see if it " +
                    "indicates a file or if it is written correctly");
            return;
        }
        try {
            xmlFile = new FileWriter(filePath);
        }
        catch (IOException e) {
            logger.error(e);
        }
    }

    @Override
    public void writeToFile(Shop shop) {
        //Coming soon!
//        EntityManager entityManager = factory.createEntityManager();
//        try {
//            List<Category> categoryList = entityManager.createNamedQuery("Category.printEntries").getResultList();
//            List<Consumer> consumerList = entityManager.createNamedQuery("Consumer.printEntries").getResultList();
//
//            ObjectMapper mapper = new XmlMapper();
//            StringBuilder stringBuilder = new StringBuilder();
//            mapper.enable(SerializationFeature.INDENT_OUTPUT);
//
//            for (Category category : categoryList) {
//                List<Product> productList = entityManager.createNamedQuery("Product.printByCategory")
//                        .setParameter("category", category).getResultList();
//                for (Product product : productList) {
//                    stringBuilder.append(mapper.writeValueAsString(product));
//                }
//            }
//        }
//        catch (Exception e) {
//            logger.error("An error has occured: ", e);
//        }
//        finally {
//            entityManager.close();
//        }
    }

    @Override
    public void closeFile() {
        try {
            xmlFile.close();
        }
        catch (IOException e) {
            logger.error(e);
        }
    }
}
