package me.accessoreaze.scraper.scapers.wareHouse;

import me.accessoreaze.scraper.ScraperMain;
import me.accessoreaze.scraper.accessory.Accessory;
import me.accessoreaze.scraper.accessory.PhoneCase;
import me.accessoreaze.scraper.accessory.type.AccessoryType;
import me.accessoreaze.scraper.scapers.Scraper;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

public class WareHousePhoneCase implements Scraper {

    public static void main(String[] args) {
        ScraperMain.test(new WareHousePhoneCase());
    }

    // This is the first page to scrape, giving us the amount of pages
    @Override
    public String getScrapeURL() {
        return getBaseURL() + "/c/electronics-gaming/tech-accessories/phone-accessories/phone-cases";
    }


    // Base url for the website, useful for links
    @Override
    public String getBaseURL() {
        return "https://www.thewarehouse.co.nz/";
    }

    @Override
    public Collection<String> getPages(Document document) {
        Set<String> pages = new LinkedHashSet<>();

//        System.out.println(document +"hi");
        int maxPages = 5;//Integer.parseInt(document.select(".ninety").attr("totalProducts"));
//        maxPages = (int)(Math.ceil((double)maxPages/30));
//        System.out.println(getScrapeURL());
        // For each number up to max
        for(int page = 1; page <= maxPages; page++){
            // Add each page to scrape
            pages.add(getScrapeURL() + "#start="+(page-1)*24+"&sz=24");
            pages.add(getScrapeURL());
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
        Elements page = document.select(".search-result-items .span6 ");

        Set<Accessory> cases = new LinkedHashSet<>();

        for (Element e : page) {
            // name of the case
            String title = e.select(".thumb-link").attr("title");

            String model = "";

            if (title.toLowerCase().contains("iphone"))
            {
                int temp = title.indexOf("iPhone");
//                System.out.println(temp);
//                System.out.println(title);
                model = title.substring(temp);
                model = model.split(" ")[0] + " " + model.split(" ")[1];
            }
            else  if (title.toLowerCase().contains("samsung"))
            {
                int temp = title.indexOf("Samsung");
                model = title.substring(temp);
                model = model.split(" ")[0] + " " + model.split(" ")[1]+" "+model.split(" ")[2];
            }
            else if (title.toLowerCase().contains("huawei"))
            {
                int temp = title.indexOf("Huawei");
                model = title.substring(temp);
                model = model.split(" ")[0] + " " + model.split(" ")[1];
            }
            else {
                continue;
            }

            String strPrice = e.select(".standardprice").text();
            Double price;

            //handling sale error
            try {
                //remove everything except . and numbers from that string
                strPrice = strPrice.replaceAll("[^\\d.]", "");
                price = Double.parseDouble(strPrice);
            } catch(NumberFormatException exc)
            {
//                strPrice = e.select(".oldPriceWrapper .amount.onSale").text();
//                strPrice = strPrice.replaceAll("[^\\d.]", "");
//                price = Double.parseDouble(strPrice);
                continue;
            }

//            String image = getBaseURL() + e.getElementsByTag("a").first().attr("href");
            String image = e.select(".thumb-link").first().getElementsByTag("img").attr("src");
//            String caseUrl = getBaseURL() + e.select(".link").attr("href");

            String url = e.select(".thumb-link").first().attr("href");

            cases.add(new PhoneCase(title, model, url,image, image, price,"WareHouse"));

        }

        for (Accessory acc : cases) {
            System.out.println(acc.toString());
        }
        return cases;
    }
}

