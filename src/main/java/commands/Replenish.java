package commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import main_components.Shop;

public class Replenish implements UndoableCommand{
    private Shop shop;
    private final String productName;
    private final String quantityString;
    private static final Logger logger = LogManager.getLogger(Replenish.class);

    public Replenish(Shop shop, String productName, String quantity) {
        this.shop = shop;
        this.productName = productName;
        this.quantityString =quantity;
    }

    @Override
    public void execute() {
        logger.info("REPLENISH PRODUCT " + productName + " " + quantityString);
        shop.write("REPLENISH PRODUCT " + productName + " " + quantityString);
        shop.replenish(productName, quantityString);
    }

    @Override
    public void undo() {
        logger.info("Action undone: REPLENISH PRODUCT " + productName + " " + quantityString);
        shop.write("Action undone: REPLENISH PRODUCT " + productName + " " + quantityString);
        shop.undoReplenish(productName, quantityString);
    }
}
