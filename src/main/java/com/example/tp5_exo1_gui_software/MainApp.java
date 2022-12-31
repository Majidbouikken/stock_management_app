package com.example.tp5_exo1_gui_software;

import com.example.tp5_exo1_gui_software.adapters.ProductAdapter;
import com.example.tp5_exo1_gui_software.adapters.UserAdapter;
import com.example.tp5_exo1_gui_software.config.GsonSingleton;
import com.example.tp5_exo1_gui_software.models.Product;
import com.example.tp5_exo1_gui_software.models.User;
import com.example.tp5_exo1_gui_software.screens.LoginScreen;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

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
    public static void main(String[] args) throws IOException {
        // Pour le stockage des données nous avons utilisé des fichiers Json
        // et Gson pour la lecture et écriture des fichiers Json
        GsonSingleton.initialize();
        // Lecture des Users et du Repository
        try (FileReader reader = new FileReader(GsonSingleton.currentDir + "\\data\\users.json")) {
            User.users = GsonSingleton.getInstance().fromJson(reader, new TypeToken<List<User>>() {
            }.getType());
        }
        try (FileReader reader = new FileReader(GsonSingleton.currentDir + "\\data\\repository.json")) {
            Product.repository = GsonSingleton.getInstance().fromJson(reader, new TypeToken<List<Product>>() {
            }.getType());
        }
        // Création et affichage de la page d'authentification
        LoginScreen loginScreen = new LoginScreen();
        loginScreen.setVisible(true);
    }
}