package commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import main_components.Receiver;

public class Help implements SimpleCommand{
    private static final Logger logger = LogManager.getLogger(Help.class);
    private Receiver receiver;
    public Help(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute(){
        logger.info("HELP");
        logger.info(
                "List of commands:\n\n" +
                        "\tADD NEW CATEGORY ${NAME} -- adds a new category of products\n" +
                        "\tADD NEW PRODUCT ${NAME} ${CATEGORY} ${QUANTITY} ${PRICE} ${MAXQUANTITY} " +
                        "-- adds a new product of given category, current quantity, price and maximum quantity\n" +
                        "\tBUY ${PRODUCT} ${QUANTITY} FOR ${USERNAME} -- buys quantity of product for a user\n" +
                        "\tEXIT -- exits the program, closing current output file if set\n" +
                        "\tEXPORT ${FILE_PATH} -- exports the current contents in a JSON file\n" +
                        "\tPRINT CATEGORIES -- prints all current categories of products\n" +
                        "\tPRINT DISPLAY_MODE -- prints CONSOLE or output file's path. It will print CONSOLE" +
                        " by default, until it's changed to a given file\n" +
                        "\tPRINT PRODUCTS ALL -- prints all current products currently in stock\n" +
                        "\tPRINT PRODUCTS CATEGORY ${CATEGORY_NAME} -- prints all products of given" +
                        " category\n" +
                        "\tPRINT PRODUCTS ${PRODUCT_NAME} -- prints the product of given name\n" +
                        "\tREDO -- redoes the latest undone command\n" +
                        "\tREMOVE PRODUCT ${PRODUCT_NAME} -- removes the product of given name. It's" +
                        " quantity must be 0\n" +
                        "\tREPLENISH ${PRODUCT_NAME} ${QUANTITY} -- replenishes product's current stock\n" +
                        "\tSWITCH DISPLAY_MODE CONSOLE -- program will print the results to console\n" +
                        "\tSWITCH DISPLAY_MODE FILE ${FILE_PATH} -- program will print the results in" +
                        "the file. If the file doesn't exist, it will be created automatically\n" +
                        "\tUNDO -- undo the latest executed command");
        receiver.write("HELP");
        receiver.write(
                "List of commands:\n\n" +
                        "\tADD NEW CATEGORY ${NAME} -- adds a new category of products\n" +
                        "\tADD NEW PRODUCT ${NAME} ${CATEGORY} ${QUANTITY} ${PRICE} ${MAXQUANTITY} " +
                        "-- adds a new product of given category, current quantity, price and maximum quantity\n" +
                        "\tBUY ${PRODUCT} ${QUANTITY} FOR ${USERNAME} -- buys quantity of product for a user\n" +
                        "\tEXIT -- exits the program, closing current output file if set\n" +
                        "\tEXPORT ${FILE_PATH} -- exports the current contents in a JSON file\n" +
                        "\tPRINT CATEGORIES -- prints all current categories of products\n" +
                        "\tPRINT DISPLAY_MODE -- prints CONSOLE or output file's path. It will print CONSOLE" +
                        " by default, until it's changed to a given file\n" +
                        "\tPRINT PRODUCTS ALL -- prints all current products currently in stock\n" +
                        "\tPRINT PRODUCTS CATEGORY ${CATEGORY_NAME} -- prints all products of given" +
                        " category\n" +
                        "\tPRINT PRODUCTS ${PRODUCT_NAME} -- prints the product of given name\n" +
                        "\tREDO -- redoes the latest undone command\n" +
                        "\tREMOVE PRODUCT ${PRODUCT_NAME} -- removes the product of given name. It's" +
                        " quantity must be 0\n" +
                        "\tREPLENISH ${PRODUCT_NAME} ${QUANTITY} -- replenishes product's current stock\n" +
                        "\tSWITCH DISPLAY_MODE CONSOLE -- program will print the results to console\n" +
                        "\tSWITCH DISPLAY_MODE FILE ${FILE_PATH} -- program will print the results in" +
                        "the file. If the file doesn't exist, it will be created automatically\n" +
                        "\tUNDO -- undo the latest executed command");
    }
}
