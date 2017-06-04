package org.sticker.pack.model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Mikhail on 04.06.2017.
 */
@Entity
@Table(name = "order_status")
public class OrderStatus {

    @Id
    @Column(name = "status_uuid", nullable = false)
    private String uuid;

    @Column(name = "status_type", nullable = false)
    private String status;

    @OneToMany(mappedBy = "orderStatus")
    private List<Order> orders;
}
