package testbase;

import com.aventstack.extentreports.ExtentTest;

public class ExtentFactory {
	
	private static ExtentFactory instance = new ExtentFactory();
	ThreadLocal<ExtentTest> extent = new ThreadLocal<ExtentTest>();
	
	private ExtentFactory() {
		
	}
	
	public static ExtentFactory getInstantane() {
		return instance;
	}
	public ExtentTest getExtent() {
		return extent.get();
	}
	public void setExtent(ExtentTest ext) {
		extent.set(ext);
	}
	public void removeExtentObject() {
		extent.remove();
	}
	
	
	
	
	

}
