package me.accessoreaze.scraper.accessory;

import com.google.gson.JsonObject;
import me.accessoreaze.scraper.accessory.type.AccessoryType;

public abstract class Accessory {

    protected String model, url, name, imageSmall, imageBig;
    protected double price;
    protected AccessoryType type;
    protected JsonObject extra;


    public Accessory(String name, String model, String url, String imageSmall, String imageBig, double price) {
        this.name = name;
        this.model = model;
        this.url = url;
        this.price = price;
        this.imageSmall = imageSmall;
        this.imageBig = imageBig;
        this.extra = new JsonObject();
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

    public String getImageSmall() {
        return imageSmall;
    }

    public String getImageBig() {
        return imageBig;
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
        String result = this.model + "\\" + this.name + ": " + this.price;

        result += "\nURL:" + this.url + "\nPicture:" + this.imageBig;

        return result;
    }

    public String getExtraString(){
        return extra.toString();
    }

    public void addExtra(String key, String value){
        extra.addProperty(key, value);
    }
}
