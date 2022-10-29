package onlineshop.service;

import onlineshop.model.Cart;
import onlineshop.model.User;
import onlineshop.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
