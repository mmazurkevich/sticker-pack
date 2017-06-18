package org.sticker.pack.model;

import org.sticker.pack.model.converter.OrderStatusConverter;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Mikhail on 04.06.2017.
 */
@Entity
@Table(name = "order")
public class Order {

    @Id
    @Column(name = "order_uuid", nullable = false)
    private String uuid;

    @Column(name = "creation_time", nullable = false)
    private long creationTime;

    @Column(name = "order_number", nullable = false)
    private int orderNumber;

    @Column(name = "delivery_address", nullable = false)
    private String deliveryAddress;

    @Column(name = "order_status", nullable = false)
    @Convert(converter = OrderStatusConverter.class)
    private OrderStatus orderStatus;

    @ManyToOne
    @JoinColumn(name="user_uuid")
    private User user;

    @PrePersist
    void onCreate() {
        this.uuid = UUID.randomUUID().toString().replace("-", "");
        this.creationTime = new Date().getTime();
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(long creationTime) {
        this.creationTime = creationTime;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
