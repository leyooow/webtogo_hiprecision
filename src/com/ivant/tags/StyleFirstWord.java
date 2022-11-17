package com.ivant.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class StyleFirstWord extends TagSupport {

	private static final long serialVersionUID = 6876855821042245423L;

	private String text;
	private String className;
	private int wordCount = 1;
	
	@Override
	public int doStartTag() throws JspException {
		try {
			String[] words = text.trim().split(" ");
			JspWriter out = pageContext.getOut();
			
			out.print("<span class=\""+ className +"\">");
			for(int i=0; i<words.length; i++) {
				out.print(words[i] + " ");
				if(i == wordCount - 1) {
					out.print("</span>");
				}

			}
			
//			String firstWord = words[0];
//			String remaining = "";
//			for(int i=1; i<words.length; i++) {
//				remaining += words[i] + " ";
//			}
//			remaining = remaining.trim();
//			
//			JspWriter out = pageContext.getOut();
//			out.println("<span class=\""+ className +"\">"+ firstWord +"</span> " +remaining);
		}
		catch(IOException ioe) {
			throw new Error("problem executing styleFirstWord tag in webtogo tag library");
		}
		return super.doStartTag();
	}
	 
	@Override
	public int doEndTag() throws JspException {
		// TODO Auto-generated method stub
		return super.doEndTag();
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public void setClassName(String className) {
		this.className = className;
	}
	
	public void setWordCount(int wordCount) {
		this.wordCount = wordCount;
	}
}
