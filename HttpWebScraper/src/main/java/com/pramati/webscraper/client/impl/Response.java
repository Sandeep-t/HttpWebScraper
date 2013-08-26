package com.pramati.webscraper.client.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
/**
 * Class to read the inputstream sent across in the Future.
 * 
 * @author sandeep-t
 *
 */
public class Response {
   
	
   final private InputStream body;
   
   final private URL url;
   
    public Response(InputStream body,URL url) {
        this.body = body;
        this.url=url;
    }

    public InputStream getBody() {
        return body;
    } 
    
    public HttpURLConnection getHttpUrlConnection() throws IOException{
      	 return (HttpURLConnection)url.openConnection();
      }
    
}