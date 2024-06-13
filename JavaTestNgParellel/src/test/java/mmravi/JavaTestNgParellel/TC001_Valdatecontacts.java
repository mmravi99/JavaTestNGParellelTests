package mmravi.JavaTestNgParellel;

import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import testbase.BaseTest;
import testbase.BrowserFactory;
import testbase.DriverFactory;

public class TC001_Valdatecontacts extends BaseTest{
// code for test cases
	BrowserFactory bf = new BrowserFactory();
	@Test
	public void validateCreateDeleteContacts() {
		
		DriverFactory.getInstance().setDriver(bf.createBrowserInstance("chrome"));
		DriverFactory.getInstance().getDriver().navigate().to("https://www.google.co.in/");
		childTestNew.get().log(Status.INFO, DriverFactory.getInstance().getDriver().getCurrentUrl());
	}
	
}
