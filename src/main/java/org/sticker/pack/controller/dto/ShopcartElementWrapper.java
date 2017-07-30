package org.sticker.pack.controller.dto;

/**
 * Created by Mikhail on 30.07.2017.
 */
public class ShopcartElementWrapper {

    private String uuid;
    private String name;
    private int count;
    private float price;

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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
