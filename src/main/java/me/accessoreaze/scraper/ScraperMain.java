package me.accessoreaze.scraper;

import me.accessoreaze.scraper.accessory.Accessory;
import me.accessoreaze.scraper.scapers.JBHIFI.JBHIFIPhoneCase;
import me.accessoreaze.scraper.scapers.Scaper;
import me.accessoreaze.scraper.scapers.pbTech.PBTechScreenProtectedScraper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

public class ScraperMain {

    public static DecimalFormat df = new DecimalFormat("0.00");

    public static void main(String[] args) {
        List<Scaper> scrapers = new ArrayList<>();

        // Add more scrapers here, group them
//        scrapers.add(new PBTechScreenProtectedScraper());
//        scrapers.add(new PBTechPhoneCase());
        scrapers.add(new JBHIFIPhoneCase());
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
                System.out.println(accessoriesAll.size());
            }
            // TODO Add to db
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
