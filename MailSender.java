package com.crypto.mail;

import java.security.KeyFactory;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Properties;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MailSender {
	
	//authentication
	public static String username = "yourmail@gmail.com";
	public static String password = "yourpassword";
	
	public static void main(String[] args) throws Exception{
		
		//Bob executes this program
		
		//crypto variables
		//byte[] key = {75, 110, -45, -61, 101, -103, -26, -25, 55, -70, 19, 51, 66, -91, -35, 19}; //128 bit key
		String algorithm = "AES";
		
		KeyGenerator generator = KeyGenerator.getInstance(algorithm);
		generator.init(128); //generate 128 bit key
		SecretKey secretKey = generator.generateKey();
		byte[] key = secretKey.getEncoded();
		
		//mail variables
		String subject = "You've got an encrypted mail!";
		String content = " Time can never mend\n The careless whispers of a good friend\n To the heart and mind\n Ignorance is kind\n There's no comfort in the truth\n Pain is all you'll find";
		String recipient = "yourmail@gmail.com";
		
		String encryptedContent = encryptContent(key, algorithm, content);	
				
		//---------------------------------------
		//key exchange		
		
		//Alice's public key
		byte[] publicKey = {48, -126, 1, 34, 48, 13, 6, 9, 42, -122, 72, -122, -9, 13, 1, 1, 1, 5, 0, 3, -126, 1, 15, 0, 48, -126, 1, 10, 2, -126, 1, 1, 0, -95, -31, 108, -69, 59, 77, -11, 43, -116, 118, 74, -75, -123, 117, -44, 39, 121, 89, 67, 10, -57, -27, 25, 110, -86, 28, -57, -116, 119, -93, 40, -84, 123, -105, 30, -66, 69, -67, 104, 121, -68, 102, 71, -44, 87, -3, -45, -21, -88, 59, 64, 88, -124, -20, -90, 21, 52, -115, 125, -33, 93, 0, 43, -67, -6, 76, 22, -123, 88, -91, -43, 63, 32, 74, 107, 12, -69, -80, -80, 124, -79, 16, 0, -55, -113, 84, -43, 17, -15, 26, -29, -2, 124, 0, -26, 25, 97, -64, 32, -22, 99, 91, 91, -42, -85, 93, -52, 76, -71, -53, 45, 38, 52, -28, -6, 92, 108, -105, -84, -53, 29, 80, 60, -98, -103, 63, 71, 3, -30, -118, -94, 76, 39, 46, -112, -65, 14, 112, -82, -38, 31, 20, -10, -53, 101, -52, 37, 3, -20, -84, -83, -128, -88, 27, -109, -5, 69, -18, -42, 123, -31, 85, -101, -4, -93, -95, 13, -8, 49, -120, 90, -42, -102, -73, 85, -59, -56, 69, -79, 106, 32, 51, -77, -3, 108, -101, -99, -96, 33, 63, -66, -82, 16, 60, -45, 52, -125, -114, -23, -84, -47, -19, 75, -24, 67, -62, 34, 124, -18, -38, 7, -24, -126, 123, 80, 34, 104, -107, -85, -11, -6, -8, -81, -59, -121, 81, 99, -90, -27, 0, 80, 30, -122, 71, 112, 116, 114, -79, -45, 3, -40, -119, 119, -61, 19, 16, -95, -28, 64, -73, -87, 4, 62, -70, 54, 17, 2, 3, 1, 0, 1};
		
		Cipher publicKeyEncryption = Cipher.getInstance("RSA");
		publicKeyEncryption.init(Cipher.ENCRYPT_MODE, KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(publicKey)));
		byte[] encryptedKey = publicKeyEncryption.doFinal(key);
		//String encryptedByteKey = Arrays.toString(encryptedKey);
		String encryptedByteKey = new String(Base64.getEncoder().encode(encryptedKey));
		
		//key exchange end
		
		//---------------------------------------
		
		sendMail(recipient, subject, encryptedContent, encryptedByteKey);
		
	}
	
	public static String encryptContent(byte[] key, String algorithm, String content) throws Exception{
				
		byte[] plainContent = content.getBytes();
		Cipher encryption = Cipher.getInstance(algorithm);
		encryption.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, 0, key.length, algorithm));
		byte[] encryptedContent = encryption.doFinal(plainContent);
		
		//return new String(encryptedContent, "ISO-8859-1");
		//return Arrays.toString(encryptedContent);
		
		return new String(Base64.getEncoder().encode(encryptedContent));
		
	}
	
	public static void sendMail(String recipient, String subject, String content, String encryptedKey) throws Exception{
		
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		
		Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
					protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
						return new javax.mail.PasswordAuthentication(username, password);
					}
				});
		
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(username));
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
		message.setSubject(subject);
		//message.setText(content, "ISO-8859-1");
		//message.setText(content);
		//---------------------------------------------------
		
		//attachment
		Multipart multipart = new MimeMultipart();
		
		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setText(content);
		multipart.addBodyPart(messageBodyPart);
		
		MimeBodyPart attachment = new MimeBodyPart();
		attachment.setText(encryptedKey);
		attachment.setFileName("encrypted_key.txt");
		multipart.addBodyPart(attachment);
		
		message.setContent(multipart);
		
		//attachment end
		
		//-------------------------------------------------
		Transport.send(message);
		
		System.out.println("mail delivered successfully...");
		
	}
	

}
