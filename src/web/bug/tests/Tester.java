package web.bug.tests;

import java.util.Enumeration;
import java.util.Hashtable;

public class Tester 
{

	public static void main(String[] args)
	{
		// define variables
		String url = "http://10.0.0.14/wb.html";
		String charset = java.nio.charset.StandardCharsets.UTF_8.name();
		int numberOfCalls = 10000;
		int sleepMS = 0;
		Hashtable <String,String> params = new Hashtable<String,String>();
		boolean testAnyway = true;
		
		// init hashtable
		params.put("sid", "12345");
		params.put("email", "bdonges@whatcounts.com");
		params.put("fname", "bill");
		params.put("rid", "1");
		params.put("site_id", "1");
		params.put("b", "chrome");
		params.put("os", "windows");
		params.put("ip", "10.0.0.49");
		
		String qry = "";
		Enumeration<String> e = params.keys();
		while (e.hasMoreElements())
		{
			String key = e.nextElement();
			String val = params.get(key);
			System.out.println("    adding " + key + "=" + val);
			qry = qry + key + "=" + val + ",";
		}
		
		System.out.println("qry: " + qry);
		
		qry = qry.replaceAll(",$", "");
		
		System.out.println("qry: " + qry);
	}
	
}
