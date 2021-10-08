package dataprocessing;

import org.apache.commons.lang3.SystemUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.Constants;
import utils.VariableException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OutputFilePrintStrategy implements PrintStrategy{
    private static final Logger logger
            = LogManager.getLogger(OutputFilePrintStrategy.class);
    private String filePath = null;
    private BufferedWriter bufferedWriter;
    public OutputFilePrintStrategy(String filePath) {
        try {
            String extractedFilePath;
            if (SystemUtils.IS_OS_WINDOWS) {
                extractedFilePath = filePath.replace("\"", "").replace("\\", "\\\\");
            } else {
                extractedFilePath = filePath.replace("\"", "");
            }
            if (!Constants.isPathValid(extractedFilePath)) {
                logger.error(new VariableException.InvalidCommandValueException("Invalid path " + extractedFilePath));
            }
            File file = new File(extractedFilePath);
            if (file.createNewFile()) {
                logger.info("Output file created.");
                bufferedWriter = new BufferedWriter(new FileWriter(file));
            }
            else {
                logger.info("Output file already exists, messages will be appended.");
                bufferedWriter = new BufferedWriter(new FileWriter(file, true));
            }
            this.filePath = extractedFilePath;
        }
        catch (IOException e) {
            logger.error("Can't open file");
        }
    }

    @Override
    public void print(String message) {
        try {
            bufferedWriter.write("[" + LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss, SSS")) +  "] " + message);
            bufferedWriter.newLine();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public String getStrategyDescription() {
        return "This strategy will print to a given output file, and it will append the " +
                "local date and time before the message.";
    }

    @Override
    public String getDisplayMode() {
        return "FILE " + filePath;
    }

    @Override
    public void close() {
        try {
            bufferedWriter.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
