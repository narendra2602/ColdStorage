package com.coldstorage.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileReaderUtility 
{

	public static String readFile(String pathname)  
	{
		BufferedReader br = null;;
		StringBuilder sb =null;
		InputStreamReader reader=null;
		try 
		{
			reader = new InputStreamReader(FileReaderUtility.class.getResourceAsStream(pathname));
			br = new BufferedReader(reader);
//			br = new BufferedReader(new FileReader(pathname));
			sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) 
			{
				sb.append(line);
				sb.append("\n");
				line = br.readLine();
			}

		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		finally 
		{
			try 
			{
				br.close();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}

		return sb.toString().replaceAll("\\s+$", "");
		
	} 
	


	public static String readLocalFile(String pathname)  
	{
		BufferedReader br = null;;
		StringBuilder sb =null;
		try 
		{
			br = new BufferedReader(new FileReader(pathname));
			sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) 
			{
				sb.append(line);
				sb.append("\n");
				line = br.readLine();
			}

		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		finally 
		{
			try 
			{
				br.close();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}

		return sb.toString().replaceAll("\\s+$", "");
		
	} 

	
		
	 
}
