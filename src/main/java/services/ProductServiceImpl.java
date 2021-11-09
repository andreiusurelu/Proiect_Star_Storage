package services;

import entity.Product;
import entity_dao.ProductDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
//@Transactional
public class ProductServiceImpl implements ProductService{

    private ProductDAO productDAO;

    @Autowired
    public void setProductDAO(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @Override
    public List<Product> getProducts() {
        return productDAO.getProducts();
    }

    @Override
    public List<Product> getProducts(int categoryId) {
        return productDAO.getProducts(categoryId);
    }

    @Override
    public Product getProduct(int productId) {
        return productDAO.getProduct(productId);
    }

    @Override
    public Product getProduct(String name) {
        return productDAO.getProduct(name);
    }

    @Override
    public void addProduct(Product product) {
        productDAO.addProduct(product);
    }

    @Override
    public void deleteProduct(int productId) {
        productDAO.deleteProduct(productId);
    }

    @Override
    public void increment(int productId, int quantity) {
        productDAO.increment(productId, quantity);
    }

    @Override
    public void decrement(int productId, int quantity) {
        productDAO.decrement(productId, quantity);
    }
}
