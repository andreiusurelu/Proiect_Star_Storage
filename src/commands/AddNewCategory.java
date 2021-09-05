package commands;

import shop.Shop;

public class AddNewCategory implements Command{
    private Shop shop;
    private final String name;
    public AddNewCategory(String name) {
        this.shop = Shop.getInstance();
        this.name = name;
    }

    @Override
    public void execute(){

        shop.write("ADD NEW CATEGORY" + name);
        shop.storage.addCategory(name);
    }
}
