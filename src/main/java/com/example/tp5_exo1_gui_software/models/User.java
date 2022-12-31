package com.example.tp5_exo1_gui_software.models;

import java.util.ArrayList;
import java.util.Random;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

// Modèle de donnée d'un User
public class User {
    @Expose
    private String id;
    @Expose
    private String name;
    @Expose
    private int age;
    @Expose
    private String email;
    @Expose
    private String password;
    public static ArrayList<User> users = new ArrayList<>();

    public User() {
        this.id = Long.toString(generateId());
    }

    // Méthode pour s'authentifier
    public static User login(String email, String password) throws Exception {
        for (User user : users) {
            if (user.email.equals(email) && user.password.equals(password)) {
                return user;
            }
        }
        throw new Exception("Invalid email or password");
    }

    public static long generateId() {
        Random random = new Random();
        long id = random.nextLong();
        while (getUserById(Long.toString(id)) != null) {
            id = random.nextLong();
        }
        return id;
    }

    public static User getUserById(String id) {
        for (User user : users) {
            if (user.id.equals(id)) {
                return user;
            }
        }
        return null;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}