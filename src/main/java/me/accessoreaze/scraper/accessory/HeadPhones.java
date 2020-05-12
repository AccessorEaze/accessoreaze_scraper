package me.accessoreaze.scraper.accessory;

import me.accessoreaze.scraper.accessory.type.AccessoryType;

public class HeadPhones extends Accessory {


    public HeadPhones(String name,String model, String url, String imageSmall, String imageBig, double price) {
        super(name, model, url, imageSmall, imageBig, price);

        type = AccessoryType.HEADPHONES;
    }
    public AccessoryType getAccessoryType()
    {
        return this.type;
    }
}