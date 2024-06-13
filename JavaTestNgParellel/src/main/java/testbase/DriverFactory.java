package testbase;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

public class DriverFactory {
	private static DriverFactory instance = new DriverFactory();
	private static ThreadLocal <WebDriver> driver = new ThreadLocal<WebDriver>();
	
	
	private DriverFactory() {
	
	}
	
	public static DriverFactory getInstance() {
			return instance;
	}
	
	public WebDriver getDriver() {
		return driver.get();
	}
	public void setDriver(WebDriver dr) {
		driver.set(dr);
	}
	public void closeBrowser() {
		driver.get().quit();
		driver.remove();
	}

}
