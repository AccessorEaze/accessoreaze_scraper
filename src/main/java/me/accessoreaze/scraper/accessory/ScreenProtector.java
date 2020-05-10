package me.accessoreaze.scraper.accessory;

import me.accessoreaze.scraper.accessory.type.AccessoryType;

public class ScreenProtector extends Accessory {


    public ScreenProtector(String name, String model, String url, String picture, double price) {
        super(name, model, url, picture, price);

        type = AccessoryType.SCREEN_PROTECTOR;
    }

    public AccessoryType getAccessoryType()
    {
        return this.type;
    }

}
