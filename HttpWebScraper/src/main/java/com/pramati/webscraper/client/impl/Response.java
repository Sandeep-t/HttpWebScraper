package com.pramati.webscraper.client.impl;

import java.io.FileInputStream;
import java.io.InputStream;

public class Response {
   final private InputStream body;

    public Response(InputStream body) {
        this.body = body;
    }

    public InputStream getBody() {
        return body;
    } 
    
}