package MultipleScenario;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class JavaScriptExecutorExample {
	
WebDriver driver;
	
	@BeforeMethod
	public void setUp() throws InterruptedException {		
		//System.setProperty("webdriver.chrome.driver", "C:\\BrowserDrivers\\chromedriver_win32\\chromedriver.exe");	
		System.setProperty("webdriver.gecko.driver", "C:\\BrowserDrivers\\geckodriver-v0.24.0-win64\\geckodriver.exe");
		//driver = new ChromeDriver();
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}
	
	@AfterMethod
	public void tearDown() {		
		//driver.close();
	}
	
	@Test(enabled=false)
	 public void JavaScriptTest() throws InterruptedException {
	
		 driver.get("https://www.slideshare.net/");
		JavascriptExecutor js = ((JavascriptExecutor)driver);
		
		js.executeScript("document.getElementById('nav-search-query').value='java script tutorial';");
		Thread.sleep(2000);
		js.executeScript("document.getElementById('login').click();");
		Thread.sleep(2000);
		String username="anand";
		//js.executeScript("document.getElementById('user_login').value='anand';");
		js.executeScript("document.getElementById('user_login').value='"+username+"';");
		js.executeScript("document.getElementById('remember').checked=false;");
		Thread.sleep(2000);
		js.executeScript("document.getElementById('remember').checked=true;");

	 }
	
	 @Test(enabled=true)
	 public void ScrollToElement() throws InterruptedException {
	
		 driver.get("http://manos.malihu.gr/repository/custom-scrollbar/demo/examples/complete_examples.html");
		
		 WebElement element = driver.findElement(By.xpath(".//*[@id='mCSB_3_container']/p[3]"));
		 
		 Thread.sleep(5000);
		 
		 JavascriptExecutor js = (JavascriptExecutor) driver;		 
		 js.executeScript("arguments[0].scrollIntoView(true);",element);
		  
		  // Extract the text and verify		  
		 System.out.println(element.getText());		 
		 Thread.sleep(5000);
		 
		 js.executeScript("scroll(0,800)");

	 }

}
