package parsers;

import main_components.Receiver;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public interface Parser {
    EntityManagerFactory factory = Persistence.createEntityManagerFactory("entity");
    void setUpFile(String filePath);
    void writeToFile(Receiver receiver);
    void closeFile();
}
