package scripts;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import applibs.Setup;
import applibs.ShoeStore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * The purpose of this script is to click on the All Shoes link and verify the following info for each shoe entry: 
 * 		1.  Each shoe should display a small Blurb  
 * 		2.  Each shoe should display an image 
 * 		3.  Each shoe should have a suggested price pricing.
 * 				a.  assumption that pricing starts with a "$".
 * 				b.  assumption that pricing will have 2 decimal places.
 * 
 * Additional verifications:
 * 		4.  The page title corresponds to the "All Shoes" link.
 * 		5.  Verify a Name exists for each shoe entry
 * 		6. 	Verify a Brand exists for each shoe entry
 *  	7.  Verify the email notification field exists for entries with future release months
 *  			a. verify error message upon invalid email entry.
 *  			b. verify correct message upon valid email entry.
 *  
 * @author Jennifer Lanham
 *
 */
public class AllShoeList {
	
	public String sScriptName = "AllShoeList";
	public String sEmail = "jane_doe@email.com";
	public int iCurrMonth, iCase=1, iCasePass=0, iCaseFail=0;
	public WebDriver browser = new FirefoxDriver ();
	
	@Before
	public void setUp() throws Exception {
		browser.manage().window().maximize();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Setup.start = new Date();
		Setup.startScript(sScriptName, Setup.start);
		String sToday = df.format(Setup.start);
		String [] splitDate = sToday.split("/");
		iCurrMonth = Integer.parseInt(splitDate[1]);
	}
	
	@Test
	public void test() throws Exception {
		try{
			String sRelMonth;
			int iRelMonth;
			String sPageTitle;
			
			Setup.openURL(browser, ShoeStore.sURL);
			
			//PAGE TITLE
			//Click on Month Link; Verify title - extract month from Title and compare to link clicked	
			browser.findElement(ShoeStore.lnkAllShoes).click();
			System.out.println("ACTION: Click on the All Shoes link");
			sPageTitle = browser.findElement(ShoeStore.stAllShoesTitle).getText();
			System.out.println("ACTION: Extract the text from the page title.");
			if (!sPageTitle.equals(ShoeStore.sAllShoes)){
				System.err.println("TEST CASE " + iCase++ + ":-FAIL - Expected Result:  Page title shows \"" + ShoeStore.sAllShoes + "\". \n\tActual Results:  Page title shows" + sPageTitle);
				iCaseFail++;
			} else {
				System.out.println("TEST CASE " + iCase++ + ":-PASS- Expected Result:  Page title shows \"" + ShoeStore.sAllShoes + "\"." );
				iCasePass++;
			}

			//SHOE LIST
			int iShoeNum = 1;
			String sText = "";
			Boolean bExists = false;
			Boolean bVisible = false;
			bExists = browser.findElements(ShoeStore.objIndShoeEntry(iShoeNum)).size() > 0;
			System.out.println("VERIFY there is at least one shoe listed.");
			if (!bExists)
				System.out.println(ShoeStore.sMonth + " does not contain a new shoe list.");
			while (bExists){

				//DESCRIPTION
				//Verifying the shoe entry contains a description
				bVisible = ShoeStore.verifyFieldExists(browser, "Description", iShoeNum);
				if (bVisible){
					if (ShoeStore.isFieldEmpty(browser, "Description", iShoeNum)){
						System.err.println("TEST CASE "+ iCase++ + ": Shoe #"+ iShoeNum + "-FAIL - Expected Result:  Shoe entry contains Description; \n\tActual Results:  Description is missing the Description.");
						iCaseFail++;
					} else {
						System.out.println("TEST CASE "+ iCase++ + ": Shoe #"+ iShoeNum + "-PASS- Expected Result:  Shoe entry contains Description.");
						iCasePass++;
					}
				} else {
					System.err.println("TEST CASE "+ iCase++ + ": Shoe #"+ iShoeNum + "-FAIL - Expected Result:  Shoe entry contains Description; \n\tActual Results:  Description is missing the Description.");
					iCaseFail++;
				}
				//IMAGE
				//Verifying the shoe entry has an image
				//disconnect from wifi and see if image shows up see behavior
				Boolean bImage = false;
				bImage = browser.findElements(ShoeStore.imgShoePic(iShoeNum)).size() > 0;
				System.out.println("VERIFY the shoe image exists for the shoe entry.");
				if (bImage) {
					System.out.println("TEST CASE "+ iCase++ + ": Shoe #"+ iShoeNum + "-PASS- Expected Result:  An image exists for this entry.");
					iCasePass++;
				} else {
					System.err.println("TEST CASE "+ iCase++ + ": Shoe #"+ iShoeNum + "-FAIL - Expected Result:  An image exists for this entry.\n\tActual Result: The image is missing for this entry.");
					iCaseFail++;
				}
				
				//PRICE
				//Verifying each shoe entry has a price
				bVisible = ShoeStore.verifyFieldExists(browser, "Price", iShoeNum);
				if (bVisible){
					sText = browser.findElement(ShoeStore.stPrice(iShoeNum)).getText();
					System.out.println("ACTION: Extract the text from the Price field for this shoe entry.");
					//Verify it isn't empty
					if(ShoeStore.isFieldEmpty(browser, "Price", iShoeNum)){
						System.err.println("TEST CASE "+ iCase++ + ": Shoe #"+ iShoeNum + "-FAIL - Expected Result:  Price field is not empty. \n\tActual Results: Price field is empty for this shoe entry.");
						iCaseFail++;
					} else {
						System.out.println("TEST CASE "+ iCase++ + ": Shoe #"+ iShoeNum + "-PASS- Expected Result:  Price field is not empty.");
						iCasePass++;
					}
					
					//Verifying the price starts with a "$"
					if (sText.startsWith("$")) {
						System.out.println("TEST CASE "+ iCase++ + ": Shoe #"+ iShoeNum + "-PASS- Expected Result:  The price starts with a $.");
						iCasePass++;
					} else {
						System.err.println("TEST CASE "+ iCase++ + ": Shoe #"+ iShoeNum + "-FAIL - Expected Result:  The price starts with a $. \n\tActual Results:  The price starts with " + sText.charAt(0));
						iCaseFail++;
					}
					
					//Verifying the price has 2 decimal places
					String[] decimal = sText.split("\\.");
					if (decimal[1].length()==2) {
						System.out.println("TEST CASE "+ iCase++ + ": Shoe #"+ iShoeNum + "-PASS- Expected Result:  The price is properly formatted with 2 decimal places.");
						iCasePass++;
					} else {
						System.err.println("TEST CASE "+ iCase++ + ": Shoe #"+ iShoeNum + "-FAIL - Expected Result:  The price is properly formatted with 2 decimal places.\n\tActual Results:  The price is not properly formatted.  ");
						iCaseFail++;
					}
				} else {
					System.err.println("TEST CASE "+ iCase++ + ": Shoe #"+ iShoeNum + "-FAIL - Expected Result:  Price field displays with a valid price. \n\tActual Results: Price field is completely missing for this shoe entry.");
					iCaseFail++;
				}
				
				//BRAND
				//Verifying the brand exists
				bVisible = ShoeStore.verifyFieldExists(browser, "Brand", iShoeNum);
				if (bVisible){
					if (ShoeStore.isFieldEmpty(browser, "Brand", iShoeNum)){
						System.err.println("TEST CASE "+ iCase++ + ":"+ ShoeStore.sMonth + "-Shoe Entry #" + iShoeNum + "-FAIL - Expected Result:  Shoe entry contains a Brand Name. \n\tActual Results:  Brand Name of shoe is missing.");
						iCaseFail++;
					} else {
						System.out.println("TEST CASE "+ iCase++ + ":"+ ShoeStore.sMonth + "-Shoe Entry #" + iShoeNum + "-PASS- Expected Result:  Shoe entry contains a Brand Name.");
						iCasePass++;
					}
				} else {
					System.err.println("TEST CASE "+ iCase++ + ":"+ ShoeStore.sMonth + "-Shoe Entry #" + iShoeNum + "-FAIL - Expected Result:  Shoe entry contains a Brand Name. \n\tActual Results:  Brand Name field for shoe entry is completely missing.");
					iCaseFail++;
				}
				
				//NAME
				//Verifying the name exists
				bVisible = ShoeStore.verifyFieldExists(browser, "Name", iShoeNum);
				if (bVisible){
					if (ShoeStore.isFieldEmpty(browser, "Name", iShoeNum)){
						System.err.println("TEST CASE "+ iCase++ + ":"+ ShoeStore.sMonth + "-Shoe Entry #" + iShoeNum + "-FAIL - Expected Result:  Shoe entry contains a Name; \n\tActual Results:  Name of shoe is missing.");
						iCaseFail++;
					} else {
						System.out.println("TEST CASE "+ iCase++ + ":"+ ShoeStore.sMonth + "-Shoe Entry #" + iShoeNum + "-PASS- Expected Result:  Shoe entry contains a Name.");
						iCasePass++;
					}
				} else {
					System.err.println("TEST CASE "+ iCase++ + ":"+ ShoeStore.sMonth + "-Shoe Entry #" + iShoeNum + "-FAIL - Expected Result:  Shoe entry contains a Name; \n\tActual Results:  Name field for shoe entry is completely missing.");
					iCaseFail++;
				}
					
				//EMAIL REMINDER
				//if the Release month of the shoe is greater than the current month, the email reminder field should be present for the individual shoe fields.
				
				sRelMonth = browser.findElement(ShoeStore.lnkRelMonth(iShoeNum)).getText();
				System.out.println("ACTION: Extract the text from the Release Month field for this shoe entry.");
				iRelMonth = Setup.getMonthValue(sRelMonth);
				
				if (iRelMonth > iCurrMonth){
					bVisible = ShoeStore.verifyFieldExists(browser, "Email", iShoeNum);
					if (bVisible){
						//verify error message after submitting blank email address
						browser.findElement(ShoeStore.btnSubmitThis(iShoeNum)).click();
						System.out.println("ACTION: Click on the Submit Query button.");
						sText = browser.findElement(ShoeStore.stMessage).getText();
						System.out.println("ACTION: Extract the text from the Message displayed.");
						if (sText.equals("Invalid email format. Ex. name@example.com")) {
							System.out.println("TEST CASE "+ iCase++ + ": Shoe #"+ iShoeNum + "-PASS- Expect Result: Invalid email format. Ex. name@example.com ");
							iCasePass++;
						} else {
							System.err.println("TEST CASE "+ iCase++ + ": Shoe #"+ iShoeNum + "-FAIL - Expect Result: Invalid email format. Ex. name@example.com \n\tActual Result:  " + sText);
							iCaseFail++;
						}
											
						//verify error message after submitting invalid email address
						browser.findElement(ShoeStore.tfThisShoeEmail(iShoeNum)).sendKeys("invalid");
						System.out.println("ACTION: Enter an invalid email address.");
						browser.findElement(ShoeStore.btnSubmitThis(iShoeNum)).click();
						System.out.println("ACTION: Click on the Submit Query button.");
						sText = browser.findElement(ShoeStore.stMessage).getText();
						System.out.println("ACTION: Extract the text from the Message displayed.");
						if (sText.equals("Invalid email format. Ex. name@example.com")) {
							System.out.println("TEST CASE "+ iCase++ + ": Shoe #"+ iShoeNum + "-PASS- Expect Result: Invalid email format. Ex. name@example.com ");
							iCasePass++;
						} else {
							System.err.println("TEST CASE "+ iCase++ + ": Shoe #"+ iShoeNum + "-FAIL - Expect Result: Invalid email format. Ex. name@example.com \n\tActual Result:  " + sText);
							iCaseFail++;
						}
					
						//enter valid email; verify successful message
						browser.findElement(ShoeStore.tfThisShoeEmail(iShoeNum)).sendKeys(sEmail);
						System.out.println("ACTION: Enter a valid email address.");
						browser.findElement(ShoeStore.btnSubmitThis(iShoeNum)).click();
						System.out.println("ACTION: Click on the Submit Query button.");
						sText = browser.findElement(ShoeStore.stMessage).getText();
						System.out.println("ACTION: Extract the text from the Message displayed.");
						if (sText.equals("Thanks! We will notify you when this shoe is available at this email: " + sEmail)) {
							System.out.println("TEST CASE "+ iCase++ + ": Shoe #"+ iShoeNum + "-PASS- Expected Result:  The message: Thanks! We will notify you when this shoe is available at this email: " + sEmail + " is displayed.");
							iCasePass++;
						} else {
							System.err.println("TEST CASE "+ iCase++ + ": Shoe #"+ iShoeNum + "-FAIL - Expected Result:  The message: Thanks! We will notify you when this shoe is available at this email: " + sEmail + " is displayed./n/tActual result: Displayed Message:  " + sText);
							iCaseFail++;
						}
					} else {
						System.err.println("TEST CASE "+ iCase++ + ":"+ ShoeStore.sMonth + "-Shoe Entry #" + iShoeNum + "-FAIL - Expected Result:  The Email reminder field should be displayed for future month shoes.\n\tActual Result:  The email reminder field is completely missing from this shoe entry.");
						iCaseFail++;
					}
				} else {
					bExists = browser.findElements(ShoeStore.tfThisShoeEmail(iShoeNum)).size() > 0;
					System.out.println("VERIFY the email address fields does not exist for this shoe entry.");
					if (bExists) {
						System.err.println("TEST CASE "+ iCase++ + ":"+ ShoeStore.sMonth + "-Shoe Entry #" + iShoeNum + "-FAIL - Expected Result:  Month is in the past and does not offer email notification. \n\tActual Result:  Month: " + ShoeStore.sMonth + " is in the past and should not offer email notification.");
						iCaseFail++;
					} else {
						System.out.println("TEST CASE "+ iCase++ + ":"+ ShoeStore.sMonth + "-Shoe Entry #" + iShoeNum + "-PASS- Expected Result:  Month is in the past and does not offer email notification.");
						iCasePass++;					
					}
				}			
				iShoeNum++;
				bExists = browser.findElements(ShoeStore.objIndShoeEntry(iShoeNum)).size() > 0;
				System.out.println("VERIFY there is another shoe listed.");
				
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
