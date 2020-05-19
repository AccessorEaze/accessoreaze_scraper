package me.accessoreaze.scraper.scapers.pbTech;

import me.accessoreaze.scraper.ScraperMain;
import me.accessoreaze.scraper.accessory.Accessory;
import me.accessoreaze.scraper.accessory.ScreenProtector;
import me.accessoreaze.scraper.accessory.type.AccessoryType;
import me.accessoreaze.scraper.scapers.Scraper;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

public class PBTechScreenProtectedScraper implements Scraper {

    public static void main(String[] args) {
        ScraperMain.test(new PBTechScreenProtectedScraper());
    }

    // This is the first page to scrape, giving us the amount of pages
    @Override
    public String getScrapeURL() {
        return getBaseURL() + "category/phones-gps/phone-screen-protectors/shop-all";
    }

    // Base url for the website, useful for links
    @Override
    public String getBaseURL() {
        return "https://www.pbtech.co.nz/";
    }

    @Override
    public Collection<String> getPages(Document document) {
        Set<String> pages = new LinkedHashSet<>();
        //<ul class="pagination">
        //contains our buttons
        Element pagination = document.getElementsByClass("pagination").first();

        // <b>5</b>
        // Each number is in a b tag
        Elements buttons = pagination.getElementsByTag("b");

        // Currently page 1
        int max = 1;
        // For each of the buttons
        for (Element button : buttons) {
            try{
                // Set the max to the maximum
                // eg if max is 1 and the button is 5, the max value will now be 5
                // but it the max is 3 and button is 1, then the max will stay 3
               max = Math.max(max, Integer.parseInt(button.text()));
            }catch (NumberFormatException e){

            }
        }

        // For each number up to max
        for(int page = 1; page <= max; page++){
            // Add each page to scrape
            pages.add(getScrapeURL() + "?pg=" + page);
        }

        // return pages
        return pages;
    }

    // Accessory type
    @Override
    public AccessoryType getType() {
        return AccessoryType.SCREEN_PROTECTOR;
    }

    @Override
    public Collection<Accessory> getAccessories(Document document) {
        Set<Accessory> accessories = new LinkedHashSet<>();
        // Get the list of items
        Element list_wrapper = document.getElementsByClass("products_list_wrapper").first();
        // If doesn't exist return
        if(list_wrapper == null){
            return accessories;
        }
        // Each element contains an item
        Elements items = list_wrapper.getElementsByClass("item");

        // For each item
        for (Element item : items) {
            // Get the the name
            String name = item.getElementsByClass("item_line_name").first().attr("title");
            String itemUrl = getBaseURL()+item.getElementsByClass("item_line_name").first().attr("href");


            // Model is in the more section
            Element more = item.getElementsByClass("item_more").first();
            Element modelSearch = more.getElementsByClass("item_attribute").last();
            if(modelSearch == null || !modelSearch.toString().contains("Compatible Model: ")){
                continue;
            }
            String model = modelSearch.getElementsByClass("attr_value").first().text();

            // Get images
            Element imageArea = item.getElementsByClass("item_image").first();
            String image = "";
            String imageBig = "";

            // Image given: https://www.pbtech.co.nz/thumbs/M/P/MPPSGP24527.jpg.large.jpg?h=3342544701
            // Needed: https://www.pbtech.co.nz/imgprod/M/P/MPPSGP24527__1.jpg?h=2956730283
            if(imageArea != null){
                image = imageArea.getElementsByTag("img").last().attr("src");
                imageBig = "https://www.pbtech.co.nz/imgprod/M/P/" + image.split("thumbs/M/P/")[1].split(".large")[0];
            }

            Element priceArea = item.getElementsByClass("price_full").first();
//            System.out.println(priceArea.toString());
            double price = 0d;
            if(priceArea != null){
                try{
                    price = Double.parseDouble(priceArea.text().replaceAll("\\$", ""));
                }catch (NumberFormatException e){

                }
            }

            //if price is 0 it means the item is out of stock, so continue
            if (price == 0)
            {
                continue;
            }

            price = price*1.15;
            price = Double.parseDouble(ScraperMain.df.format(price));

            System.out.println(model + ": " + imageBig + " : " + Math.floor(price*1.15+1));
//            System.out.printf("%s: %s : %.3f",model,imageBig,price*1.15);
            accessories.add(new ScreenProtector(name, model, itemUrl, image, imageBig, price, "PBTech"));
        }
        return accessories;
    }
}
