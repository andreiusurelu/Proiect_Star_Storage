package commands;

import shop.Shop;

public class PrintDisplayMode implements Command{
    private Shop shop;

    public PrintDisplayMode(Shop shop) {
        this.shop = shop;
    }


    @Override
    public void execute() {
        shop.write("PRINT DISPLAY_MODE");
        shop.showDisplayMode();
    }

    @Override
    public void undo() {

    }
}
