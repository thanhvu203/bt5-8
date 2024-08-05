package com.productcrud.demo.restapi;

import java.io.IOException;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.productcrud.demo.entities.Product;
import com.productcrud.demo.services.IServiceProduct;

import lombok.AllArgsConstructor;

// allow everyone to consume api
@CrossOrigin("*")
@RestController
@RequestMapping("/productApi")
@AllArgsConstructor
public class ProductRestController {
    IServiceProduct serviceProduct;

    @GetMapping(value = { "/", "/allProducts" })
    public List<Product> getAllProducts() {
        return serviceProduct.getAllProducts();
    }

    @GetMapping("/searchByKeyword/{keyword}")
    public List<Product> getProductByKeyword(@PathVariable("keyword") String keyword) {
        return serviceProduct.getProductsByKeyword(keyword);
    }

    @GetMapping(path = "/getImage/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImage(@PathVariable("id") int id) throws IOException {
        return serviceProduct.getImage(id);
    }

    @GetMapping(path = "/allproducts/{id}")
    public Product getProduct(@PathVariable("id") int id) throws IOException {
        return serviceProduct.getProduct(id);
    }

    @PostMapping("/addProduct")
    public void addProduct(@RequestParam("product") String product,
            @RequestParam(value = "file", required = false) MultipartFile multiFile)
            throws IOException {
        Product new_product = new ObjectMapper().readValue(product, Product.class);
        serviceProduct.saveProduct(new_product, multiFile);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteProduct(@PathVariable int id) throws IOException {
        serviceProduct.deleteProduct(id);
    }

    @PutMapping("/update")
    public void update(@RequestParam("product") String product, @RequestParam("file") MultipartFile mfile)
            throws IOException {
        Product new_product = new ObjectMapper().readValue(product, Product.class);
        serviceProduct.saveProduct(new_product, mfile);
    }

}
