package commands;

import shop.Product;
import shop.Shop;
import utils.Constants;
import utils.VariableException;

public class Replenish implements Command{
    public Shop shop;
    private final String productName;
    private final String quantityString;

    public Replenish(String productName, String quantity) {
        this.shop = Shop.getInstance();
        this.productName = productName;
        this.quantityString =quantity;
    }

    @Override
    public void execute() throws VariableException.InvalidCommandValueException {
        if (Constants.isIncorrectName(productName)) {
            throw new VariableException.InvalidInputValueError("Invalid product name!");
        }
        if (Constants.isIncorrectNumber(quantityString)) {
            throw new VariableException.InvalidCommandValueException("Invalid quantity!");
        }

        if (shop.noSuchProduct(productName)) {
            throw new VariableException
                    .InvalidCommandValueException("Product " + productName + " doesn't exist!");
        }
        int quantity = Integer.parseInt(quantityString);
        Product p = shop.getProduct(productName);
        shop.write("REPLENISH PRODUCT " + productName + " " + quantityString);
        if (p.getQuantity() + quantity > p.getMaxQuantity()) {
            shop.write("Quantity " + quantity + " is too large.");
            return;
        }
        p.increment(quantity);
        shop.write("Product " + productName + " replenished.");
    }
}
