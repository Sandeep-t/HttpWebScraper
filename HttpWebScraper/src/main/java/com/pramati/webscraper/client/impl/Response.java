package com.pramati.webscraper.client.impl;

import java.io.InputStream;
/**
 * Class to read the inputstream sent across in the Future.
 * 
 * @author sandeep-t
 *
 */
public class Response {
   final private InputStream body;

    public Response(InputStream body) {
        this.body = body;
    }

    public InputStream getBody() {
        return body;
    } 
    
}