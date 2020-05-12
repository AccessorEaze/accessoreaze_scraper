package me.accessoreaze.scraper.accessory;

import me.accessoreaze.scraper.accessory.type.AccessoryType;

public class PhoneCase extends Accessory {

    public PhoneCase(String name,String model, String url, String imageSmall, String imageBig, double price) {
        super(name, model, url, imageSmall, imageBig, price);

        type = AccessoryType.PHONE_CASE;
    }
    public AccessoryType getAccessoryType()
    {
        return this.type;
    }

}
