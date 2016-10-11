package web.bug.tests;

public class BugInfo 
{

	/**
	 * empty constructor
	 */
	public BugInfo() {}
	
	/**
	 * loaded constructor
	 * @param sid
	 * @param email
	 * @param fname
	 * @param rid
	 * @param siteId
	 * @param brower
	 * @param os
	 * @param ip
	 */
	public BugInfo(int sid, String email, String fname, int rid, int siteId, String browser, String os, String ip)
	{
		this.sid = sid;
		this.email = email;
		this.fname = fname;
		this.rid = rid;
		this.siteId = siteId;
		this.browser = browser;
		this.os = os;
		this.ip = ip;
	}
	
	// variables
	public int sid = 0;
	public String email = "";
	public String fname = "";
	public int rid = 0;
	public int siteId = 0;
	public String browser = "";
	public String os = "";
	public String ip = "";
	
}
