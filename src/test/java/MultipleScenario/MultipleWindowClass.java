package MultipleScenario;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class MultipleWindowClass {

	public static void main(String[] args) throws InterruptedException {
		
		//System.setProperty("webdriver.chrome.driver", "C:\\BrowserDrivers\\chromedriver_win32\\chromedriver.exe");
		System.setProperty("webdriver.gecko.driver", "C:\\BrowserDrivers\\geckodriver-v0.24.0-win64\\geckodriver.exe");
		
		//WebDriver driver = new ChromeDriver();	
		WebDriver driver = new FirefoxDriver();
		
		//driver.get("https://www.toolsqa.com/automation-practice-switch-windows/");
		driver.get("https://www.aa.com/");
		driver.manage().window().maximize();
		
		String titleMainWindow = driver.getTitle();
		System.out.println("title of main window: "+titleMainWindow);
		
		String parentWindowHandle = driver.getWindowHandle(); //This will have string id of parent window.
		System.out.println("parentWindowHandle: "+parentWindowHandle);
		
		driver.findElement(By.xpath("/html/body/header/div/div[2]/a/img")).click();
		Thread.sleep(4000);
				
	//get the handle of all windows i.e get the string id of all windows.
		
		Set<String> allWindowHandles = driver.getWindowHandles();
		
		int size = allWindowHandles.size();
		System.out.println("Total no of Windows: "+size);
		
	   for (String childWindowHandle : allWindowHandles) {
			System.out.println("childWindowHandle: "+childWindowHandle);
			if(parentWindowHandle.equalsIgnoreCase(childWindowHandle))
			{
			   continue;
			}
			driver.switchTo().window(childWindowHandle);
						
			String titleChildWindow = driver.getTitle();
			System.out.println("title of child window: "+titleChildWindow);
						
			driver.close();
		}	
		
		/*Iterator<String> itr = allWindowHandles.iterator();		
		while(itr.hasNext()) {
			String childWindowHandle = itr.next();
			//System.out.println("childWindowHandle: "+childWindowHandle);
			
			if(parentWindowHandle.equalsIgnoreCase(childWindowHandle))
					{
					   continue;
					}
			driver.switchTo().window(childWindowHandle);
			String titleChildWindow = driver.getTitle();
			System.out.println("title of child window: "+titleChildWindow);
			driver.close();			
		}*/
		
		
		
		//Go back to Parent Window
		driver.switchTo().window(parentWindowHandle);
			
		driver.findElement(By.xpath("//*[@id='plan-travel-expander']")).click();			
		
		System.out.println("title of main window at end: "+driver.getTitle());
		
			
		
		driver.close();
		//driver.quit();
		
	}

}
