package objectrepository;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import helper.Utilities;

public class RediffLoginPage {
	
	WebDriver driver;

	public RediffLoginPage(WebDriver driver)
	{
		this.driver=driver;
	}
	
	
	//Objects- Locators
	By userName = By.id("login1");
	By password = By.name("passwd");
	By go = By.name("proceed");	
	By homeLink = By.linkText("Home");
	
	//Methods
	public void loginRediff(String uname, String psswd) throws InterruptedException
	{
		Utilities.highlightElement(driver, driver.findElement(userName));
		Thread.sleep(1000);
		driver.findElement(userName).sendKeys(uname);
		Utilities.highlightElement(driver, driver.findElement(password));
		Thread.sleep(1000);
		driver.findElement(password).sendKeys(psswd);
		Utilities.highlightElement(driver, driver.findElement(go));
		Thread.sleep(1000);
		driver.findElement(go).click();		
	}
	
	public WebElement home()
	{
		return driver.findElement(homeLink);
	}
	
	
}
