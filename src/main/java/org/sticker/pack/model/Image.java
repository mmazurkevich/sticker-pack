package org.sticker.pack.model;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Mikhail on 14.05.2017.
 */
@Entity
@Table(name = "image")
public class Image {

    @Id
    @Column(name = "image_uuid", nullable = false)
    private String uuid;

    @Column(name = "origin_name", nullable = false)
    private String originName;

    @Column(name = "hash_name", nullable = false)
    private String hashName;

    @Column(name = "create_date", nullable = false)
    private long createDate;

    @Column(name = "url", nullable = false)
    private String url;

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

    public String getOriginName() {
        return originName;
    }

    public void setOriginName(String originName) {
        this.originName = originName;
    }

    public String getHashName() {
        return hashName;
    }

    public void setHashName(String hashName) {
        this.hashName = hashName;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
