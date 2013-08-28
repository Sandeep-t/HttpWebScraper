/**
 * 
 */
package com.pramati.webscraper.utils;

/**
 * Every html link in HTML data will contain 2 parts actual link and 
 * the textpart(displayed in the webpage).This Class will serve as the object of HTML.  
 * @author sandeep-t
 *
 */
public class HtmlLink {

	String link;
	String linkText;

	HtmlLink(String link ){
		this.link=replaceInvalidChar(link);
		//this.linkText=linkText;
		
	};

	@Override
	public String toString() {
		return new StringBuffer("Link : ").append(this.link)
		.append(" Link Text : ").append(this.linkText).toString();
	}

	public String getLink() {
		return link;
	}



	public String getLinkText() {
		return linkText;
	}

	

	private String replaceInvalidChar(String link){
		final String replacedLink = link.replaceAll("'", "");
		
		return replacedLink.replaceAll("\"", "");
	}

}

