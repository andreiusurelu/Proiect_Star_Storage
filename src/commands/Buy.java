package commands;

import shop.Consumer;
import shop.Product;
import shop.Shop;
import utils.Constants;
import utils.VariableException;


public class Buy implements Command{
    public Shop shop;

    private final String productName;
    private final String quantityString;
    private final String username;


    public Buy(String productName, String quantity, String username) {
        this.shop = Shop.getInstance();
        this.productName = productName;
        this.quantityString = quantity;
        this.username = username;
    }

    public void execute() throws VariableException.InvalidCommandValueException {
        if (Constants.isIncorrectName(productName)) {
            throw new VariableException.InvalidCommandValueException("Product name is invalid!");
        }
        if (Constants.isIncorrectName(username)) {
            throw new VariableException.InvalidCommandValueException("Username is invalid");
        }
        if (!shop.hasConsumer(username)) {
            throw new VariableException.InvalidCommandValueException("Client " + username + " doesn't exist!");
        }
        if (shop.noSuchProduct(productName)) {
            throw new VariableException.InvalidCommandValueException("Product " + productName + " doesn't exist!");
        }
        if (Constants.isIncorrectNumber(quantityString)) {
            throw new VariableException.InvalidCommandValueException("Product's quantity is invalid!");
        }
        int quantity = Integer.parseInt(quantityString);
        Product p = shop.getProduct(productName);
        Consumer c = shop.getConsumer(username);

        shop.write("BUY " + productName + " " + quantity + " FOR " + username);
        if (p.getQuantity() < quantity) {
            shop.write("User " + username + " cannot buy " + quantity + " " + productName + " because there" +
                    " is only " + p.getQuantity() + " " + productName + " left.");
            return;
        }
        if (c.getBalance() < quantity * p.getPrice()) {
            shop.write("User " + username + " cannot buy " + quantity + " " + productName + " because the" +
                    "current balance is too low: " + c.getBalance() + ".");
            return;
        }
        c.decrement(quantity * p.getPrice());
        p.decrement(quantity);

        shop.write("User " + username + " has bought " + quantity + " " + productName + ".");
    }
}
