package main;

import commands.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import shop.Shop;
import utils.VariableException;

import java.io.IOException;

@Component
public class Client {
    private Invoker invoker;
    private Shop shop;
    @Autowired
    public Client(Shop shop, Invoker invoker) {
        this.invoker = invoker;
        this.shop = shop;
    }

    public void executeAction(String commandName, String ...args) {
        CommandType type = CommandType.fromString(commandName);
        Command command = getCommand(type, args);
        try  {
            invoker.execute(command);
        }
        catch(VariableException.InvalidCommandValueException |
                VariableException.OverrideCommandException | IOException e) {
            e.printStackTrace();
        }
    }

    public void undoAction() {
        shop.write("UNDO");
        invoker.undo();
        shop.write("Action undone.");
    }

    public void redoAction() {
        try {
            shop.write("REDO");
            invoker.redo();
            shop.write("Action redone.");
        }
        catch (VariableException.InvalidCommandValueException |
                VariableException.OverrideCommandException | IOException e) {
            e.printStackTrace();
        }
    }

    private Command getCommand(CommandType type, String ...args) {
        //eventual si la commanduri sa implementez cu Spring
        return switch (type) {

            case ADD_NEW_CATEGORY -> new AddNewCategory(shop, args[0]);
            case ADD_NEW_PRODUCT -> new AddNewProduct(shop, args[0],
                    args[1], args[2], args[3], args[4]);
            case BUY -> new Buy(shop, args[0], args[1], args[2]);
            case PRINT_CATEGORIES -> new PrintCategories(shop);
            case PRINT_DISPLAY_MODE -> new PrintDisplayMode(shop);
            case PRINT_PRODUCTS -> new PrintProducts(shop, args[0]);
            case PRINT_PRODUCTS_ALL -> new PrintProductsAll(shop);
            case PRINT_PRODUCTS_CATEGORY -> new PrintProductsCategory(shop, args[0]);
            case REMOVE_PRODUCT -> new RemoveProduct(shop, args[0]);
            case REPLENISH -> new Replenish(shop, args[0], args[1]);
            case SWITCH_DISPLAY_MODE -> new SwitchDisplayMode(shop, args);
            case EXPORT -> new Export(shop, args[0]);
            case EXIT -> new Exit(shop);
            case HELP -> new Help(shop);
            default -> null;
        };
    }
}
