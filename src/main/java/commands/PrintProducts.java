package commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import main_components.Shop;

public class PrintProducts implements SimpleCommand{
    private Shop shop;
    private final String productName;
    private static final Logger logger = LogManager.getLogger(PrintProducts.class);

    public PrintProducts(Shop shop, String product) {
        this.shop = shop;
        this.productName = product;
    }



    @Override
    public void execute(){
        logger.info("PRINT PRODUCTS " + productName);
        shop.write("PRINT PRODUCTS " + productName);
        shop.showProduct(productName);
    }
}
