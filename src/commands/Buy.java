package commands;
import shop.Shop;


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

    public void execute(){

        shop.write("BUY " + productName + " " + quantityString + " FOR " + username);
        shop.storage.doTransaction(username, productName, quantityString);
    }
}
