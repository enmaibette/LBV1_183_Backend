package com.lbv3.backend_.model;

import javax.persistence.*;


/**
 * Modelclass Person
 * @author Lara Akgün
 * @author Enma Ronquillo
 * @version 20.01.2020
 */
@Entity
@Table(name="produkt")
public class Produkt {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name="product")
    private String product;
    @Column(name="price")
    private double price;
    @Column (name = "description")
    private String description;


    public Produkt() {
    }

    //Getters & Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
