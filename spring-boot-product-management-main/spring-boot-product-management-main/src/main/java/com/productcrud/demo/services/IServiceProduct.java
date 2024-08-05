package com.productcrud.demo.services;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.productcrud.demo.entities.Product;

public interface IServiceProduct {
    void saveProduct(Product product, MultipartFile mFile) throws IOException;

    List<Product> getAllProducts();

    Product getProduct(int id);

    List<Product> getProductsByKeyword(String keyword);

    void deleteProduct(int id) throws IOException;

    String saveImage(MultipartFile mFile) throws IOException;

    void deleteImage(Integer id) throws IOException;

    List<Product> getProductsByCategory(int idCategory);

    public byte[] getImage(int id) throws IOException;

}
