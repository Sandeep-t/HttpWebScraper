/**
 * 
 */
package com.pramati.webscraper.utils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

import com.pramati.webscraper.client.impl.Response;

/**
 * @author sandeep-t
 *
 */
public class WebScraperResponseQueue {
	
	private BlockingQueue<Future<Response>> childFutureList = new LinkedBlockingQueue<Future<Response>>();
	
	

}
