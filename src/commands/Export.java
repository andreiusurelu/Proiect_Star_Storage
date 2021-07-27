package commands;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.SystemUtils;
import shop.Shop;
import utils.VariableException;
import utils.Writer;

public class Export implements Command{
    private Shop shop;
    private final String outputPath;
    public Export(String outputPath) {
        this.shop = Shop.getInstance();
        String extractedPath = outputPath.replaceAll("\"","");
        if (SystemUtils.IS_OS_WINDOWS) {
            this.outputPath = extractedPath.replace("\\", "\\\\");
        }
        else {
            this.outputPath = extractedPath;
        }
    }
    @Override
    public void execute() throws VariableException.InvalidCommandValueException {
        shop.write("EXPORT " + outputPath);
        Writer writer = new Writer();
        writer.setJsonFile(outputPath);
        ObjectNode obj = writer.writeFile(shop);
        writer.closeJSON(obj);
    }
}
