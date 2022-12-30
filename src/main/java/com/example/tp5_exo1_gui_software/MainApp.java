package com.example.tp5_exo1_gui_software;

import com.example.tp5_exo1_gui_software.models.Product;
import com.example.tp5_exo1_gui_software.models.User;
import com.example.tp5_exo1_gui_software.screens.LoginScreen;

import java.time.LocalDate;

/*
 * TP: Introduction to software traceability with Spoon
 * Module: HAI913I Évolution et restructuration des logiciels
 * Option: Génie Logiciel (M2)
 *
 * Projet: Stock Management App
 * Nom: Bouikken Bahi Amar
 * Prénom: Abdelmadjid
 * Email: bouikkenmajid@gmail.com
 *
 * Encadrant: M. Bachar Rima
 */
public class MainApp {
    public static void main(String[] args) {
        // Utilisateurs par default
        User.users.add(new User("Joachim", 49, "user@gmail.com", "123"));
        User.users.add(new User("Hakim", 29, "hakim@gmail.com", "123"));
        User.users.add(new User("Reda", 24, "reda@gmail.com", "123"));
        User.users.add(new User("Sami", 32, "sami@gmail.com", "123"));
        User.users.add(new User("Maher", 38, "maher@gmail.com", "123"));
        User.users.add(new User("Bassam", 19, "bassam@gmail.com", "123"));
        User.users.add(new User("Abdelmadjid", 23, "bouikkenmajid@gmail.com", "123456"));

        // État du Repository des Produits par default
        Product.repository.add(new Product("101", "Apple Iphone 11", 1400, LocalDate.of(2039, 12, 31)));
        Product.repository.add(new Product("102", "Google Pixel 6a", 599, LocalDate.of(2037, 1, 1)));
        Product.repository.add(new Product("103", "Samsung Galaxy S22 Ultra", 1099, LocalDate.of(2044, 7, 8)));
        Product.repository.add(new Product("107", "Apple Macbook Pro M2", 2749, LocalDate.of(2051, 4, 10)));
        Product.repository.add(new Product("108", "Google Chromebook Pixel", 999, LocalDate.of(2034, 6, 5)));
        Product.repository.add(new Product("109", "Dell XPS 13 9370", 1699, LocalDate.of(2049, 10, 17)));

        // Création de la page d'authentification
        LoginScreen loginScreen = new LoginScreen();

        // Affichage de la page d'authentification
        loginScreen.setVisible(true);
    }
}