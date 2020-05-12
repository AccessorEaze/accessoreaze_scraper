package me.accessoreaze.scraper.database.databases;

import me.accessoreaze.scraper.database.mysql.listener.SQLListener;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class AccessoryTypeDatabase extends SQLListener {

    private static PreparedStatement createType, getType;

    private static Map<String, Integer> cache = new HashMap();

    @Override
    public void setup(Connection con) {
        try{
            con.prepareStatement("CREATE TABLE IF NOT EXISTS `accessory_type` (`type_id` INT NOT NULL AUTO_INCREMENT , `type` tinytext NOT NULL, PRIMARY KEY (`type_id`));").executeUpdate();

            createType = con.prepareStatement("INSERT INTO `accessory_type` (`type`) VALUES (?);");
            getType = con.prepareStatement("SELECT * FROM `accessory_type` WHERE `type` = ?;");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static int getType(String accessory){
        if(cache.containsKey(accessory)){
            return cache.get(accessory);
        }
        int typeId = -1;
        try{
            getType.setString(1, accessory);
            ResultSet rs = getType.executeQuery();
            if(rs.next()){
                rs.close();
                typeId = rs.getInt("type_id");
            }else {
                createType.setString(1, accessory);
                createType.executeUpdate();
                rs = getType.executeQuery();
                if (!rs.next()) {
                    return -1;
                }
                typeId = rs.getInt("type_id");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        cache.put(accessory, typeId);
        return typeId;
    }

    @Override
    public void close(Connection con) {
        closeStatements();
    }
}
