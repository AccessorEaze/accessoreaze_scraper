package me.accessoreaze.scraper.scapers.jbhifi;

import me.accessoreaze.scraper.accessory.type.AccessoryType;
import org.junit.Test;

import static org.junit.Assert.*;

public class JBHIFIPhoneCaseTest {

    @Test
    public void getScrapeURL() {
        JBHIFIPhoneCase test = new JBHIFIPhoneCase();

        assertNotEquals(null, test.getScrapeURL());
    }

    @Test
    public void getBaseURL() {
        JBHIFIPhoneCase test = new JBHIFIPhoneCase();

        assertEquals("https://www.jbhifi.co.nz", test.getBaseURL());
    }

    @Test
    public void getType() {
        JBHIFIPhoneCase test = new JBHIFIPhoneCase();

        assertEquals(AccessoryType.PHONE_CASE, test.getType());

    }
}