package edu.miu.appointmentsystem.service;

import edu.miu.appointmentsystem.domain.Category;
import edu.miu.appointmentsystem.domain.Category;
import edu.miu.appointmentsystem.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository CategoryRepository;

    @Override
    public List<Category> getCategories() {
        return CategoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(Integer id) {
        return CategoryRepository.findById(id).orElse(null);
    }
    @Override
    public void addCategory(Category Category) {
        CategoryRepository.save(Category);
    }
    @Override
    public void updateCategory(Category Category) {
        CategoryRepository.save(Category);
    }
    @Override
    public void deleteCategory(Integer id) {
        CategoryRepository.deleteById(id);
    }



}
