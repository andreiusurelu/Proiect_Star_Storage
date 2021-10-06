package entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "consumers")
@NamedQueries({
        @NamedQuery(name = "Consumer.findByName", query = "SELECT c FROM Consumer c WHERE c.consumerUsername " +
                "= :consumerUsername"),
        @NamedQuery(name = "Consumer.printEntries", query = "SELECT c FROM Consumer c ORDER BY c.ID")
})
public class Consumer implements Serializable {

    @Id
    @Column(name = "consumerID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    @Basic
    @Column(name = "consumerUsername", unique = true, nullable = false)
    private String consumerUsername;

    @Column(name = "consumerBalance", nullable = false)
    private int consumerBalance;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getConsumerUsername() {
        return consumerUsername;
    }

    public void setConsumerUsername(String consumerUsername) {
        this.consumerUsername = consumerUsername;
    }

    public int getConsumerBalance() {
        return consumerBalance;
    }

    public void setConsumerBalance(int consumerBalance) {
        this.consumerBalance = consumerBalance;
    }

    public void decrement(int balance) {
        consumerBalance -= balance;
    }

    public void increment(int balance) {
        consumerBalance += balance;
    }
}
