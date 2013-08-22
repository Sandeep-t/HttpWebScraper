
package com.pramati.webscraper.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;

import com.pramati.webscraper.client.WebScrapper;
import com.pramati.webscraper.client.impl.Response;

/**
 * @author sandeep-t
 * 
 */
public class ResponsePooler implements Runnable {

	private static final Logger LOGGER = Logger.getLogger(ResponsePooler.class);
	
	@Override
	public void run() {
		List<Future<Response>> futureList=new ArrayList<Future<Response>>();
		int i = 0;
		while (true) {
			
			if (WebScrapper.childFutureList.size() > 0) {
				if (futureList.size() > 0) {
					WebScrapper.childFutureList.removeAll(futureList);
					futureList.clear();
					if (WebScrapper.childFutureList.size() > 0) {
						futureList.addAll(WebScrapper.childFutureList);
					}
					
				} else {
					futureList.addAll(WebScrapper.childFutureList);
				}
				if (futureList.size() > 0) {

					for (Future<Response> future : futureList) {
						i++;
						Response response = null;
						try {
							response = future.get();
						} catch (InterruptedException e) {
							LOGGER.error(
									"InterruptedException occured while processing the Request ",
									e);

						} catch (ExecutionException e) {
							LOGGER.error(
									"ExecutionException occured while processing the Request ",
									e);

						}
						FileWriter writer = new FileWriter(response, "D:/Test/"+ i + ".html");
						WebScrapper.executor.execute(writer);

					}
				}

			}
		}

	}


	private int submitForWriting(List<Future<Response>> futureList, int i) {
		for (Future<Response> future : futureList) {
			i++;
			Response response = null;
			try {
				response = future.get();
			} catch (InterruptedException e) {
				LOGGER.error("InterruptedException occured while processing the Request ",e);

			} catch (ExecutionException e) {
				LOGGER.error("ExecutionException occured while processing the Request ",e);

			}
			FileWriter writer= new FileWriter(response,"D:/Test/"+i+".html");
			WebScrapper.executor.execute(writer);
			
		}
		return i;
	}

}
