package org.sticker.pack.model;

import javax.persistence.*;

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

    @ManyToOne
    @JoinColumn(name="user_uuid")
    private User user;

    @ManyToOne
    @JoinColumn(name="status_uuid")
    private OrderStatus orderStatus;
}
