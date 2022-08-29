package testcases;

import org.testng.annotations.Test;

import MultipleScenario.Log4j2Example;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import objectrepository.RediffHomePage;
import objectrepository.RediffLoginPage;
import objectrepository.RediffShoppingPage;
import org.testng.Assert;

public class RediffTest 
{
	WebDriver driver;  
	Logger logger;
	
	@BeforeClass
	  public void setUp() {
		 
	//System.setProperty("webdriver.chrome.driver", "C:\\BrowserDrivers\\chromedriver_win32\\chromedriver.exe");
	//System.setProperty("webdriver.gecko.driver", "C:\\BrowserDrivers\\geckodriver-v0.24.0-win64\\geckodriver.exe");
	//driver = new ChromeDriver();
	//driver = new FirefoxDriver();
				
	//driver.get("http://rediff.com");			
	//driver.manage().window().maximize(); 
	logger = LogManager.getLogger(RediffTest.class.getName());
	}
  
	/*@AfterClass
	  public void afterClass() {		  
		  
	System.out.println("All Tests Completed");
	driver.close();
		  
	} */
	
	@Test (enabled = true)
	public void Logging4jTestRediff() {		
		logger.trace("This is trace message1");
		logger.debug("This is debug message1");
		logger.info("This is informational message1");
		logger.warn("This is warning message1");
		logger.error("This is error message1");
		logger.fatal("This is fatel message1");			
	}
	
	  
	@Test (enabled=true)
	public void rediffTestCase1() throws InterruptedException {
  
		//Crating object of home page
		RediffHomePage rhome = new RediffHomePage(driver);  
			
		rhome.SignIn().click();	
		
		//Creating object of login page		
		RediffLoginPage rlogin = new RediffLoginPage(driver); 
		rlogin.loginRediff("anand100", "password1234");	
		
		//Validation

	}
	
	@Test (enabled=false)
	public void rediffTestCase2() throws InterruptedException {
		
		driver.navigate().to("http://rediff.com");
		//Crating object of home page
		RediffHomePage rhome = new RediffHomePage(driver);  
		rhome.ShoppingLink().click();
	
		//Creating object of Shopping page
		RediffShoppingPage rshopping = new RediffShoppingPage(driver); 
		rshopping.typeSearchAndGo("selenium webdriver");
		Thread.sleep(5000);
		
		String expectedMsg = "No products found for Selenium Webdriver";
		String actualMsg = rshopping.noProductFoundMsg().getText();
		//System.out.println(actualMsg);
		
		//Assert.assertEquals(actualMsg, expectedMsg);
		Assert.assertEquals(actualMsg, expectedMsg,"Actual message is not same as expected msg");
		
		
		/*if (expectedMsg.equalsIgnoreCase(actualMsg))
		{
			System.out.println("Pass");
			Assert.assertTrue(true);
		} else {
			System.out.println("Fail");
			Assert.assertTrue(false);
		}*/
		
	}
 }
