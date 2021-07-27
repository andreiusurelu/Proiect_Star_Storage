package commands;

import shop.Product;
import shop.Shop;
import utils.Constants;
import utils.VariableException;


public class AddNewProduct implements Command{
    private Shop shop;
    private final String name;
    private final String category;
    private final String quantityString;
    private final String priceString;
    private final String maxQuantityString;
    public AddNewProduct(String name, String category,
                         String quantity, String price, String maxQuantity) {
        this.shop = Shop.getInstance();
        this.name = name;
        this.category = category;
        this.quantityString = quantity;
        this.priceString = price;
        this.maxQuantityString = maxQuantity;
    }
    @Override
    public void execute() throws VariableException.InvalidCommandValueException,
            VariableException.OverrideCommandException {
        if (Constants.isIncorrectName(name)) {
            throw new VariableException.InvalidCommandValueException("Invalid name");
        }
        if (Constants.isIncorrectName(category)) {
            throw new VariableException.OverrideCommandException("Invalid category");
        }
        if (Constants.isIncorrectNumber(quantityString)) {
            throw new VariableException.InvalidCommandValueException("Invalid quantity");
        }
        if (Constants.isIncorrectNumber(priceString)) {
            throw new VariableException.InvalidCommandValueException("Invalid price");
        }
        if (Constants.isIncorrectNumber(maxQuantityString)) {
            throw new VariableException.InvalidCommandValueException("Invalid maxQuantity");
        }
        if (!shop.hasCategory(category)) {
            throw new VariableException.InvalidCommandValueException("Category " + category + " doesn't exist");
        }
        if (shop.hasProduct(category, name)) {
            throw new VariableException.OverrideCommandException("Product " + name + " already exists");
        }
        int quantity = Integer.parseInt(quantityString);
        int maxQuantity = Integer.parseInt(maxQuantityString);
        if (quantity > maxQuantity) {
            throw new VariableException.InvalidCommandValueException("Quantity " + quantity + " is bigger than " +
                    "maxQuantity " + maxQuantity);
        }
        int price = Integer.parseInt(priceString);
        shop.addProduct(category, name, new Product(name, quantity, price, maxQuantity));

        shop.write("ADD NEW PRODUCT " + name + " "
                + category + " " + quantity + " " + price + " " + maxQuantity);
        shop.write("Product added.");
    }
}
