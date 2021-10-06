package commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import main_components.Shop;

public class PrintDisplayMode implements SimpleCommand{
    private Shop shop;
    private static final Logger logger = LogManager.getLogger(PrintDisplayMode.class);

    public PrintDisplayMode(Shop shop) {
        this.shop = shop;
    }


    @Override
    public void execute() {
        logger.info("PRINT DISPLAY_MODE");
        shop.write("PRINT DISPLAY_MODE");
        shop.showDisplayMode();
    }
}
