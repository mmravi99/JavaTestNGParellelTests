package mmravi.JavaTestNgParellel;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

import pages.Contacts_ContactsPage;
import pages.Contacts_HomePage;
import pages.Concierge_HomePage;
import pages.Concierge_ContactsPage;
import testbase.BaseTest;
import testbase.BrowserFactory;
import testbase.DriverFactory;
import testbase.RetryAnalyzer;
import utils.RandomValues;

public class TC001_Valdatecontacts extends BaseTest{
public TC001_Valdatecontacts() throws IOException {
		super();
		// TODO Auto-generated constructor stub
	}

	// code for test cases
	BrowserFactory bf = new BrowserFactory();
	
	@Test @Parameters("browser")
	public void validateCreateAndDeleteContacts(String br) throws IOException, InterruptedException {
		System.out.println("Browser : "+br);
		DriverFactory.getInstance().setDriver(bf.createBrowserInstance(br));
		Contacts_HomePage homepage = new Contacts_HomePage(DriverFactory.getInstance().getDriver());
		Contacts_ContactsPage contContactsPage = new Contacts_ContactsPage(DriverFactory.getInstance().getDriver());
		Concierge_HomePage consSignPage = new Concierge_HomePage(DriverFactory.getInstance().getDriver());
		Concierge_ContactsPage consContactPage = new Concierge_ContactsPage(DriverFactory.getInstance().getDriver());
		assertEquals(homepage.navigateToSigninPage(getContactsURL()), "Sign In to LiveSwitch Contact");
		System.out.println(homepage.signIn(getUsername(), getPassword()));
		reportLog("INFO", DriverFactory.getInstance().getDriver().getCurrentUrl());
		reportScreenshot("SignIn Page");
		String fn = RandomValues.generateRandomName();
		contContactsPage.addConatacts(fn, RandomValues.generateRandomName(), "Creative Synergies", RandomValues.generateRandomEmail(),
				RandomValues.generateRandomPhoneNumber());
		reportScreenshot("New Contact "+fn+" created in CONTACTS");
		consSignPage.navigateToHomePage(getConciergeURL());
		consContactPage.clickContactsTab();
		reportScreenshot("New Contact "+fn+" created in CONCEIRGE");
		assertTrue(consContactPage.deleteContact(fn));
		reportScreenshot("New Contact "+fn+" deleted in CONCEIRGE");
	}
}