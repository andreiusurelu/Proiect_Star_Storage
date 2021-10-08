package commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import main_components.Receiver;



public class AddNewProduct implements UndoableCommand{
    private Receiver receiver;
    private final String name;
    private final String category;
    private final String quantityString;
    private final String priceString;
    private final String maxQuantityString;
    private static final Logger logger = LogManager.getLogger(AddNewProduct.class);
    public AddNewProduct(Receiver receiver, String name, String category,
                         String quantity, String price, String maxQuantity) {
        this.receiver = receiver;
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
        receiver.write("ADD NEW PRODUCT " + name + " "
                + category + " " + quantityString + " " + priceString + " " + maxQuantityString);
        receiver.addNewProduct(name, category, quantityString, priceString, maxQuantityString);
    }

    @Override
    public void undo() {
        logger.info("Action undone: ADD NEW PRODUCT " + name + " " + category + " " +
                quantityString + " " + priceString + " " + maxQuantityString);
        receiver.write("Action undone: ADD NEW PRODUCT " + name + " " + category + " " +
                quantityString + " " + priceString + " " + maxQuantityString);
        receiver.undoAddNewProduct(name, category, quantityString, priceString, maxQuantityString);
    }

}
