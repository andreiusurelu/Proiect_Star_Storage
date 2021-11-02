package entity_dao;

import entity.Category;
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
public class CategoryDAOImpl implements CategoryDAO{
    private static final Logger logger = LogManager.getLogger(CategoryDAOImpl.class);
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Override
    public List<Category> getCategoryList() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return entityManager.createNamedQuery("Category.fetchEntries", Category.class)
                .getResultList();
    }

    @Override
    public void addCategory(Category category) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(category);
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

    public void deleteCategory(int id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.createNamedQuery("Category.deleteEntry").setParameter("id", id)
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
    public Category getCategory(int id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return entityManager.find(Category.class, id);
    }

    @Override
    public Category getCategory(String name) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return entityManager.createNamedQuery("Category.fetchByName", Category.class)
                .setParameter("categoryName", name).getSingleResult();
    }
}
