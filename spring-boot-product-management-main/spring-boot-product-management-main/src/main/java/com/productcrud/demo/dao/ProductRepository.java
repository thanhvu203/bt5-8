package com.productcrud.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.productcrud.demo.entities.Product;

public interface ProductRepository extends JpaRepository <Product, Integer>{
    // ? we are implementing a search Method of products by name
    // * First way of doing it
    List <Product> findByNameContains(String keyword);
    // * Second way of doing it 
    @Query("select p from Product p where p.name like %:x%")
    List <Product> searchByProductName(@Param("x")String keyword);
}
