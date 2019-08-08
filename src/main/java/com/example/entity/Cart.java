package com.example.entity;

import javax.persistence.*;

// сущность Корзина, отмечена аннотациями для маппинга в БД (создание/обновление таблиц в БД где поля класса соответствуют столбцам в таблице БД),
// и для передачи данных в БД
@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "userid")
    private Long userid;

    @Column(name = "productid")
    private Long productid;

    public Cart() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Long getProductid() {
        return productid;
    }

    public void setProductid(Long productid) {
        this.productid = productid;
    }
}
