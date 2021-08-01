package commands;

import dataprocessing.PrintStrategyFactory;
import shop.Shop;

public class SwitchDisplayMode implements Command{
    private Shop shop;
    private final String path;

    public SwitchDisplayMode (String path) {
        this.shop = Shop.getInstance();
        this.path = path;
    }
    @Override
    public void execute() {
        if (path.equals("CONSOLE")) {
            shop.output.close();
            shop.output = PrintStrategyFactory.createStrategy("CONSOLE");
        }

        shop.write("SWITCH DISPLAY_MODE FILE " + path);
        shop.output.close();
        shop.output = PrintStrategyFactory.createStrategy("FILE", path);
    }
}
