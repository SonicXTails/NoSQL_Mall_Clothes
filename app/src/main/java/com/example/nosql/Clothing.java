package com.example.nosql;

public class Clothing {
    private String name;
    private String size;
    private String description;
    private String imagePath;

    public Clothing(String name, String size, String description, String imagePath) {
        this.name = name;
        this.size = size;
        this.description = description;
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public String getSize() {
        return size;
    }

    public String getDescription() {
        return description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}