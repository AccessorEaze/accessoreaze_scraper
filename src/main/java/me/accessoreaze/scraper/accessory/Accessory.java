package me.accessoreaze.scraper.accessory;

import me.accessoreaze.scraper.accessory.type.AccessoryType;

public abstract class Accessory {

    protected String model, url, picture, name;
    protected double price;
    protected AccessoryType type;


    public Accessory(String name, String model, String url, String picture, double price) {
        this.name = name;
        this.model = model;
        this.url = url;
        this.picture = picture;
        this.price = price;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public AccessoryType getAccessoryType()
    {
        return this.type;
    }


    public String getName()
    {
        return this.name;
    }

    public String toString(){
        String result = this.model + ": " + this.price;

        result += "\nURL:" + this.url + "\nPicture:" + this.picture;

        return result;
    }
}
