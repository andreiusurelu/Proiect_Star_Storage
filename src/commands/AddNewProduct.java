package commands;
import shop.Shop;



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
    public void execute(){

        shop.write("ADD NEW PRODUCT " + name + " "
                + category + " " + quantityString + " " + priceString + " " + maxQuantityString);
        shop.storage.addProduct(name, category, quantityString, priceString, maxQuantityString);
    }
}
