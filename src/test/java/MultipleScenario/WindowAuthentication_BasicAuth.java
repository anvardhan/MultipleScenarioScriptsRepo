package MultipleScenario;
import static org.testng.Assert.assertEquals;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class WindowAuthentication_BasicAuth {
	
	WebDriver driver;
	Properties prop;	
		
	@Test (enabled = false)
	public void WindowBasicAuth_PassingCredential() throws InterruptedException {
				
		System.setProperty("webdriver.chrome.driver", "C:\\BrowserDrivers\\chromedriver_win32\\chromedriver.exe");		
		driver = new ChromeDriver();	
		driver.manage().window().maximize();
		//driver.get("http://the-internet.herokuapp.com/basic_auth");
		//http://userName:password@SiteURL
		
		String userName = "admin";
		String password = "admin";		
		String URL = "http://"+ userName +":"+ password +"@the-internet.herokuapp.com/basic_auth";
		
		//driver.get("http://admin:admin@the-internet.herokuapp.com/basic_auth");
		driver.get(URL);
				
		Thread.sleep(5000);
		System.out.println("Using Alerts: "+driver.getTitle());	
		
		driver.close();
	}
	
	@Test (enabled = false)
	public void WindowBasicAuth_UsingAutoIT_IE() throws InterruptedException {
		
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL, "");
		InternetExplorerOptions options = new InternetExplorerOptions();
		options.merge(cap);	
		
		System.setProperty("webdriver.ie.driver", "C:\\BrowserDrivers\\IEDriverServer_Win32_3.9.0\\IEDriverServer.exe");		
		driver = new InternetExplorerDriver(options);	
		driver.manage().window().maximize();
					
		driver.get("http://the-internet.herokuapp.com/basic_auth");		
		//Thread.sleep(5000);
		
		try {
			Runtime.getRuntime().exec("C:\\Users\\Anand\\Documents\\LearningDoc\\AutoIT\\AutoIT_script\\basic_authIE.exe");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Thread.sleep(5000);
		System.out.println("Using AutoIT: "+driver.getTitle());	
		
		driver.close();
	}
	
	@Test (enabled = false)
	public void WindowBasicAuth_UsingAlerts() throws InterruptedException {
		
		//Not working code
		System.setProperty("webdriver.chrome.driver", "C:\\BrowserDrivers\\chromedriver_win32\\chromedriver.exe");		
		driver = new ChromeDriver();	
		driver.manage().window().maximize();
					
		String userName = "admin";
		String password = "admin";		
		
		driver.get("http://the-internet.herokuapp.com/basic_auth");
		
		Thread.sleep(5000);
		
		Alert alert = driver.switchTo().alert();
		Thread.sleep(3000);
		alert.sendKeys(userName+Keys.TAB.toString()+password);
		alert.accept();
				
		Thread.sleep(5000);
		System.out.println("Pass Credentials in URL: "+driver.getTitle());	
		
		driver.close();
	}
}
