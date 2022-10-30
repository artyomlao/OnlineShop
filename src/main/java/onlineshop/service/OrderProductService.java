package onlineshop.service;

import onlineshop.model.CartProduct;
import onlineshop.model.OrderList;
import onlineshop.model.OrderProduct;
import onlineshop.repository.OrderProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderProductService {
    private final OrderProductRepository orderProductRepository;

    @Autowired
    public OrderProductService(OrderProductRepository orderProductRepository) {
        this.orderProductRepository = orderProductRepository;
    }

    public void addAll(List<CartProduct> productList, OrderList orderList) {
        productList.stream()
                .map(cartProduct -> new OrderProduct(cartProduct.getProduct(), cartProduct.getQuantity(), orderList))
                .forEach(orderProductRepository::save);
    }
}
