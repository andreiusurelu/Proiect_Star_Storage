package commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import main_components.Shop;


public class Buy implements UndoableCommand{
    private Shop shop;
    private final String productName;
    private final String quantityString;
    private final String username;
    private static final Logger logger = LogManager.getLogger(Buy.class);

    public Buy(Shop shop, String productName, String quantity, String username) {
        this.shop = shop;
        this.productName = productName;
        this.quantityString = quantity;
        this.username = username;
    }

    public void execute(){

        logger.info("BUY " + productName + " " + quantityString + " FOR " + username);
        shop.write("BUY " + productName + " " + quantityString + " FOR " + username);
        shop.doTransaction(username, productName, quantityString);
    }

    @Override
    public void undo() {
        logger.info("Undoing action: BUY " + productName + " " + quantityString + " FOR " + username);
        shop.undoTransaction(username, productName, quantityString);
    }
}
