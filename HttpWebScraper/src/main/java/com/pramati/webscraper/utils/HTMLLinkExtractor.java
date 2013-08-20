/**
 * 
 */
package com.pramati.webscraper.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class will serve the purpose of hyperlink validation and varification.
 * 
 * 
 * @author sandeep-t
 *
 */
public class HTMLLinkExtractor {

	private Pattern patternTag, patternLink;
	private Matcher matcherTag, matcherLink;
 
	private static final String HTML_TAG_PATTERN = "(?i)<a([^>]+)>(.+?)</a>";
	private static final String HTML_HREF_TAG_PATTERN = 
		"\\s*(?i)href\\s*=\\s*(\"([^\"]*\")|'[^']*'|([^'\">\\s]+))";
 
 
	public HTMLLinkExtractor() {
		patternTag = Pattern.compile(HTML_TAG_PATTERN);
		patternLink = Pattern.compile(HTML_HREF_TAG_PATTERN);
	}
 
	/**
	 * 
	 * Validate html with regular expression
	 * and return the list of htmllinks extracted from html data passed.
	 * @param html
	 *            html content for validation
	 * @return List links and link text
	 */
	public List<HtmlLink> grabHTMLLinks(final String html) {
 
		List<HtmlLink> result = new ArrayList<HtmlLink>();
 
		matcherTag = patternTag.matcher(html);
 
		while (matcherTag.find()) {
 
			String href = matcherTag.group(1); // href
			String linkText = matcherTag.group(2); // link text
 
			matcherLink = patternLink.matcher(href);
 
			while (matcherLink.find()) {
 
				String link = matcherLink.group(1); // link
				HtmlLink obj = new HtmlLink();
				obj.setLink(link);
				obj.setLinkText(linkText);
 
				result.add(obj);
 
			}
 
		}
 
		return result;
}
}
