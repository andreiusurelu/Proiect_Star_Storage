package services;

import entity.Consumer;

import java.util.List;

public interface ConsumerService {
    List<Consumer> getConsumers();

    void addConsumer(Consumer consumer);

    Consumer getConsumer(int id);

    Consumer getConsumer(String name);

    void decrement(int id, int balance);

    void deleteConsumer(int id);
}
