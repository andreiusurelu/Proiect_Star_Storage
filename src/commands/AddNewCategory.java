package commands;

import shop.Shop;
import utils.Constants;
import utils.VariableException;

public class AddNewCategory implements Command{
    private Shop shop;
    private final String name;
    public AddNewCategory(String name) {
        this.shop = Shop.getInstance();
        this.name = name;
    }

    @Override
    public void execute() throws VariableException.InvalidCommandValueException,
            VariableException.OverrideCommandException {
        if (Constants.isIncorrectName(name)) {
            throw new VariableException.InvalidCommandValueException("Invalid Name");
        }
        if (shop.hasCategory(name)) {
            throw new VariableException.OverrideCommandException("Category " + name + " already exists");
        }

        shop.write("ADD NEW CATEGORY" + name);
        shop.write("Category added");
        shop.addCategory(name);
    }
}
