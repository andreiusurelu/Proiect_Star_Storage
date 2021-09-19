package shop;

public class Product {
    private String name;
    private String category;
    private int quantity;
    private int price;
    private int maxQuantity;

    public Product (String newName, String newCategory,
                    int newQuantity, int newPrice, int newMaxQuantity) {
        name = newName;
        category = newCategory;
        quantity = newQuantity;
        price = newPrice;
        maxQuantity = newMaxQuantity;
    }

    public void increment(int q) {
        quantity += q;
    }

    public void decrement(int q) {
        quantity -= q;
    }

    @Override
    public String toString() {
        return name + " " + quantity + " " + price;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPrice() {
        return price;
    }

    public int getMaxQuantity() {
        return maxQuantity;
    }
}
