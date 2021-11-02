package services;

import entity.Category;
import entity_dao.CategoryDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
//@Transactional
public class CategoryServiceImpl implements CategoryService{
    private CategoryDAO categoryDAO;

    @Autowired
    public void setCategoryDAO(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    @Override
    public List<Category> getCategories() {
        return categoryDAO.getCategoryList();
    }

    @Override
    public void addCategory(Category category) {
        categoryDAO.addCategory(category);
    }

    @Override
    public Category getCategory(int id) {
        return categoryDAO.getCategory(id);
    }

    @Override
    public Category getCategory(String name) {
        return categoryDAO.getCategory(name);
    }

    @Override
    public void deleteCategory(int id) {
        categoryDAO.deleteCategory(id);
    }
}
