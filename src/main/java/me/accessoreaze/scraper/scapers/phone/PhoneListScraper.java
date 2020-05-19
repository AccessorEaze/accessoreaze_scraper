package me.accessoreaze.scraper.scapers.phone;

import me.accessoreaze.scraper.ScraperMain;
import me.accessoreaze.scraper.accessory.Accessory;
import me.accessoreaze.scraper.accessory.type.AccessoryType;
import me.accessoreaze.scraper.scapers.Scraper;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PhoneListScraper {

    private static PhoneListScraper phoneListScraper = new PhoneListScraper();

    public static PhoneListScraper getInstance(){
        return phoneListScraper;
    }

    private PhoneListScraper(){

    }

    public static void main(String[] args) {
        phoneListScraper.scrape();
    }

    public List<Phone> scrape(){
        List<Phone> phones = new ArrayList<>();
        try{
            Document document = ScraperMain.connect("https://storage.googleapis.com/play_public/supported_devices.html");
            Element element = document.getElementsByTag("table").first();
            for (Element tr : element.getElementsByTag("tr")) {
                Elements td = tr.getElementsByTag("td");
                if(td.size()==4) {
                    if(td.get(0).text().isEmpty() || td.get(1).text().isEmpty()){
                        continue;
                    }
                    phones.add(new Phone(td.get(0).text() + " " + td.get(1).text(), td.get(3).text()));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return phones;
    }


}
