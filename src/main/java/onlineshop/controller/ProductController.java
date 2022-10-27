package onlineshop.controller;

import onlineshop.model.Product;
import onlineshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public String products(Model model) {
        List<Product> products =  productService.findAll();
        model.addAttribute("products", products);
        return "products";
    }

    @GetMapping("/products/{id}")
    public String productItem(@PathVariable Integer id) {
        return "productItem";
    }
}
