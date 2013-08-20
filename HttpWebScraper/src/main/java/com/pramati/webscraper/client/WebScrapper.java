/**
 * 
 */
package com.pramati.webscraper.client;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;

import com.pramati.webscraper.client.impl.Request;
import com.pramati.webscraper.client.impl.Response;
import com.pramati.webscraper.utils.HTMLLinkExtractor;
import com.pramati.webscraper.utils.HtmlLink;

/**
 * @author sandeep-t
 * 
 */
public class WebScrapper {

	private static final Logger LOGGER = Logger.getLogger(WebScrapper.class);
	private static final  int BUFFERSIZE = 1024;

	/*
	 * public static void main(String[] argv) { WebScrapper scrapper = new
	 * WebScrapper(); String
	 * urlOfPage="http://www.tutorialspoint.com/java/java_regular_expressions.htm"
	 * ; scrapper.getWebLinksList(urlOfPage);
	 * 
	 * }
	 */
	

	public List<String> getWebLinksListFromHtml(String htmlData) {
		final List<String> webLinksList = new ArrayList<String>();
		htmlData.replaceAll("\\s+", " ");
		final HTMLLinkExtractor extractor = new HTMLLinkExtractor();
		final List<HtmlLink> links = extractor.grabHTMLLinks(htmlData);
		for (int i = 0; i < links.size(); i++) {
			final HtmlLink htmlLinks = links.get(i);
			webLinksList.add(htmlLinks.getLink());
			//System.out.println("Links " + htmlLinks.getLink());

		}
		return webLinksList;
	}

	public Future<Response> hitMainPage(String urlOfMainPage) throws Exception {
		Request task;
		final String spec = urlOfMainPage;
		try {
			final URL url = new URL(spec);
			task = new Request(url);

		} catch (MalformedURLException e1) {
			LOGGER.error("MalformedURLException occured while parsing the URL "
					+ spec, e1);
			throw new Exception(
					"MalformedURLException occured while parsing the URL "
							+ spec, e1);
		}
		//Future<Response> response = null;
		final ExecutorService executor = Executors.newFixedThreadPool(2);
		final List<Future<Response>> futureList = new ArrayList<Future<Response>>();
		final Future<Response> response  = executor.submit(task);
		futureList.add(response);
		executor.shutdown();
		return response;
	}

	public String readResponseForHtmlData(final Future<Response> responseList)
			throws InterruptedException, ExecutionException, IOException {
		final Response resp = responseList.get();
		final InputStream body = resp.getBody();
		final BufferedReader responseBuffer = new BufferedReader(new InputStreamReader(
				body));
		String output;
		StringBuilder htmlData = new StringBuilder();
		//System.out.println("Output from Server .... \n");
		try {
			while ((output = responseBuffer.readLine()) != null) {
				htmlData.append(output);
			}
		} catch (IOException e) {
			LOGGER.error("Exception occured while reading data from Response",
					e);
			
			throw new IOException(
					"Exception occured while reading data from Response", e);
			
		} finally {
			responseBuffer.close();
		}
		//System.out.println("Response Code " + htmlData.toString());

		return htmlData.toString();
	}

	public List<Future<Response>> getResonseListForWebLinks(
			final List<String> urlLists,String webLink) {
		final ExecutorService childExecutor = Executors.newFixedThreadPool(100);
		final List<Future<Response>> childFutureList = new ArrayList<Future<Response>>();
		StringBuilder stbr;
		URL url;
		Request task;
		for (final String hyperLink : urlLists) {
			//System.out.println("Link " + "" + hyperLink);
			stbr= new StringBuilder();
			try {
				stbr.append(webLink).append("/").append(hyperLink);
				//System.out.println("STBR "+stbr.toString());
				//System.out.println();
				url = new URL(stbr.toString());
				task = new Request(url);
				final Future<Response> response = childExecutor.submit(task);
				childFutureList.add(response);
			} catch (MalformedURLException e1) {
				LOGGER.error(
						"MalformedURLException occured while parsing the URL "
								+ stbr.toString(), e1);
				// throw new
				// Exception("MalformedURLException occured while parsing the URL "+spec,
				// e1);
			} 
			
			
		}
		childExecutor.shutdown();
		return childFutureList;
	}

	public void writeStreamToFile(InputStream in, String outFileStr) {
		
		try {
			final FileOutputStream out = new FileOutputStream(outFileStr);
			int numBytesRead;
			final byte[] byteBuf = new byte[BUFFERSIZE];
			while ((numBytesRead = in.read(byteBuf)) != -1) {
				out.write(byteBuf, 0, numBytesRead);
			}
		} catch (FileNotFoundException e) {
			LOGGER.error("Exception occurecd while writing file " + outFileStr);
			// e.printStackTrace();
		} catch (IOException e) {
			LOGGER.error("Exception occurecd while writing file " + outFileStr);
			// e.printStackTrace();
		}

	}
}
