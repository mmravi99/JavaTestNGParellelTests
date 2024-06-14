package testbase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
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
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentReporter;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.sun.org.apache.bcel.internal.classfile.Method;

public class BaseTest {
	
	final static String workingdir = System.getProperty("user.dir");
	final static String filePath = "\\test-output\\MyReport.html";
	public static String path = workingdir + filePath;
	public static ExtentReports extent;
	public static ExtentTest extentTest;
	public static ExtentSparkReporter sparkReporter = null;
	public static ThreadLocal<ExtentTest> parentTest = new ThreadLocal<ExtentTest>();
	public static ThreadLocal<ExtentTest> childTest = new ThreadLocal<ExtentTest>();
	public static ThreadLocal<ExtentTest> childTestNew = new ThreadLocal<ExtentTest>();
	Properties properties = new Properties();
    FileInputStream fileInputStream = null;
	public BaseTest() throws IOException {
		fileInputStream = new FileInputStream(System.getProperty("user.dir") +"/src/test/resources/globalsettings.properties");
        properties.load(fileInputStream);
	}

	@BeforeSuite
	public void beforeSuite() {
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyy HH-mm-ss");
		Date date = new Date();
		String actDate = format.format(date);
		String reportPath = System.getProperty("user.dir") + "/Reports/ExecutableReport_" + actDate + ".html";
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
		ExtentTest testMethod = childTest.get().createNode(results.getMethod().getMethodName());
		childTestNew.set(testMethod);
	}

	@AfterMethod
	public void tearDown(ITestResult results) throws IOException {
		if (results.getStatus() == ITestResult.FAILURE) {
			childTestNew.get().fail(results.getThrowable());
			reportScreenshot("Test Failed");
		} else if (results.getStatus() == ITestResult.SKIP) {
			childTestNew.get().skip(results.getThrowable());
		} else if (results.getStatus() == ITestResult.SUCCESS) {
			childTestNew.get().pass("Test Successful");
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

	public static String getScreenshot(WebDriver driver) throws IOException {

		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		String destination = System.getProperty("user.dir") + "/Reports/screenshots/"+ dateName
				+ ".png";
		File finaldDestination = new File(destination);
		FileUtils.copyFile(source, finaldDestination);
		return destination;

	}

	public static void reportLog(String status, String msg) {
		if (status.equals("PASS"))
			childTestNew.get().log(Status.PASS, msg);
		else if (status.equals("FAIL"))
			childTestNew.get().log(Status.FAIL, msg);
		else if (status.equals("INFO"))
			childTestNew.get().log(Status.INFO, msg);

	}
	
	public static void reportScreenshot(String title) throws IOException {
		childTestNew.get().info(title, MediaEntityBuilder.createScreenCaptureFromPath(getScreenshot(DriverFactory.getInstance().getDriver())).build());
	}
	
	public String getContactsURL() {
    	return properties.getProperty("conatctsURL");
	}
    public String getConciergeURL() {
    	return properties.getProperty("conciergeURL");
	}
    public String getBrowser() {
    	return properties.getProperty("browser");
	}
    public String getUsername() {
    	return properties.getProperty("username");
	}
    public String getPassword() {
    	return properties.getProperty("password");
	}
}
