package com.pramati.webscraper.main;

import java.io.InputStream;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.log4j.Logger;

import com.pramati.webscraper.client.WebScrapper;
import com.pramati.webscraper.client.impl.Response;
import com.pramati.webscraper.executors.ThreadExecutor;

/**
 * Main method for webScrapper to start.
 * 
**/
public final class WebScrapperMain {
	 
	private static final Logger LOGGER = Logger.getLogger(WebScrapperMain.class);
	
	private WebScrapperMain() {
		super();
		
	}

	public static void main(String[] args) throws Exception { 
		
		final WebScrapper webScrapper= new WebScrapper();
	
		
		String urlOfMainPage = "http://www.mail-archive.com/cassandra-user@incubator.apache.org/maillist.html";
		
		if(LOGGER.isDebugEnabled()){
		
			LOGGER.debug("Processing Url  "+urlOfMainPage);
		}
		
		webScrapper.stratResponsePooler();
		
		final Future<Response> futureResponse=webScrapper.getFutureAsResponse(urlOfMainPage);
		
		InputStream responseStream= futureResponse.get().getBody();
		
		final String pageData=webScrapper.getPageData(responseStream);
		
		webScrapper.processWeblinksinPageData(pageData,urlOfMainPage.substring(0, urlOfMainPage.lastIndexOf('/')));
		
		ThreadExecutor.getInstance().shutDownExecutor();
		
	}	

}
