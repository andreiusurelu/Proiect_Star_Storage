package commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import main_components.Receiver;

public class Replenish implements UndoableCommand{
    private Receiver receiver;
    private final String productName;
    private final String quantityString;
    private static final Logger logger = LogManager.getLogger(Replenish.class);

    public Replenish(Receiver receiver, String productName, String quantity) {
        this.receiver = receiver;
        this.productName = productName;
        this.quantityString =quantity;
    }

    @Override
    public void execute() {
        logger.info("REPLENISH PRODUCT " + productName + " " + quantityString);
        receiver.write("REPLENISH PRODUCT " + productName + " " + quantityString);
        receiver.replenish(productName, quantityString);
    }

    @Override
    public void undo() {
        logger.info("Action undone: REPLENISH PRODUCT " + productName + " " + quantityString);
        receiver.write("Action undone: REPLENISH PRODUCT " + productName + " " + quantityString);
        receiver.undoReplenish(productName, quantityString);
    }
}
