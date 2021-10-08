package commands;
import java.util.regex.*;

public enum CommandType {

    PRINT_PRODUCTS_CATEGORY("PRINT PRODUCTS (CATEGORY) (?<categoryName>\\p{Upper}+)"),
    PRINT_PRODUCTS_ALL("PRINT PRODUCTS (ALL)"),
    PRINT_PRODUCTS("PRINT PRODUCTS (?<productName>\\p{Upper}+)"),
    PRINT_CATEGORIES("PRINT CATEGORIES"),
    BUY("BUY (?<productName>\\p{Upper}+) (?<quantity>\\d{1,7}) FOR (?<consumerUsername>\\p{Alpha}+)"),
    REPLENISH("REPLENISH (?<productName>\\p{Upper}+) (?<quantity>\\d{1,7})"),
    ADD_NEW_CATEGORY("ADD NEW CATEGORY (?<categoryName>\\p{Upper}+)"),
    ADD_NEW_PRODUCT("ADD NEW PRODUCT (?<productName>\\p{Upper}+) (?<productCategory>\\p{Upper}+) " +
            "(?<productQuantity>\\d{1,7}) (?<productPrice>\\d{1,7}) (?<productMaxQuantity>\\d{1,7})"),
    REMOVE_PRODUCT("REMOVE PRODUCT (?<productName>\\p{Upper}+)"),
    PRINT_DISPLAY_MODE("PRINT DISPLAY_MODE"),
    SWITCH_DISPLAY_MODE("SWITCH DISPLAY_MODE (?<url>CONSOLE|(FILE (\"([a-zA-Z]:)?(\\\\[a-zA-Z0-9_.-]+)+\\\\?\")))"),
    EXIT("EXIT"),
    HELP("HELP"),
    EXPORT("EXPORT (?<url>\"([a-zA-Z]:)?(\\\\[a-zA-Z0-9_.-]+)+\\\\?\")"),
    UNDO("UNDO"),
    REDO("REDO");


    public final String text;

    CommandType(String text) {
        this.text = text;
    }

    public static CommandType fromString(String text) {
        Pattern pattern;
        Matcher matcher;
        for (CommandType commandType : CommandType.values()) {
            pattern = Pattern.compile(commandType.text);
            matcher = pattern.matcher(text);
            if (matcher.matches()) {
                return commandType;
            }
        }
        return null;
    }

    public static boolean notCommand(String text) {
        for (CommandType commandType : CommandType.values()) {
            if (Pattern.matches(commandType.text, text)) {
                return false;
            }
        }
        return true;
    }
}
