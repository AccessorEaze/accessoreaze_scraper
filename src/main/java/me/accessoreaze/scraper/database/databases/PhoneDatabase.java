package me.accessoreaze.scraper.database.databases;

import me.accessoreaze.scraper.accessory.Accessory;
import me.accessoreaze.scraper.database.mysql.listener.SQLListener;
import me.accessoreaze.scraper.scapers.phone.Phone;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class PhoneDatabase extends SQLListener {

    private static PhoneDatabase accessoryDatabase = new PhoneDatabase();

    public static PhoneDatabase getInstance(){
        return accessoryDatabase;
    }

    private PhoneDatabase(){

    }

    private static PreparedStatement insertPhone;

    @Override
    public void setup(Connection con) {
        try {
            con.prepareStatement("CREATE TABLE IF NOT EXISTS `phones_temp`  (`id` INT NOT NULL AUTO_INCREMENT,  `name` TINYTEXT NOT NULL, `model` TINYTEXT NOT NULL, PRIMARY KEY (`id`));").executeUpdate();

            insertPhone = con.prepareStatement("INSERT INTO `phones_temp` (`name`, `model`) VALUES (?,?);");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertPhone(Phone phone){
        try{
            insertPhone.setString(1, phone.getName());
            insertPhone.setString(2, phone.getModel());
            insertPhone.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public  void transferTable(){
        try{
            ensureConnection();
            Connection connection = getDatabase().getConnection();
            connection.prepareStatement("DROP TABLE `phones`;").execute();
            connection.prepareStatement("RENAME TABLE `phones_temp` TO `phones`;").execute();
            connection.prepareStatement("DROP TABLE `phones_temp`;");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void close(Connection con) {
        closeStatements();
    }
}
