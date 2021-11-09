package services;

import entity.Consumer;
import entity_dao.ConsumerDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsumerServiceImpl implements ConsumerService{
    private ConsumerDAO consumerDAO;

    @Autowired
    public void setConsumerDAO(ConsumerDAO consumerDAO) {
        this.consumerDAO = consumerDAO;
    }


    @Override
    public List<Consumer> getConsumers() {
        return consumerDAO.getConsumerList();
    }

    @Override
    public void addConsumer(Consumer consumer) {
        consumerDAO.addConsumer(consumer);
    }

    @Override
    public Consumer getConsumer(int id) {
        return consumerDAO.getConsumer(id);
    }

    @Override
    public Consumer getConsumer(String name) {
        return consumerDAO.getConsumer(name);
    }



    @Override
    public void decrement(int id, int balance) {
        consumerDAO.decrementBalance(id, balance);
    }

    @Override
    public void deleteConsumer(int id) {
        consumerDAO.deleteConsumer(id);
    }
}
