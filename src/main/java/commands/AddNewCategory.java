package commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import main_components.Shop;

public class AddNewCategory implements UndoableCommand{
    private Shop shop;
    private final String name;
    private static final Logger logger = LogManager.getLogger(AddNewCategory.class);

    public AddNewCategory(Shop shop, String name) {
        this.shop = shop;
        this.name = name;
    }


    @Override
    public void execute(){
        logger.info("ADD NEW CATEGORY" + name);
        shop.write("ADD NEW CATEGORY " + name);
        shop.addNewCategory(name);
    }

    @Override
    public void undo() {
        logger.info("Action undone: ADD NEW CATEGORY " + name);
        shop.write("Action undone: ADD NEW CATEGORY " + name);
        shop.undoAddNewCategory(name);
    }
}
