package onlineshop.service;

import onlineshop.exception.ProductDoesNotExist;
import onlineshop.model.Cart;
import onlineshop.model.CartProduct;
import onlineshop.repository.CartRepository;
import onlineshop.repository.CartProductRepository;
import onlineshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartProductService {
    private final CartProductRepository cartProductRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    @Autowired
    public CartProductService(CartProductRepository cartProductRepository, UserRepository userRepository, CartRepository cartRepository) {
        this.cartProductRepository = cartProductRepository;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
    }

    public void addOrderProduct(CartProduct cartProduct) {
        cartProductRepository.save(cartProduct);
    }

    public List<CartProduct> findAllByCart(Cart cart) {
        return cartProductRepository.findAllByCart(cart);
    }

    public void update(CartProduct cartProduct, int quantity) {
        cartProduct.setQuantity(quantity);
        cartProductRepository.save(cartProduct);
    }

    public CartProduct findById(int id) throws ProductDoesNotExist {
        return cartProductRepository.findById(id).
                orElseThrow(() -> new ProductDoesNotExist("Product doesn't exist"));
    }

    public void delete(CartProduct cartProduct) {
        cartProductRepository.delete(cartProduct);
    }
}
