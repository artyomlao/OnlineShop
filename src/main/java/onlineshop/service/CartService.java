package onlineshop.service;

import onlineshop.exception.CartDoesNotExist;
import onlineshop.model.Cart;
import onlineshop.model.User;
import onlineshop.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final UserService userService;

    @Autowired
    public CartService(CartRepository cartRepository, UserService userService) {
        this.cartRepository = cartRepository;
        this.userService = userService;
    }

    public Cart findCartByUserEmail(String email) {
        User user = userService.getUserByEmail(email);
        return cartRepository.findByUser(user).orElseGet(() -> cartRepository.save(new Cart(user)));
    }

    public boolean checkUserId(Cart cart, String email) {
        User user = userService.getUserByEmail(email);
        if(user.getId().equals(cart.getUser().getId())) {
            return true;
        } else return false;
    }

    public Cart findCartById(int id) throws CartDoesNotExist {
        return cartRepository.findById(id).orElseThrow(() -> new CartDoesNotExist("Cart doesn't exist"));
    }

    public void deleteCart(Cart cart) {
        cart.getCartProducts().removeIf(c -> c.getCart().getId().equals(cart.getId()));
        cartRepository.deleteById(cart.getId());
    }
}
