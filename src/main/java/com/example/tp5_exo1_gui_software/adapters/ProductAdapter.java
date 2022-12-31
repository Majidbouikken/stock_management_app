package com.example.tp5_exo1_gui_software.adapters;

import com.example.tp5_exo1_gui_software.models.Product;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDate;

public class ProductAdapter extends TypeAdapter<Product> {
    @Override
    public void write(JsonWriter out, Product value) throws IOException {
        out.beginObject();
        out.name("id").value(value.getId());
        out.name("name").value(value.getName());
        out.name("price").value(value.getPrice());
        out.name("expiration_date").value(value.getExpirationDate().toString());
        out.endObject();
    }

    @Override
    public Product read(JsonReader in) throws IOException {
        Product product = new Product();
        in.beginObject();
        while (in.hasNext()) {
            switch (in.nextName()) {
                case "id":
                    try {
                        Field field = Product.class.getDeclaredField("id");
                        field.setAccessible(true);
                        field.set(product, in.nextString());
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    break;
                case "name":
                    product.setName(in.nextString());
                    break;
                case "price":
                    product.setPrice(in.nextDouble());
                    break;
                case "expiration_date":
                    product.setExpirationDate(LocalDate.parse(in.nextString()));
                    break;
                default:
                    in.skipValue();
                    break;
            }
        }
        in.endObject();
        return product;
    }
}
