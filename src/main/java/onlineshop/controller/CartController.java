package onlineshop.controller;

import onlineshop.dto.OrderProductDTO;
import onlineshop.exception.ProductDoesNotExist;
import onlineshop.model.Cart;
import onlineshop.model.CartProduct;
import onlineshop.service.CartService;
import onlineshop.service.CartProductService;
import onlineshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/shop/cart")
public class CartController {
    private final CartProductService cartProductService;
    private final UserService userService;
    private final CartService cartService;

    @Autowired
    public CartController(CartProductService cartProductService, UserService userService,
                          CartService cartService) {
        this.cartProductService = cartProductService;
        this.userService = userService;
        this.cartService = cartService;
    }


    @GetMapping
    public String cart(Model model) {
        Cart cart = cartService.findCartByUserEmail
                (SecurityContextHolder.getContext().getAuthentication().getName());
        List<CartProduct> cartProducts = cartProductService.findAllByCart(cart);
        model.addAttribute("cartProducts", cartProducts);
        return "cart";
    }

    //patch didn't work
    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable int id,
                                @ModelAttribute("product") OrderProductDTO product) {
        Cart cart = cartService.findCartByUserEmail
                (SecurityContextHolder.getContext().getAuthentication().getName());
        try {
            CartProduct cartProduct = cartProductService.findById(id);
            if(cartProduct.getCart().equals(cart)) {
                cartProductService.update(cartProduct, product.getQuantity());
            }
        } catch (ProductDoesNotExist e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return "redirect:/shop/cart";
    }

    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable int id,
                                @ModelAttribute("product") OrderProductDTO product) {
        Cart cart = cartService.findCartByUserEmail
                (SecurityContextHolder.getContext().getAuthentication().getName());
        try {
            CartProduct cartProduct = cartProductService.findById(id);
            if (cartProduct.getCart().equals(cart)) {
                cartProductService.delete(cartProduct);
            }
        } catch (ProductDoesNotExist e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return "redirect:/shop/cart";
    }

}
