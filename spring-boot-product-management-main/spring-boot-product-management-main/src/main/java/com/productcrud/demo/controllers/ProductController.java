package com.productcrud.demo.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.productcrud.demo.entities.Product;
import com.productcrud.demo.services.ServiceCategory;
import com.productcrud.demo.services.ServiceProduct;

@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    ServiceProduct serviceProduct;
    @Autowired
    ServiceCategory serviceCategory;

    @GetMapping(value = { "/", "/allproducts" })
    public String getAllProducts(Model model) {
        List<Product> products = serviceProduct.getAllProducts();
        model.addAttribute("products", products);
        model.addAttribute("categories", serviceCategory.getAllCategories());
        model.addAttribute("category", "All Categories");
        return "products";
    }

    @GetMapping(value = { "/productkeyword", "/allproducts/productkeyword" })
    public String getProductsByKeyword(@RequestParam String keyword, Model model) {
        List<Product> products = serviceProduct.getProductsByKeyword(keyword);
        model.addAttribute("products", products);
        return "products";
    }

    @GetMapping("/deleteproduct/{id}")
    public String deleteProduct(@PathVariable int id) throws IOException {
        serviceProduct.deleteProduct(id);
        return "redirect:/products/";
    }

    @GetMapping("/addproduct")
    public String addProduct(Model model) {
        model.addAttribute("categories", serviceCategory.getAllCategories());
        return "addProduct";
    }

    @PostMapping("/saveproduct")
    public String saveProduct(@ModelAttribute Product product, @RequestParam("file") MultipartFile mFile, Model model)
            throws IOException {
        Integer id = product.getId();
        serviceProduct.saveProduct(product, mFile);
        if (id != null) { // ? for editing a product
            return "redirect:/products/";
        } else {// ? for adding new Product
            model.addAttribute("message", "Added Successfully.");
            model.addAttribute("categories", serviceCategory.getAllCategories());
            return ("addProduct");
        }

    }

    @GetMapping("/editproduct/{idp}")
    public String editProduct(Model model, @PathVariable("idp") int id) {
        model.addAttribute("categories", serviceCategory.getAllCategories());
        model.addAttribute("product", serviceProduct.getProduct(id));
        return ("addProduct");
    }

    @GetMapping(value = { "/productByCategory", "allproducts/productByCategory" })
    public String getProductByCategoryKey(@RequestParam("category") int idCategory, Model model) {
        model.addAttribute("categories", serviceCategory.getAllCategories());
        if (idCategory == 0) {
            model.addAttribute("products", serviceProduct.getAllProducts());
            return "redirect:/products/";
        } else {
            model.addAttribute("products", serviceProduct.getProductsByCategory(idCategory));
            model.addAttribute("category", serviceCategory.getCategory(idCategory).getName());
            return "products";
        }
    }
}
