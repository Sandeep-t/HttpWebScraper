package com.pramati.webscraper.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileHelper {
	
	private static final  int BUFFERSIZE = 1024;
	
	
	public void writeToFile(InputStream response,FileOutputStream out) throws IOException{
		int numBytesRead;
		final byte[] byteBuf = new byte[BUFFERSIZE];
		while ((numBytesRead = response.read(byteBuf)) != -1) {
			out.write(byteBuf, 0, numBytesRead);
		}
		
	}
		
	}
