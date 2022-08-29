package MultipleScenario;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.os.WindowsUtils;
import org.testng.annotations.Test;

public class KillingProcesses {
	
	@Test (enabled = true)
	public void GoogleSearchElements() throws InterruptedException {
		
		WindowsUtils.killByName("Notepad.exe");
		
		System.setProperty("webdriver.chrome.driver", "C:\\BrowserDrivers\\chromedriver_win32\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		
		driver.get("https://www.google.com");
		driver.manage().window().maximize();
		
		driver.findElement(By.xpath("//*[@name='q']")).sendKeys("java tutorial for beginners 2018");
		
		Thread.sleep(5000);
		
		List<WebElement> searchElements = driver.findElements(By.xpath("//ul[@role='listbox']//li/div/div/div/span"));
		//List<WebElement> searchElements = driver.findElements(By.xpath("//ul[@role='listbox']//li/descendant::span"));
		//List<WebElement> searchElements = driver.findElements(By.xpath("//ul[@role='listbox']//li/descendant::div[@class='sbl1']/span"));
		
		for (int i=0;i<searchElements.size();i++) {
			WebElement searchElement = searchElements.get(i);
			String searchElementtext = searchElement.getText();
			
			System.out.println(searchElementtext);
			
			if(searchElement.getText().contains("java eclipse tutorial")) {
				searchElement.click();
				break;
			}
		}
	}

}
