package dataprocessing;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PrintStrategyFactory {
    private static final Logger logger = LogManager.getLogger(PrintStrategyFactory.class);
    public static PrintStrategy createStrategy(String url) {
        String[] args = url.split(" ");
        PrintType type = PrintType.fromString(args[0]);
        if (type == null) {
            logger.error("Invalid type: " + type.text);
            return null;
        }
        return switch (type) {
            case CONSOLE -> new ConsolePrintStrategy();
            case FILE -> new OutputFilePrintStrategy(args[1]);
        };

    }
}
