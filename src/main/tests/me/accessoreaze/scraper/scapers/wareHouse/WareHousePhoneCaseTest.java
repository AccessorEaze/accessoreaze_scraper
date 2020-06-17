package me.accessoreaze.scraper.scapers.wareHouse;

import me.accessoreaze.scraper.accessory.type.AccessoryType;
import me.accessoreaze.scraper.scapers.jbhifi.JBHIFIHeadPhones;
import org.junit.Test;

import static org.junit.Assert.*;

public class WareHousePhoneCaseTest {

    @Test
    public void getScrapeURL() {
        WareHousePhoneCase test = new WareHousePhoneCase();

        assertNotEquals(null, test.getScrapeURL());
    }

    @Test
    public void getBaseURL() {
        WareHousePhoneCase test = new WareHousePhoneCase();

        assertEquals("https://www.thewarehouse.co.nz", test.getBaseURL());
    }

    @Test
    public void getType() {
        WareHousePhoneCase test = new WareHousePhoneCase();

        assertEquals(AccessoryType.PHONE_CASE, test.getType());
    }
}