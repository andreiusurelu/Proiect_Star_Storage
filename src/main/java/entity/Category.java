package entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Table(name = "categories")
@Entity
@JsonIgnoreProperties({"products"})
@NamedQueries({
        @NamedQuery(name = "Category.fetchByName",
                query = "SELECT c FROM Category c WHERE c.name = :categoryName"),
        @NamedQuery(name = "Category.fetchEntries", query = "SELECT c FROM Category c ORDER BY c.id"),
        @NamedQuery(name = "Category.fetchEntriesNameOnly",
                query = "SELECT c.name FROM Category c ORDER BY c.id"),
        @NamedQuery(name = "Category.deleteEntry", query = "DELETE FROM Category c WHERE c.id = :id"),
        @NamedQuery(name = "Category.fetchProductsByName", query = "SELECT c.products FROM Category c " +
                "WHERE c.name = :name")
})
public class Category implements Serializable {

    @Id
    @Column(name = "categoryID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "categoryName", unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "category")
    private List<Product> products;

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

    public List<Product> getProducts() {
        return products;
    }

    @Override
    public String toString() {
        return name;
    }
}
