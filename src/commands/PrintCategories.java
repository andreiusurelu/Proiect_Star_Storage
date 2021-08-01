package commands;

import shop.Shop;

public class PrintCategories implements Command{
    public Shop shop;
    public PrintCategories () {
        this.shop = Shop.getInstance();
    }

    @Override
    public void execute() {
        shop.write("PRINT CATEGORIES");
        shop.showCategories();
    }
}
