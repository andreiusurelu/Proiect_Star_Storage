package commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import main_components.Shop;



public class AddNewProduct implements UndoableCommand{
    private Shop shop;
    private final String name;
    private final String category;
    private final String quantityString;
    private final String priceString;
    private final String maxQuantityString;
    private static final Logger logger = LogManager.getLogger(AddNewProduct.class);
    public AddNewProduct(Shop shop, String name, String category,
                         String quantity, String price, String maxQuantity) {
        this.shop = shop;
        this.name = name;
        this.category = category;
        this.quantityString = quantity;
        this.priceString = price;
        this.maxQuantityString = maxQuantity;
    }

    @Override
    public void execute(){

        logger.info("ADD NEW PRODUCT " + name + " "
                + category + " " + quantityString + " " + priceString + " " + maxQuantityString);
        shop.write("ADD NEW PRODUCT " + name + " "
                + category + " " + quantityString + " " + priceString + " " + maxQuantityString);
        shop.addNewProduct(name, category, quantityString, priceString, maxQuantityString);
    }

    @Override
    public void undo() {
        logger.info("Action undone: ADD NEW PRODUCT " + name + " " + category + " " +
                quantityString + " " + priceString + " " + maxQuantityString);
        shop.write("Action undone: ADD NEW PRODUCT " + name + " " + category + " " +
                quantityString + " " + priceString + " " + maxQuantityString);
        shop.undoAddNewProduct(name, category, quantityString, priceString, maxQuantityString);
    }

}
