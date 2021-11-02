package entity_dao;

import entity.Category;

import java.util.List;

public interface CategoryDAO {
    List<Category> getCategoryList();

    void addCategory(Category category);

    void deleteCategory(int id);

    Category getCategory(int id);

    Category getCategory(String name);
}
