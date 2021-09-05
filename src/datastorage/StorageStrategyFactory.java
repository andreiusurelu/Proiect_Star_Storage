package datastorage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class StorageStrategyFactory {
    public static StorageStrategy createStrategy(String propertyFilePath) {
        try (InputStream input = new FileInputStream(propertyFilePath)){
            Properties prop = new Properties();
            prop.load(input);
            StorageType type = StorageType.fromString(prop.getProperty("strategyType"));
            if (type == null) {
                throw new IllegalArgumentException("Invalid type.");
            }
            return switch (type) {
                case DATA_STRUCTURE -> new DataStructureStorageStrategy(prop.getProperty("path"));
                case DATA_BASE -> new DataBaseStorageStrategy(prop.getProperty("url"),
                        prop.getProperty("username"), prop.getProperty("password"));
            };
        }
        catch (IllegalArgumentException | IOException ex) {
            System.out.println(ex.getMessage());
            return null;
        }

    }
}
