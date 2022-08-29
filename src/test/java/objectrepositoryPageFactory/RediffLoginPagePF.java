package objectrepositoryPageFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RediffLoginPagePF {
	
	WebDriver driver;

	public RediffLoginPagePF(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	
	//Objects- Locators
	//By userName = By.id("login1");
	//By password = By.name("passwd");
	//By go = By.name("proceed");	
	//By homeLink = By.linkText("Home");
	
	@FindBy (id="login1") WebElement userName;
	
	@FindBy (name="passwd") WebElement password;
	
	@FindBy (name="proceed") WebElement go;
	
	@FindBy (linkText="Home") WebElement homeLink;
	
	
	//Methods
	public void loginRediff(String uname, String psswd)
	{
		userName.sendKeys(uname);
		password.sendKeys(psswd);
		go.click();		
	}
	
	public WebElement home()
	{
		return homeLink;
	}
	
	
}
