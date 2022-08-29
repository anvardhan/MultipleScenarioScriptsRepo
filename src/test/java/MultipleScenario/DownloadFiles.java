package MultipleScenario;
import static org.testng.Assert.assertEquals;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
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
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
//import org.sikuli.script.FindFailed;
//import org.sikuli.script.Pattern;
//import org.sikuli.script.Screen;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import helper.Utilities;

public class DownloadFiles {
	
	WebDriver driver;
	Properties prop;
	
		
	@Test (enabled = false)
	public void DownloadTXTPdf_DefaultLocation() throws InterruptedException {
		
		System.setProperty("webdriver.chrome.driver", "C:\\BrowserDrivers\\chromedriver_win32\\chromedriver.exe");		
		driver = new ChromeDriver();	
		driver.get("http://demo.automationtesting.in/FileDownload.html");
		driver.manage().window().maximize();
		
		//Download txt file
		driver.findElement(By.id("textbox")).sendKeys("Selenium Download txt file");
		driver.findElement(By.id("createTxt")).click();
		//Below click downloads txt at C:\Users\Anand\Downloads\info.txt
		driver.findElement(By.id("link-to-download")).click();		
		Thread.sleep(5000);
		
		//check if file exist or not
		if(isFileExist("C:\\Users\\Anand\\Downloads\\info.txt")) {
			System.out.println("Text File exist");
		} else {
			System.out.println("Text File does not exist");
		}		
		
		//Download pdf file
		driver.findElement(By.id("pdfbox")).sendKeys("Selenium Download pdf file");
		driver.findElement(By.id("createPdf")).click();
		//Below click downloads pdf at C:\Users\Anand\Downloads\info.txt
		driver.findElement(By.id("pdf-link-to-download")).click();		
		Thread.sleep(5000);
		
		//check if file exist or not
		if(isFileExist("C:\\Users\\Anand\\Downloads\\info.pdf")) {
			System.out.println("Pdf File exist");
		} else {
			System.out.println("Pdf File does not exist");
		}
		
		driver.close();
	}
	
	@Test (enabled = false)
	public void DownloadTXTPDF_DesiredLocation() throws InterruptedException {
		
		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		
		chromePrefs.put("profile.default_content_settings.popups", 0);
		chromePrefs.put("download.prompt_for_download", "false");
		chromePrefs.put("download.default_directory", "C:\\DownloadFiles");
		
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", chromePrefs);
		
		
		System.setProperty("webdriver.chrome.driver", "C:\\BrowserDrivers\\chromedriver_win32\\chromedriver.exe");		
		driver = new ChromeDriver(options);	
		driver.get("http://demo.automationtesting.in/FileDownload.html");
		driver.manage().window().maximize();
		
		//Download txt file
		driver.findElement(By.id("textbox")).sendKeys("Selenium Download txt file");
		driver.findElement(By.id("createTxt")).click();
		//Below click downloads txt at C:\\DownloadFiles\info.txt
		driver.findElement(By.id("link-to-download")).click();		
		Thread.sleep(5000);
		
		//check if file exist or not
		if(isFileExist("C:\\DownloadFiles\\info.txt")) {
			System.out.println("Text File exist");
		} else {
			System.out.println("Text File does not exist");
		}		
		
		//Download pdf file
		driver.findElement(By.id("pdfbox")).sendKeys("Selenium Download pdf file");
		driver.findElement(By.id("createPdf")).click();
		//Below click downloads pdf at C:\\DownloadFiles\info.txt
		driver.findElement(By.id("pdf-link-to-download")).click();		
		Thread.sleep(5000);
		
		//check if file exist or not
		if(isFileExist("C:\\DownloadFiles\\info.pdf")) {
			System.out.println("Pdf File exist");
		} else {
			System.out.println("Pdf File does not exist");
		}
		
		driver.close();
	}
	
	@Test (enabled = true)
	public void DownloadTXTPdf_Firefox() throws InterruptedException {
		
		FirefoxProfile profile = new FirefoxProfile();
		
		profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "text/plain,application/pdf"); //set mime type
		profile.setPreference("browser.download.manager.showWhenStarting", false); //This will hide popup i.e download window will not appear
		profile.setPreference("browser.download.dir", "C:\\DownloadFiles");
		profile.setPreference("browser.download.folderList", 2);
		profile.setPreference("pdfjs.disabled", true);
		
		FirefoxOptions options = new FirefoxOptions();
		options.setProfile(profile);
		
		
		System.setProperty("webdriver.gecko.driver", "C:\\BrowserDrivers\\geckodriver-v0.24.0-win64\\geckodriver.exe");		
		driver = new FirefoxDriver(options);	
		driver.get("http://demo.automationtesting.in/FileDownload.html");
		driver.manage().window().maximize();
		
		//Download txt file
		driver.findElement(By.id("textbox")).sendKeys("Selenium Download txt file");
		driver.findElement(By.id("createTxt")).click();
		//Below click downloads txt at C:\\DownloadFiles\info.txt
		driver.findElement(By.id("link-to-download")).click();		
		Thread.sleep(5000);
		
		//check if file exist or not
		if(isFileExist("C:\\DownloadFiles\\info.txt")) {
			System.out.println("Text File exist");
		} else {
			System.out.println("Text File does not exist");
		}		
		
		//Download pdf file
		driver.findElement(By.id("pdfbox")).sendKeys("Selenium Download pdf file");
		driver.findElement(By.id("createPdf")).click();
		//Below click downloads pdf at C:\\DownloadFiles\info.txt
		driver.findElement(By.id("pdf-link-to-download")).click();		
		Thread.sleep(5000);
		
		//check if file exist or not
		if(isFileExist("C:\\DownloadFiles\\info.pdf")) {
			System.out.println("Pdf File exist");
		} else {
			System.out.println("Pdf File does not exist");
		}
		
		driver.close();
	}
	
	static boolean isFileExist(String path) {
		
		File f = new File(path);
		
		if (f.exists()) {
			return true;
		} else {
			return false;
		}
		
	}
	
}
