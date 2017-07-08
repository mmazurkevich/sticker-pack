package org.sticker.pack.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "sticker")
public class Sticker {

    @Id
    @Column(name = "sticker_uuid", nullable = false)
    private String uuid;

    @Column(name = "sticker_name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private float price;

    @Column(name = "create_date", nullable = false)
    private long createDate;

    @Column(name = "count", nullable = false)
    private int count;

    @OneToOne
    @JoinColumn(name="image_uuid", referencedColumnName = "image_uuid", unique=true, nullable=false)
    private Image image;

    @OneToMany(fetch=FetchType.LAZY, mappedBy = "sticker")
    private List<OrderItem> orderItems;

    @PrePersist
    void onCreate() {
        this.uuid = UUID.randomUUID().toString().replace("-", "");
        this.createDate = new Date().getTime();
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
