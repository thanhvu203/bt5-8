package com.productcrud.demo.services;

import java.util.List;

import com.productcrud.demo.entities.Category;

public interface IServiceCategory {
    public void addCategory (Category category);
    public List <Category> getAllCategories();
    public Category getCategory (int id);
    public void deleteCategory(int id);

}
