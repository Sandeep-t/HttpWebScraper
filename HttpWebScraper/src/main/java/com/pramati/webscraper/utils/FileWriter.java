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
	InputStream dataStream;
	String fileNameWithPath;
	
	public FileWriter(InputStream response,String fileNameWithPath){
		//this.data=data;
		this.fileNameWithPath=fileNameWithPath;
		this.dataStream=response;
	}
	
	
	
	@Override
	public void run() {

		try {
			final FileOutputStream out = new FileOutputStream(fileNameWithPath);
			int numBytesRead;
			final byte[] byteBuf = new byte[BUFFERSIZE];
			while ((numBytesRead = dataStream.read(byteBuf)) != -1) {
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
