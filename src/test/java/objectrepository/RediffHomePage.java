package objectrepository;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import helper.Utilities;

public class RediffHomePage {
	
	WebDriver driver;

	public RediffHomePage(WebDriver driver)
	{
		this.driver=driver;
	}
	
	
	//Objects- Locators
	By signIn = By.linkText("Sign in");
	By ShoppingLink = By.xpath("//a[@title='Online Shopping']");
	
	
	//Methods
	public WebElement SignIn() throws InterruptedException
	{
		Utilities.highlightElement(driver, driver.findElement(signIn));
		Thread.sleep(1000);
		return driver.findElement(signIn);
	}
	
	public WebElement ShoppingLink()
	{
		return driver.findElement(ShoppingLink);
	}
	
	

}
