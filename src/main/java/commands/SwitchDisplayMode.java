package commands;
import shop.Shop;


public class SwitchDisplayMode implements Command{
    private Shop shop;
    private String[] info;
    private String prevInfo;

    public SwitchDisplayMode (Shop shop, String ...args) {
        this.shop = shop;
        info = args;
    }

    @Override
    public void execute() {
        shop.write("SWITCH DISPLAY_MODE " + String.join(" ", info));
        prevInfo = shop.getDisplayMode();
        shop.switchDisplayMode(info[0], java.util
                .Arrays.stream(info, 1, info.length).toArray(String[]::new));
    }

    @Override
    public void undo() {
        String[] prevInfoArr = prevInfo.split(" ");
        shop.switchDisplayMode(prevInfoArr[0], java.util
                .Arrays.stream(prevInfoArr, 1, info.length).toArray(String[]::new));
    }
}
