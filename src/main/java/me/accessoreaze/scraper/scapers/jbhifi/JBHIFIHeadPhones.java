package me.accessoreaze.scraper.scapers.jbhifi;

import me.accessoreaze.scraper.ScraperMain;
import me.accessoreaze.scraper.accessory.Accessory;
import me.accessoreaze.scraper.accessory.HeadPhones;
import me.accessoreaze.scraper.accessory.type.AccessoryType;
import me.accessoreaze.scraper.scapers.Scraper;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

public class JBHIFIHeadPhones implements Scraper {

    public static void main(String[] args) {
        ScraperMain.test(new JBHIFIPhoneCase());
    }

    // This is the first page to scrape, giving us the amount of pages
    @Override
    public String getScrapeURL() {
        return getBaseURL() + "/headphones-speakers-audio/headphones/";
    }


    // Base url for the website, useful for links
    @Override
    public String getBaseURL() {
        return "https://www.jbhifi.co.nz";
    }

    @Override
    public Collection<String> getPages(Document document) {
        Set<String> pages = new LinkedHashSet<>();

        int maxPages = Integer.parseInt(document.select(".message.bp.bp3.bp4 .total").first().text());
        maxPages = (int)(Math.ceil((double)maxPages/36));
        // For each number up to max
        for(int page = 1; page <= maxPages; page++){
            // Add each page to scrape
            pages.add(getScrapeURL() + "?p=" + page);
        }

        // return pages
        return pages;
    }

    // Accessory type
    @Override
    public AccessoryType getType() {
        return AccessoryType.HEADPHONES;
    }

    @Override
    public Collection<Accessory> getAccessories(Document document) {
        // Get the list of items
        Elements page = document.select(".grid.load-more-section .span03.product-tile");

               Set<Accessory> headphones = new LinkedHashSet<>();

        for (Element e : page) {
            // name of the case
            String title = e.attr("title");
//            if (!title.contains("earphone") && !title.contains("headphone") && !title.contains("pod"))
//            {
//                continue;
//            }

            String model;

            if (title.toLowerCase().contains("wireless") || title.toLowerCase().contains("bud"))
            {
                 model = "Wireless";
            }
            else
            {
                model = "3.5mm Jack" ;
            }

            String strPrice = e.select(".oldPriceWrapper").text();
            //remove everything except . and numbers from that string
//            System.out.println(strPrice);

            Double price;

            //handling sale error
            try {
                strPrice = strPrice.replaceAll("[^\\d.]", "");
                 price = Double.parseDouble(strPrice);
            } catch(NumberFormatException exc)
            {
                strPrice = e.select(".oldPriceWrapper .amount.onSale").text();
                strPrice = strPrice.replaceAll("[^\\d.]", "");
                 price = Double.parseDouble(strPrice);
            }

            //removes colour of the case
//            model = model.split("\\(")[0];

            String image = getBaseURL() + e.select(".image").first().getElementsByTag("img").first().attr("data-src");

            String caseUrl = getBaseURL() + e.select(".link").attr("href");

            headphones.add(new HeadPhones(title, model, caseUrl,image, image, price));

        }

        for (Accessory acc : headphones) {
            System.out.println(acc.toString());
        }
        return headphones;
    }
}
