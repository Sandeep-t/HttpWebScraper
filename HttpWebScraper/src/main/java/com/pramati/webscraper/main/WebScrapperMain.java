package com.pramati.webscraper.main;

import java.util.concurrent.Future;

import org.apache.log4j.Logger;

import com.pramati.webscraper.client.WebScrapper;
import com.pramati.webscraper.client.impl.Response;
import com.pramati.webscraper.executors.ThreadExecutor;
import com.pramati.webscraper.utils.ResponsePooler;

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
		if(LOGGER.isDebugEnabled()){
		LOGGER.debug("Web Scrapper Starting....");
		}
		String urlOfMainPage = "http://www.mail-archive.com/cassandra-user@incubator.apache.org/maillist.html";
		//Parent URL from which the system is going to get the Data.
		ResponsePooler pooler= new ResponsePooler();
		
		ThreadExecutor.getInstance().executeTask(pooler);
		
		final Future<Response> htmlResponse=webScrapper.getMainPageResponse(urlOfMainPage);
		
		final String htmlData=webScrapper.readResponseForHtmlData(htmlResponse);
		
		urlOfMainPage=urlOfMainPage.substring(0, urlOfMainPage.lastIndexOf('/')); 
		
		webScrapper.getWebLinksListFromHtml(htmlData,urlOfMainPage);
		
		
		
	/*	final List<Future<Response>> responseList=webScrapper.getResonseListForWebLinks(webLinks,urlOfMainPage);
		 int loopCounter=0;
		 for(final Future<Response> resp:responseList){
			 loopCounter++;
			final Response response = resp.get();
			final InputStream body = response.getBody();
			webScrapper.writeStreamToFile(body, FILEDUMPDIR+loopCounter+".html");
		 }*/
	
	}
	
	
	

	
	
	

}
