package entity_dao;

import entity.Product;
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
public class ProductDAOImpl implements ProductDAO{
    @Autowired
    private EntityManagerFactory entityManagerFactory;
    private static final Logger logger = LogManager.getLogger(ProductDAOImpl.class);

    @Override
    public List<Product> getProducts() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return entityManager.createNamedQuery("Product.fetchEntries", Product.class).getResultList();
    }

    @Override
    public List<Product> getProducts(int categoryId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return entityManager.createNamedQuery("Product.fetchEntriesByCategory", Product.class)
                .setParameter("id", categoryId).getResultList();
    }

    @Override
    public void addProduct(Product product) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(product);
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
    public void deleteProduct(int id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.createNamedQuery("Product.deleteEntry")
                    .setParameter("id", id).executeUpdate();
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
    public Product getProduct(int id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return entityManager.find(Product.class, id);
    }

    @Override
    public Product getProduct(String name) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return entityManager.createNamedQuery("Product.fetchByName", Product.class).setParameter("productName", name)
                .getSingleResult();
    }

    @Override
    public void increment(int id, int quantity) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.createNamedQuery("Product.increment")
                    .setParameter("id", id).setParameter("quantity", quantity).executeUpdate();
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
    public void decrement(int id, int quantity) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.createNamedQuery("Product.decrement")
                    .setParameter("id", id).setParameter("quantity", quantity).executeUpdate();
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
