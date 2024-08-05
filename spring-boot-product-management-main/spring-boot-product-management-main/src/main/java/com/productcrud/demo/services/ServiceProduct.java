package com.productcrud.demo.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties.Io;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.productcrud.demo.dao.CategoryRepository;
import com.productcrud.demo.dao.ProductRepository;
import com.productcrud.demo.entities.Product;

@Service
public class ServiceProduct implements IServiceProduct {

    @Autowired
    ProductRepository productRepo;

    @Autowired
    CategoryRepository categoryRepo;

    @Override
    public void saveProduct(Product product, MultipartFile mFile) throws IOException {
        String photo;
        if (/* mFile.getOriginalFilename().equals("") */ mFile != null) {
            photo = saveImage(mFile);
            product.setPhoto(photo);
        }
        productRepo.save(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    @Override
    public Product getProduct(int id) {
        return productRepo.findById(id).get();
    }

    @Override
    public List<Product> getProductsByKeyword(String keyword) {
        return productRepo.searchByProductName(keyword);
    }

    @Override
    public void deleteProduct(int id) throws IOException {
        if (!productRepo.getById(id).getPhoto().equals("")) {
            deleteImage(id);
        }
        productRepo.deleteById(id);

    }

    @Override
    public String saveImage(MultipartFile mFile) throws IOException {
        String nameFile = mFile.getOriginalFilename();
        System.out.print(nameFile);
        String tab[] = nameFile.split("\\.");
        String fileModif = tab[0] + "_" + System.currentTimeMillis() + "." + tab[1];
        // String chemin = System.getProperty("user.dir") +
        // "/src/main/webapp/imagesdata/";
        String chemin = System.getProperty("user.home") + "/Myimages/";
        Path path = Paths.get(chemin, fileModif);
        Files.write(path, mFile.getBytes());
        return fileModif;
    }

    @Override
    public void deleteImage(Integer id) throws IOException {
        Product product = productRepo.getById(id);
        String route = System.getProperty("user.dir") + "/src/main/webapp/imagesdata";
        Path path = Paths.get(route, product.getPhoto());
        Files.delete(path);

    }

    @Override
    public List<Product> getProductsByCategory(int idCategory) {
        return categoryRepo.getById(idCategory).getProducts();

    }

    @Override
    public byte[] getImage(int id) throws IOException {
        String image = productRepo.findById(id).get().getPhoto();
        Path path = Paths.get(System.getProperty("user.home") + "/Myimages/", image);
        return Files.readAllBytes(path);
    }

}
