package commands;

import shop.Shop;

public class Exit implements Command{
    private Shop shop;

    public Exit(Shop shop) {
        this.shop = shop;
    }

    @Override
    public void execute() {
        shop.write("EXIT");
        shop.write("Quitting program...");
        shop.close();
    }
    public void undo() {

    }
}
