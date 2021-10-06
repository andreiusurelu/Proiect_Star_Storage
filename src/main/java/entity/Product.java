package entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="products")
@NamedQueries({
        @NamedQuery(name = "Product.findByFields", query = "SELECT p FROM Product p WHERE p.name = :productName " +
                "AND p.category = :productCategory AND p.quantity = :productQuantity AND p.price = :productPrice " +
                "AND p.maxQuantity = :productMaxQuantity"),
        @NamedQuery(name = "Product.findByName", query = "SELECT p FROM Product p WHERE p.name = :productName"),
        @NamedQuery(name = "Product.printEntries", query = "SELECT p FROM Product p ORDER BY p.ID"),
        @NamedQuery(name = "Product.printByCategoryName", query = "SELECT p FROM Product p WHERE p.category.name = " +
                ":categoryName ORDER BY p.ID"),
        @NamedQuery(name = "Product.printByCategory", query = "SELECT p FROM Product p WHERE p.category = :category")
})
public class Product implements Serializable{

    @Id
    @Column(name = "productID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    @Basic
    @Column(name = "productName", unique = true, nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "productCategory", referencedColumnName = "categoryName", nullable = false)
    private Category category;

    @Basic
    @Column(name = "productQuantity", nullable = false)
    private int quantity;

    @Basic
    @Column(name = "productPrice", nullable = false)
    private int price;

    @Basic
    @Column(name = "productMaxQuantity", nullable = false)
    private int maxQuantity;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getMaxQuantity() {
        return maxQuantity;
    }

    public void setMaxQuantity(int maxQuantity) {
        this.maxQuantity = maxQuantity;
    }

    @Override
    public String toString() {
        return name + " " + quantity + " " + price;
    }

    public void decrement(long quantity) {
        this.quantity -= quantity;
    }

    public void increment(long quantity) {
        this.quantity += quantity;
    }
}
