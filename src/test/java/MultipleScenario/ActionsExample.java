package MultipleScenario;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ActionsExample {
	
	WebDriver driver;
	
	@BeforeMethod
	public void setUp() throws InterruptedException {		
		//System.setProperty("webdriver.chrome.driver", "C:\\BrowserDrivers\\chromedriver_win32\\chromedriver.exe");
		System.setProperty("webdriver.gecko.driver", "C:\\BrowserDrivers\\geckodriver-v0.24.0-win64\\geckodriver.exe");
		//driver = new ChromeDriver();
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}
	
	@AfterMethod
	public void tearDown() {		
		//driver.close();
	}
	
	@Test(enabled=false)
	public void KeyboardActionsTest() throws InterruptedException {
						
		driver.get("https://www.flipkart.com");
		
		driver.findElement(By.xpath("/html/body/div[2]/div/div/button")).click();
		Thread.sleep(6000);
		
		Actions act = new Actions(driver);	
				
		//CTRL+F5
		//Action a = act.keyDown(Keys.CONTROL).sendKeys(Keys.F5).keyUp(Keys.CONTROL).build();
		//a.perform();
		
		//act.keyDown(Keys.CONTROL).sendKeys(Keys.F5).keyUp(Keys.CONTROL).perform();
		act.keyDown(Keys.CONTROL).sendKeys(Keys.F5).perform();
		Thread.sleep(5000);
		
		//Find element - stale-element exception
		
		
	}
	
	@Test (enabled=false)
	public void MouseMovementTest() throws InterruptedException {
		
		driver.get("https://www.flipkart.com");
		
		driver.findElement(By.xpath("/html/body/div[2]/div/div/button")).click();
		Thread.sleep(1000);
		
		WebElement TVAndAppliance = driver.findElement(By.xpath("//*[@id='container']//*[text()='TVs & Appliances']"));
		WebElement SplitAC = driver.findElement(By.xpath("//*[@id='container']//*[text()='Split ACs']"));
			
		Actions act = new Actions(driver);		
		//act.moveToElement(TVAndAppliance).build().perform();
		act.moveToElement(TVAndAppliance).perform();
		Thread.sleep(2000);
		
		SplitAC.click();
	}
	
	@Test (enabled=true)
	public void seriesOfActions() throws InterruptedException {
		
		driver.get("https://facebook.com");
		
		WebElement fname = driver.findElement(By.xpath("//*[@name='firstname']"));
		
		Actions act = new Actions(driver);
		Action seriesofactions=	act.moveToElement(fname).click()
								   .keyDown(fname,Keys.SHIFT)
								   .sendKeys(fname,"anand")
								   .keyUp(fname,Keys.SHIFT)
								   .doubleClick(fname)
								   .sendKeys(Keys.ENTER)
								   .build();
		
		seriesofactions.perform();
	}
	
	
	

}
