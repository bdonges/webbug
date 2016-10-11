package web.bug.tests;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.Key;
import java.util.Base64;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Caller 
{

	public static void main(String[] args)
	{
		try
		{
			// define variables
			String url = "http://10.0.0.14/wb.html";
			String charset = java.nio.charset.StandardCharsets.UTF_8.name();
			int numberOfCalls = 5;
			int sleepMS = 0;
			String phrase = "TheBestSecretKey";
			boolean testAnyway = true;
			
			// init BugInfo		
			BugInfo bug = new BugInfo(12345,"bdonges@whatcounts.com","bill",1,1,"chrome","windows","10.0.0.14");
			
			// init objects
			Caller c = new Caller();
			
			// test the url
			if (c.testUrlAvailability(url) || testAnyway)
			{
				// if url is reachable, run tests
				System.out.println("the url '"+url+"' is available.  starting call tests");
				c.makeCalls(url, numberOfCalls, charset, sleepMS, phrase, bug);
			}
			else
			{
				// url isn't available
				System.out.println("the url '"+url+"' isn't available");
			}
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * makes calls to a url.  parameters
	 * 
	 * url:  the url to test
	 * numberOfCalls:  the total number of calls to make
	 * sleepMS:  the time to sleep (in milliseconds) between calls to the url
	 * 
	 * @param url 
	 * @param numberOfCalls
	 * @param sleepMS
	 * @throws Exception
	 */
	public void makeCalls(String url, int numberOfCalls, String charset, int sleepMS, String phrase, BugInfo bug) throws Exception
	{
		System.out.println("starting makeCalls:  " + new java.util.Date());
		
		KeyManager keyMgr = new KeyManager();
		
		for (int i = 0; i < numberOfCalls; i++)
		{
			try
			{
				// set up query string
				StringBuilder sb = new StringBuilder();
				sb.append("rid="+bug.rid+",");
				sb.append("siteId="+bug.siteId+",");
				sb.append("sid="+(bug.sid + 1)+",");
				sb.append("email="+bug.email+i+",");
				sb.append("fname="+i);
				String qry = sb.toString();
				
				// add query to url
				String furl = url + "?" + keyMgr.encrypt(qry, phrase)+"&b="+bug.browser+"&ip="+bug.ip+"&os="+bug.os;
				System.out.println(furl.length() + ", furl: '"+furl+"'");
				
				// create connection and get input stream
				URLConnection uc = new URL(furl).openConnection();
				uc.setRequestProperty("Accept-Charset", charset);				
				InputStream response = uc.getInputStream();
				
				if (i % 100 == 0)
				{
					System.out.println("    " + i + " :: " + new java.util.Date());
				}
				
				// wait x number of milliseconds before trying again.
				Thread.sleep(sleepMS);
				qry = "";
			}
			catch (Exception e)
			{
				System.err.println("exception - makeCalls - message: " + e.getMessage());
			}
		}
		System.out.println("ending makeCalls:  " + new java.util.Date());
	}
	
	/**
	 * tests that a url actually works before making calls to it.  parameters
	 * 
	 * url:  the url to test
	 * returns boolean:  url worked (true) or didn't (false)
	 * 
	 * @param url
	 * @return boolean
	 * @throws Exception
	 */
	public boolean testUrlAvailability(String url) throws Exception
	{
		System.out.println("starting testUrl:  " + new java.util.Date());
		boolean validUrl = true;
		try
		{
			URL u = new URL(url);
			URLConnection uc = u.openConnection();
			uc.connect();
		} 
		catch (Exception e)
		{
			System.err.println("exception - testurl - message: " + e.getMessage());
		}
		System.out.println("ending testUrl:  " + new java.util.Date());
		return validUrl;
	}

	
}
