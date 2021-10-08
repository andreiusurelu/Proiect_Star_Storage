package commands;

import org.apache.commons.lang3.SystemUtils;
import main_components.Receiver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Export implements SimpleCommand{
    private Receiver receiver;
    private final String outputPath;
    private static final Logger logger = LogManager.getLogger(Export.class);
    public Export(Receiver receiver, String outputPath) {
        this.receiver = receiver;
        String extractedPath = outputPath.replaceAll("\"","");
        if (SystemUtils.IS_OS_WINDOWS) {
            this.outputPath = extractedPath.replace("\\", "\\\\");
        }
        else {
            this.outputPath = extractedPath;
        }
    }


    @Override
    public void execute() {
        logger.info("EXPORT " + outputPath);
        receiver.write("EXPORT " + outputPath);
        receiver.exportData("JSON", outputPath);
    }
}
