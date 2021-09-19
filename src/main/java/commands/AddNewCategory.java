package commands;

import shop.Shop;

public class AddNewCategory implements Command{
    private Shop shop;
    private final String name;

    public AddNewCategory(Shop shop, String name) {
        this.shop = shop;
        this.name = name;
    }


    @Override
    public void execute(){
        shop.write("ADD NEW CATEGORY " + name);
        shop.addNewCategory(name);
    }

    @Override
    public void undo() {
        shop.write("Action undone: ADD NEW CATEGORY " + name);
        shop.undoAddNewCategory(name);
    }
}
