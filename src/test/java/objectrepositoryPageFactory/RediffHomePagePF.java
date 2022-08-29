package objectrepositoryPageFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RediffHomePagePF {
	
	WebDriver driver;

	public RediffHomePagePF(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	
	//Objects- Locators
	//By signIn = By.linkText("Sign in");
	//By ShoppingLink = By.xpath("//a[@title='Online Shopping']");
	
	//using page factory	
	@FindBy (linkText="Sign in") WebElement signIn;
	@FindBy (xpath="//a[@title='Online Shopping']") WebElement ShoppingLink;
	
	
	public WebElement SignIn()
	{
		return signIn;
	}
	
	public WebElement ShoppingLink()
	{
		return ShoppingLink;
	}
	
	

}
