package pages;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Contacts_ContactsPage {
	private WebDriver driver;

	private By btnAddContacts = By.xpath("//span[normalize-space()='Add Contact']");
	private By txtFirsName = By.xpath("(//input)[1]");
	private By txtLastName = By.xpath("(//input)[2]");
	private By txtCompanyName = By.xpath("(//input)[3]");
	private By txtEmail = By.xpath("(//input)[4]");
	private By txtPhoneNo = By.xpath("(//input)[5]");
	
	private By btnSaveContacts = By.xpath("//span[normalize-space()='Save']");
	
	
	private By tbRowConacts= By.xpath("//div[@class='v-data-table__wrapper']//table/tbody/tr");
	public Contacts_ContactsPage(WebDriver driver) throws IOException {
	    	this.driver = driver;
	 }
	public void clickContactsTab() {
		driver.get("https://app.contact.liveswitch.com/contacts");
	}
	public void addConatacts(String fn,String ln,String cn,String mail,String phone ) throws InterruptedException {
		clickContactsTab();
		Thread.sleep(8000);
		driver.findElement(btnAddContacts).click();
		Thread.sleep(5000);
		driver.findElement(txtFirsName).sendKeys(fn);
		driver.findElement(txtLastName).sendKeys(ln);
		driver.findElement(txtCompanyName).sendKeys(cn);
		driver.findElement(txtEmail).sendKeys(mail);
		driver.findElement(txtPhoneNo).sendKeys(phone);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();",driver.findElement((By.xpath("//span[normalize-space()='Save']"))));
	}
	 public boolean getFirstNameContactsRow(String name) {
		  List<WebElement> contacts = driver.findElements(tbRowConacts);
		  for(WebElement rows: contacts) {
			  List <WebElement> col =rows.findElements(By.tagName("td")); 
			  if(col.get(0).getText().equals(name)) {
				  System.out.println("First Name : "+col.get(0).getText());
				  return true;
			  }
			  System.out.println("First Name : "+col.get(0).getText());
		  }
		  return false;
	}
	 
}
