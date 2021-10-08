package commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import main_components.Receiver;

public class PrintDisplayMode implements SimpleCommand{
    private Receiver receiver;
    private static final Logger logger = LogManager.getLogger(PrintDisplayMode.class);

    public PrintDisplayMode(Receiver receiver) {
        this.receiver = receiver;
    }


    @Override
    public void execute() {
        logger.info("PRINT DISPLAY_MODE");
        receiver.write("PRINT DISPLAY_MODE");
        receiver.showDisplayMode();
    }
}
