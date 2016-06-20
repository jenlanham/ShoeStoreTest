package scripts;

import applibs.Setup;
import applibs.ShoeStore;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Suite file which ultimately executes the following scripts in order to test the Shoe Store Application:
 * 
 * 1.  MonthlyShoeList.java
 * 2.  ShoeStoreMain.java
 * 3.  AllShoeList.java
 * 4.  BrandShoeList.java
 * 
 * @author Jennifer Lanham
 *
 */
public class SuiteFile {
	public String sSuiteName = "SuiteFile";
	public String sMonth = "";	
	public Date suiteStart;
	public Date suiteFinish;
	
	@Before
	public void setUp() throws Exception {
		Setup.iCaseNum=1;
		suiteStart = new Date();
		Setup.startSuite(sSuiteName, suiteStart);
		
	}

	@Test
	public void test() {

		String [] Months = {
				"January","February","March","April","May","June","July","August","September","October","November","December"};
				
		Class [ ] lcScripts = {
			scripts.MonthlyShoeList.class, 
			scripts.ShoeStoreMain.class,
			scripts.AllShoeList.class,
			scripts.BrandShoeList.class
		};
		
		//Runs the MonthlyShoeList script while cycling through the Months array which allows it to test 
		//each Month link in the application.
		for ( int x = 0 ; x < Months.length ; x ++ ) {
			try {
				ShoeStore.sMonth = Months[x];
				org.junit.runner.JUnitCore.runClasses ( lcScripts [ 0 ] );
			} catch ( Exception e ) {
				System.err.println ("Exception: " + e.getMessage());
			}
		}
		for (int y=1; y<lcScripts.length; y++) {
			try {
				org.junit.runner.JUnitCore.runClasses(lcScripts[y]);
			} catch (Exception e) {
				System.err.println ("Exception: " + e.getMessage());
			}
		}
	}
	
	@After
	public void tearDown() throws Exception {
		suiteFinish = new Date();
		String sSuiteTime = Setup.calcTime(suiteStart, suiteFinish);
		Setup.finishSuite(sSuiteName, sSuiteTime);
	}

}
