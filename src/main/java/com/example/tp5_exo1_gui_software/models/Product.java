package com.example.tp5_exo1_gui_software.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// Modèle de donnée d'un Produit
public class Product {
    private String id;
    private String name;
    private double price;
    private LocalDate expirationDate;

    // Repository des produits
    public static final List<Product> repository = new ArrayList<>();

    public Product(String id, String name, double price, LocalDate expirationDate) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.expirationDate = expirationDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public String toString() {
        return "ID: " + id + "\nName: " + name + "\nPrice: " + price + "\nExpiration Date: " + expirationDate.toString();
    }
}
