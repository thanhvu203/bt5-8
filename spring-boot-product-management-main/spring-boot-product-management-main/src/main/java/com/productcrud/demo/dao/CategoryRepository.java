package com.productcrud.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.productcrud.demo.entities.Category;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
    
}
