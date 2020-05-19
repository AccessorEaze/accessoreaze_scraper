package me.accessoreaze.scraper.scapers.jbhifi;

import me.accessoreaze.scraper.accessory.type.AccessoryType;
import org.junit.Test;

import static org.junit.Assert.*;

public class JBHIFIHeadPhonesTest {

    @Test
    public void getScrapeURL() {
        JBHIFIHeadPhones test = new JBHIFIHeadPhones();

        assertNotEquals(null, test.getScrapeURL());
    }

    @Test
    public void getBaseURL() {
        JBHIFIHeadPhones test = new JBHIFIHeadPhones();

        assertEquals("https://www.jbhifi.co.nz", test.getBaseURL());
    }

    @Test
    public void getType() {
        JBHIFIHeadPhones test = new JBHIFIHeadPhones();

        assertEquals(AccessoryType.HEADPHONES, test.getType());
    }
}