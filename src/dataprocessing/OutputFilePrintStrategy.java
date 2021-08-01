package dataprocessing;

import org.apache.commons.lang3.SystemUtils;
import utils.Constants;
import utils.VariableException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OutputFilePrintStrategy implements PrintStrategy{
    private File file;
    private BufferedWriter bufferedWriter;
    public OutputFilePrintStrategy(String filePath) {
        try {
            String extractedFilePath;
            if (SystemUtils.IS_OS_WINDOWS) {
                extractedFilePath = filePath.replace("\"", "").replace("\\", "\\\\");
            }
            else {
                extractedFilePath = filePath.replace("\"", "");
            }
            file = new File(extractedFilePath);
            file.createNewFile();

            if (Constants.isPathValid(extractedFilePath)) {
                this.bufferedWriter = new BufferedWriter(new FileWriter(file, true));
            }
            else {
                throw new VariableException
                        .InvalidCommandValueException("Invalid path: " + extractedFilePath);
            }
        }
        catch (IOException | VariableException.InvalidCommandValueException e) {
            e.printStackTrace();
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
        return "FILE " + file.getAbsolutePath();
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
