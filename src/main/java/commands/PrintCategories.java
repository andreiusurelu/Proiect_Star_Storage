package commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import main_components.Receiver;

public class PrintCategories implements SimpleCommand{
    private static final Logger logger = LogManager.getLogger(PrintCategories.class);
    private Receiver receiver;
    public PrintCategories (Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        logger.info("PRINT CATEGORIES");
        receiver.write("PRINT CATEGORIES");
        receiver.showCategories();
    }

}
