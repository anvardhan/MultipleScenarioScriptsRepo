package helper;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class BaseClass {
	
	public WebDriver driver;
	public Properties prop;
	
	public static ExtentHtmlReporter htmlReporter;
	public static ExtentReports extent;
	public static ExtentTest logger;
	
	@BeforeMethod
	public void setUp() throws IOException {
		prop=Utilities.readConfig();
		driver = Utilities.openBrowser();		
		//driver.get(prop.getProperty("url"));
		driver.get("https://www.google.com");
	}
	
	@AfterMethod
	public void tearDown(ITestResult result) throws IOException {
		
		if(result.getStatus()==ITestResult.FAILURE) {
			logger.log(Status.FAIL, "Test Case FAILED is "+result.getName()); //To add test name in the report
			logger.log(Status.FAIL, "Error of the failed Test is: "+result.getThrowable());
			
			String screenshotPath =  Utilities.getScreenshot(driver, result.getName());
			
			logger.addScreenCaptureFromPath(screenshotPath); //adding screenshot in the report for failed test case.
						
		} else if (result.getStatus()==ITestResult.SKIP) {
			logger.log(Status.SKIP, "Test Case SKIPPED is "+result.getName());
		} else if (result.getStatus()==ITestResult.SUCCESS) {
			logger.log(Status.PASS, "Test Case PASSED is "+result.getName());
		}		
		
		driver.close();
	} 	
	
	@BeforeTest
	public static void setExtent() {
		
		Date date = new Date();
		
		String currentDate = date.toString().replace(" ", "_").replace(":", "_");
		
				
		//Create ExtentHtmlReporter object and Specify path for the report
		htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir")+"/reports/myExtentReport-"+currentDate+".html");
		
		htmlReporter.config().setDocumentTitle("Automation Execution Report"); //Title of the report
		htmlReporter.config().setReportName("Functional And Regression Testing"); //Name of the report
		htmlReporter.config().setTheme(Theme.DARK); //Theme
		
		//Create ExtentReports object and attach it with ExtentHtmlReporter object
		extent = new ExtentReports();		
		extent.attachReporter(htmlReporter);
		//Pass General details
		extent.setSystemInfo("Host Name", "Local Host");
		extent.setSystemInfo("Environemnt", "QA");
		extent.setSystemInfo("OS", "Chrome");
		extent.setSystemInfo("Tester Name", "Anand");	
	}
	
	@AfterTest
	public void endExtent() {
		//Write test to report
		extent.flush();
	}

}
