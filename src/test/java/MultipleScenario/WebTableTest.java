package MultipleScenario;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class WebTableTest {
	
	public static void main(String[] args) throws InterruptedException {
		
		System.setProperty("webdriver.chrome.driver", "C:\\BrowserDrivers\\chromedriver_win32\\chromedriver.exe");		
		ChromeDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		
		driver.get("http://www.qaclickacademy.com/");
		
		Thread.sleep(8000);
		
		driver.findElement(By.xpath("//*[@id=\"homepage\"]/div[5]/div[2]/div/div/div/span/div/div[6]/div/div/button")).click();
		driver.findElement(By.xpath("//*[@id=\"homepage\"]/header/div[2]/div/nav/ul/li[5]/a")).click();
		List<WebElement> product = driver.findElements(By.xpath("//*[@id='product']/tbody/tr"));
		
		int rowSize = product.size();
		System.out.println("Total Rows: "+rowSize);
		
		List<WebElement> productHeader = driver.findElements(By.xpath("//*[@id='product']/tbody/tr/th"));
		
		int colSize = productHeader.size();
		System.out.println("Total Columns: "+colSize);
		
		
			for(int col=1;col<=colSize;col++) {				
				String data = driver.findElement(By.xpath("//*[@id='product']/tbody/tr[3]/td["+col+"]")).getText();
				System.out.println(data);
			}
		
		driver.close();
	}

}
