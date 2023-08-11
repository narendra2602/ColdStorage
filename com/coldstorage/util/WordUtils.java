package com.coldstorage.util;

import java.util.Enumeration;
import java.util.Vector;

public class WordUtils 
{

	public static String wrap(String text, int len)
	{
		// return empty array for null text
		if (text == null)
			return new String("");

		// return text if len is zero or less
		if (len <= 0)
			return new String(text);

		// return text if less than length
		if (text.length() <= len)
			return new String(text);

		Vector lines = new Vector();
		StringBuffer line = new StringBuffer();
		StringBuffer word = new StringBuffer();

		for (int i = 0; i < text.length(); i++) 
		{
			word.append(text.charAt(i));

			if (text.charAt(i) == '\n' ) 
			{
				if ((line.length() + word.length()) > len) 
				{
					lines.add(line.toString());
					line.delete(0, line.length());
				}
				line.append(word);
				lines.add(line.toString().substring(0, line.length()-1));

				line.delete(0, line.length());
				word.delete(0, word.length());
			}

			else if (text.charAt(i) == ' ') 
			{

				if ((line.length() + word.length()) > len) {
					lines.add(line.toString());
					line.delete(0, line.length());
				}

				line.append(word);   
				word.delete(0, word.length());
			}

		} // end of for LOOP 

		// handle any extra chars in current word
		if (word.length() > 0) {
			if ((line.length() + word.length()) > len) {
				lines.add(line.toString());
				line.delete(0, line.length());
			}
			line.append(word);
		}

		// handle extra line
		if (line.length() > 0) {
			lines.add(line.toString());
		}

		StringBuffer ret = new StringBuffer();
		for (Enumeration e = lines.elements(); e.hasMoreElements();) 
		{
			ret.append((String) e.nextElement()+"\n");
		}


		
		return ret.toString();
	}



	public static String [] wrapText (String text, int len)
	{
		// return empty array for null text
		if (text == null)
			return new String [] {};

		// return text if len is zero or less
		if (len <= 0)
			return new String [] {text};

		// return text if less than length
		if (text.length() <= len)
			return new String [] {text};

		char [] chars = text.toCharArray();
		Vector lines = new Vector();
		StringBuffer line = new StringBuffer();
		StringBuffer word = new StringBuffer();

		for (int i = 0; i < chars.length; i++) 
		{
			word.append(chars[i]);

			if (chars[i] == '\n' ) 
			{
				/*	    	  if ((line.length() + word.length()) > len) {
		        lines.add(line.toString());
		        line.delete(0, line.length());
		      }
				 */

				line.append(word);
				lines.add(line.toString().substring(0, line.length()-1));

				line.delete(0, line.length());

				word.delete(0, word.length());
			}

			else if (chars[i] == ' ') 
			{

				if ((line.length() + word.length()) > len) {
					lines.add(line.toString());
					line.delete(0, line.length());
				}

				line.append(word);
				word.delete(0, word.length());
			}

		} // end of for LOOP 

		// handle any extra chars in current word
		if (word.length() > 0) {
			if ((line.length() + word.length()) > len) {
				lines.add(line.toString());
				line.delete(0, line.length());
			}
			line.append(word);
		}

		// handle extra line
		if (line.length() > 0) {
			lines.add(line.toString());
		}

		String [] ret = new String[lines.size()];
		int c = 0; // counter
		for (Enumeration e = lines.elements(); e.hasMoreElements(); c++) {
			ret[c] = (String) e.nextElement();
		}

		return ret;
	}

	
}
