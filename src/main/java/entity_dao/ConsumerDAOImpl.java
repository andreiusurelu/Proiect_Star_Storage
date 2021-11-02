package entity_dao;

import entity.Consumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

import java.util.List;

@Component
public class ConsumerDAOImpl implements ConsumerDAO{
    private static final Logger logger = LogManager.getLogger(ConsumerDAOImpl.class);
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Override
    public List<Consumer> getConsumerList() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return entityManager.createNamedQuery("Consumer.fetchEntries", Consumer.class)
                .getResultList();
    }

    @Override
    public void addConsumer(Consumer consumer) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(consumer);
            transaction.commit();
        }
        catch (Exception e) {
            logger.error(e);
            if (transaction != null) {
                transaction.rollback();
            }
        }
        finally {
            entityManager.close();
        }
    }

    @Override
    public void deleteConsumer(int id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.createNamedQuery("Consumer.deleteEntry").setParameter("id", id)
                    .executeUpdate();
            transaction.commit();
        }
        catch (Exception e) {
            logger.error(e);
            if (transaction != null) {
                transaction.rollback();
            }
        }
        finally {
            entityManager.close();
        }
    }

    @Override
    public Consumer getConsumer(int id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return entityManager.find(Consumer.class, id);
    }

    @Override
    public Consumer getConsumer(String name) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return entityManager.createNamedQuery("Consumer.fetchEntryByName", Consumer.class)
                .getSingleResult();
    }

    @Override
    public void decrementBalance(int id, int balance) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.createNamedQuery("Consumer.decreaseBalance")
                    .setParameter("id", id).setParameter("balance", balance).executeUpdate();
            transaction.commit();
        }
        catch (Exception e) {
            logger.error(e);
            if (transaction != null) {
                transaction.rollback();
            }
        }
        finally {
            entityManager.close();
        }
    }
}
