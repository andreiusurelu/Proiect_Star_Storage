package entity_dao;

import entity.Consumer;

import java.util.List;

public interface ConsumerDAO {
    List<Consumer> getConsumerList();

    void addConsumer(Consumer consumer);
    void deleteConsumer(int id);

    Consumer getConsumer(int id);
    Consumer getConsumer(String name);

    void decrementBalance(int id, int balance);
}
