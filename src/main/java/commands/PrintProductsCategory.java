package commands;

import shop.Shop;

public class PrintProductsCategory implements Command {
    private Shop shop;
    private final String category;
    public PrintProductsCategory(Shop shop, String category){
        this.shop = shop;
        this.category = category;
    }

    @Override
    public void execute() {
        shop.write("PRINT PRODUCTS CATEGORY " + category);
        if (shop.noSuchCategory(category)) {
            shop.write("Category " + category + " doesn't exist!");
            return;
        }
        shop.showByCategory(category);
    }

    @Override
    public void undo() {

    }
}
