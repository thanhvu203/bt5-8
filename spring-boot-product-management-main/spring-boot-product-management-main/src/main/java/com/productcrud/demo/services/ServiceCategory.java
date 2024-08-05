package com.productcrud.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.productcrud.demo.dao.CategoryRepository;
import com.productcrud.demo.entities.Category;

@Service
public class ServiceCategory implements IServiceCategory {

    @Autowired
    CategoryRepository categoryRepo;

    @Override
    public void addCategory(Category category) {
        categoryRepo.save(category);    
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepo.findAll();
    }

    @Override
    public Category getCategory(int id) {
        return categoryRepo.findById(id).get();
    }

    @Override
    public void deleteCategory(int id) {
        categoryRepo.deleteById(id);
        
    }
    
}
