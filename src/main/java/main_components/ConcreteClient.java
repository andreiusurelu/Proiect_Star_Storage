package main_components;

import commands.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import utils.VariableException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ConcreteClient implements Client {
    private Invoker invoker;
    private Receiver receiver;
    private static final Logger logger = LogManager.getLogger(ConcreteClient.class);
    @Autowired
    public ConcreteClient(@Qualifier("shop")Receiver receiver,@Qualifier("concreteInvoker") Invoker invoker) {
        this.invoker = invoker;
        this.receiver = receiver;
    }

    public void executeAction(String commandLine) {
        CommandType type = CommandType.fromString(commandLine);
        if (type == null) {
            logger.error("Couldn't find a valid type!");
        }
        else {
            Command command = getCommand(type, commandLine);
            if (command == null) {
                logger.error("Invalid command.", new VariableException.InvalidCommand());
            } else {
                invoker.execute(command);
            }
        }
    }
    private Command getCommand(CommandType type, String line) {
        Pattern pattern = Pattern.compile(type.text);
        Matcher matcher = pattern.matcher(line);
        if (matcher.matches()) {
            return switch (type) {
                case ADD_NEW_CATEGORY -> new AddNewCategory(receiver, matcher.group("categoryName"));

                case ADD_NEW_PRODUCT -> new AddNewProduct(receiver, matcher.group("productName"),
                        matcher.group("productCategory"), matcher.group("productQuantity"),
                        matcher.group("productPrice"), matcher.group("productMaxQuantity"));

                case BUY -> new Buy(receiver, matcher.group("productName"), matcher.group("quantity"),
                        matcher.group("consumerUsername"));
                case PRINT_CATEGORIES -> new PrintCategories(receiver);
                case PRINT_DISPLAY_MODE -> new PrintDisplayMode(receiver);
                case PRINT_PRODUCTS -> new PrintProducts(receiver, matcher.group("productName"));
                case PRINT_PRODUCTS_ALL -> new PrintProductsAll(receiver);
                case PRINT_PRODUCTS_CATEGORY -> new PrintProductsCategory(receiver,
                        matcher.group("categoryName"));
                case REMOVE_PRODUCT -> new RemoveProduct(receiver, matcher.group("productName"));
                case REPLENISH -> new Replenish(receiver, matcher.group("productName"),
                        matcher.group("quantity"));
                case SWITCH_DISPLAY_MODE -> new SwitchDisplayMode(receiver, matcher.group("url"));
                case EXPORT -> new Export(receiver, matcher.group("url"));
                case EXIT -> new Exit(receiver);
                case HELP -> new Help(receiver);
                case UNDO -> new Undo(receiver, invoker);
                case REDO -> new Redo(receiver, invoker);
            };
        }
        else {
            return null;
        }
    }
}
