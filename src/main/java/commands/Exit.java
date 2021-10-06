package commands;

import main_components.Shop;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Exit implements SimpleCommand{
    private Shop shop;
    private static final Logger logger = LogManager.getLogger(Exit.class);

    public Exit(Shop shop) {
        this.shop = shop;
    }

    @Override
    public void execute() {
        logger.info("EXIT");
        logger.info("Quitting program...");
        shop.write("EXIT");
        shop.write("Quitting program...");
        shop.close();
    }
}
