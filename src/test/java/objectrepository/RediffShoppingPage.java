package objectrepository;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class RediffShoppingPage {

	WebDriver driver;

	public RediffShoppingPage(WebDriver driver)
	{
		this.driver=driver;
	}
	
	
	//Objects- Locators
	By searchField = By.id("srchword");
	By searchButton = By.xpath("//*[@id='div_srchcontainer']/input[5]");
	By noProductFound = By.xpath("//*[@id='catmore_0']/div[1]/div");
	
	
	//Methods
	public void typeSearchAndGo(String search)
	{
		driver.findElement(searchField).sendKeys(search);
		driver.findElement(searchButton).click();		
	}
	
	public WebElement noProductFoundMsg()
	{
		return driver.findElement(noProductFound);
	}
	
		
}
