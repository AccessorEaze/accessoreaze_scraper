package me.accessoreaze.scraper.scapers;

import me.accessoreaze.scraper.accessory.Accessory;
import me.accessoreaze.scraper.accessory.type.AccessoryType;
import org.jsoup.nodes.Document;

import java.util.Collection;

public interface Scraper {

    String getScrapeURL();

    String getBaseURL();

    Collection<String> getPages(Document document);

    AccessoryType getType();

    Collection<Accessory> getAccessories(Document document);

}
