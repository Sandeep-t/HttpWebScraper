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

	HtmlLink(){};

	@Override
	public String toString() {
		return new StringBuffer("Link : ").append(this.link)
		.append(" Link Text : ").append(this.linkText).toString();
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = replaceInvalidChar(link);
	}

	public String getLinkText() {
		return linkText;
	}

	public void setLinkText(String linkText) {
		this.linkText = linkText;
	}

	private String replaceInvalidChar(String link){
		final String replacedLink = link.replaceAll("'", "");
		//String finallink = link.replaceAll("\"", "");
		return replacedLink.replaceAll("\"", "");
	}

}

