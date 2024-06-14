package testbase;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

public class BrowserFactory {
	
	public WebDriver createBrowserInstance(String browser) throws MalformedURLException {
		WebDriver driver = null;
		
		if(browser.equals("chrome")) {
			ChromeOptions options = new ChromeOptions();
			//driver = new ChromeDriver(opt);
			
			options.addArguments("--remote-allow-origins=*");
	        driver = new RemoteWebDriver(new URL("http://localhost:4444"), options);
	        driver.manage().window().maximize();
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
