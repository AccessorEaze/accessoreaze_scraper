package me.accessoreaze.scraper;

import me.accessoreaze.scraper.accessory.Accessory;
import me.accessoreaze.scraper.database.databases.AccessoryDatabase;
import me.accessoreaze.scraper.database.databases.AccessoryTypeDatabase;
import me.accessoreaze.scraper.database.databases.PhoneDatabase;
import me.accessoreaze.scraper.database.mysql.MySQLDatabase;
import me.accessoreaze.scraper.database.mysql.MySQLProperties;
import me.accessoreaze.scraper.scapers.JBHIFI.JBHIFIHeadPhones;
import me.accessoreaze.scraper.scapers.JBHIFI.JBHIFIPhoneCase;
import me.accessoreaze.scraper.scapers.JBHIFI.JBHIFIScreenProtector;
import me.accessoreaze.scraper.scapers.Scraper;
import me.accessoreaze.scraper.scapers.pbTech.PBTechPhoneCase;
import me.accessoreaze.scraper.scapers.pbTech.PBTechScreenProtectedScraper;
import me.accessoreaze.scraper.scapers.phone.Phone;
import me.accessoreaze.scraper.scapers.phone.PhoneListScraper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.*;

import static java.lang.Class.forName;

public class ScraperMain {

    public static DecimalFormat df = new DecimalFormat("0.00");
    private static MySQLDatabase database;

    public static void main(String[] args) {
        new ScraperMain();
    }

    public ScraperMain(){
        saveResource(new File("."), "mysql.properties", false);
        MySQLProperties properties = new MySQLProperties(new File(".", "mysql.properties"));
        database = new MySQLDatabase(properties, true);
        database.addListener(AccessoryDatabase.getInstance());
        database.addListener(AccessoryTypeDatabase.getInstance());
        database.addListener(PhoneDatabase.getInstance());

        List<Scraper> scrapers = new ArrayList<>();

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
        List<Accessory> accessories = new ArrayList<>();
        scrapers.forEach(scaper -> accessories.addAll(runScraper(scaper)));

        AccessoryDatabase accessoryDatabase = AccessoryDatabase.getInstance();
        accessories.forEach(accessoryDatabase::insertAccessory);
        accessoryDatabase.transferTable();

        List<Phone> phones = PhoneListScraper.getInstance().scrape();
        PhoneDatabase phoneDatabase = PhoneDatabase.getInstance();
        phones.forEach(phoneDatabase::insertPhone);
        phoneDatabase.transferTable();
    }

    public static void test(Scraper scraper) {
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

    private static Set<Accessory> runScraper(Scraper scraper) {
        // Create collection of accessories
        Set<Accessory> accessoriesAll = new LinkedHashSet<>();
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
                Collection<Accessory> pageAccessories = scraper.getAccessories(pageDocument);
                // Add to main collection
                accessoriesAll.addAll(pageAccessories);
//                System.out.println(accessoriesAll.size());
            }
            // TODO Add to db

            String tableName = "";
            Accessory first = accessoriesAll.iterator().next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accessoriesAll;
    }

    public static Document connect(String url) {
        try {
            return Jsoup.connect(url).maxBodySize(120000000).execute().bufferUp().parse();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public InputStream getResource(String filename){
        if(filename == null){ throw new IllegalArgumentException("Filename cannot be null"); }

        try{
            URL url = this.getClass().getClassLoader().getResource(filename);

            if(url == null){ return null; }

            URLConnection connection = url.openConnection();
            connection.setUseCaches(false);
            return connection.getInputStream();
        }catch(IOException ex){
            return null;
        }
    }

    public void saveResource(File folder, String resourcePath, boolean replace){
        if((resourcePath == null) || (resourcePath.equals(""))){ throw new IllegalArgumentException("ResourcePath cannot be null or empty"); }
        resourcePath = resourcePath.replace('\\', '/');
        InputStream in = getResource(resourcePath);
        if(in == null){
            System.out.println("The embedded resource '" + resourcePath + "' cannot be found");
            return;
        }
        File outFile = new File(folder, resourcePath);
        int lastIndex = resourcePath.lastIndexOf('/');
        File outDir = new File(folder, resourcePath.substring(0, lastIndex >= 0 ? lastIndex : 0));
        if(!outDir.exists()){
            outDir.mkdirs();
        }
        try{
            if(!outFile.exists() || replace){
                OutputStream out = new FileOutputStream(outFile);
                byte[] buf = new byte[1024];
                int len;
                while((len = in.read(buf)) > 0){
                    out.write(buf, 0, len);
                }
                out.close();
                in.close();
            }else{
                System.out.print("Could not save " + outFile.getName() + " to " + outFile + " because " + outFile.getName() + " already exists.");
            }
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
}
