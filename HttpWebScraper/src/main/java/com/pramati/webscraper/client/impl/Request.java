package com.pramati.webscraper.client.impl;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.Callable;



public class Request implements Callable<Response> {
	
    final private URL url;
   
    public Request(URL url)  {
    		this.url = url;	
    }

    @Override
    public Response call() throws IOException {
        return new Response(url.openStream());
    }
} 