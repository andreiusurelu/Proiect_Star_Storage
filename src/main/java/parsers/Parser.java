package parsers;

import main_components.Shop;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public interface Parser {
    EntityManagerFactory factory = Persistence.createEntityManagerFactory("entity");
    void setUpFile(String filePath);
    void writeToFile(Shop shop);
    void closeFile();
}
