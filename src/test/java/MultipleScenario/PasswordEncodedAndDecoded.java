package MultipleScenario;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;

import helper.ExcelReader;
import helper.ExcelUtilities;
import helper.Utilities;

public class PasswordEncodedAndDecoded {
	
	public WebDriver driver;
	public Properties prop;
	
	@BeforeMethod
	public void setUp() throws IOException {
		//driver= Utilities.openBrowser();		
	}
	
	@Test (enabled=false)
	public void PaswordEncodedDecodedTest() throws Base64DecodingException {
		
		String password = "Acts2019";
		
		String encodedPassword = Base64.encode(password.getBytes());
		System.out.println("Encoded Password: "+encodedPassword); //Encoded Password: QWN0czIwMTk=
		
		byte[] decodedPassword = Base64.decode("QWN0czIwMTk=");
		System.out.println("Decoded Password: "+new String(decodedPassword)); //Decoded Password: Acts2019
	}
	
	@Test(enabled=true)
	public void SeleniumTestUsingDecodedPassword() throws Exception {
		
		prop = Utilities.readConfig();
		driver = Utilities.openBrowser();
		
		driver.get("https://classic.crmpro.com/");
		
		Thread.sleep(5000);
		
		driver.findElement(By.xpath("//*[@id='loginForm']/div/input[1]")).sendKeys("Avardhan");
		//driver.findElement(By.xpath("//*[@name='password']")).sendKeys(new String(Base64.decode("QWN0czIwMTk=")));
		//driver.findElement(By.xpath("//*[@name='password']")).sendKeys(Utilities.decodePassword("QWN0czIwMTk="));
		driver.findElement(By.xpath("//*[@name='password']")).sendKeys(Utilities.decodePassword(prop.getProperty("password")));
		System.out.println("Login successful..");
		driver.findElement(By.xpath("//*[@id='loginForm']/div/div/input")).click();
		
		WebElement frameElement = driver.findElement(By.xpath("//*[@name='mainpanel']"));
		
		//using iframe WebElement
		driver.switchTo().frame(frameElement);
		
		Thread.sleep(3000);
		
		driver.findElement(By.xpath("//*[@id='navmenu']/ul/li[4]/a")).click();
		driver.findElement(By.xpath("//*[@id='navmenu']/ul/li[5]/a")).click();
		
		driver.close();	
		
	}	
	
	
}
