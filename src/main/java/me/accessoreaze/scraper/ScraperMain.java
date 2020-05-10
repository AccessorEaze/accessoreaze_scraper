package me.accessoreaze.scraper;

import me.accessoreaze.scraper.accessory.Accessory;
import me.accessoreaze.scraper.accessory.type.AccessoryType;
import me.accessoreaze.scraper.database.JDBC;
import me.accessoreaze.scraper.scapers.JBHIFI.JBHIFIHeadPhones;
import me.accessoreaze.scraper.scapers.JBHIFI.JBHIFIPhoneCase;
import me.accessoreaze.scraper.scapers.JBHIFI.JBHIFIScreenProtector;
import me.accessoreaze.scraper.scapers.Scaper;
import me.accessoreaze.scraper.scapers.pbTech.PBTechPhoneCase;
import me.accessoreaze.scraper.scapers.pbTech.PBTechScreenProtectedScraper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.*;

import static java.lang.Class.forName;

public class ScraperMain {

    public static DecimalFormat df = new DecimalFormat("0.00");
    private static JDBC db = new JDBC();

    public static void main(String[] args) {

        List<Scaper> scrapers = new ArrayList<>();

        // Add more scrapers here, group them
        scrapers.add(new PBTechScreenProtectedScraper());
        scrapers.add(new PBTechPhoneCase());
        scrapers.add(new JBHIFIScreenProtector());
        scrapers.add(new JBHIFIPhoneCase());
            scrapers.add(new JBHIFIHeadPhones());
        // Run each scraper
        //for (Scaper scraper : scrapers) {
        //    runScraper(scraper);
        //}


        // Using java 8 achieves the same result
        scrapers.forEach(ScraperMain::runScraper);
    }

    public static void test(Scaper scraper) {
        try {
            // Connect to main page
            Document website = connect(scraper.getScrapeURL());
            // Obtain list of pages
            Collection<String> pages = scraper.getPages(website);

            // For each page
            for (String page : pages) {
                // Connect to page
                Document pageDocument = connect(page);
                // Obtain collection of accessories
                scraper.getAccessories(pageDocument);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void runScraper(Scaper scraper) {
        try {
            // Connect to main page
            Document website = connect(scraper.getScrapeURL());
            // Obtain list of pages
            Collection<String> pages = scraper.getPages(website);

            // Create collection of accessories
            Set<Accessory> accessoriesAll = new LinkedHashSet<>();

            // For each page
            for (String page : pages) {
                // Connect to page
                Document pageDocument = connect(page);
                // Obtain collection of accessories
                Collection<Accessory> pageAccessories = scraper.getAccessories(pageDocument);
                // Add to main collection
                accessoriesAll.addAll(pageAccessories);
//                System.out.println(accessoriesAll.size());
            }
            // TODO Add to db

            String tableName = "";
            Accessory first = accessoriesAll.iterator().next();

            if (first.getAccessoryType() == AccessoryType.PHONE_CASE)
            {
                tableName = JDBC.phoneCaseTable;
            }
            else if (first.getAccessoryType() == AccessoryType.SCREEN_PROTECTOR)
            {
                tableName = JDBC.screenProtectorTable;
            }
            else if (first.getAccessoryType() == AccessoryType.HEADPHONES)
            {
                tableName = JDBC.headPhonesTable;
            }

            db.updateTable(tableName, accessoriesAll);


            /* System.out.println(accessoriesAll.toString());
            Class.forName("com.mysql.cj.jdbc.Driver");

            String url = "jdbc:mysql://localhost/testscraper";

            Connection conn = DriverManager.getConnection(url,"root","");

            System.out.println("Connected");

            Statement state = conn.createStatement();

            state.executeUpdate("drop table if exists PHONE_CASES");

            state.executeUpdate("CREATE TABLE PHONE_CASES " +
                    "(PRICE FLOAT, " +
                    "NAME VARCHAR(1500), " +
                    "MODEL VARCHAR(100), " +
                    "URL VARCHAR(500), "+
                    "PICTURE VARCHAR(500))");


            Connection conn = new Connection*/

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Document connect(String url) {
        try {
            return Jsoup.connect(url).execute().bufferUp().parse();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
