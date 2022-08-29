package MultipleScenario;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import helper.BaseClass;

public class FrameClass extends BaseClass {
	
	@Test
	public void FrameTest() throws InterruptedException {
		logger = extent.createTest("FrameTest", "FrameTest");
		System.setProperty("webdriver.chrome.driver", "C:\\BrowserDrivers\\chromedriver_win32\\chromedriver.exe");
		
		WebDriver driver = new ChromeDriver();			
		
		driver.get("https://classic.crmpro.com/");
		//driver.get("https://www.tutorialrepublic.com/codelab.php?topic=html&file=specify-dimensions-for-an-iframe");
		driver.manage().window().maximize();
		
		driver.findElement(By.xpath("//*[@id='loginForm']/div/input[1]")).sendKeys("Avardhan");
		driver.findElement(By.xpath("//*[@name='password']")).sendKeys("Acts2019");
		driver.findElement(By.xpath("//*[@id='loginForm']/div/div/input")).click();
		
		//using name attribute of iframe
		//driver.switchTo().frame("mainpanel");
		//Thread.sleep(3000);	
		
		
		WebElement frameElement = driver.findElement(By.xpath("/html/frameset/frame[2]"));
		//WebElement frameElement = driver.findElement(By.xpath("//*[@name='mainpanel']"));
		
		//using iframe WebElement
		driver.switchTo().frame(frameElement);
		
				
		//using index of iframe - if its 2nd iframe then index = 1
		
		//driver.findElement(By.xpath("//*[@id='navmenu']/ul/li[4]/a")).click();
		//driver.switchTo().frame(1);
		Thread.sleep(3000);
		
		//System.out.println(driver.findElement(By.xpath("//*[@name='mainpanel']")).getAttribute("src"));
		
				
		driver.findElement(By.xpath("//*[@id='navmenu']/ul/li[4]/a")).click();
		//Thread.sleep(3000);
		driver.findElement(By.xpath("//*[@id='navmenu']/ul/li[5]/a")).click();
		
		//driver.switchTo().defaultContent();
		
		//driver.findElement(By.xpath("//*[@id='navmenu']/ul/li[4]/a")).click();
		
		//driver.close();	
		
		logger.pass("FrameTest Passed");
		
		driver.close();

		
	/*System.out.println(driver.findElement(By.xpath("//*[@id=\"code-pane\"]/div/div/div/div[6]/div[1]/div/div/div/div[5]/div[9]/pre/span")).getText());
		
		driver.switchTo().frame("preview");
		System.out.println(driver.findElement(By.xpath("/html")).getAttribute("lang"));
		
		driver.switchTo().frame(driver.findElement(By.xpath("/html/body/iframe")));		
		System.out.println(driver.findElement(By.xpath("/html/body/h1")).getText());
		System.out.println(driver.findElement(By.xpath("/html")).getAttribute("lang")); 
		//System.out.println(driver.findElement(By.xpath("//*[@id=\"code-pane\"]/div/div/div/div[6]/div[1]/div/div/div/div[5]/div[9]/pre/span")).getText());
		
		driver.switchTo().defaultContent();
				
		System.out.println(driver.findElement(By.xpath("//*[@id=\"code-pane\"]/div/div/div/div[6]/div[1]/div/div/div/div[5]/div[9]/pre/span")).getText());
		
		driver.close();*/
		
		
	}

}
