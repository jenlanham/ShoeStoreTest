package scripts;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import applibs.Setup;
import applibs.ShoeStore;

/**
 * The purpose of this script is to test the following:
 * 		1.  There should be an area to submit email address
 * 		2.  On successful submission of a valid email address user should receive a message:
 * 			 Thanks! We will notify you of our new shoes at this email: users email address
 * 
 * Other verifications:
 * 		3.  After clicking on the All Shoes link, then clicking on the first Release Month link, verify that all shoes listed are
 * 			also for that given month.
 * 		4.  Verify clicking on the Home button takes you back to the Home screen.
 * 		5.  After clicking on the All Shoes link, then clicking on the first Brand link, verify that all of the shoes listed have 
 * 			the same brand.
 * 		6.  Promotion code field
 * 				a.  Can't be blank
 * 				b.  invalid entry
 * 				Note:  can't test positive entry since requirements 
 * 
 * @author Jennifer Lanham
 *
 */
public class ShoeStoreMain {
	public WebDriver browser = new FirefoxDriver();
	public int iCase=1, iCasePass=0, iCaseFail=0;
	public String sScriptName = "ShoeStoreMain";
	public String sEmail = "jane_doe@email.com";
	public String sJimmyChoo = "Jimmy Choo";
	
	@Before
	public void setUp() throws Exception {
		Setup.openURL(browser, ShoeStore.sURL);
		browser.manage().window().maximize();
		Setup.start = new Date();
		Setup.startScript(sScriptName, Setup.start);
	}

	@Test
	public void test() {
		try {
			Boolean bExists = true;
			Boolean bMatch = true;
			int iShoeNum=1;
			String sRelMonth,sText,sBrand;
			
			//Verify the Email Reminder field on the Home screen.
			//verify error message after submitting blank email address
			browser.findElement(ShoeStore.btnSubmitEmail).click();
			System.out.println("ACTION: Click on the Submit Query button.");
			sText = browser.findElement(ShoeStore.stMessage).getText();
			System.out.println("ACTION: Extract the text from the Message displayed.");
			if (sText.equals("Please enter an email address")) {
				System.out.println("Test Case "+ iCase++ + ": -PASS- Expect Result: Message displayed:  \"Please enter an email address.\" ");
				iCasePass++;
			} else {
				System.err.println("Test Case "+ iCase++ + ": -FAIL - Expect Result: Message displayed:  \"Please enter an email address.\" \n\t\t\tActual Result:  " + sText);
				iCaseFail++;
			}
			
			
			//verify error message after submitting invalid email address
			browser.findElement(ShoeStore.tfNewShoeEmail).sendKeys("invalid");
			System.out.println("ACTION: Enter an invalid email address.");
			browser.findElement(ShoeStore.btnSubmitEmail).click();
			System.out.println("ACTION: Click on the Submit Query button.");
			sText = browser.findElement(ShoeStore.stMessage).getText();
			System.out.println("ACTION: Extract the text from the Message displayed.");
			if (sText.equals("Invalid email format. Ex. name@example.com")) {
				System.out.println("Test Case "+ iCase++ + ": -PASS- Expect Result: Message displayed:  \"Invalid email format. Ex. name@example.com\" ");
				iCasePass++;
			} else {
				System.err.println("Test Case "+ iCase++ + ": -FAIL - Expect Result: Message displayed:  \"Invalid email format. Ex. name@example.com\" \n\t\t\tActual Result:  " + sText);
				iCaseFail++;
			}
		
			//enter valid email; verify successful message
			browser.findElement(ShoeStore.tfNewShoeEmail).sendKeys(sEmail);
			System.out.println("ACTION: Enter a valid email address.");
			browser.findElement(ShoeStore.btnSubmitEmail).click();
			System.out.println("ACTION: Click on the Submit Query button.");
			sText = browser.findElement(ShoeStore.stMessage).getText();
			System.out.println("ACTION: Extract the text from the Message displayed.");
			if (sText.equals("Thanks! We will notify you of our new shoes at this email: " + sEmail)) {
				System.out.println("Test Case "+ iCase++ + ": -PASS- Expected Result:  Message displayed: Thanks! We will notify you when this shoe is available at this email: " + sEmail + ".");
				iCasePass++;
			} else {
				System.err.println("Test Case "+ iCase++ + ": -FAIL - Expected Result:  Message displayed: Thanks! We will notify you when this shoe is available at this email: " + sEmail + "./n/tActual result: Displayed Message:  " + sText);
				iCaseFail++;
			}
			
			//Verifying the Release Month link
			//1.  The Page Title should be correct.
			//2.  There should at least be one shoe listed. (since we clicked on the link)
			//3.  All shoes on the page should have the same Release Month.
			browser.findElement(ShoeStore.lnkAllShoes).click();
			System.out.println("ACTION: Click on the All Shoes link");
			bExists = browser.findElement(ShoeStore.stAllShoesTitle).isDisplayed();
			System.out.println("VERIFY the title is displayed for All Shoes.");
			if (bExists){
				sRelMonth = browser.findElement(ShoeStore.lnkRelMonth(1)).getText();
				System.out.println("ACTION: Extract the text from the Release Month field for this shoe");
				browser.findElement(ShoeStore.lnkRelMonth(1)).click();
				System.out.println("ACTION: Click on the Release Month link");
				//verify the title of the page matches the link clicked
				sText = browser.findElement(ShoeStore.stPageTitle).getText();
				System.out.println("ACTION: Extract the text from the Page Title.");
				if(sText.contains(sRelMonth)){
					System.out.println("Test Case "+ iCase++ + ": -PASS- Expected Result:  \"" + sRelMonth + "'s Shoes\" Page Title is displayed.");
					iCasePass++;
				} else {
					System.err.println("Test Case "+ iCase++ + ":-FAIL - Expected Result:  \"" + sRelMonth + "'s Shoes\" Page Title is displayed.\n\t\t\tActual Result: The displayed title is: " + sText);
					iCaseFail++;
				}
				bExists = browser.findElements(ShoeStore.objIndShoeEntry(iShoeNum)).size() > 0;
				System.out.println("VERIFY there is at least one shoe listed.");
				if (!bExists)
					System.err.println("Test Case "+ iCase++ + ":-FAIL - Expected Result:  There should be at least one shoe listed.\n\t\t\tActual Result: There are no shoes listed when clicking the Release Month for " + sRelMonth);
				while (bExists && bMatch){
					sText = browser.findElement(ShoeStore.lnkRelMonth(iShoeNum++)).getText();
					System.out.println("ACTION: Extract the text from the Release Month field for this shoe entry.");
					if (!sText.equals(sRelMonth))
						bMatch=false;
					bExists = browser.findElements(ShoeStore.objIndShoeEntry(iShoeNum)).size() > 0;
					System.out.println("VERIFY: Is there another shoe listed.");
				}
				if (bMatch) {
					System.out.println("Test Case "+ iCase++ + ": -PASS- Expected Result:  A total of " + Integer.toString(iShoeNum-1) + " shoe(s) all have the correct release month of " + sRelMonth + ".");
					iCasePass++;
				} else {
					System.err.println("Test Case "+ iCase++ + ":-FAIL - Expected Result:  A total of " + Integer.toString(iShoeNum-1) + " shoe(s) all have the correct release month of " + sRelMonth + ".\n\t\t\tActual Result: Not all shoes have the correct Release Month.  One is showing: " + sText);
					iCaseFail++;
				}
				
			} else {
				System.err.println("Error: The All Shoes page did not display as expected.");
			}
			
			//Verify the Home button takes you back to the Home page
			browser.findElement(ShoeStore.lnkHome).click();
			System.out.println("ACTION: Click on the Home link.");
			sText = browser.findElement(ShoeStore.stWelcome).getText();
			System.out.println("ACTION: Extract the text from the Welcome message on the screen.");
			if (sText.contains("Welcome to Shoe Store")){
				System.out.println("Test Case "+ iCase++ + ": -PASS- Expected Result:  Clicking the Home link takes you  to the \"Welcome to Shoe Store\" Page.");
				iCasePass++;
			} else {
				System.err.println("Test Case "+ iCase++ + ":-FAIL - Expected Result:  Clicking the Home link takes you  to the \"Welcome to Shoe Store\" Page.\n\t\t\tActual Result: Not all shoes have the correct Release Month.  One is showing: " + sText);
				iCaseFail++;
			}
			
			//Verifying the Brand Name link
			//1.  The Page Title should be correct.
			//2.  There should at least be one shoe listed. (since we clicked on the link)
			//3.  All shoes on the page should have the same Brand.
			Boolean bVisible=false;
			browser.findElement(ShoeStore.lnkAllShoes).click();
			System.out.println("ACTION: Click on the All Shoes link");
			bExists = browser.findElement(ShoeStore.stAllShoesTitle).isDisplayed();
			System.out.println("VERIFY the title is displayed for All Shoes.");
			if (bExists){
				bVisible = browser.findElements(ShoeStore.lnkBrand(iShoeNum)).size() > 0;
				System.out.println("VERIFY the Brand exists for the shoe entry.");
				if (bVisible){
					sBrand = browser.findElement(ShoeStore.lnkRelMonth(1)).getText();
					System.out.println("ACTION: Extract the text from the Brand field for this shoe entry.");
					browser.findElement(ShoeStore.lnkBrand(1)).click();
					System.out.println("ACTION: Click on the Brand link: " + sBrand);
					//verify the title of the page matches the link clicked
					sText = browser.findElement(ShoeStore.stPageTitle).getText();
					System.out.println("ACTION: Extract the text from the Page Title.");
					if(sText.contains(sBrand)){
						System.out.println("Test Case "+ iCase++ + ": -PASS- Expected Result:  \"" + sBrand + "'s Shoes\" Page Title is displayed.");
						iCasePass++;
					} else {
						System.err.println("Test Case "+ iCase++ + ":-FAIL - Expected Result:  \"" + sBrand + "'s Shoes\" Page Title is displayed.\n\t\t\tActual Result: The displayed title is: " + sText);
						iCaseFail++;
					}
					bExists = browser.findElements(ShoeStore.objIndShoeEntry(iShoeNum)).size() > 0;
					System.out.println("VERIFY there is at least one shoe listed.");
					if (!bExists)
						System.err.println("Test Case "+ iCase++ + ":-FAIL - Expected Result:  There should be at least one shoe listed.\n\t\t\tActual Result: There are no shoes listed when clicking the Brand for " + sBrand);
					while (bExists && bMatch){
						sText = browser.findElement(ShoeStore.lnkBrand(iShoeNum++)).getText();
						System.out.println("ACTION: Extract the text from the Brand field for this shoe entry.");
						if (!sText.equals(sBrand))
							bMatch=false;
						bExists = browser.findElements(ShoeStore.objIndShoeEntry(iShoeNum)).size() > 0;
						System.out.println("VERIFY there is another shoe listed.");
					}
					if (bMatch) {
						System.out.println("Test Case "+ iCase++ + ": -PASS- Expected Result:  A total of " + Integer.toString(iShoeNum-1) + " shoe(s) all have the correct Brand of " + sBrand + ".");
						iCasePass++;
					} else {
						System.err.println("Test Case "+ iCase++ + ":-FAIL - Expected Result:  A total of " + Integer.toString(iShoeNum-1) + " shoe(s) all have the correct Brand of " + sBrand + ".\n\t\t\tActual Result: Not all shoes have the correct Brand.  One is showing: " + sText);
						iCaseFail++;
					}
				} else {
					System.err.println("TEST CASE "+ iCase++ + ":"+ ShoeStore.sMonth + "-Shoe Entry #" + iShoeNum + "-FAIL - Expected Result:  Shoe entry contains a Brand Name. \n\tActual Results:  Brand Name field for shoe entry is completely missing.");
					iCaseFail++;
				}
			} else {
				System.err.println("Error: The All Shoes page did not display as expected.");
			}		
			browser.findElement(ShoeStore.lnkHome).click();
			System.out.println("ACTION: Click on the Home link.");
				
		} catch (Exception e) {
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
