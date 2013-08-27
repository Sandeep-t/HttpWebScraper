package com.pramati.webscraper.utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;

import com.pramati.webscraper.client.impl.Response;

public class FileWriter {
	
	private static final Logger LOGGER = Logger.getLogger(FileWriter.class);
	private static final  int BUFFERSIZE = 1024;
	
	
	public void writeToFile(InputStream response,FileOutputStream out) throws IOException{
		int numBytesRead;
		final byte[] byteBuf = new byte[BUFFERSIZE];
		while ((numBytesRead = response.read(byteBuf)) != -1) {
			out.write(byteBuf, 0, numBytesRead);
		}
	}
		
	}
