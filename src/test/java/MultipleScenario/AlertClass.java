package MultipleScenario;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class AlertClass {
	
	public static void main(String[] args) throws InterruptedException {
		
		System.setProperty("webdriver.chrome.driver", "C:\\BrowserDrivers\\chromedriver_win32\\chromedriver.exe");
		
		WebDriver driver = new ChromeDriver();	
		
		driver.get("https://www.rediff.com");
		driver.manage().window().maximize();
		
		driver.findElement(By.xpath("//*[@id='signin_info']/a[1]")).click();
		
		driver.findElement(By.name("proceed")).click();	
		Thread.sleep(2000);
		
		Alert alert = driver.switchTo().alert();
		
		System.out.println(alert.getText());
		alert.accept();
		
		driver.findElement(By.id("login1")).sendKeys("anand1");
		Thread.sleep(2000);		
		driver.findElement(By.name("proceed")).click();
		Thread.sleep(4000);
		
		System.out.println(driver.switchTo().alert().getText());
		driver.switchTo().alert().dismiss();
		
		driver.findElement(By.id("password")).sendKeys("password1");
		Thread.sleep(4000);
		driver.findElement(By.name("proceed")).click();
		
		
		 driver.close();
	}
}
