package utils;

import org.apache.commons.lang3.StringUtils;

import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;

public final class Constants {

    private Constants() {
    }

    public static final String STOCK = "stock";

    public static final String CLIENTS = "clients";

    public static final String CATEGORY = "category";

    public static final String NAME = "name";

    public static final String QUANTITY = "quantity";

    public static final String PRICE = "price";

    public static final String MAXQUANTITY = "maxQuantity";

    public static final String USERNAME = "username";

    public static final String BALANCE = "balance";

    public static boolean isPathValid(String path) {
        try {
            Files.isWritable(Paths.get(path));
        } catch (InvalidPathException | NullPointerException | SecurityException ex) {
            return false;
        }
        return true;
    }

    public static boolean isIncorrectName(String name) {
        return name == null || name.isBlank() || !StringUtils.isAlpha(name);
    }

    public static boolean isIncorrectNumber(String number) {
        try {
            int n = Integer.parseInt(number);
            return n <= 0;
        }
        catch (NumberFormatException e) {
            return true;
        }
    }
}
