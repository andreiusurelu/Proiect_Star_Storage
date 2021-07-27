package commands;

import shop.Shop;

public class PrintProductsAll implements Command{
    public Shop shop;

    public PrintProductsAll() {
        this.shop = Shop.getInstance();
    }

    @Override
    public void execute(){
        shop.write("PRINT PRODUCTS ALL");
        shop.showAll();
    }
}
