package me.accessoreaze.scraper.accessory;

public abstract class Accessory {

    protected String model, url, picture;
    protected double price;

    public Accessory(String model, String url, String picture, double price) {
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

    public String toString(){
        String result = this.model + ": " + this.price;

        result += "\nURL:" + this.url + "\nPicture:" + this.picture;

        return result;
    }
}
