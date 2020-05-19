package me.accessoreaze.scraper.scapers.jbhifi;

import me.accessoreaze.scraper.accessory.type.AccessoryType;
import org.junit.Test;

import static org.junit.Assert.*;

public class JBHIFIScreenProtectorTest {

    @Test
    public void getScrapeURL() {
        JBHIFIScreenProtector test = new JBHIFIScreenProtector();

        assertNotEquals(null, test.getScrapeURL());
    }

    @Test
    public void getBaseURL() {
        JBHIFIScreenProtector test = new JBHIFIScreenProtector();

        assertEquals("https://www.jbhifi.co.nz", test.getBaseURL());
    }

    @Test
    public void getType() {
        JBHIFIScreenProtector test = new JBHIFIScreenProtector();

        assertEquals(AccessoryType.SCREEN_PROTECTOR, test.getType());
    }
}