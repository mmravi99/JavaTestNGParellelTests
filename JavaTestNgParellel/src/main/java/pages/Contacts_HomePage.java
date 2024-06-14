package pages;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class Contacts_HomePage{
private WebDriver driver;
    
    // Locators
	
	private By txtUsername = By.xpath("//input[@id='1-email']");
    private By txtPassword = By.xpath("//input[@id='1-password']");
    private By btnSignIn = By.xpath("//button[@id='1-submit']");
    
    public Contacts_HomePage(WebDriver driver) throws IOException {
    	this.driver = driver;
    }
    
    public String  navigateToSigninPage(String url) throws InterruptedException, IOException {
    	driver.get(url);
    	Thread.sleep(3000);
    	return driver.getTitle();
    	
    }
    public void fillUsername(String un) {
    	driver.findElement(txtUsername).clear();
    	driver.findElement(txtUsername).sendKeys(un);
    }
    public void fillPassword(String pwd) {
    	driver.findElement(txtPassword).clear();
    	driver.findElement(txtPassword).sendKeys(pwd);
    }
    public void clickSigIn() {
    	driver.findElement(btnSignIn).click();
    }
    
    public String signIn(String un,String pwd) throws InterruptedException, IOException {
    	fillUsername(un);
    	fillPassword(pwd);
    	clickSigIn();
    	//captureScreenShot(driver);
    	Thread.sleep(8000);
    	return driver.getTitle();
    	
    }
    
    

}
