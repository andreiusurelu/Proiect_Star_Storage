package commands;
import shop.Shop;


public class Buy implements Command{
    private Shop shop;
    private final String productName;
    private final String quantityString;
    private final String username;


    public Buy(Shop shop, String productName, String quantity, String username) {
        this.shop = shop;
        this.productName = productName;
        this.quantityString = quantity;
        this.username = username;
    }

    public void execute(){

        shop.write("BUY " + productName + " " + quantityString + " FOR " + username);
        shop.doTransaction(username, productName, quantityString);
    }

    @Override
    public void undo() {
        shop.undoTransaction(username, productName, quantityString);
    }
}
