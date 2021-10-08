package commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import main_components.Receiver;

public class PrintProductsAll implements SimpleCommand{
    private Receiver receiver;
    private static final Logger logger = LogManager.getLogger(PrintProductsAll.class);

    public PrintProductsAll(Receiver receiver) {
        this.receiver = receiver;
    }


    @Override
    public void execute(){
        logger.info("PRINT PRODUCTS ALL");
        receiver.write("PRINT PRODUCTS ALL");
        receiver.showAll();
    }

}
