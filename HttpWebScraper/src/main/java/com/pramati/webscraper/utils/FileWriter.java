package com.pramati.webscraper.utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;

import com.pramati.webscraper.client.impl.Response;

public class FileWriter implements Runnable {
	
	private static final Logger LOGGER = Logger.getLogger(FileWriter.class);
	private static final  int BUFFERSIZE = 1024;
	

	//String data;
	Response response;
	String fileNameWithPath;
	
	public FileWriter(Response response,String fileNameWithPath){
		//this.data=data;
		this.fileNameWithPath=fileNameWithPath;
		this.response=response;
	}
	
	
	
	@Override
	public void run() {

		try {
			final FileOutputStream out = new FileOutputStream(fileNameWithPath);
			int numBytesRead;
			final byte[] byteBuf = new byte[BUFFERSIZE];
			InputStream body = response.getBody();
			while ((numBytesRead = body.read(byteBuf)) != -1) {
				out.write(byteBuf, 0, numBytesRead);
			}
			LOGGER.info("FIle "+fileNameWithPath+" written successfully");
		} catch (FileNotFoundException e) {
			LOGGER.error("Exception occurecd while writing file " + fileNameWithPath);
		} catch (IOException e) {
			LOGGER.error("IOException occurecd while writing file " + fileNameWithPath);
			
		}

	}
		
	}
