package entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "consumers")
@NamedQueries({
        @NamedQuery(name = "Consumer.fetchEntryByName", query = "SELECT c FROM Consumer c WHERE c.consumerUsername " +
                "= :consumerUsername"),
        @NamedQuery(name = "Consumer.fetchEntries", query = "SELECT c FROM Consumer c ORDER BY c.ID"),
        @NamedQuery(name = "Consumer.decreaseBalance", query = "UPDATE Consumer c " +
                "SET c.consumerBalance = c.consumerBalance - :balance WHERE c.ID = :id " +
                "AND :balance <= c.consumerBalance"),
        @NamedQuery(name = "Consumer.deleteEntry", query = "DELETE FROM Consumer c WHERE c.ID = :id")
})
public class Consumer implements Serializable {

    @Id
    @Column(name = "consumerID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    @Column(name = "consumerUsername", unique = true, nullable = false)
    private String consumerUsername;

    @Column(name = "consumerBalance", nullable = false)
    private int consumerBalance;

    public int getID() {
        return ID;
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
}
