package commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import entity.Product;
import main_components.Shop;


public class RemoveProduct implements UndoableCommand{
    private Shop shop;
    private final String productName;
    private Product removedProduct;
    private static final Logger logger = LogManager.getLogger(RemoveProduct.class);
    public RemoveProduct(Shop shop, String productName) {
        this.shop = shop;
        this.productName = productName;
    }

    @Override
    public void execute(){
        logger.info("REMOVE PRODUCT " + productName);
        shop.write("REMOVE PRODUCT " + productName);
        removedProduct = shop.fetchProductByName(productName);
        shop.removeProduct(productName);
    }

    @Override
    public void undo() {
        logger.info("Action undone: REMOVE PRODUCT " + productName);
        shop.write("Action undone: REMOVE PRODUCT " + productName);
        shop.addNewProduct(removedProduct.getName(), removedProduct.getCategory(),
                removedProduct.getQuantity(), removedProduct.getPrice(),
                removedProduct.getMaxQuantity());
    }

}
