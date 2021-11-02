package services;

import entity.Category;

import java.util.List;

public interface CategoryService {

    List<Category> getCategories();

    void addCategory(Category category);

    Category getCategory(int id);

    Category getCategory(String name);

    void deleteCategory(int id);
}
