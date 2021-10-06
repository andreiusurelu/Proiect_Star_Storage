package commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import main_components.Shop;

public class PrintProductsAll implements SimpleCommand{
    private Shop shop;
    private static final Logger logger = LogManager.getLogger(PrintProductsAll.class);

    public PrintProductsAll(Shop shop) {
        this.shop = shop;
    }


    @Override
    public void execute(){
        logger.info("PRINT PRODUCTS ALL");
        shop.write("PRINT PRODUCTS ALL");
        shop.showAll();
    }

}
