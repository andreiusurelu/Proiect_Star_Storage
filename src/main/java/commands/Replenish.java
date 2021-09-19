package commands;

import shop.Shop;

public class Replenish implements Command{
    private Shop shop;
    private final String productName;
    private final String quantityString;

    public Replenish(Shop shop, String productName, String quantity) {
        this.shop = shop;
        this.productName = productName;
        this.quantityString =quantity;
    }

    @Override
    public void execute() {
        shop.write("REPLENISH PRODUCT " + productName + " " + quantityString);
        shop.replenish(productName, quantityString);
    }

    @Override
    public void undo() {
        shop.undoReplenish(productName, quantityString);
    }
}
