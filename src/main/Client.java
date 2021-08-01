package main;

import commands.*;
import utils.InputLoader;
import utils.VariableException;

import java.io.IOException;

public class Client {
    private final Invoker invoker;

    Client(String inputPath) {
        invoker = new Invoker();
        InputLoader loader = new InputLoader(inputPath);
        loader.readInput();
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

    private Command getCommand(CommandType type, String ...args) {
        return switch (type) {

            case ADD_NEW_CATEGORY -> new AddNewCategory(args[0]);
            case ADD_NEW_PRODUCT -> new AddNewProduct(args[0],
                    args[1], args[2], args[3], args[4]);
            case BUY -> new Buy(args[0], args[1], args[2]);
            case PRINT_CATEGORIES -> new PrintCategories();
            case PRINT_DISPLAY_MODE -> new PrintDisplayMode();
            case PRINT_PRODUCTS -> new PrintProducts(args[0]);
            case PRINT_PRODUCTS_ALL -> new PrintProductsAll();
            case PRINT_PRODUCTS_CATEGORY -> new PrintProductsCategory(args[0]);
            case REMOVE_PRODUCT -> new RemoveProduct(args[0]);
            case REPLENISH -> new Replenish(args[0], args[1]);
            case SWITCH_DISPLAY_MODE -> new SwitchDisplayMode(args[0]);
            case EXPORT -> new Export(args[0]);
            case EXIT -> new Exit();
            case HELP -> new Help();
        };
    }
}
