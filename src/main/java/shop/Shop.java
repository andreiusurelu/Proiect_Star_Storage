package shop;

import datastorage.StorageStrategy;
import datastorage.StorageStrategyFactory;

public class Shop {
    public StorageStrategy storage;
    private static Shop instance = null;

    private Shop() {
    }

    public static Shop getInstance() {
        if (instance == null) {
            instance = new Shop();
        }
        return instance;
    }

    public void initializeStorage(String propertyFilePath) {
        storage = StorageStrategyFactory.createStrategy(propertyFilePath);
    }

    public void showDisplayMode() {
        storage.write(storage.getPrintStrategy());
    }

    public void write(String message) {
        storage.write(message);
    }
}
