package MultipleScenario;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import helper.Utilities;

public class Pagination {
	WebDriver driver;
	
	@BeforeMethod
	public void setUp() throws IOException {
		driver= Utilities.openBrowser();		
	}
	
	@Test (enabled=false)
	public void PaginationTestWalmart() throws InterruptedException {
		
		//driver.get("https://dgrid.io/js/dgrid/test/extensions/Pagination.html"); 
		driver.get("https://www.walmart.com/");
		driver.findElement(By.xpath("//*[@name='query']")).sendKeys("Laptop");
		driver.findElement(By.xpath("//*[@id='global-search-submit']/span/span")).click();
		
		Thread.sleep(3000);
		List<WebElement> pagination = driver.findElements(By.xpath("//*[@id='mainSearchContent']/div[3]/div[2]/ul/li/a"));
		System.out.println(pagination.size());
		
		if(pagination.size()>0) {
			System.out.println("Pagination Exist..");
			for(int i = 1; i<=50;i++) { //No of pages = 50
				System.out.println("This is Page# "+i);
				//Test for item if found break from loop if not then click and go to next page
					List<WebElement> product = driver.findElements(By.xpath("//*[@id='searchProductResult']//a/span[contains(text(),'Acer Spin 5 Touchscreen 2-in-1')]"));
					if(product.size()>0) {				
						driver.findElement(By.xpath("//*[@id='searchProductResult']//a/span[contains(text(),'Acer Spin 5 Touchscreen 2-in-1')]")).click();
						System.out.println("Found on page# "+i);
						break;
					}
				System.out.println("Not Found on Page: "+i+" Go to next Page.."+(i+1));
				//pagination.get(i).click();
				if(i==1) {
				driver.findElement(By.xpath("//*[@id='mainSearchContent']/div[3]/div[2]/button")).click();
				Thread.sleep(5000);
				}
				if(i!=1) {
					driver.findElement(By.xpath("(//*[@id='mainSearchContent']/div[3]/div[2]/button)[2]")).click();
					Thread.sleep(5000);
				}
			}
			
		} else {
			System.out.println("Pagination does not exist..");
		}
	}
	
	@Test (enabled=false)
	public void PaginationTestWalmart2() throws InterruptedException {
		
		driver.get("https://www.walmart.com/");
		driver.findElement(By.xpath("//*[@name='query']")).sendKeys("Laptop");
		driver.findElement(By.xpath("//*[@id='global-search-submit']/span/span")).click();
		
		Thread.sleep(3000);
		List<WebElement> pagination = driver.findElements(By.xpath("//*[@id='mainSearchContent']/div[3]/div[2]/ul/li/a"));
		System.out.println(pagination.size());
		
		if(pagination.size()>0) {
			System.out.println("Pagination Exist..");
			for(int i = 1; i<=50;i++) { //No of pages = 50
				System.out.println("This is Page# "+i);
				//Test for item if found break from loop if not then click and go to next page
					List<WebElement> product = driver.findElements(By.xpath("//*[@id='searc1hProductResult']//a/span[contains(text(),'HP 15 Ghost Silver')]"));
					if(product.size()>0) {				
						driver.findElement(By.xpath("//*[@id='searchProductResult']//a/span[contains(text(),'HP 15 Ghost Silver')]")).click();
						System.out.println("Found on page# "+i);
						break;
					}
				System.out.println("Not Found on Page: "+i+" Go to next Page.."+(i+1));
				
				List<WebElement> nextButton = driver.findElements(By.xpath("(//*[@id='mainSearchContent']/div[3]/div[2]/button)[2]"));
				
				if(nextButton.size()>0) {
					driver.findElement(By.xpath("(//*[@id='mainSearchContent']/div[3]/div[2]/button)[2]")).click();
					Thread.sleep(5000);
				} else {
					driver.findElement(By.xpath("//*[@id='mainSearchContent']/div[3]/div[2]/button")).click();
					Thread.sleep(5000);
				}
			}
			
		} else {
			System.out.println("Pagination does not exist..");
		}
	}
	
	@Test (enabled=true)
	public void PaginationTestAmazon() throws InterruptedException {
		
		driver.get("https://www.amazon.com/");
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@id='twotabsearchtextbox']")).sendKeys("Lenovo Yoga 920");
		driver.findElement(By.xpath("(//*[@type='submit'])[1]")).click();		
		Thread.sleep(3000);
		
		List<WebElement> pagination = driver.findElements(By.xpath("//*[@id='search']/div[1]/div[2]/div/span[7]/div/div/div/ul/li/a"));
		System.out.println(pagination.size());
		
		if(pagination.size()>0) {
			System.out.println("Pagination Exist..");
			
			List<WebElement> next = driver.findElements(By.xpath("//*[@id='search']/div[1]/div[2]/div/span[7]/div/div/div/ul//a[text()='Next']"));
			int i=1;
			while(next.size()>0) {
				System.out.println("This is Page# "+i);
								
				//find product and break from loop.
				List<WebElement> product = driver.findElements(By.xpath("//*[@id='search']/div[1]/div[2]/div/span[3]/div[1]//a/span[contains(text(),'MOSISO Polyester Vertical Style Water Repellent Laptop Sleeve')]"));
				
				if(product.size()>0) {
					driver.findElement(By.xpath("//*[@id='search']/div[1]/div[2]/div/span[3]/div[1]//a/span[contains(text(),'MOSISO Polyester Vertical Style Water Repellent Laptop Sleeve')]")).click();
					System.out.println("Found on page# "+i);
					break;
				}			
				
				System.out.println("Not Found on Page: "+i+" Go to next Page.."+(i+1));
				i=i+1;
				next = driver.findElements(By.xpath("//*[@id=\"search\"]/div[1]/div[2]/div/span[7]/div/div/div/ul//a[text()='Next']"));
				if(next.size()>0) {
				driver.findElement(By.xpath("//*[@id=\"search\"]/div[1]/div[2]/div/span[7]/div/div/div/ul//a[text()='Next']")).click();
				Thread.sleep(3000);	
				}
			}
		} else {
			System.out.println("Pagination does not exist..");
		}
	}

}


/*
 * 
 * Pagination in selenium using core java
Pagination Solution One:

Find the pagination elements by using findElemnts() and store as list of elements.
Get the pagination size.
Check pagination exist or not.
If exist make a loop and click until last pagination size.
Ex:


List<webElement> pagination =driver.findElemnts(By.xpath("//div[@class='nav-pages']//a")); 
// checkif pagination link exists 

if(pagination .size()>0){ 
sop("pagination exists"); 

// click on pagination link 

for(int i=0; i<pagination .size(); i++){ 
pagination.get(i).click(); 
} 
} else { 
sop("pagination not exists"); 
} 


Pagination Solution Two:

Find the pagination elements by using findElemnts() and store as list of elements.
Get the pagination size.
Get Last Page Number.
Find Next Button of pagination.
Check pagination exist or not by taking pagination size.
If exist make a loop and click until last pagination number.
Ex:


List<webElement> pagination =driver.findElemnts(By.xpath("//div[@class='nav-pages']//a")); 


webElement NextButton= driver.findElemnt(By.xpath("//*[@id='nextbutton id']"))

int LastPageNumber = (int)driver.findElemnt(By.xpath("//*[@text='>>]/preceding::/span[1]")); 

// checkif pagination link exists
if(pagination .size()>0)
{ 
sop("pagination exists"); 

// click on pagination link 

for(int i=1; i <LastPageNumber; i++)
{ 
      
    NextButton.click();

} 
} else { 
sop("pagination not exists"); 

} 

Pagination Solution Three:
Find the pagination elements by using findElemnts() and store as list of elements.
Get the pagination size.
Get Last Page Number.
Find Next Button for pagination.
Check pagination exist or not by taking pagination size.
Then check Nextbutton is enable or not.If enable go inside loop and clcik next button until it's not disable.
If exist make a loop and click until last pagination number.
Ex:

List<webElement> pagination =driver.findElemnts(By.xpath("//div[@class='nav-pages']//a")); 


webElement NextButton= driver.findElemnt(By.xpath("//*[@id='nextbutton id']"));

webElement prevButton= driver.findElemnt(By.xpath("//*[@id='prevButtonid']"));

int LastPageNumber = (int)driver.findElemnt(By.xpath("//*[@text='>>]/preceding::/span[1]")); 

// checkif pagination link exists
if(pagination .size()>0)
{ 
sop("pagination exists"); 

// click on pagination link 

for(int i=1; i <pagination .size(); i++)
{ 

//Check if nextbutton is enable or not.

   if(NextButton.isEnabled()) 
{

    NextButton.click();
}
else { 
sop("pagination not exists"); 
} 
}}
 * 
 */





