package com.ivant.tags;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;
import org.hibernate.util.StringHelper;

public class TruncateString extends TagSupport {

	private static final long serialVersionUID = 6266875777206466049L;
	private static final Logger logger = Logger.getLogger(TruncateString.class);
	
	private String text;
	private int maxCharacters;
	private String suffix = "...";
	
	@Override
	public int doStartTag() throws JspException {
		String newText1;
		String newText2;
		String newText3;
		String newText4;
		String newText5;
		
		//removes all tags
		newText1 = text.replaceAll("<(.*?)>", " ");
		newText2 = newText1.replaceAll("<br>","\n");
		newText3 = newText2.replaceAll("&nbsp;"," ");
		newText4 = newText3.replace('(', ' ');
		newText5 = newText4.replace(')', ' ');
		text = newText5;
		
		//text = text.replaceAll("(\\<(/)*[A-Z-a-z0-9,_,\"]*\\>)", "");
		try {
			String newText = StringHelper.truncate(text, maxCharacters);
			newText.trim();			
			JspWriter out = pageContext.getOut();
			out.println(newText + suffix);
		}
		catch(IOException ioe) {
			throw new Error("problem executing truncateString tag in webtogo tag library");
		}
		return super.doStartTag();
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public void setMaxCharacters(int maxCharacters) {
		this.maxCharacters = maxCharacters;
	}
	
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
}
