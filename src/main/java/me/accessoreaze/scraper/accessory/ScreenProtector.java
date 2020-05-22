package me.accessoreaze.scraper.accessory;

import me.accessoreaze.scraper.accessory.type.AccessoryType;

public class ScreenProtector extends Accessory {


    public ScreenProtector(String name, String model, String url, String imageSmall, String imageBig, double price,String vendor) {
        super(name, model, url, imageSmall, imageBig, price, vendor);

        type = AccessoryType.SCREEN_PROTECTOR;
    }

    public AccessoryType getAccessoryType()
    {
        return this.type;
    }

}
