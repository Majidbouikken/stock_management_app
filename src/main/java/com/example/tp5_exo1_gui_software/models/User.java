package com.example.tp5_exo1_gui_software.models;

import java.util.ArrayList;
import java.util.Random;

// Modèle de donnée d'un User
public class User {
    private long id;
    private String name;
    private int age;
    private String email;
    private String password;
    public static ArrayList<User> users = new ArrayList<>();

    public User(String name, int age, String email, String password) {
        this.id = generateId();
        this.name = name;
        this.age = age;
        this.email = email;
        this.password = password;
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
        while (getUserById(id) != null) {
            id = random.nextLong();
        }
        return id;
    }

    public static User getUserById(long id) {
        for (User user : users) {
            if (user.id == id) {
                return user;
            }
        }
        return null;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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