package commands;

import main_components.Receiver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Exit implements SimpleCommand{
    private Receiver receiver;
    private static final Logger logger = LogManager.getLogger(Exit.class);

    public Exit(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        logger.info("EXIT");
        logger.info("Quitting program...");
        receiver.write("EXIT");
        receiver.write("Quitting program...");
        receiver.close();
        System.exit(0);
    }
}
