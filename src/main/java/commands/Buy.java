package commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import main_components.Receiver;


public class Buy implements UndoableCommand{
    private Receiver receiver;
    private final String productName;
    private final String quantityString;
    private final String username;
    private static final Logger logger = LogManager.getLogger(Buy.class);

    public Buy(Receiver receiver, String productName, String quantity, String username) {
        this.receiver = receiver;
        this.productName = productName;
        this.quantityString = quantity;
        this.username = username;
    }

    public void execute(){

        logger.info("BUY " + productName + " " + quantityString + " FOR " + username);
        receiver.write("BUY " + productName + " " + quantityString + " FOR " + username);
        receiver.doTransaction(username, productName, quantityString);
    }

    @Override
    public void undo() {
        logger.info("Undoing action: BUY " + productName + " " + quantityString + " FOR " + username);
        receiver.undoTransaction(username, productName, quantityString);
    }
}
