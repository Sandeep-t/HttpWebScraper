/**
 * 
 */
package com.pramati.webscraper.executors;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;

import com.pramati.webscraper.client.impl.Response;

/**
 * @author sandeep-t
 *
 */
public final class ThreadExecutor {

	 private static final Logger LOGGER =Logger.getLogger(ThreadExecutor.class);
	 
	 private static volatile ThreadExecutor executor;
	 
	 private ExecutorService executorService;
	// ThreadExecutor executor;

	private ThreadExecutor() {
		executorService =Executors.newFixedThreadPool(24);
	}

	public void executeTask(Runnable task) {
		//System.out.println("Q Size " + queue.size());
		executorService.execute(task);
	}
	
	public Future<Response> submitTask(Callable<Response> task) {
		//System.out.println("Q Size " + queue.size());
		return executorService.submit(task);
	}

	public void shutDownExecutor() {
		executorService.shutdown();
	}
	
	
	public static ThreadExecutor getInstance() {
		if (executor == null) {
			synchronized (ThreadExecutor.class) { // 1
				if (executor == null) { // 2
					executor = new ThreadExecutor(); // 3
				}
			}
		}
		return executor;
	}
	
	
	

}
