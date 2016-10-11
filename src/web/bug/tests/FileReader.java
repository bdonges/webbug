package web.bug.tests;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

public class FileReader 
{

	public static void main(String[] args)
	{
		try
		{
			// init variables
			String nginxLogDir = "/whatcounts/web_bug_logs";
			String processingDir = "/whatcounts/web_bug_files";
			String archiveDir = "/whatcounts/web_bug_files/archive";
			String bulkInsertDir = "/whatcounts/web_bug_files/bulk_ready";
						
			// init objects
			FileReader f = new FileReader();
			
			// run
			f.moveFiles(nginxLogDir, processingDir);
			f.processFiles(processingDir, archiveDir, bulkInsertDir);
		}
		catch (Exception e)
		{
			System.err.println("exception - message: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param nginxLogDir
	 * @param processingDir
	 * @throws Exception
	 */
	public void moveFiles(String nginxLogDir, String processingDir) throws Exception 
	{
		// set current dir to nginx log dir 
		File dir = new File(nginxLogDir);

		// get files in directory
		System.out.println("Getting all files in " + dir.getCanonicalPath() + " including those in subdirectories");
		List<File> files = (List<File>) FileUtils.listFiles(dir, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
		
		// current file should be...
		String currentFileName = getCurrentFileName();
		
		for (File file : files) 
		{
			if (!file.getName().equals(currentFileName))
			{
				FileUtils.moveFile(file.getCanonicalFile(), new File(processingDir+"/"+file.getName()));
				file.delete();
			}
			System.out.println(file.getName());
			//System.out.println("file: " + file.getCanonicalPath());
		}
	}
	
	/**
	 * 
	 * @param processingDir
	 * @param bulkInsertDir
	 * @throws Exception
	 */
	public void processFiles(String processingDir, String archiveDir, String bulkInsertDir) throws Exception 
	{
		// set current dir to nginx log dir 
		File dir = new File(processingDir);

		// get files in directory
		System.out.println("Getting all files in " + dir.getCanonicalPath() + " including those in subdirectories");
		List<File> files = (List<File>) FileUtils.listFiles(dir, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
		
		KeyManager keyMgr = new KeyManager();
		
		for (File file : files) 
		{
			System.out.println(file.getName() + " ::f " + file.isFile() + " ::d " + file.isDirectory() + " ::h " + file.isHidden());
			if (file.isFile() && !file.isHidden())
			{
				List<String> lines = FileUtils.readLines(file); 
				
				String data = "";
				for (String line : lines)
				{
					if (line.indexOf("/wb.html?") > 0)
					{
						System.out.println(line);
						data = line.substring(line.indexOf("wb.html?")+"wb.html?".length(), line.length());
						System.out.println(data);
						data = data.substring(0, data.indexOf(" "));
						System.out.println(data);
						
						String encryptedData = data.substring(0,data.indexOf("&b="));
						System.out.println("encryptedData:   " + encryptedData);
						
						String unencryptedData = data.substring(data.indexOf("&b="), data.length());
						unencryptedData = unencryptedData.replaceAll("&",",");
						System.out.println("unecryptedData:  " + unencryptedData);
						
						String decryptedData = keyMgr.decrypt(encryptedData, GeneralData.phrase);
						System.out.println("decryptedData:   " + decryptedData);

						String totalLine = decryptedData + unencryptedData;
						System.out.println("totalLine:       " + totalLine);

						
						System.out.println(" ");
						System.out.println(" ");
					}
				}
	
				
			}
		}
	}
	
	/**
	 * 
	 * @return String
	 */ 
	public String getCurrentFileName()
	{
		LocalDateTime dt = new LocalDateTime();
		System.out.println(dt);
		int day = dt.getDayOfMonth();
		int year = dt.getYear();
		int month = dt.getMonthOfYear();
		int hour = dt.getHourOfDay();
		int min = dt.getMinuteOfHour();
		
		String f = year + "-" + month + "-" + day + "-" + hour + "-" + min + "-access.log";
		return f;
	}
	
	
}
