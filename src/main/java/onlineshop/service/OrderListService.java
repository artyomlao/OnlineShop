package onlineshop.service;

import onlineshop.model.OrderList;
import onlineshop.model.User;
import onlineshop.repository.OrderListRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderListService {
    private final OrderListRepository orderListRepository;

    public OrderListService(OrderListRepository orderListRepository) {
        this.orderListRepository = orderListRepository;
    }

    public OrderList addNewOrder(User user) {
        OrderList order = new OrderList(user);
        return orderListRepository.save(order);
    }
}
