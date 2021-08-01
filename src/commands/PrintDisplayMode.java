package commands;

import shop.Shop;

public class PrintDisplayMode implements Command{
    public Shop shop;

    public PrintDisplayMode() {
        this.shop = Shop.getInstance();
    }
    @Override
    public void execute() {
        shop.write("PRINT DISPLAY_MODE");
        shop.showDisplayMode();
    }
}
