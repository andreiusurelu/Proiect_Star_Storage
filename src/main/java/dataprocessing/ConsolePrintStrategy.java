package dataprocessing;

import commands.CommandType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConsolePrintStrategy implements PrintStrategy{
    private static final Logger logger = LogManager.getLogger(ConsolePrintStrategy.class);

    @Override
    public void print(String message) {
        if (CommandType.notCommand(message)) {
            System.out.println(message);
            logger.info(message);
        }
    }
    @Override
    public String getStrategyDescription() {
        return "This strategy will print to the console.";
    }

    @Override
    public String getDisplayMode() {
        return "CONSOLE";
    }

    @Override
    public void close() {

    }
}
