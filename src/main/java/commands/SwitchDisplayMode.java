package commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import main_components.Shop;


public class SwitchDisplayMode implements UndoableCommand{
    private Shop shop;
    private String[] info;
    private String prevInfo;
    private static final Logger logger = LogManager.getLogger(SwitchDisplayMode.class);

    public SwitchDisplayMode (Shop shop, String ...args) {
        this.shop = shop;
        info = args;
    }

    @Override
    public void execute() {
        logger.info("SWITCH DISPLAY_MODE " + String.join(" ", info));
        shop.write("SWITCH DISPLAY_MODE " + String.join(" ", info));
        prevInfo = shop.getDisplayMode();
        shop.switchDisplayMode(info[0], java.util
                .Arrays.stream(info, 1, info.length).toArray(String[]::new));
    }

    @Override
    public void undo() {
        String[] prevInfoArr = prevInfo.split(" ");
        logger.info("Action undone: SWITCH DISPLAY_MODE " + String.join(" ", info));
        shop.write("Action undone: SWITCH DISPLAY_MODE " + String.join(" ", info));
        shop.switchDisplayMode(prevInfoArr[0], java.util
                .Arrays.stream(prevInfoArr, 1, info.length).toArray(String[]::new));
    }
}
