package web.bug.tests;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Key;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class KeyManager 
{
	
	public static void main(String[] args)
	{
		try
		{
			String secretKey = "TheBestSecretKey";
			
			String valToEnc = URLEncoder.encode("rid=1234,email=bdonges@whatcounts.com,fname=bill","UTF-8");
			valToEnc = "rid=1234,email=bdonges@whatcounts.com,fname=bill";
			
			KeyManager k = new KeyManager();
			
			String e = "";
			String d = "";
			
		 	e = k.encrypt(valToEnc, secretKey);
		 	d = k.decrypt(e, secretKey);
		 	
			System.out.println("original:  " + valToEnc);
			System.out.println("encrypted: " + e);
			System.out.println("decrypted: " + d);
			
		}
		catch (Exception e)
		{
			System.err.println("exception - message: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param valueToEnc
	 * @param phrase
	 * @return
	 * @throws Exception
	 */
	public String encrypt(String valueToEnc, String phrase) throws Exception 
	{
		Key key = new SecretKeySpec(phrase.getBytes() , "AES");
	    Cipher c = Cipher.getInstance("AES");
	    c.init(Cipher.ENCRYPT_MODE, key);
	    byte[] encValue = c.doFinal(valueToEnc.getBytes());
	    String encryptedValue = Base64.getEncoder().encodeToString(encValue);
	    return encryptedValue;	
	}
	
	/**
	 * 
	 * @param encryptedValue
	 * @param phrase
	 * @return
	 * @throws Exception
	 */
	public String decrypt(String encryptedValue, String phrase) throws Exception
	{
		Key key = new SecretKeySpec(phrase.getBytes() , "AES");
		Cipher c = Cipher.getInstance("AES");
		c.init(Cipher.DECRYPT_MODE, key);
		byte[] decordedValue = Base64.getDecoder().decode(encryptedValue);
		byte[] decValue = c.doFinal(decordedValue);
		String decryptedValue = new String(decValue);
		return decryptedValue;
	}
	
}
