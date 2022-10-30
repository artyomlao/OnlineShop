package onlineshop.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "orderList")
public class OrderList {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "orderList")
    private List<OrderProduct> orderProducts;

    public void setId(Integer id) {
        this.id = id;
    }

    public OrderList() {}

    public OrderList(User user) {
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(List<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }

}
