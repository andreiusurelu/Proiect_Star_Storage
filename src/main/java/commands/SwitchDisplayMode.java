package commands;
import shop.Shop;


public class SwitchDisplayMode implements Command{
    private Shop shop;
    private String[] info;

    public SwitchDisplayMode (String ...args) {
        this.shop = Shop.getInstance();
        info = args;
    }
    @Override
    public void execute() {
        shop.storage.write("SWITCH DISPLAY_MODE " + String.join(" ", info));
        shop.storage.switchPrintStrategy(info[0], java.util
                .Arrays.stream(info, 1, info.length).toArray(String[]::new));
        }
}
