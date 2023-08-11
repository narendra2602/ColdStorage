package com.coldstorage.util;
import java.io.ByteArrayOutputStream;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

public class SendMail
{
	
	private static String SMTP_HOST_NAME = null;
	private static int SMTP_HOST_PORT = 0;
	private static String SMTP_AUTH_USER = null;
	private static String SMTP_AUTH_PWD = null;
	private static String from = null;
	
	private static String isSecured=null;
	public SendMail()
	{
		ResourceBundle rb=null;
		try 
		{
			rb = ResourceBundle.getBundle("Database");
			SMTP_HOST_NAME=rb.getString("emailHost");
			SMTP_HOST_PORT=Integer.parseInt(rb.getString("emailPort"));
			SMTP_AUTH_USER=rb.getString("emailAuthUser");
			SMTP_AUTH_PWD=rb.getString("emailAuthpass");
			from=rb.getString("emailFrom");
			isSecured=rb.getString("emailIsSecured");

		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	
	/**
	 * @param subject
	 * @param attachment 
	 * @param bodyTemplate
	 * @param toEmails
	 * @param fileName
	 */
	public static void send(String subject, ByteArrayOutputStream outputStream, String bodyTemplate,String toEmails,String fileName)	 
	{
		Transport transport=null;
		
		try
		{


			 
			Properties props = new Properties();
			props.put("mail.transport.protocol", "smtp");
			props.put("mail.smtp.host", SMTP_HOST_NAME);
			//props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.quitwait", "false");	
			Session mailSession = Session.getDefaultInstance(props);
			mailSession.setDebug(false);
			transport = mailSession.getTransport();


			MimeMessage message = new MimeMessage(mailSession);
			message.setSubject(subject);
			message.setFrom(new InternetAddress(from));
			message.addRecipients(Message.RecipientType.TO, getSplitEmailAddress(toEmails));

			//3) create MimeBodyPart object and set your message content    
			BodyPart messageBodyPart1 = new MimeBodyPart();
			messageBodyPart1.setText(bodyTemplate);

			
			byte[] bytes = outputStream.toByteArray();
			DataSource dataSource = new ByteArrayDataSource(bytes, "application/pdf");
			MimeBodyPart messageBodyPart2 = new MimeBodyPart();
			messageBodyPart2.setDataHandler(new DataHandler(dataSource));
			messageBodyPart2.setFileName(fileName);


			//5) create Multipart object and add MimeBodyPart objects to this object    
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart1);
			multipart.addBodyPart(messageBodyPart2);

			//6) set the multiplart object to the message object
			message.setContent(multipart );




			transport.connect(SMTP_HOST_NAME, SMTP_HOST_PORT, SMTP_AUTH_USER,SMTP_AUTH_PWD);

			transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));

			transport.close();


		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(transport!=null)
				try 
				{
					transport.close();
				} 
				catch (MessagingException e) 
				{
					
					e.printStackTrace();
				}

		}
	}
	
	


	public static InternetAddress[] getSplitEmailAddress(String toEmails)
	{

		StringTokenizer sto = new StringTokenizer(toEmails,",");
		InternetAddress[] address = new InternetAddress[sto.countTokens()];
		try
		{
			for(int i =0;sto.hasMoreElements(); i++)
			{
				address[i] = new InternetAddress(sto.nextToken());
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}


		return address;

	}

	}