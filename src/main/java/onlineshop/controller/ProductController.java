package onlineshop.controller;

import onlineshop.dto.OrderProductDTO;
import onlineshop.model.Cart;
import onlineshop.model.CartProduct;
import onlineshop.model.Product;
import onlineshop.service.CartService;
import onlineshop.service.CartProductService;
import onlineshop.service.ProductService;
import onlineshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/shop/products")
public class ProductController {
    private final ProductService productService;
    private final CartProductService cartProductService;
    private final UserService userService;
    private final CartService cartService;

    @Autowired
    public ProductController(ProductService productService, CartProductService cartProductService,
                             UserService userService, CartService cartService) {
        this.productService = productService;
        this.cartProductService = cartProductService;
        this.userService = userService;
        this.cartService = cartService;
    }

    @GetMapping("")
    public String products(Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "products";
    }

    @GetMapping("/{id}")
    public String productItem(@PathVariable Integer id, Model model) {
        Product product = productService.findById(id);
        model.addAttribute("product", product);
        return "productItem";
    }

    @PostMapping("/add/{id}")
    public String addProductToCart(@PathVariable int id,
                                   @ModelAttribute("product") OrderProductDTO product) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Cart cart = cartService.findCartByUserEmail(email);

        Product foundProduct = productService.findById(id);

        CartProduct cartProduct = new CartProduct(foundProduct, product.getQuantity(), cart);
        cartProductService.addOrderProduct(cartProduct);
        return "redirect:/shop/products";
    }
}
