package commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import main_components.Shop;

public class PrintCategories implements SimpleCommand{
    private static final Logger logger = LogManager.getLogger(PrintCategories.class);
    private Shop shop;
    public PrintCategories (Shop shop) {
        this.shop = shop;
    }

    @Override
    public void execute() {
        logger.info("PRINT CATEGORIES");
        shop.write("PRINT CATEGORIES");
        shop.showCategories();
    }

}
