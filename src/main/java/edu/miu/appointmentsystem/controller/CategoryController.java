package edu.miu.appointmentsystem.controller;

import edu.miu.appointmentsystem.domain.Category;
import edu.miu.appointmentsystem.dto.UserDTO;
import edu.miu.appointmentsystem.exception.CustomErrorType;
import edu.miu.appointmentsystem.service.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CategoryController {

    @Autowired
    private CategoryServiceImpl categoryService;

    @GetMapping("/categories")
    public ResponseEntity<?> getAllCategories() {
        Categories categories = new Categories(categoryService.getCategories());
        if (categories != null)
            return new ResponseEntity<Categories>(categories, HttpStatus.OK);
        else
            return new ResponseEntity<CustomErrorType>(new CustomErrorType("Not authorized"), HttpStatus.METHOD_NOT_ALLOWED);
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Integer id) {
        Category category = categoryService.getCategoryById(id);
        if (category == null) {
            return new ResponseEntity<CustomErrorType>(new CustomErrorType("Category with reference = "
                    + id.toString() + " is not available"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Category>(category, HttpStatus.OK);
    }

    @PostMapping("/categories")
    public ResponseEntity<?> createUser(@RequestBody Category category) {
        categoryService.addCategory(category);
        return new ResponseEntity<Category>(category, HttpStatus.OK);
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Integer id, @RequestBody Category category) {
        categoryService.updateCategory(category);
        return new ResponseEntity<Category>(category, HttpStatus.OK);
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        Category category = categoryService.getCategoryById(id);
        if (category == null)
            return new ResponseEntity<CustomErrorType>(new CustomErrorType("Category with reference = "
                    + id.toString() + " is not available"), HttpStatus.NOT_FOUND);
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
