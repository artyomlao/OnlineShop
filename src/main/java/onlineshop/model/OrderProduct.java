package onlineshop.model;

import javax.persistence.*;

@Entity
@Table(name = "orderproduct")
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "productId", referencedColumnName = "id")
    private Product product;

    @Column
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "orderListId", referencedColumnName = "id")
    private OrderList orderList;

    public OrderProduct() {
    }

    public OrderProduct(Product product, int quantity, OrderList orderList) {
        this.product = product;
        this.quantity = quantity;
        this.orderList = orderList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public OrderList getOrder() {
        return orderList;
    }

    public void setOrder(OrderList orderList) {
        this.orderList = orderList;
    }
}
