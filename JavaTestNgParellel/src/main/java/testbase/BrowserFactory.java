package testbase;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class BrowserFactory {
	
	public WebDriver createBrowserInstance(String browser) {
		WebDriver driver = null;
		
		if(browser.equals("chrome")) {
			ChromeOptions opt = new ChromeOptions();
			driver = new ChromeDriver(opt);
		}
		else if(browser.equals("firefox")) { 
			driver = new FirefoxDriver();
		}
		else if(browser.equals("edge")) {
			driver = new EdgeDriver();
		}
		driver.manage().window().maximize();
		return driver;
	}

}
