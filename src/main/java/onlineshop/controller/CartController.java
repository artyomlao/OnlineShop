package onlineshop.controller;

import onlineshop.dto.OrderProductDTO;
import onlineshop.exception.CartDoesNotExist;
import onlineshop.exception.ProductDoesNotExist;
import onlineshop.model.Cart;
import onlineshop.model.CartProduct;
import onlineshop.model.OrderList;
import onlineshop.model.User;
import onlineshop.service.*;
import org.hibernate.criterion.Order;
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
    private final OrderProductService orderProductService;
    private final UserService userService;
    private final CartService cartService;
    private final OrderListService orderListService;

    @Autowired
    public CartController(CartProductService cartProductService, OrderProductService orderProductService, UserService userService,
                          CartService cartService, OrderListService orderListService) {
        this.cartProductService = cartProductService;
        this.orderProductService = orderProductService;
        this.userService = userService;
        this.cartService = cartService;
        this.orderListService = orderListService;
    }


    @GetMapping
    public String cart(Model model) {
        Cart cart = cartService.findCartByUserEmail
                (SecurityContextHolder.getContext().getAuthentication().getName());
        List<CartProduct> cartProducts = cartProductService.findAllByCart(cart);
        model.addAttribute("cartProducts", cartProducts);
        model.addAttribute("cart", cart);
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

    @PostMapping("/confirmOrder/{cart}")
    public String confirmOrder(@PathVariable("cart") int cartId) throws CartDoesNotExist {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Cart cart = cartService.findCartById(cartId);

        if (cart.getCartProducts().size() == 0) {
            System.out.println("cart is empty");
            return "redirect:/shop/products";
        } else if (cartService.checkUserId(cart, email)) {
            User user = cart.getUser();

            OrderList orderList = orderListService.addNewOrder(user);
            List<CartProduct> productList = cartProductService.findAllByCart(cart);

            orderProductService.addAll(productList, orderList);
            cartService.deleteCart(cart);
            System.out.println("order was confirmed");

            return "redirect:/shop/home";
        } else System.out.println("something went wrong");
        return "redirect:/shop/products";
    }

}
