package MultipleScenario;
import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import helper.Utilities;

public class Log4j2Example {
	WebDriver driver;
	Logger logger;
	
	
	
	@BeforeMethod
	public void setUp() throws IOException {
		//driver= Utilities.openBrowser();	
		 //logger = LogManager.getLogger(Log4j2Example.class);
		 logger = LogManager.getLogger(Log4j2Example.class.getName());
	}
	
	@Test (enabled = true)
	public void Logging4jTest() {	
		logger.trace("This is trace message2");
		logger.debug("This is debug message2");
		logger.info("This is informational message2");
		logger.warn("This is warning message2");
		logger.error("This is error message2");
		logger.fatal("This is fatel message2");		
	}
	
	@Test (enabled = false)
	public void GoogleSearchElements() throws InterruptedException {
		
		//driver.get(prop.getProperty("url"));
		driver.get("https://www.google.com");
		driver.manage().window().maximize();
		
		driver.findElement(By.xpath("//*[@name='q']")).sendKeys("selenium");
		
		Thread.sleep(5000);
		
		List<WebElement> searchElements = driver.findElements(By.xpath("//ul[@role='listbox']//li/div/div/div/span"));
		//List<WebElement> searchElements = driver.findElements(By.xpath("//ul[@role='listbox']//li/descendant::span"));
		//List<WebElement> searchElements = driver.findElements(By.xpath("//ul[@role='listbox']//li/descendant::div[@class='sbl1']/span"));
		
		for (int i=0;i<searchElements.size();i++) {
			WebElement searchElement = searchElements.get(i);
			String searchElementtext = searchElement.getText();
			
			System.out.println(searchElementtext);
			
			if(searchElement.getText().contains("selenium")) {
				searchElement.click();
				break;
			}
		}
	}
	
	@Test (enabled=false)
	public void PaginationTestWalmart() throws InterruptedException {
		
		//driver.get("https://dgrid.io/js/dgrid/test/extensions/Pagination.html"); 
		driver.get("https://www.walmart.com/");
		driver.findElement(By.xpath("//*[@name='query']")).sendKeys("Laptop");
		driver.findElement(By.xpath("//*[@id='global-search-submit']/span/img")).click();
		
		Thread.sleep(3000);
		List<WebElement> pagination = driver.findElements(By.xpath("//*[@id='mainSearchContent']/div[3]/div[2]/ul/li/a"));
		System.out.println(pagination.size());
		
		if(pagination.size()>0) {
			System.out.println("Pagination Exist..");
			for(int i = 1; i<=50;i++) { //No of pages = 50
				System.out.println("This is Page# "+i);
				//Test for item if found break from loop if not then click and go to next page
					List<WebElement> product = driver.findElements(By.xpath("//*[@id='searchProductResult']//a/span[contains(text(),'Acer Spin 5 Touchscreen 2-in-1')]"));
					if(product.size()>0) {				
						driver.findElement(By.xpath("//*[@id='searchProductResult']//a/span[contains(text(),'Acer Spin 5 Touchscreen 2-in-1')]")).click();
						System.out.println("Found on page# "+i);
						break;
					}
				System.out.println("Not Found on Page: "+i+" Go to next Page.."+(i+1));
				//pagination.get(i).click();
				if(i==1) {
				driver.findElement(By.xpath("//*[@id='mainSearchContent']/div[3]/div[2]/button")).click();
				Thread.sleep(5000);
				}
				if(i!=1) {
					driver.findElement(By.xpath("(//*[@id='mainSearchContent']/div[3]/div[2]/button)[2]")).click();
					Thread.sleep(5000);
				}
			}
			
		} else {
			System.out.println("Pagination does not exist..");
		}
	}
	
	@Test (enabled=false)
	public void PaginationTestWalmart2() throws InterruptedException {
		
		driver.get("https://www.walmart.com/");
		driver.findElement(By.xpath("//*[@name='query']")).sendKeys("Laptop");
		driver.findElement(By.xpath("//*[@id='global-search-submit']/span/span")).click();
		
		Thread.sleep(3000);
		List<WebElement> pagination = driver.findElements(By.xpath("//*[@id='mainSearchContent']/div[3]/div[2]/ul/li/a"));
		System.out.println(pagination.size());
		
		if(pagination.size()>0) {
			System.out.println("Pagination Exist..");
			for(int i = 1; i<=50;i++) { //No of pages = 50
				System.out.println("This is Page# "+i);
				//Test for item if found break from loop if not then click and go to next page
					List<WebElement> product = driver.findElements(By.xpath("//*[@id='searc1hProductResult']//a/span[contains(text(),'HP 15 Ghost Silver')]"));
					if(product.size()>0) {				
						driver.findElement(By.xpath("//*[@id='searchProductResult']//a/span[contains(text(),'HP 15 Ghost Silver')]")).click();
						System.out.println("Found on page# "+i);
						break;
					}
				System.out.println("Not Found on Page: "+i+" Go to next Page.."+(i+1));
				
				List<WebElement> nextButton = driver.findElements(By.xpath("(//*[@id='mainSearchContent']/div[3]/div[2]/button)[2]"));
				
				if(nextButton.size()>0) {
					driver.findElement(By.xpath("(//*[@id='mainSearchContent']/div[3]/div[2]/button)[2]")).click();
					Thread.sleep(5000);
				} else {
					driver.findElement(By.xpath("//*[@id='mainSearchContent']/div[3]/div[2]/button")).click();
					Thread.sleep(5000);
				}
			}
			
		} else {
			System.out.println("Pagination does not exist..");
		}
	}
	
	
}

