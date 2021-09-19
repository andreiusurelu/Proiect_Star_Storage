package commands;

import shop.Shop;

public class PrintCategories implements Command{
    private Shop shop;
    public PrintCategories (Shop shop) {
        this.shop = shop;
    }

    @Override
    public void execute() {
        shop.write("PRINT CATEGORIES");
        shop.showCategories();
    }

    @Override
    public void undo() {

    }
}
