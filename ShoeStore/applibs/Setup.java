package applibs;

import java.io.BufferedWriter;
import java.io.File;
import java.util.Date;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Setup {

	public static int iFail = 0;
	public static int iPass = 0;
	public static int iCaseNum = 1;
	public static Date start;
	public static Date finish;
	public static String sFileName = "/results/Log.txt";
	public static File file = new File(sFileName);
	public static BufferedWriter output;	
	
	
	/**
	 * 
	 */
	public static void openURL(WebDriver browser,String sURL) {
		try {
			browser.get(ShoeStore.sURL);  //open URL
			System.out.println("ACTION: Opening URL " + ShoeStore.sURL);
		} catch (Exception e) {
			System.err.println ("Exception: " + e.getMessage());
		}	
	}
	
	
	/**
	 * Method to display Script information to the console at startup.
	 * @param sScriptName
	 * @param stdate
	 */
	public static void startScript(String sScriptName, Date stdate){
		System.out.println("*******************************************");
		System.out.println("Script Name:\t" + sScriptName);
		System.out.println("Start Time:\t" + getStartTime(stdate));
		System.out.println("*******************************************");
	}
	
	/**
	 * Method to display Suite information to the console at startup.
	 * @param sSuiteName
	 * @param stdate
	 */
	public static void startSuite(String sSuiteName, Date stdate){
		System.out.println("*****************************************************************");
		System.out.println("Suite Script Name:\t" + sSuiteName);
		System.out.println("Suite Start Time:\t" + getStartTime(stdate));
		System.out.println("*****************************************************************");
	}
	
	public static void finishSuite(String sSuiteName, String sSuiteTime){
		System.out.println("*****************************************************************");
		System.out.println("Suite Script Name:\t " + sSuiteName);
		System.out.println("\nTOTAL STEPS PASSED:\t" + Setup.iPass);
		System.out.println("TOTAL STEPS FAILED:\t" + Setup.iFail);
		System.out.println("TOTAL SUITE RUN TIME:\t" + sSuiteTime);
		System.out.println("*****************************************************************");
	}
	
	/**
	 * Method to extract the start time from the date in String format
	 * @param stDate
	 * @return String 
	 */
	public static String getStartTime(Date stDate){
		String sMinutes="00", sSeconds="00", sHours="00";
		sHours = String.valueOf(stDate.getHours());
		if (stDate.getMinutes()<10)
			sMinutes = "0" + String.valueOf(stDate.getMinutes());
		else
			sMinutes = String.valueOf(stDate.getMinutes());
		if (stDate.getSeconds()<10)
			sSeconds = "0" + String.valueOf(stDate.getSeconds());
		else 
			sSeconds = String.valueOf(stDate.getSeconds());
		return sHours + ":" + sMinutes + ":" + sSeconds;
	}
	
	/**
	 * Method to display ending script information to the console
	 * @param sScriptName
	 * @param iPass
	 * @param iFail
	 * @param sTime
	 */
	public static void finishScript (String sScriptName, int iPass, int iFail, String sTime){
		System.out.println("*******************************************");
		System.out.println("Execution complete for " + sScriptName);
		System.out.println("Total Test Cases Pass:\t" + iPass);
		System.out.println("Total Test Cases Fail:\t" + iFail);
		System.out.println("Total Test Run Time:\t" + sTime);
		System.out.println("*******************************************");
	}
	
	/**
	 * Method to calculate the run time of a script or suite file given the begin and end time.
	 * @param begin
	 * @param end
	 * @return String 
	 */
	public static String calcTime(Date begin, Date end){
		int iStartHour, iFinHour;
		int iStartMin, iFinMin;
		int iDiffHour, iDiffMin, iDiffSec;
		int iStartSec, iFinSec;
		int iDiff;
		String sDiffHour = "00", sDiffSec="00", sDiffMin="00";
		String sDiff = "";
		try{
			iStartHour = begin.getHours();
			iStartMin = begin.getMinutes();
			iStartSec = begin.getSeconds();
			iFinHour = end.getHours();
			iFinMin = end.getMinutes();
			iFinSec = end.getSeconds();
			
			if (iStartHour > iFinHour )
				iFinHour += 24;
			
			if (iStartSec > iFinSec){
				iFinMin--;
				iFinSec+=60;
			}
			if (iStartHour < iFinHour)
				if (iFinMin < iStartMin){
					iFinHour--;
					iFinMin+=60;
				}
			iDiffHour = iFinHour - iStartHour;
			iDiffMin = iFinMin - iStartMin;
			iDiffSec = iFinSec - iStartSec;
			
			if (iDiffHour < 10)
				sDiffHour = "0" + String.valueOf(iDiffHour);
			else
				sDiffHour = String.valueOf(iDiffHour);
			if (iDiffMin < 10)
				sDiffMin = "0" + String.valueOf(iDiffMin);
			else
				sDiffMin = String.valueOf(iDiffMin);
			if (iDiffSec < 10)
				sDiffSec = "0" + String.valueOf(iDiffSec);
			else
				sDiffSec = String.valueOf(iDiffSec);
				
			sDiff = sDiffHour + ":" + sDiffMin + ":" + sDiffSec;
					
		} catch (Exception e) {
			System.err.println ("Error in calcTime" + e.getMessage());
		}
		return sDiff;
	}

	/**
	 * Method to open URL
	 * @param sURL
	 */
	public static WebDriver openURL (String sURL){
		WebDriver driver = new FirefoxDriver ();
		driver.get(sURL);
		return driver;
	}
	
	/**
	 * Method to determine the int Month value given the String
	 * @param sMonth
	 * @return String
	 */
	public static int getMonthValue(String sMonth){
		int iMonth=0;
		switch (sMonth){
			case "January":
				iMonth = 1;
				break;
			case "February":
				iMonth = 2;
				break;
			case "March":
				iMonth = 3;
				break;
			case "April":
				iMonth = 4;
				break;
			case "May":
				iMonth = 5;
				break;
			case "June":
				iMonth = 6;
				break;
			case "July":
				iMonth = 7;
				break;
			case "August":
				iMonth = 8;
				break;
			case "September":
				iMonth = 9;
				break;
			case "October":
				iMonth = 10;
				break;
			case "November":
				iMonth = 11;
				break;
			case "December":
				iMonth = 12;
				break;
			}
		return iMonth;
		
	}

}
