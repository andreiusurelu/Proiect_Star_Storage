package commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import entity.Product;
import main_components.Receiver;


public class RemoveProduct implements UndoableCommand{
    private Receiver receiver;
    private final String productName;
    private Product removedProduct;
    private static final Logger logger = LogManager.getLogger(RemoveProduct.class);
    public RemoveProduct(Receiver receiver, String productName) {
        this.receiver = receiver;
        this.productName = productName;
    }

    @Override
    public void execute(){
        logger.info("REMOVE PRODUCT " + productName);
        receiver.write("REMOVE PRODUCT " + productName);
        removedProduct = receiver.fetchProductByName(productName);
        receiver.removeProduct(productName);
    }

    @Override
    public void undo() {
        logger.info("Action undone: REMOVE PRODUCT " + productName);
        receiver.write("Action undone: REMOVE PRODUCT " + productName);
        receiver.addNewProduct(removedProduct.getName(), removedProduct.getCategory(),
                removedProduct.getQuantity(), removedProduct.getPrice(),
                removedProduct.getMaxQuantity());
    }

}
