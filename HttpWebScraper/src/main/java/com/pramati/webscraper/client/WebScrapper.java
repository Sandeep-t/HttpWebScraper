/**
 * 
 */
package com.pramati.webscraper.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;

import com.pramati.webscraper.client.impl.Request;
import com.pramati.webscraper.client.impl.Response;
import com.pramati.webscraper.executors.ThreadExecutor;
import com.pramati.webscraper.utils.FileHelper;
import com.pramati.webscraper.utils.HTMLLinkExtractor;
import com.pramati.webscraper.utils.HtmlLink;

/**
 * @author sandeep-t Util Class for doing operations like url reading, url
 *         extraction and data extraction.
 * 
 */

public class WebScrapper {

	private static final Logger LOGGER = Logger.getLogger(WebScrapper.class);
	private final String fileLocation = "D:/Test/WebScrappedData.html";
	private final FileOutputStream out;

	public WebScrapper() throws FileNotFoundException {
		out = new FileOutputStream(fileLocation);
	}

	// private static ExecutorService executor =
	// Executors.newFixedThreadPool(20);
	// private static List<Future<Response>> childFutureList =
	// Collections.synchronizedList(new ArrayList<Future<Response>>());
	// private static Vector<Future<Response>> childFutureList = new
	// Vector<Future<Response>>();
	public BlockingQueue<Future<Response>> childFutureList = new LinkedBlockingQueue<Future<Response>>();

	/**
	 * This method will take html data in form of String and will return a list
	 * of weblinks embedded in thathtml data
	 * 
	 * @param htmlData
	 * @return list of webaddress
	 */
	public void processWeblinksinPageData(String htmlData, String webLink) {
		/**
		 * Replace all one or more space characters with " "
		 * 
		 */
		htmlData.replaceAll("\\s+", " ");
		final HTMLLinkExtractor extractor = new HTMLLinkExtractor();
		final List<HtmlLink> links = extractor.grabHTMLLinks(htmlData);
		for (HtmlLink link : links) {
			StringBuilder stbr = new StringBuilder();
			stbr.append(webLink).append("/").append(link.getLink());
			LOGGER.debug("Processing the child URL " + stbr);
			try {
				final Future<Response> response = getFutureAsResponse(stbr
						.toString());
				childFutureList.add(response);
			} catch (MalformedURLException e1) {
				LOGGER.error(
						"MalformedURLException occured while parsing the URL "
								+ stbr.toString(), e1);

			}
		}
	}

	/**
	 * This function serves the purpose of hitting the the given URL and get the
	 * response out of it. If in case any exception occurs the exception will be
	 * thrown up and the system is going to halt.
	 * 
	 * @param urlOfMainPage
	 * @return Future as the response
	 * @throws Exception
	 */
	public Future<Response> getFutureAsResponse(final String urlString)
			throws MalformedURLException {
		try {
			LOGGER.debug("Processing url " + urlString);
			final URL url = new URL(urlString);
			Request task = new Request(url);
			return ThreadExecutor.getInstance().submitTask(task);
		} catch (MalformedURLException e1) {
			LOGGER.error("MalformedURLException occured while parsing the URL "
					+ urlString, e1);
			throw new MalformedURLException(
					"MalformedURLException occured while parsing the URL "
							+ urlString);
		}
	}

	/**
	 * 
	 * This fuction will be used to get the html response after parsing the data
	 * from future passed to the function.
	 * 
	 * @param responseList
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 * @throws IOException
	 */
	public String getPageData(final InputStream body)
			throws InterruptedException, ExecutionException, IOException {

		final BufferedReader responseBuffer = new BufferedReader(
				new InputStreamReader(body));

		String output;

		StringBuilder fileContent = new StringBuilder();

		try {
			while ((output = responseBuffer.readLine()) != null) {
				fileContent.append(output);
			}
		} catch (IOException e) {
			LOGGER.error("Exception occured while reading data from Response",
					e);
			throw new IOException(
					"Exception occured while reading data from Response", e);

		} finally {
			responseBuffer.close();
		}

		return fileContent.toString();
	}

	/**
	 * A pooler that will keep pooling for the data in childFutureList queue and
	 * will write the data received into a file.
	 */
	public void stratResponsePooler() {

		final FileHelper writer = new FileHelper();
		Runnable pooler = new Runnable() {

			@Override
			public void run() {

				while (true) {

					Future<Response> future;
					///Long current= System.currentTimeMillis();
					while ((future = childFutureList.poll()) != null) {
						
						//LOGGER.debug("Start Time "+sys);

						Response response = null;
						try {
							response = future.get();
							LOGGER.debug("Processing the response of the URL"
									+ response.getUrl());
						} catch (InterruptedException e) {

							catchExceptions(response, e);

						} catch (ExecutionException e) {

							catchExceptions(response, e);

						} catch (IOException e) {

							catchExceptions(response, e);
						}

						InputStream stream = response.getBody();
						FileLock lock = null;
						try {
							lock = out.getChannel().lock();
							writer.writeToFile(stream, out);
						} catch (IOException cause) {
							try {
								LOGGER.error(
										"IOException occred while writing file for the URL "
												+ response.getUrl(), cause);
							} catch (IOException ioe) {
								LOGGER.error(
										"Exception occured while processing ",
										ioe);
							}
						} finally {
							try {
								lock.release();
							} catch (IOException cause) {
								try {
									LOGGER.error(
											"Exception occured while releasing the lock, writing file for "
													+ response.getUrl(), cause);
								} catch (IOException ioe) {
									LOGGER.error(
											"Exception occured while processing ",
											ioe);
									ioe.printStackTrace();
								}

							}
						}

					}
					//LOGGER.debug("Toatal time  "+ (System.currentTimeMillis()-current));

				}
			}

			private void catchExceptions(Response response, Exception e) {
				if (e instanceof InterruptedException) {
					try {
						LOGGER.error(
								"InterruptedException occured while processing the Request "
										+ response.getUrl() + "  "
										+ "with status code "
										+ response.getResponseCode(), e);
					} catch (IOException ioe) {
						LOGGER.error(
								"InterruptedException occured while processing the Request ",
								ioe);
						// ioe.printStackTrace();
					}
				}
				if (e instanceof ExecutionException) {
					try {
						LOGGER.error(
								"ExecutionException occured while processing the Request "
										+ response.getUrl() + "  "
										+ "with status code "
										+ response.getResponseCode(), e);
					} catch (IOException ioe) {
						LOGGER.error(
								"ExecutionException occured while processing the Request ",
								ioe);
						// ioe.printStackTrace();
					}
				}

				if (e instanceof IOException) {

					try {
						LOGGER.error(
								"IOException occured while processing the Request "
										+ response.getUrl() + "  "
										+ "with status code "
										+ response.getResponseCode(), e);
					} catch (IOException ioe) {
						LOGGER.error(
								"IOException occured while processing the Request ",
								ioe);
						// ioe.printStackTrace();
					}

				}

			}
		};
		ThreadExecutor.getInstance().executeTask(pooler);
	}

}
