package commands;
import shop.Shop;


public class RemoveProduct implements Command{
    public Shop shop;
    private final String productName;
    public RemoveProduct(String productName) {
        this.shop = Shop.getInstance();
        this.productName = productName;
    }
    @Override
    public void execute(){

        shop.write("REMOVE PRODUCT " + productName);
        shop.storage.removeProduct(productName);
    }
}
