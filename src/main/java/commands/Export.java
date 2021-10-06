package commands;

import org.apache.commons.lang3.SystemUtils;
import main_components.Shop;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Export implements SimpleCommand{
    private Shop shop;
    private final String outputPath;
    private static final Logger logger = LogManager.getLogger(Export.class);
    public Export(Shop shop, String outputPath) {
        this.shop = shop;
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
        shop.write("EXPORT " + outputPath);
        shop.exportData("JSON", outputPath);
    }
}
