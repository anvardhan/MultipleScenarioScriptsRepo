package MultipleScenario;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import helper.BaseClass;
import helper.Utilities;

public class ExtentReportV4 extends BaseClass {
	
	/*
	 * http://extentreports.com/docs/javadoc/com/aventstack/extentreports/Status.html
	 * http://extentreports.com/docs/versions/4/java/
	 * Extent allows creation of tests, nodes, events and assignment of tags, devices, 
	 * authors, environment values etc. This information can be printed to multiple destinations.
	 * Extent framework follows the Observer pattern and each reporter attached becomes an observer 
	 * and notified of any changes such as creation of a test, assignment of a category to the test, 
	 * adding an event etc.
	 * 
	 * What is Extent Report
	 * HTML Reporting library
	 * ExtentReports is an open-source reporting API for Java and .NET. It creates an interactive 
	 * HTML report of your test session.
	 * 
	 * Selenium provides inbuilt reports using frameworks such as JUnit and TestNG.
	 * Although the built-in reports provide information on the steps that are executed as 
	 * part of the test case, they need more customization to be shared with all the major project 
	 * stakeholders.
	 * Extent Reports is a customizable HTML report developed by Anshoo Arora which can be 
	 * integrated into Selenium WebDriver using JUnit and TestNG frameworks.
	 * Extent report also maintain very nice documentation which will explain every features.
	 * 
	 * 
	 * https://www.swtestacademy.com/extentreports-testng/    - 2.41.2  (com.releventcodes)
	 * https://www.swtestacademy.com/extent-reports-version-3-reporting-testng/    - 3.1.5  (com.avantstack)
	 * 
	 */
	
	//public WebDriver driver;
	//public Properties prop;
	
	//public ExtentHtmlReporter htmlReporter;
	//public ExtentReports extent;
	//public ExtentTest logger;
	
	/*@BeforeTest
	public void setExtent () {
		
		//Create ExtentHtmlReporter object and Specify path for the report
		htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir")+"/test-output/myExtentReport.html");
		
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
	}*/
	
	
	/*@BeforeMethod
	public void setUp() throws IOException {
		prop=Utilities.readConfig();
		driver = Utilities.openBrowser();		
		//driver.get(prop.getProperty("url"));
		driver.get("https://www.google.com");
	}*/
	
	@Test
	public void GoogleTitleTest () throws IOException {
		
		logger = extent.createTest("GoogleTitleTest", "This Test validates Title of the google home page");
				
		logger.info("Getting Actual Title from the Google Home page");
		String actualTitle = driver.getTitle();
		
		logger.info("Verifying Title of the Google Home page");
		Assert.assertTrue(actualTitle.contains("Google"));
		
		if(actualTitle.contains("Google1")) {
			logger.pass("Title matched");
		} else {
			//logger.fail("Title not matched");
			//logger.addScreenCaptureFromPath(Utilities.getScreenshot(driver, "GoogleTitleTest"));
			logger.fail("Title not matched", MediaEntityBuilder.createScreenCaptureFromPath("GoogleTitleTest.png").build());
			//logger.addScreenCaptureFromPath("GoogleTitleTest.png");
			Assert.fail();	
		}
		
	}
	
	@Test(enabled = true)
	public void GoogleCurrentUrlTest () {
		logger = extent.createTest("GoogleCurrentUrlTest","URL Test");
		
		logger.info("Getting Actual Url from the Google Home page");
		String actualUrl = driver.getCurrentUrl();
		
		logger.info("Verifying URL of the Google Home page");
		Assert.assertTrue(actualUrl.contains("www.google.com"));
		logger.pass("URL Matched");
		
	}
	
	@Test(enabled = false)
	public void GoogleLogoTest () {
		logger = extent.createTest("GoogleLogoTest");
		
		Boolean googleLogo = driver.findElement(By.xpath("//*[@id='hplogo']")).isDisplayed();
		
		logger.info("Verifying Google Logo from the Google Home page");
		Assert.assertTrue(googleLogo);
		
	}
	
	@Test (enabled = false)
	public void GoogleSearchElements() throws InterruptedException {	
		logger = extent.createTest("GoogleSearchElements");
		
		driver.findElement(By.xpath("//*[@name='q']")).sendKeys("java tutorial for beginners 2018");
		
		Thread.sleep(5000);
		
		logger.info("Get the list of search element");
		List<WebElement> searchElements = driver.findElements(By.xpath("//ul[@role='listbox']//li/div/div/div/span"));
		//List<WebElement> searchElements = driver.findElements(By.xpath("//ul[@role='listbox']//li/descendant::span"));
		//List<WebElement> searchElements = driver.findElements(By.xpath("//ul[@role='listbox']//li/descendant::div[@class='sbl1']/span"));
		String searchElementtext = null;
		for (int i=0;i<searchElements.size();i++) {
			WebElement searchElement = searchElements.get(i);
			searchElementtext = searchElement.getText();
			
			System.out.println(searchElementtext);
			logger.info("Check if search elment conatains the expected text then click on that element");
			if(searchElement.getText().contains("java eclipse tutorial")) {
				searchElement.click();
				break;
			}
		}
		logger.info("Assert the values to verify expected text");
		Assert.assertTrue(searchElementtext.contains("java eclipse tutorial"));
	}
	
	/*@AfterMethod
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
	} */

}
