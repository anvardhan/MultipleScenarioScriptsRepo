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

public class ExtentReportClass2 extends BaseClass {
		
	@Test
	public void GoogleTitleTest2 () throws IOException {
		
		logger = extent.createTest("GoogleTitleTest2", "Title Test from Class2");
				
		logger.info("Getting Actual Title from the Google Home page-Class2");
		String actualTitle = driver.getTitle();
		
		logger.info("Verifying Title of the Google Home page-Class2");
		Assert.assertTrue(actualTitle.contains("Google"));
		
		if(actualTitle.contains("Google")) {
			logger.pass("Title matched-Class2");
		} else {
			//logger.fail("Title not matched");
			//logger.addScreenCaptureFromPath(Utilities.getScreenshot(driver, "GoogleTitleTest"));
			logger.fail("Title not matched-Class2", MediaEntityBuilder.createScreenCaptureFromPath("GoogleTitleTest.png").build());
			//logger.addScreenCaptureFromPath("GoogleTitleTest.png");
			Assert.fail();
		}
		
	}
	
	@Test(enabled = true)
	public void GoogleCurrentUrlTest2 () {
		logger = extent.createTest("GoogleCurrentUrlTest2", "URL Test from Class2");
		
		logger.info("Getting Actual Url from the Google Home page-Class2");
		String actualUrl = driver.getCurrentUrl();
		
		logger.info("Verifying URL of the Google Home page-Class2");
		Assert.assertTrue(actualUrl.contains("www.google.com"));
		logger.pass("URL Matched-Class2");
	}

}
