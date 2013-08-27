
package com.pramati.webscraper.utils;

import java.io.InputStream;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;

import com.pramati.webscraper.client.WebScrapper;
import com.pramati.webscraper.client.impl.Response;
import com.pramati.webscraper.executors.ThreadExecutor;

/**
 * @author sandeep-t
 * 
 * This class will act as pooler for the Future Rresponselist that
 * will be returned by the individual weblinks.This pooler will keep on pooling for the list and
 * will start processing the list as and when a new data is added to it. 
 * 
 */
/*public class ResponsePooler implements Runnable {

	private static final Logger LOGGER = Logger.getLogger(ResponsePooler.class);
	private final String fileLocation = "D:/Test/";
	//private final List<Future<Response>> futureList=new ArrayList<Future<Response>>();
	@Override
	public void run() {
		int loopCounter = 0;

		while (true) {
			
			Future<Response> future;

			while ((future = WebScrapper.childFutureList.poll()) != null) {
				loopCounter++;
				Response response = null;
				try {
					response = future.get();
				} catch (InterruptedException e) {
					// status code
					// url
					LOGGER.error(
							"InterruptedException occured while processing the Request ",
							e);
				} catch (ExecutionException e) {
					// status code
					// url
					LOGGER.error(
							"ExecutionException occured while processing the Request ",
							e);
				}
				InputStream stream=response.getBody();
				FileWriter writer = new FileWriter(stream, fileLocation	+ "Test" + ".html");
				ThreadExecutor.getInstance().executeTask(writer);
			}
			
			// do other stuff
		}
	}
		
	}*/
	
	/*@Override
	public void run() {
		
		int loopCounter = 0;
		
		while (true) {
			
			if (WebScrapper.childFutureList.size()!=0) {
				if (futureList.size()!=0) {
					WebScrapper.childFutureList.removeAll(futureList);
					futureList.clear();
					if (WebScrapper.childFutureList.size()!=0) {
						futureList.addAll(WebScrapper.childFutureList);
					}
					
				} else {
					futureList.addAll(WebScrapper.childFutureList);
				}
				if (futureList.size() > 0) {

					for (Future<Response> future : futureList) {
						loopCounter++;
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
						
						FileWriter writer = new FileWriter(response, fileLocation+ loopCounter + ".html");
						WebScrapper.executor.execute(writer);

					}
				}

			}
		}

	} 
*/

	/*private int submitForWriting(List<Future<Response>> futureList, int i) {
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
*/

