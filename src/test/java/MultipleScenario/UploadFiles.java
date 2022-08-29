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
//import org.sikuli.script.FindFailed;
//import org.sikuli.script.Pattern;
//import org.sikuli.script.Screen;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import helper.Utilities;

public class UploadFiles {
	
	WebDriver driver;
	Properties prop;
	
		
	@Test (enabled = false)
	public void UploadUsing_SendKeys() throws InterruptedException {
		
		System.setProperty("webdriver.chrome.driver", "C:\\BrowserDrivers\\chromedriver_win32\\chromedriver.exe");		
		driver = new ChromeDriver();	
		driver.get("http://demo.guru99.com/test/upload/");
		driver.manage().window().maximize();		
		//Upload file using send keys
		String file_location = "C:\\Users\\Anand\\Documents\\Anand.jpg";		
		driver.findElement(By.name("uploadfile_0")).sendKeys(file_location);		
		driver.findElement(By.id("terms")).click();
		driver.findElement(By.id("submitbutton")).click();		
		Thread.sleep(2000);
		
		//String text1 = driver.findElement(By.xpath("//*[@id='res']/center/text()[1]")).getText();
		//String text2 = driver.findElement(By.xpath("//*[@id='res']/center/text()[2]")).getText();
		
		//Above XPath can return either text node or element : Use below xpath with * at the end
		
		String text = driver.findElement(By.xpath("//*[@id='res']/*")).getText();		
		System.out.println(text);		
		Thread.sleep(3000);		
		
		//Example2
		driver.get("http://testautomationpractice.blogspot.com/");		
		driver.switchTo().frame("frame-one1434677811");		
		driver.findElement(By.name("RESULT_FileUpload-10")).sendKeys(file_location);
		Thread.sleep(3000);		
		System.out.println(driver.findElement(By.xpath("//div[@id='content']")).getText());		
		driver.close();
	}
	
	@Test (enabled = false)
	public void UploadUsing_RobotClass() throws InterruptedException, AWTException {
		
		System.setProperty("webdriver.chrome.driver", "C:\\BrowserDrivers\\chromedriver_win32\\chromedriver.exe");		
		driver = new ChromeDriver();	
		driver.get("https://www.monster.com/");
		driver.manage().window().maximize();	
		
		driver.findElement(By.linkText("Upload Resume")).click();		
		Thread.sleep(3000);
		
		//Upload file using system clipboard and Robot Class
		
		//Step1
		// Specify the file location with extension
		String fileLocationWithFile = "C:\\Users\\Anand\\Documents\\Resume_AnandVardhan - UploadExample.docx";			
		// Use StringSelection to point to file i.e path selection
		StringSelection sel = new StringSelection(fileLocationWithFile);			
		 // Copy file to clipboard using Toolkit
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(sel,null);
		System.out.println("selection" +sel);
				
		//Step2
		// This will click on Browse/Upload button
		 driver.findElement(By.id("btnUploadFromMyComputer")).click();
		 System.out.println("Upload button clicked");
		 
		//Step3
		// Create object of Robot class
		 Robot robot = new Robot();
		 Thread.sleep(1000);		      
		// Press Enter
		 robot.keyPress(KeyEvent.VK_ENTER);		 
		// Release Enter
		 robot.keyRelease(KeyEvent.VK_ENTER);		 
		// Press CTRL+V to copy content from clipboard to choose file box i.e window popup
		 robot.keyPress(KeyEvent.VK_CONTROL);
		 robot.keyPress(KeyEvent.VK_V);		 
		// Release CTRL+V
		 robot.keyRelease(KeyEvent.VK_CONTROL);
		 robot.keyRelease(KeyEvent.VK_V);
		 Thread.sleep(1000);
		        
		 //Press Enter -This will close window popup and File will be uploaded
		 robot.keyPress(KeyEvent.VK_ENTER);
		 robot.keyRelease(KeyEvent.VK_ENTER);		 
		
		 Thread.sleep(5000);
		 System.out.println(driver.findElement(By.xpath("//*[@class='wrapper-profile']/h1")).getText());
				
		 driver.close();
	}
	
	@Test (enabled = false)
	public void UploadUsing_AutoIT() throws InterruptedException, AWTException, IOException {
		
		System.setProperty("webdriver.chrome.driver", "C:\\BrowserDrivers\\chromedriver_win32\\chromedriver.exe");		
		driver = new ChromeDriver();	
		driver.get("https://www.monster.com/");
		driver.manage().window().maximize();	
		
		driver.findElement(By.linkText("Upload Resume")).click();		
		Thread.sleep(3000);

		// This will click on Browse/Upload button
		driver.findElement(By.id("btnUploadFromMyComputer")).click();
		 System.out.println("Upload button clicked");
		 Thread.sleep(5000);
		//Upload file using AutoIT
		Runtime.getRuntime().exec("C:\\Users\\Anand\\Documents\\LearningDoc\\AutoIT\\AutoIT_script\\MonsterUpload.exe");		 
		
		Thread.sleep(5000);
		System.out.println(driver.findElement(By.xpath("//*[@class='wrapper-profile']/h1")).getText());
				
		driver.close();
	}
	
	/*Sikuli dependencies requuired
	@Test (enabled = false)
	public void UploadUsing_Sikuli() throws InterruptedException, AWTException, IOException, FindFailed {
		
		System.setProperty("webdriver.chrome.driver", "C:\\BrowserDrivers\\chromedriver_win32\\80\\chromedriver.exe");		
		driver = new ChromeDriver();	
		driver.get("https://www.monster.com/");
		driver.manage().window().maximize();	
		
		String inputFileLocation = "C:\\Users\\Anand\\Documents\\";
		String imageLocation = "C:\\ImagesForSikuli\\UploadInMonster\\";
		
		driver.findElement(By.linkText("Upload Resume")).click();		
		Thread.sleep(3000);

		// This will click on Browse/Upload button
		driver.findElement(By.id("btnUploadFromMyComputer")).click();
		System.out.println("Upload button clicked");
		
		//Use Sikuli to upload file
		
		Screen s = new Screen();		
		Pattern fileNameText = new Pattern(imageLocation+"FileNameText.PNG");
		Pattern openButton = new Pattern(imageLocation+"OpenButton.PNG");
		
		s.wait(fileNameText,20);
		//s.click(fileNameText);
		s.type(fileNameText, inputFileLocation+"Resume_AnandVardhan - UploadExample.DOCX");
		s.wait(openButton,20);
		s.click(openButton);		 
		 
		
		Thread.sleep(5000);
		System.out.println(driver.findElement(By.xpath("//*[@class='wrapper-profile']/h1")).getText());
				
		driver.close();
	} */
}
