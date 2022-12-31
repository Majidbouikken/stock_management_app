package com.example.tp5_exo1_gui_software.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// Modèle de donnée d'un Produit
public class Product {
    @Expose
    private String id;
    @Expose
    private String name;
    @Expose
    private double price;
    @Expose
    @SerializedName("expiration_date")
    private LocalDate expirationDate;

    // Repository des produits
    public static List<Product> repository = new ArrayList<>();

    public Product() {
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
