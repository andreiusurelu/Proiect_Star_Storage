package commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import main_components.Receiver;

public class AddNewCategory implements UndoableCommand{
    private Receiver receiver;
    private final String name;
    private static final Logger logger = LogManager.getLogger(AddNewCategory.class);

    public AddNewCategory(Receiver receiver, String name) {
        this.receiver = receiver;
        this.name = name;
    }


    @Override
    public void execute(){
        logger.info("ADD NEW CATEGORY" + name);
        receiver.write("ADD NEW CATEGORY " + name);
        receiver.addNewCategory(name);
    }

    @Override
    public void undo() {
        logger.info("Action undone: ADD NEW CATEGORY " + name);
        receiver.write("Action undone: ADD NEW CATEGORY " + name);
        receiver.undoAddNewCategory(name);
    }
}
