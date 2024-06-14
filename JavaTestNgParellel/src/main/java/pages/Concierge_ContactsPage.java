package pages;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Concierge_ContactsPage {
	private WebDriver driver;

	private By txtNameSearchBox= By.xpath("//input[starts-with(@id, 'input-')]");
	
	
	private By btnDelete = By.xpath("(//i[@role='button'])[1]");
	private By btnDeleteConfirm = By.xpath("//span[normalize-space()='Delete']");
	
	private By tbRowConacts= By.xpath("//div[@class='v-table__wrapper']//table/tbody/tr");
	public Concierge_ContactsPage(WebDriver driver) throws IOException {
	    	this.driver = driver;
	 }
	public void clickContactsTab() throws InterruptedException {
		driver.get("https://app.concierge.liveswitch.com/contacts");
		Thread.sleep(5000);
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
	 public boolean deleteContact(String name) throws InterruptedException {
		 
		 driver.findElement(txtNameSearchBox).sendKeys(name);
		 driver.findElement(txtNameSearchBox).sendKeys(Keys.TAB);
		 Thread.sleep(4000);
		 driver.findElement(btnDelete).click();
		 Thread.sleep(4000);
		 if(driver.findElement(btnDeleteConfirm).isDisplayed()) {
			 driver.findElement(btnDeleteConfirm).click();
			 Thread.sleep(3000);
			 return true;
		 }
		 else
			 return false;
		 

	 }
	 
}
