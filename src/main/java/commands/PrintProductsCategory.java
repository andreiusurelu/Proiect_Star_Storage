package commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import main_components.Receiver;

public class PrintProductsCategory implements SimpleCommand {
    private Receiver receiver;
    private final String category;
    private static final Logger logger = LogManager.getLogger(PrintProductsCategory.class);
    public PrintProductsCategory(Receiver receiver, String category){
        this.receiver = receiver;
        this.category = category;
    }

    @Override
    public void execute() {
        logger.info("PRINT PRODUCTS CATEGORY " + category);
        receiver.write("PRINT PRODUCTS CATEGORY " + category);
        receiver.showByCategory(category);
    }
}
