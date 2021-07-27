package commands;

import shop.Shop;

public class PrintProductsCategory implements Command {
    public Shop shop;
    private final String category;
    public PrintProductsCategory(String category){
        this.shop = Shop.getInstance();
        this.category = category;
    }
    @Override
    public void execute() {
        shop.write("PRINT PRODUCTS CATEGORY " + category);
        if (!shop.hasCategory(category)) {
            shop.write("Category " + category + " doesn't exist!");
            return;
        }
        shop.showByCategory(category);
    }
}
