package commands;

public enum CommandType {

    PRINT_PRODUCTS_CATEGORY("PRINT PRODUCTS CATEGORY"),
    PRINT_PRODUCTS_ALL("PRINT PRODUCTS ALL"),
    PRINT_PRODUCTS("PRINT PRODUCTS"),
    PRINT_CATEGORIES("PRINT CATEGORIES"),
    BUY("BUY"),
    REPLENISH("REPLENISH"),
    ADD_NEW_CATEGORY("ADD NEW CATEGORY"),
    ADD_NEW_PRODUCT("ADD NEW PRODUCT"),
    REMOVE_PRODUCT("REMOVE PRODUCT"),
    PRINT_DISPLAY_MODE("PRINT DISPLAY_MODE"),
    SWITCH_DISPLAY_MODE("SWITCH DISPLAY_MODE"),
    EXIT("EXIT"),
    HELP("HELP"),
    EXPORT("EXPORT"),
    UNDO("UNDO"),
    REDO("REDO");


    public final String text;

    CommandType(String text) {
        this.text = text;
    }

    public static CommandType fromString(String text) {
        for (CommandType commandType : CommandType.values()) {
            if (commandType.text.equalsIgnoreCase(text)) {
                return commandType;
            }
        }
        return null;
    }

    public static boolean notCommand(String text) {
        for (CommandType commandType : CommandType.values()) {
            if (text.startsWith(commandType.text)) {
                return false;
            }
        }
        return true;
    }
}
