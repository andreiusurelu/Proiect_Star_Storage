package commands;

import shop.Shop;

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
    public void execute() {


        shop.write("REPLENISH PRODUCT " + productName + " " + quantityString);
        shop.storage.incrementProductQuantity(productName, quantityString);
    }
}
