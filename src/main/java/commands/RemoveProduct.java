package commands;
import shop.Product;
import shop.Shop;


public class RemoveProduct implements Command{
    private Shop shop;
    private final String productName;
    private Product removedProduct;
    public RemoveProduct(Shop shop, String productName) {
        this.shop = shop;
        this.productName = productName;
    }

    @Override
    public void execute(){

        shop.write("REMOVE PRODUCT " + productName);
        removedProduct = shop.fetchProductByName(productName);
        shop.removeProduct(productName);
    }

    @Override
    public void undo() {
        shop.addNewProduct(removedProduct.getName(), removedProduct.getCategory(),
                removedProduct.getQuantity(), removedProduct.getPrice(),
                removedProduct.getMaxQuantity());
    }
}
