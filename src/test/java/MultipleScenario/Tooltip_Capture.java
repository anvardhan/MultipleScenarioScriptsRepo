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
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class Tooltip_Capture {
	
	WebDriver driver;
	Properties prop;	
		
	@Test (enabled = false)
	public void Tooltip_usingTitleAttribute() throws InterruptedException {
		
		System.setProperty("webdriver.chrome.driver", "C:\\BrowserDrivers\\chromedriver_win32\\chromedriver.exe");		
		driver = new ChromeDriver();	
		driver.get("http://demoqa.com/tooltip/");
		driver.manage().window().maximize();	
		
		WebElement toolTip_age = driver.findElement(By.id("age"));
		
		String toolTipAgeText = toolTip_age.getAttribute("title");
		System.out.println("Using title attribute: "+toolTipAgeText);		
		
		driver.close();
	}
	
	@Test (enabled = false)
	public void Tooltip_usingMosueHover() throws InterruptedException {
		// This method should be used when we dont have title attribute.
		
		System.setProperty("webdriver.chrome.driver", "C:\\BrowserDrivers\\chromedriver_win32\\chromedriver.exe");		
		driver = new ChromeDriver();	
		driver.get("https://demoqa.com/tooltip-and-double-click/");
		driver.manage().window().maximize();	
		
		WebElement element = driver.findElement(By.id("tooltipDemo"));
		
		Actions actions = new Actions(driver);
		
		//Now, invoke moveToElement(), this method of Actions class moves the mouse to the middle of the element.
		
		actions.moveToElement(element).perform();
		Thread.sleep(5000);
		
		//getting text here will get the tooltip tex along with text which we see in UI without hover
		//String toolTipText = toolTipElement.getText();
		
		WebElement toolTipElement = driver.findElement(By.className("tooltiptext"));
		String toolTipText = toolTipElement.getText();
		
		System.out.println("Using Actions Class: "+toolTipText);		
		
		driver.close();
	}
	
	@Test (enabled = false)
	public void Tooltip_usingTitleAttribute_guru() throws InterruptedException {
		
		System.setProperty("webdriver.chrome.driver", "C:\\BrowserDrivers\\chromedriver_win32\\chromedriver.exe");		
		driver = new ChromeDriver();	
		driver.get("http://demo.guru99.com/test/social-icon.html");
		driver.manage().window().maximize();	
		
		//Find github element
		WebElement toolTip_age = driver.findElement(By.xpath("//*[@id='top-bar']/div/div/div[2]/div/a[4]"));
		
		String toolTipAgeText = toolTip_age.getAttribute("title");
		System.out.println("Using title attribute: "+toolTipAgeText);		
		
		driver.close();
	}

	@Test (enabled = false)
	public void Tooltip_JqueryPlugin_guru() throws InterruptedException {
		// This method should be used when we dont have title attribute.
		
		System.setProperty("webdriver.chrome.driver", "C:\\BrowserDrivers\\chromedriver_win32\\chromedriver.exe");		
		driver = new ChromeDriver();	
		driver.get("http://demo.guru99.com/test/tooltip.html");
		driver.manage().window().maximize();	
		
		WebElement element = driver.findElement(By.id("download_now"));
		
		Actions actions = new Actions(driver);
		
		actions.moveToElement(element).perform();
		Thread.sleep(5000);		
		
		//Find link object inside tooltip element which comes after hovering mouse over Download now
		
		WebElement linkTextInsideTooltip = driver.findElement(By.xpath("//*[@id='demo_content']/div/div/div/a"));
		//*[@id='demo_content']/div/div/div/a
		
		String linkText = linkTextInsideTooltip.getText();
		
		System.out.println(linkText);
				
		
		driver.close();
	}
}
