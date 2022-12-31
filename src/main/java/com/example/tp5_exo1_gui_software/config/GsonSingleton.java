package com.example.tp5_exo1_gui_software.config;

import com.example.tp5_exo1_gui_software.adapters.ProductAdapter;
import com.example.tp5_exo1_gui_software.adapters.UserAdapter;
import com.example.tp5_exo1_gui_software.models.Product;
import com.example.tp5_exo1_gui_software.models.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonSingleton {
    private static Gson instance;
    public static String currentDir;

    private GsonSingleton() {
    }

    public static Gson getInstance() {
        if (instance == null) {
            initialize();
        }
        return instance;
    }

    public static void initialize() {
        currentDir = System.getProperty("user.dir");
        instance = new GsonBuilder()
                .registerTypeAdapter(User.class, new UserAdapter())
                .registerTypeAdapter(Product.class, new ProductAdapter())
                .create();
    }
}
