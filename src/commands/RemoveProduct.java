package commands;

import shop.Product;
import shop.Shop;
import utils.Constants;
import utils.VariableException;

public class RemoveProduct implements Command{
    public Shop shop;
    private final String productName;
    public RemoveProduct(String productName) {
        this.shop = Shop.getInstance();
        this.productName = productName;
    }
    @Override
    public void execute() throws VariableException.InvalidCommandValueException {
        if (Constants.isIncorrectName(productName)) {
            throw new VariableException.InvalidCommandValueException("Invalid name");
        }
        if (shop.noSuchProduct(productName)) {
            throw new VariableException.InvalidCommandValueException("Product doesn't exist");
        }
        Product p = shop.getProduct(productName);
        shop.write("REMOVE PRODUCT " + productName);
        if (p.getQuantity() > 0) {
            shop.write("Cannot remove " + productName + " because quantity" +
                    " is not zero. Quantity is " + p.getQuantity());
            return;
        }
        shop.removeProduct(productName);
        shop.write("Product successfully removed.");
    }
}
