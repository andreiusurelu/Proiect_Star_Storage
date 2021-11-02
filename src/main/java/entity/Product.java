package entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="products")
@NamedQueries({
        @NamedQuery(name = "Product.findByFields", query = "SELECT p FROM Product p WHERE p.name = :productName " +
                "AND p.category = :productCategory AND p.quantity = :productQuantity AND p.price = :productPrice " +
                "AND p.maxQuantity = :productMaxQuantity"),
        @NamedQuery(name = "Product.fetchByName", query = "SELECT p FROM Product p WHERE p.name = :productName"),
        @NamedQuery(name = "Product.fetchEntries", query = "SELECT p FROM Product p ORDER BY p.id"),
        @NamedQuery(name = "Product.fetchEntriesByCategory", query = "SELECT p FROM Product p WHERE p.category.id = " +
                ":id ORDER BY p.id"),
        @NamedQuery(name = "Product.fetchByCategory", query = "SELECT p FROM Product p WHERE p.category = :category"),
        @NamedQuery(name = "Product.decrement", query = "UPDATE Product p SET p.quantity = p.quantity - :quantity " +
                "WHERE p.id = :id AND p.quantity >= :quantity"),
        @NamedQuery(name = "Product.increment", query = "UPDATE Product p SET p.quantity = p.quantity + :quantity " +
                "WHERE p.id = :id AND p.quantity + :quantity <= p.maxQuantity"),
        @NamedQuery(name = "Product.deleteEntry", query = "DELETE FROM Product p WHERE p.id = :id"),
        @NamedQuery(name = "Product.fetchPriceByName", query = "SELECT p.price FROM Product p WHERE p.name = :name")
})
public class Product implements Serializable{

    @Id
    @Column(name = "productID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "productName", unique = true, nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "productCategory", referencedColumnName = "categoryID", nullable = false)
    private Category category;

    @Column(name = "productQuantity", nullable = false)
    private int quantity;

    @Column(name = "productPrice", nullable = false)
    private int price;

    @Column(name = "productMaxQuantity", nullable = false)
    private int maxQuantity;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
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

}
