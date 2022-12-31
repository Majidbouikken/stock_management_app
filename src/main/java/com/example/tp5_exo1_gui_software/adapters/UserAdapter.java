package com.example.tp5_exo1_gui_software.adapters;

import com.example.tp5_exo1_gui_software.models.User;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Field;

public class UserAdapter extends TypeAdapter<User> {
    @Override
    public void write(JsonWriter out, User value) throws IOException {
        out.beginObject();
        out.name("id").value(value.getId());
        out.name("name").value(value.getName());
        out.name("age").value(value.getAge());
        out.name("email").value(value.getEmail());
        out.name("password").value(value.getPassword());
        out.endObject();
    }

    @Override
    public User read(JsonReader in) throws IOException {
        User user = new User();
        in.beginObject();
        while (in.hasNext()) {
            switch (in.nextName()) {
                case "id":
                    try {
                        Field field = User.class.getDeclaredField("id");
                        field.setAccessible(true);
                        field.set(user, in.nextString());
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    break;
                case "name":
                    user.setName(in.nextString());
                    break;
                case "age":
                    user.setAge(in.nextInt());
                    break;
                case "email":
                    user.setEmail(in.nextString());
                    break;
                case "password":
                    user.setPassword(in.nextString());
                    break;
                default:
                    in.skipValue();
                    break;
            }
        }
        in.endObject();
        return user;
    }
}