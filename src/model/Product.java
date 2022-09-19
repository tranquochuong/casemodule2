package model;

import java.time.Instant;

public class Product {
    private  long id;
    private String name;
    private double price;
    private int quantity;
    private Instant createdAt;
    private Instant updatedAt;
    public Product(){}
    public Product(long id, String name, double price, int quantity){
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
    public Product(long id, String name, double price, int quantity,Instant createdAt, Instant updatedAt){
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public static Product parseProduct(String raw) {
        Product product = new Product();
        String[] fields = raw.split(",");
        product.id = Long.parseLong(fields[0]);
        product.name = fields[1];
        product.price = Double.parseDouble(fields[2]);
        product.quantity = Integer.parseInt(fields[3]);
        product.createdAt = Instant.parse(fields[4]);
        String temp = fields[5];
        if (temp != null && !temp.equals("null"))
            product.updatedAt = Instant.parse(temp);
        return product;
    }

    public long getId() {
        return id;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return id + "," +
                name + "," +
                price + "," +
                quantity + "," +
                createdAt + "," +
                updatedAt
                ;
    }
}