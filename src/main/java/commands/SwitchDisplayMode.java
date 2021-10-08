package commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import main_components.Receiver;


public class SwitchDisplayMode implements UndoableCommand{
    private Receiver receiver;
    private String info;
    private String prevInfo;
    private static final Logger logger = LogManager.getLogger(SwitchDisplayMode.class);

    public SwitchDisplayMode (Receiver receiver, String url) {
        this.receiver = receiver;
        info = url;
    }

    @Override
    public void execute() {
        logger.info("SWITCH DISPLAY_MODE " + String.join(" ", info));
        receiver.write("SWITCH DISPLAY_MODE " + String.join(" ", info));
        prevInfo = receiver.getDisplayMode();
        receiver.switchDisplayMode(info);
    }

    @Override
    public void undo() {
        logger.info("Action undone: SWITCH DISPLAY_MODE " + String.join(" ", info));
        receiver.write("Action undone: SWITCH DISPLAY_MODE " + String.join(" ", info));
        receiver.switchDisplayMode(prevInfo);
    }
}
