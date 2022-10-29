package onlineshop.repository;

import onlineshop.model.Cart;
import onlineshop.model.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CartProductRepository extends JpaRepository<CartProduct, Integer> {
    List<CartProduct> findAllByCart(Cart cart);
}
