package testbase;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.xml.XmlTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentReporter;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.sun.org.apache.bcel.internal.classfile.Method;

public class BaseTest {
	final static String workingdir =System.getProperty("user.dir");
	final static String filePath ="\\test-output\\MyReport.html";
	public static String path = workingdir+filePath;
	public static ExtentReports extent;
	public static ExtentTest extentTest;
	public static ExtentSparkReporter sparkReporter = null;
	public static ThreadLocal <ExtentTest> parentTest  = new ThreadLocal<ExtentTest>();
	public static ThreadLocal <ExtentTest> childTest  = new ThreadLocal<ExtentTest>();
	public static ThreadLocal <ExtentTest> childTestNew  = new ThreadLocal<ExtentTest>();
	
	@BeforeSuite
	public void beforeSuite() {
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyy HH-mm-ss");
		Date date = new Date();
		String actDate = format.format(date);
		String reportPath = System.getProperty("user.dir")+"/Reports/ExecutableReport_"+actDate+".html";
		sparkReporter = new ExtentSparkReporter(reportPath);	
		sparkReporter.config().setEncoding("utf-8");
		System.out.println("Extent Report location initialized . . .");
		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);
		extent.setSystemInfo("Application", "LiveSwitch");
		extent.setSystemInfo("Environment URL", "https://www.liveswitch.com");
		extent.setSystemInfo("Operating System", System.getProperty("os.name"));
		extent.setSystemInfo("User Name", System.getProperty("user.name"));
		System.out.println("System Info. set in Extent Report");
		
	}
	
	@BeforeTest
	public synchronized void beforeTest(XmlTest method) {
		ExtentTest test = extent.createTest(method.getName());
		parentTest.set(test);
	}
	@BeforeClass
	public synchronized void beforeClass(ITestContext result) {
		ExtentTest testClass = parentTest.get().createNode(getClass().getSimpleName());
		childTest.set(testClass);
	}
	
	@BeforeMethod
	public void setup(ITestResult results) {
		System.out.println("Hello ::  "+results.getMethod().getMethodName());
		ExtentTest testMethod = childTest.get().createNode(results.getMethod().getMethodName());
		childTestNew.set(testMethod);
	}
	
	public static String getScreenshot(RemoteWebDriver driver, String screenshotName) throws IOException {
		
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date(0));
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		String destination = System.getProperty("user.dir")+"/screenshots"+screenshotName+dateName+".png";
		File finaldDestination = new File(destination);
		FileUtils.copyFile(source, finaldDestination);
		return destination;
		
	}
	
	@AfterMethod
	public void tearDown(ITestResult results) {
		if(results.getStatus() == ITestResult.FAILURE) {
			ExtentFactory.getInstantane().getExtent().log(Status.FAIL, "Test Case: "+results.getMethod().getMethodName()+" is Failed");
			childTestNew.get().log(Status.FAIL, "Test Case: "+results.getMethod().getMethodName()+" is Failed");
			childTestNew.get().fail(results.getThrowable());
			File src =  ((TakesScreenshot)DriverFactory.getInstance().getDriver()).getScreenshotAs(OutputType.FILE);
			SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyy HH-mm-ss");
			Date date = new Date();
			String actDate = format.format(date);
			String screenshot = System.getProperty("user.dir")+"/Reports/Screenshots/"+actDate+".jpeg";
			File dest = new File(screenshot);
			try {
				FileUtils.copyFile(src, dest);
			}
			catch(IOException e) {
				e.printStackTrace();
			}
			try {
				ExtentFactory.getInstantane().getExtent().addScreenCaptureFromPath(screenshot,"Test Case Failure Screenshot");
				ExtentFactory.getInstantane().removeExtentObject();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		else if(results.getStatus() == ITestResult.SKIP) {
			ExtentFactory.getInstantane().getExtent().log(Status.FAIL, "Test Case: "+results.getMethod().getMethodName()+" is Skipped");
			childTestNew.get().log(Status.FAIL, "Test Case: "+results.getMethod().getMethodName()+" is Skipped");
			childTestNew.get().skip(results.getThrowable());
		}
		else if(results.getStatus() == ITestResult.SUCCESS) {
			childTestNew.get().pass("Test Passed");
		}
		extent.flush();
		DriverFactory.getInstance().closeBrowser();
		
	}
	
	@AfterSuite
	public void testDown() {
		
	}
	@AfterClass
	public void afterClass() {
		extent.flush();
	}
}
