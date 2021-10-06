package entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Table(name = "categories")
@Entity
@NamedQueries({
        @NamedQuery(name = "Category.findByName",
                query = "SELECT c FROM Category c WHERE c.name = :categoryName"),
        @NamedQuery(name = "Category.printEntriesName", query = "SELECT c.name FROM Category c ORDER BY c.ID"),
        @NamedQuery(name = "Category.printEntries", query = "SELECT c FROM Category c ORDER BY c.ID")
})
public class Category implements Serializable {

    @Id
    @Column(name = "categoryID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    @Basic
    @Column(name = "categoryName", unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "category")
    private List<Product> products;

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProducts() {
        return products;
    }

    @Override
    public String toString() {
        return name;
    }
}
