package me.accessoreaze.scraper.scapers.JBHIFI;

import me.accessoreaze.scraper.ScraperMain;
import me.accessoreaze.scraper.accessory.Accessory;
import me.accessoreaze.scraper.accessory.PhoneCase;
import me.accessoreaze.scraper.accessory.type.AccessoryType;
import me.accessoreaze.scraper.scapers.Scaper;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

public class JBHIFIPhoneCase implements Scaper {

    public static void main(String[] args) {
        ScraperMain.test(new JBHIFIPhoneCase());
    }

    // This is the first page to scrape, giving us the amount of pages
    @Override
    public String getScrapeURL() {
        return getBaseURL() + "/phones/phone-cases/";
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
        return AccessoryType.PHONE_CASE;
    }

    @Override
    public Collection<Accessory> getAccessories(Document document) {
        // Get the list of items
        Elements page = document.select(".grid.load-more-section .span03.product-tile");

        Set<Accessory> cases = new LinkedHashSet<>();

        for (Element e : page) {
            // name of the case
            String title = e.attr("title");

            if (!title.contains("for"))
            {
                continue;
            }

            String model = title.substring(title.indexOf("for")+4);

            String strPrice = e.select(".oldPriceWrapper").text();
            //remove everything except . and numbers from that string
            strPrice = strPrice.replaceAll("[^\\d.]", "");
            Double price = Double.parseDouble(strPrice);

            //removes colour of the case
            model = model.split("\\(")[0];

            String image = getBaseURL() + e.select(".image").first().getElementsByTag("img").first().attr("data-src");

            String caseUrl = getBaseURL() + e.select(".link").attr("href");

            cases.add(new PhoneCase(model, caseUrl, image, price));

        }

        for (Accessory acc : cases) {
            System.out.println(acc.toString());
        }
        return cases;
    }
}

