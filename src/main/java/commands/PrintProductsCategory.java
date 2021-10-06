package commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import main_components.Shop;

public class PrintProductsCategory implements SimpleCommand {
    private Shop shop;
    private final String category;
    private static final Logger logger = LogManager.getLogger(PrintProductsCategory.class);
    public PrintProductsCategory(Shop shop, String category){
        this.shop = shop;
        this.category = category;
    }

    @Override
    public void execute() {
        logger.info("PRINT PRODUCTS CATEGORY " + category);
        shop.write("PRINT PRODUCTS CATEGORY " + category);
        shop.showByCategory(category);
    }
}
