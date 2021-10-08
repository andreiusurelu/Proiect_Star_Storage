package commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import main_components.Receiver;

public class PrintProducts implements SimpleCommand{
    private Receiver receiver;
    private final String productName;
    private static final Logger logger = LogManager.getLogger(PrintProducts.class);

    public PrintProducts(Receiver receiver, String product) {
        this.receiver = receiver;
        this.productName = product;
    }



    @Override
    public void execute(){
        logger.info("PRINT PRODUCTS " + productName);
//        receiver.write("PRINT PRODUCTS " + productName);
        receiver.showProduct(productName);
    }
}
