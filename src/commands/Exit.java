package commands;

import shop.Shop;

public class Exit implements Command{
    private Shop shop;
    public Exit() {
        this.shop = Shop.getInstance();
    }
    @Override
    public void execute() {
        shop.write("EXIT");
        shop.write("Quitting program...");
        shop.storage.closeConnections();
    }
    public void undo() {

    }
}
