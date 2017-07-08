package org.sticker.pack.model;

import javax.persistence.*;
import java.util.UUID;

/**
 * Created by Mikhail on 08.07.2017.
 */
@Entity
@Table(name = "order_item")
public class OrderItem {

    @Id
    @Column(name = "item_uuid", nullable = false)
    private String uuid;

    @Column(name = "items_count", nullable = false)
    private int itemsCount;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="order_uuid", referencedColumnName = "order_uuid")
    private Order order;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="sticker_uuid", referencedColumnName = "sticker_uuid")
    private Sticker sticker;

    @PrePersist
    void onCreate() {
        this.uuid = UUID.randomUUID().toString().replace("-", "");
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getItemsCount() {
        return itemsCount;
    }

    public void setItemsCount(int itemsCount) {
        this.itemsCount = itemsCount;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Sticker getSticker() {
        return sticker;
    }

    public void setSticker(Sticker sticker) {
        this.sticker = sticker;
    }
}
