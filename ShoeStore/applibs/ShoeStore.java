package applibs;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * This application library contains the object definitions for the Home page of the Shoe Store.  
 * @author Jennifer Lanham
 *
 */
public class ShoeStore {
	public static String sAllShoes = "All Shoes"; // Title for all shoes page
	public static String sMonth = ""; //global variable to keep up with the Month being tested.
	public static String sURL = "https://sleepy-beach-51039.herokuapp.com/"; //URL for the shoe store

	//Home Page objects
	public static By lnkHome = By.xpath("//a[text()='Home']");
	public static By lnkMonth(String sMonth) {  
		return By.xpath("//li/a[text()='" + sMonth + "']");
	}
	public static By lnkAllShoes = By.xpath("//a[@href='/shoes']");
	public static By tfNewShoeEmail = By.xpath("//div/form/input[@id='remind_email_input']");
	public static By tfPromotionalCode = By.xpath("//div/form/input[@id='promo_code_input']");
	public static By btnSubmitEmail = By.xpath("//div/form/input[@id='remind_email_submit']");
	public static By btnSubmitPromo = By.xpath("//div/form/input[@id='promo_code_submit']");
	public static By lnkReportIssue = By.xpath("//div/div/a[text()='Report An Issue']");
	public static By lnkIssues = By.xpath("//div/div/a[text()='Issues']");
	public static By lnkProblemDef = By.xpath("//div/a[text()='Problem Definition']");
	public static By stWelcome = By.xpath("//div/h2[text()='Welcome to Shoe Store!']");
	public static By lstBrand = By.xpath("//dd/select[@id='brand']");
	public static By btnSearch = By.xpath("//input[@id='search_button']");
	public static By stAllShoesTitle = By.xpath("//h2[text()='All Shoes']");
	public static By stPageTitle = By.xpath("//div[@class='title']");
	public static By stMessage = By.xpath("//div[@id='flash']"); //any message from email or promo code entry
	
	//Month Page objects
	public static By stMonthTitle (String sMonth) {
		return By.xpath("//h2[text()=\"" + sMonth + "'s Shoes\"]");
	}

	public By objShoe = By.xpath("//ul[@id='shoe_list']");
	public static By objIndShoeEntry(int i){
		return By.xpath("//ul[@id='shoe_list']/li["+ i + "]");
	}
	
	public static By lnkBrand(int row){
		return By.xpath("//ul[@id='shoe_list']/li["+ row + "]//tr/td[@class='shoe_result_value shoe_brand']");
	}
	
	public static By stName(int row){
		return By.xpath("//ul[@id='shoe_list']/li["+ row + "]//tr/td[@class='shoe_result_value shoe_name']");
	}
	
	public static By stDesc(int row){
		return By.xpath("//ul[@id='shoe_list']/li["+ row + "]//tr/td[@class='shoe_result_value shoe_description']");
	}
	
	public static By stPrice(int row){
		return By.xpath("//ul[@id='shoe_list']/li["+ row + "]//tr/td[@class='shoe_result_value shoe_price']");
	}
	
	public static By lnkRelMonth(int row){
		return By.xpath("//ul[@id='shoe_list']/li["+ row + "]//tr/td[@class='shoe_result_value shoe_release_month']/a");
	}
	
	public static By imgShoePic(int row) {
		return By.xpath("//ul[@id='shoe_list']/li["+ row + "]//tr/td/img");
	}
	
	public static By tfThisShoeEmail (int row) {
		return By.xpath("//ul[@id='shoe_list']/li["+ row + "]//tr/td/form/input[@class='notification_email_input']");
	}
	
	public static By btnSubmitThis (int row) {
		return By.xpath("//ul[@id='shoe_list']/li["+ row + "]//tr/td/form/input[@class='notification_email_submit']");
	}
	
	public static boolean verifyFieldExists(WebDriver browser, String sField, int iShoeNum){
		Boolean bVisible = false;
		String sXpath;
		try {
			sXpath = getXpath(sField,iShoeNum);
			bVisible = browser.findElements(By.xpath(sXpath)).size() > 0;
			System.out.println("VERIFY " + sField + " exists for the shoe entry.");
		} catch (Exception e){
			System.err.println("Exception: " + e.getMessage());
		}
		return bVisible;
	}
	
	public static String getXpath (String sField, int iShoeNum){
		String sXpath, sClass;
		sClass = getClass(sField);
		if (sField.equals("Release Month"))
			sXpath = "//ul[@id='shoe_list']/li["+ iShoeNum + "]//tr/td[@class='" + sClass + "']/a";
		else if (sField.equals("Email"))
			sXpath = "//ul[@id='shoe_list']/li["+ iShoeNum + "]//tr/td/form/input[@class='" + sClass + "']";				
		else 
			sXpath = "//ul[@id='shoe_list']/li["+ iShoeNum + "]//tr/td[@class='" + sClass + "']";
		return sXpath;
	}
	
	public static boolean isFieldEmpty(WebDriver browser, String sField, int iShoeNum)	{
		String sXpath, sText;
		Boolean bIsEmpty = false;
		try {
			sXpath = getXpath(sField,iShoeNum);
			sText = browser.findElement(By.xpath(sXpath)).getText();
			System.out.println("ACTION: Extract the text from the " + sField + " field for this shoe entry.");
			if (sText.isEmpty())
				bIsEmpty = true;
			else
				bIsEmpty = false;			
		} catch (Exception e){
			System.err.println("Exception: " + e.getMessage());
		}
		return bIsEmpty;
	}
	
	public static String getClass(String sField){
		String sClass= "";
		try {
			switch (sField) {
				case "Brand" : 
					sClass = "shoe_result_value shoe_brand";
					break;
				case "Name" :
					sClass = "shoe_result_value shoe_name";
					break;
				case "Description" :
					sClass = "shoe_result_value shoe_description";
					break;
				case "Price" :
					sClass = "shoe_result_value shoe_price";
					break;
				case "Release Month" :
					sClass = "shoe_result_value shoe_release_month";
					break;
				case "Email":
					sClass = "notification_email_input";
					break;
				default:
					System.err.println("ERROR: getClass() - field not recognized.");
			}			
		} catch (Exception e){
			System.err.println("Exception: " + e.getMessage());
		}
		return sClass;
	}	
}
