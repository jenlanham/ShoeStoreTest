package scripts;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import applibs.Setup;
import applibs.ShoeStore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;
/**
 * The following script will test the Brand drop down list.  It will loop through selecting each brand from the list.  The following verificatins
 * will take place: 
 * 		1.  The resulting title matches the brand that was selected.
 * 		2.  If a shoe list is displayed, each shoe entry on the list has the same brand as the one selected from the drop down list. 
 * 
 * @author Jennifer lanham
 *
 */
public class BrandShoeList {
	
	public String sScriptName = "BrandShoeList";
	public int iCase=1, iCasePass=0, iCaseFail=0;
	public WebDriver browser = new FirefoxDriver ();
	public String sJimmyChoo = "Jimmy Choo";
	
	@Before
	public void setUp() throws Exception {
		browser.manage().window().maximize();
		Setup.start = new Date();
		Setup.startScript(sScriptName, Setup.start);
	}
	
	@Test
	public void test() throws Exception {
		try{
			String sPageTitle, sBrand,sText="";
			Boolean bMatch = false;
			Boolean bExists = false;
			int iShoeNum = 1;

			Setup.openURL(browser, ShoeStore.sURL);
			
			//Verify the Choose a Brand drop down list.
			WebElement dropDownList = browser.findElement(ShoeStore.lstBrand);
			Select listAction = new Select (dropDownList);
			int iSize = listAction.getOptions().size();
			for (int i=1; i<iSize; i++){
				browser.findElement(ShoeStore.lnkHome).click();
				System.out.println("ACTION: Click on Home Link.");
				dropDownList = browser.findElement(ShoeStore.lstBrand);
				listAction = new Select (dropDownList);
				sBrand = listAction.getOptions().get(i).getText();
				listAction.selectByIndex(i);
				System.out.println("ACTION: Select item # " + i + " from the Brand list.");
				browser.findElement(ShoeStore.btnSearch).click();
				System.out.println("ACTION: Click on the Search button.");
				//Verify Page Title
				sPageTitle = browser.findElement(ShoeStore.stPageTitle).getText();
				if (!sPageTitle.contains(sBrand)) {
					System.err.println("TEST CASE " + iCase++ + ": FAIL - Expected Result:  Page title shows \"" + sBrand + "'s Shoes\". \n\tActual Results:  Page title shows" + sPageTitle);
					iCaseFail++;
				} else {
					System.out.println("TEST CASE " + iCase++ + ": PASS- Expected Result:  Page title shows \"" + sBrand + "'s Shoes\"." );
					iCasePass++;
				}
					
				bExists = browser.findElements(ShoeStore.objIndShoeEntry(iShoeNum)).size() > 0;
				System.out.println("VERIFY if there are any shoes listed for the brand selected."); 
				if (!bExists)
					System.out.println("INFO: There are no new shoes listed for brand: " + sBrand);
				else {
					bMatch=true;
					while (bExists && bMatch){
						sText = browser.findElement(ShoeStore.lnkBrand(iShoeNum++)).getText();
						System.out.println("ACTION: Extract the Brand from the Brand link and compare it to the brand selected.");
						if (!sText.equals(sBrand)) // if the text from the shoe entry matches the brand selected from the list.
							bMatch=false;
						bExists = browser.findElements(ShoeStore.objIndShoeEntry(iShoeNum)).size() > 0;
						System.out.println("VERIFY there is another shoe on the list to check."); 
					}
					if (bMatch) {
						System.out.println("TEST CASE "+ iCase++ + ": PASS- Expected Result:  A total of " + Integer.toString(iShoeNum-1) + " shoe(s) all have the correct Brand of " + sBrand + ".");
						iCasePass++;
					} else {
						System.err.println("TEST CASE "+ iCase++ + ": FAIL - Expected Result:  A total of " + Integer.toString(iShoeNum-1) + " shoe(s) all have the correct Brand of " + sBrand + ".\n\t\t\tActual Result: Not all shoes have the correct Brand.  One is showing: " + sText);
						iCaseFail++;
					}
				}
			}
		}  catch (Exception e) {
			System.err.println("Exception: " + e.getMessage());
			
		}
	}
	
	@After
	public void tearDown() throws Exception {
		Setup.finish = new Date();
		String sTime = Setup.calcTime(Setup.start, Setup.finish);
		Setup.iCaseNum+=iCase;
		Setup.iFail+=iCaseFail;
		Setup.iPass+=iCasePass;
		Setup.finishScript(sScriptName, iCasePass, iCaseFail, sTime);
		browser.quit();
	}

}
