package edu.miu.appointmentsystem.service;


import java.util.List;
import edu.miu.appointmentsystem.domain.Category;

public interface CategoryService {

    List<Category> getCategories() ;

    Category getCategoryById(Integer id) ;

    void addCategory(Category Category) ;

    void updateCategory(Category Category) ;

    void deleteCategory(Integer id) ;

}
