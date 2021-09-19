package commands;

import shop.Shop;

public class PrintProductsAll implements Command{
    private Shop shop;

    public PrintProductsAll(Shop shop) {
        this.shop = shop;
    }


    @Override
    public void execute(){
        shop.write("PRINT PRODUCTS ALL");
        shop.showAll();
    }

    @Override
    public void undo() {

    }
}
