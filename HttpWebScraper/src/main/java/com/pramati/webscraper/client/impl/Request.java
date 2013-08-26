package com.pramati.webscraper.client.impl;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;

/**
 * Class implementing Callable and to return the response.
 * @author sandeep-t
 *
 */

public class Request implements Callable<Response> {
	
    final private URL url;
   
    public Request(URL url)  {
    		this.url = url;	
    }
    

    @Override
    public Response call() throws IOException {
        return new Response(url.openStream(),url);
    }
} 