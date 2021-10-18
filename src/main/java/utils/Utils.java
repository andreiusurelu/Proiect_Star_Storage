package utils;

public final class Utils {
    private Utils() {
    }

    public static String checkBuyConstraints(String consumerUsername, String productName,
                                             String quantity) {
        if (Constants.isIncorrectName(consumerUsername)) {
            return "Incorrect consumer name: " + consumerUsername;
        }
        else if (Constants.isIncorrectName(productName)) {
            return "Incorrect product name: " + productName;
        }
        else if (Constants.isIncorrectNumber(quantity)) {
            return "Incorrect quantity: " + quantity;
        }
        else {
            return null;
        }
    }

    public static String checkReplenishConstraints(String productName, String quantity) {
        if (Constants.isIncorrectName(productName)) {
            return "Incorrect product name: " + productName;
        }
        else if (Constants.isIncorrectName(quantity)) {
            return "Incorrect quantity: " + quantity;
        }
        else {
            return null;
        }
    }

    public static String checkCategoryConstraints(String name) {
        if (Constants.isIncorrectName(name)) {
            return "Invalid category name: " + name;
        }
        else {
            return null;
        }
    }

    public static String checkProductConstraints(String productName, String productCategory,
                                      String quantity, String price, String maxQuantity) {
        //o pot face mai bine??? arata oribil cu atatea if-uri
        if (Constants.isIncorrectName(productName)) {
            return "Invalid product name: " + productName;
        }
        else if (Constants.isIncorrectName(productCategory)) {
            return "Invalid product category: " + productCategory;
        }
        else if (Constants.isIncorrectNumber(quantity)) {
            return "Invalid quantity: " + quantity;
        }
        else if (Constants.isIncorrectNumber(price)) {
            return "Invalid price: " + price;
        }
        else if (Constants.isIncorrectNumber(maxQuantity)) {
            return "Invalid maxQuantity: " + maxQuantity;
        }
        else {
            return null;
        }
    }

}
