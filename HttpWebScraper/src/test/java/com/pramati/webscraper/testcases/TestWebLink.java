package com.pramati.webscraper.testcases;
import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.pramati.webscraper.client.WebScrapper;
import com.pramati.webscraper.client.impl.Response;

/**
 * 
 */

/**
 * @author sandeep-t
 * 
 */
public class TestWebLink {
	
	private static final Logger LOGGER = Logger.getLogger(TestWebLink.class);
	
	private static final String URL_OF_MAINPAGE = "http://localhost:8080/TestConsumer/DataConsumer";

	@Test
	public void testIfurlIsCorrect() throws FileNotFoundException {
		final WebScrapper webScrapper = new WebScrapper();
		Future<Response> htmlResponse = null;
		try {
			htmlResponse = webScrapper.getFutureAsResponse(URL_OF_MAINPAGE);
		} catch (Exception e) {
			LOGGER.error("Exception occured with the url "+URL_OF_MAINPAGE,e);
			htmlResponse = null;
		}
		assertEquals("This test passed so URL seems to be OK ",	htmlResponse != null, true);
	}
	
	
	
	

}
