package me.accessoreaze.scraper.database.databases;

import me.accessoreaze.scraper.accessory.Accessory;
import me.accessoreaze.scraper.database.mysql.listener.SQLListener;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class AccessoryDatabase extends SQLListener {

    private static PreparedStatement insertAccessory;


    @Override
    public void setup(Connection con) {
        try {
            con.prepareStatement("CREATE TABLE IF NOT EXISTS `accessory_temp`  (`id` INT NOT NULL AUTO_INCREMENT,  `type_id` INT NOT NULL, `price` DOUBLE NOT NULL, `url` TEXT NOT NULL, `imageSmall` TEXT NOT NULL, `imageBig` TEXT NOT NULL, `product` TEXT NOT NULL, `device` TINYTEXT NOT NULL, `extra` MEDIUMTEXT NOT NULL, PRIMARY KEY (`id`));").executeUpdate();

            insertAccessory = con.prepareStatement("INSERT INTO `accessory_temp` (`type_id`, `price`, `url`, `imageSmall`, `imageBig`, `product`, `device`, `extra`) VALUES (?,?,?,?,?,?,?,?);");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void insertAccessory(Accessory accessory){
        try{
            int id = AccessoryTypeDatabase.getType(accessory.getAccessoryType().name());

            insertAccessory.setInt(1, id);
            insertAccessory.setDouble(2, accessory.getPrice());
            insertAccessory.setString(3, accessory.getUrl());
            insertAccessory.setString(4, accessory.getImageSmall());
            insertAccessory.setString(5, accessory.getImageBig());
            insertAccessory.setString(6, accessory.getName());
            insertAccessory.setString(7, accessory.getModel());
            insertAccessory.setString(8, accessory.getExtra());
            insertAccessory.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void close(Connection con) {
        closeStatements();
    }
}
