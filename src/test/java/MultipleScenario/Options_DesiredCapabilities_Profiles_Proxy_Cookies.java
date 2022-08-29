package MultipleScenario;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class Options_DesiredCapabilities_Profiles_Proxy_Cookies {
	
	WebDriver driver;
	
	@Test (enabled = false)
	public void DisableChromeNotification() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "C:\\BrowserDrivers\\chromedriver_win32\\chromedriver.exe");		
		
		// Create object of HashMap Class
		Map<String, Object> prefs = new HashMap<String, Object>();
		              
		// Set the notification setting it will override the default setting
		prefs.put("profile.default_content_setting_values.notifications", 2);

		// Create object of ChromeOption class
		ChromeOptions options = new ChromeOptions();

		// Set the experimental option
		options.setExperimentalOption("prefs", prefs);

		// pass the options object in Chromedriver initialization	
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10L, TimeUnit.SECONDS);
			
		driver.get("http:facebook.com");
		driver.findElement(By.id("email")).sendKeys("anand.vn100@gmail.com");
		driver.findElement(By.id("pass")).sendKeys("Kir2@chicago");
		driver.findElement(By.id("loginbutton")).click();
		
		driver.findElement(By.name("q")).sendKeys("Entererd in Facebook");
	}
	
	@Test (enabled = false)
	public void DisabledExtensionsAndPopups() throws InterruptedException {
		  System.setProperty("webdriver.chrome.driver", "C:\\BrowserDrivers\\chromedriver_win32\\chromedriver.exe");
		  
		  //Instance of ChromeOptions
		  ChromeOptions options = new ChromeOptions();
		  //Arguments to disable developer mode extensions pop-up
		  options.addArguments("--disable-extensions");
		  //Arguments to disable pop-ups displayed on chrome
		  options.addArguments("--disable-popup-blocking");

		  //WebDriver starts by passion options as parameter
		  WebDriver driver = new ChromeDriver(options);
		  
		  driver.get("https://www.inviul.com");
		  
		  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		  driver.manage().window().maximize();		  
		  System.out.println(driver.getTitle());		  
		  driver.close();
		  
	  }
	  
	@Test (enabled = false)
	public void SSLHandling_FF() throws InterruptedException {
		  System.setProperty("webdriver.gecko.driver", "C:\\BrowserDrivers\\geckodriver-v0.24.0-win64\\geckodriver.exe");
		  
		  //Create a new Firefox profile manually or use default profile
		  
		  //Create instance of ProfilesIni
		  ProfilesIni init = new ProfilesIni();
		  
		  //Create instance of FirefoxProfile and use default profile. We can also create profile and use it
		  //Created a new Firefox profile
		  FirefoxProfile ffProfile = init.getProfile("SeleniumFFProfile"); 		  
		  //FirefoxProfile ffProfile = init.getProfile("default");	
		  
		  ffProfile.setPreference("browser.popups.showPopupBlocker", false);
		  
		  //Below 2 line will handle SSL certificate error.
		  ffProfile.setAcceptUntrustedCertificates(true);
		  ffProfile.setAssumeUntrustedCertificateIssuer(false);
		  
		  //Create instance of FirefoxOptions class
		  FirefoxOptions options = new FirefoxOptions();
		  options.setProfile(ffProfile);
		  
		  //Passing FirefoxProfile instance in driver initialization is deprecated
		  //WebDriver driver = new FirefoxDriver(ffProfile); -->Old way which is deprecated
		  
		  //We now need to pass FirefoxOptions instance in driver initialization as constructor.
		  WebDriver driver = new FirefoxDriver(options);		  
		  
		  driver.get("https://cacert.org/");
		  
		  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);		  
		  driver.manage().window().maximize();		  
		  System.out.println(driver.getTitle());
		  WebElement headingEle = driver.findElement(By.cssSelector(".story h3"));
		  //Validate heading after accepting untrusted connection
		  System.out.println(headingEle.getText());
		  String expectedHeading = "Are you new to CAcert?";
		  Assert.assertEquals(headingEle.getText(), expectedHeading);
		  
		  Thread.sleep(5000);
		  
		 // driver.close();
		  
	  }
	  
	@Test (enabled = false)
	public void SSLHandling_Chrome() throws InterruptedException {
		  System.setProperty("webdriver.chrome.driver", "C:\\BrowserDrivers\\chromedriver_win32\\chromedriver.exe");
		  
		  //Create instance of DesiredCapabilities class which adds capabilities to Chrome browser.
		  DesiredCapabilities cap = new DesiredCapabilities();
		  //Below 2 line will handle SSL certificate error.
		  cap.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
		  cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				  
		  //Create instance of ChromeOptions class
		  ChromeOptions options = new ChromeOptions();
		  options.merge(cap);
		  
		  //Passing DesiredCapabilities instance in driver initialization is deprecated
		  //WebDriver driver = new ChromeDriver(cap); -->Old way which is deprecated
		  
		  //We now need to pass FirefoxOptions instance in driver initialization as constructor.
		  WebDriver driver = new ChromeDriver(options);		  
		  
		  driver.get("https://cacert.org/");
		  
		  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);		  
		  driver.manage().window().maximize();		  
		  System.out.println(driver.getTitle());
		  
		  Thread.sleep(5000);
		  
		  driver.close();
		  
	  }
	
	@Test (enabled = false)
	public void SSLHandling_IE() throws InterruptedException {
		  System.setProperty("webdriver.ie.driver", "C:\\BrowserDrivers\\IEDriverServer_x64_3.14.0\\IEDriverServer.exe");
		  
		  //Create instance of DesiredCapabilities class which adds capabilities to ie browser.
		  DesiredCapabilities cap = new DesiredCapabilities();
		  //Below 2 line will handle SSL certificate error.
		  cap.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
		  cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				  
		  //Create instance of InternetExplorerOptions class
		  InternetExplorerOptions options = new InternetExplorerOptions();
		  options.merge(cap);
		  
		  //Passing DesiredCapabilities instance in driver initialization is deprecated
		  //WebDriver driver = new InternetExplorerDriver(cap); -->Old way which is deprecated
		  
		  //We now need to pass FirefoxOptions instance in driver initialization as constructor.
		  WebDriver driver = new InternetExplorerDriver(options);		  
		  
		  driver.get("https://cacert.org/");
		  
		  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);		  
		  driver.manage().window().maximize();		  
		  System.out.println(driver.getTitle());
		  
		  Thread.sleep(5000);
		  
		  driver.close();
		  
	  }
	
	@Test (enabled = false)
	public void ChromeOptions_Usage() throws InterruptedException {
		  System.setProperty("webdriver.chrome.driver", "C:\\BrowserDrivers\\chromedriver_win32\\chromedriver.exe");
		  
		  //Create instance of ChromeOptions class
		  ChromeOptions options = new ChromeOptions();
		  
		  options.addArguments("disable-infobars");
		  options.addArguments("start-maximized");
		  //options.addArguments("--headless");
		  //options.addArguments("--disable-gpu");
		
		  WebDriver driver = new ChromeDriver(options);		  
		  
		  driver.get("https://facebook.com");
		  
		  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);		  
		  		  
		  System.out.println(driver.getTitle());
		  
		  Thread.sleep(5000);
		  
		  //driver.close();
		  
	  }
	
	@Test (enabled = false)
	public void ProxyHandle_FF() throws InterruptedException {
		  System.setProperty("webdriver.gecko.driver", "C:\\BrowserDrivers\\geckodriver-v0.24.0-win64\\geckodriver.exe");
		  
		  //Create instance of ProfilesIni
		  ProfilesIni init = new ProfilesIni();
		  
		  //Create instance of FirefoxProfile and use default profile. We can also create profile and use it
		  //Created a new Firefox profile
		  FirefoxProfile ffProfile = init.getProfile("SeleniumFFProfile"); 		  
		  //FirefoxProfile ffProfile = init.getProfile("default");	
		  
		  ffProfile.setPreference("browser.popups.showPopupBlocker", false);
		  
		  //Below 2 line will handle SSL certificate error.
		  ffProfile.setAcceptUntrustedCertificates(true);
		  ffProfile.setAssumeUntrustedCertificateIssuer(false);
		  
		//Add below code to Handle Proxy
		  Proxy p = new Proxy();
		  //Set HTTP Port to 7777. This is as per our requirement
		  p.setHttpProxy("localhost:7777");
		  //Create desired Capability object
		  DesiredCapabilities cap=new DesiredCapabilities();		 
		  //Pass proxy object p
		  cap.setCapability(CapabilityType.PROXY, p);		  
		  
		  //Create instance of FirefoxOptions class. Set the profile and merge with cap
		  FirefoxOptions options = new FirefoxOptions();
		  options.setProfile(ffProfile);
		  options.merge(cap);
				  
		  //We now need to pass FirefoxOptions instance in driver initialization as constructor.
		  WebDriver driver = new FirefoxDriver(options);		  
		  
		  driver.get("https://cacert.org/");
		  
		  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);		  
		  driver.manage().window().maximize();		  
		  System.out.println(driver.getTitle());
		  WebElement headingEle = driver.findElement(By.cssSelector(".story h3"));
		  //Validate heading after accepting untrusted connection
		  System.out.println(headingEle.getText());
		  String expectedHeading = "Are you new to CAcert?";
		  Assert.assertEquals(headingEle.getText(), expectedHeading);
		  
		  Thread.sleep(5000);
		  
		  driver.close();
	}
	
	@Test (enabled = false)
	public void Cookies_Example() throws InterruptedException {
		  System.setProperty("webdriver.chrome.driver", "C:\\BrowserDrivers\\chromedriver_win32\\chromedriver.exe");
		  
		  //Create instance of DesiredCapabilities class which adds capabilities to Chrome browser.
		  DesiredCapabilities cap = new DesiredCapabilities();
		  //Below 2 line will handle SSL certificate error.
		  cap.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
		  cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				  
		  //Create instance of ChromeOptions class
		  ChromeOptions options = new ChromeOptions();
		  options.merge(cap);
		  		  
		  options.addArguments("disable-infobars");
		  options.addArguments("start-maximized");
		  		
		  WebDriver driver = new ChromeDriver(options);		  
		  
		  driver.get("https://cacert.org/");
		  
		  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);		  
		  		  
		  System.out.println(driver.getTitle());
		  
		  System.out.println(driver.manage().getCookies());
		  System.out.println(driver.manage().getCookies().size());
		  //This is not the cookie name
		  //String cookieName = "cacert=at503mpatu686vo4vql8to91l1; path=/; domain=www.cacert.org;secure;"; 
		  //System.out.println(driver.manage().getCookieNamed(cookieName));
		  
		  driver.manage().deleteAllCookies();
		  System.out.println(driver.manage().getCookies());
		  
		  
		  Thread.sleep(5000);
		  
		  driver.close();
		  
	  }
	
	@Test (enabled=false)
	public void deleteCookieNamedExample()
	{
		System.setProperty("webdriver.chrome.driver", "C:\\BrowserDrivers\\chromedriver_win32\\chromedriver.exe");
		driver= new ChromeDriver();
		String URL="http://www.flipkart.com";
		driver.navigate().to(URL);
		System.out.println(driver.manage().getCookies());
		System.out.println(driver.manage().getCookies().size());
		//driver.manage().deleteCookieNamed("__utmb");
		driver.manage().deleteAllCookies();
		System.out.println(driver.manage().getCookies());
		System.out.println(driver.manage().getCookies().size());
		
		driver.close();
	}
	
	/*@AfterMethod
	public void tearDown(ITestResult result) throws IOException {
		
		if(ITestResult.FAILURE==result.getStatus()) {			
			TakesScreenshot ts = (TakesScreenshot) driver;
			File source = ts.getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(source, new File("./Screenshots/"+result.getName()+".png"));
			System.out.println("Screenshot taken for "+result.getName());
		}
		
		//driver.close();
	}*/
	
	
	


}
