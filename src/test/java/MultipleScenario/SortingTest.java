package MultipleScenario;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import helper.Utilities;

public class SortingTest {
	
WebDriver driver;
	
	@BeforeMethod
	public void setUp() throws IOException {
		driver= Utilities.openBrowser();		
	}
	
	@Test (enabled=false)
	public void SortingTestAmazon() throws InterruptedException {
		
		driver.get("https://www.amazon.com/");
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@id='twotabsearchtextbox']")).sendKeys("Laptop");
		driver.findElement(By.xpath("(//*[@type='submit'])[1]")).click();		
		Thread.sleep(3000);
		
		List<WebElement> pagination = driver.findElements(By.xpath("//*[@id='search']/div[1]/div[2]/div/span[7]/div/div/div/ul/li/a"));
		//System.out.println(pagination.size());
		
		List<String> productList = null;
		productList = new ArrayList<String>();
		
		if(pagination.size()>0) {
			System.out.println("Pagination Exist..And Sorting Test");
			
			List<WebElement> next = driver.findElements(By.xpath("//*[@id='search']/div[1]/div[2]/div/span[7]/div/div/div/ul//a[text()='Next']"));
			int i=1;
			while(next.size()>0) {
				System.out.println("This is Page# "+i);
								
				//Get all product and store it in a list.
				//List<WebElement> product = driver.findElements(By.xpath("//*[@id='search']/div[1]/div[2]/div/span[3]/div[1]//a/span"));
				List<WebElement> product = driver.findElements(By.xpath("//*[@id='search']/div[1]/div[2]/div/span[3]/div[1]//h2/a/span"));
				
				for(WebElement eachProductElement : product) {
					
					if(eachProductElement.getText() != "")
					productList.add(eachProductElement.getText().toLowerCase());
								
				}
				
				//System.out.println("Listed from Page: "+i+" Go to next Page..");
				//System.out.println(productList);
				
				/*for(String eachProduct : productList) {
					
					System.out.println(eachProduct);
					
				}*/
				
				i=i+1;
				//if(i==2)
					//break;
				next = driver.findElements(By.xpath("//*[@id='search']/div[1]/div[2]/div/span[7]/div/div/div/ul//a[text()='Next']"));
				if(next.size()>0) {
				driver.findElement(By.xpath("//*[@id='search']/div[1]/div[2]/div/span[7]/div/div/div/ul//a[text()='Next']")).click();
				Thread.sleep(3000);	
				}
			}
		} else {
			System.out.println("Pagination does not exist..");
		}
		
		
		System.out.println("After Sorting");
		//Sort productList in Ascending order.
		//Collections.sort(productList);
		//Sort productList in Descending order.
		Collections.sort(productList,Collections.reverseOrder());
		
		int count = 0;
		for(String eachProduct : productList) {
			
			System.out.println(eachProduct);
			count=count+1;			
		}
		
		System.out.println("Total Listed item: "+count);
		driver.close();
	}
	
	
	@Test(enabled=false)
	public void sortTableAscending() {		
		
		driver.get("https://rahulshettyacademy.com/seleniumPractise/#/offers");
		
		driver.findElement(By.xpath("//*[@id=\"sortableTable\"]/thead/tr/th[2]/b")).click(); //descending
		driver.findElement(By.xpath("//*[@id=\"sortableTable\"]/thead/tr/th[2]/b")).click(); //ascending
		
		List<WebElement> vegFruitElement = driver.findElements(By.xpath("//tr/td[2]"));
		
		ArrayList<String> originalList = new ArrayList<String>();
		for(WebElement e : vegFruitElement) {
			originalList.add(e.getText());
		}
		ArrayList<String> sortedList = new ArrayList<String>();
		sortedList = sortColumn(originalList,"","");
		
		assertTrue(originalList.equals(sortedList));
		
		driver.close();		
		
	}
	
	@Test(enabled=true)
	public void sortTableDescending() {		
		
		driver.get("https://rahulshettyacademy.com/seleniumPractise/#/offers");
		
		driver.findElement(By.xpath("//*[@id=\"sortableTable\"]/thead/tr/th[2]/b")).click(); //descending
				
		List<WebElement> vegFruitElement = driver.findElements(By.xpath("//tr/td[2]"));
		
		ArrayList<String> originalList = new ArrayList<String>();
		for(WebElement e : vegFruitElement) {
			originalList.add(e.getText());
		}
		ArrayList<String> sortedList = new ArrayList<String>();
		sortedList = sortColumn(originalList,"desc","");
		
		assertTrue(originalList.equals(sortedList));
		
		driver.close();		
		
	}
	
	@Test(enabled=false)
	public void sortTableIntegersAscending() {		
		
		driver.get("https://rahulshettyacademy.com/seleniumPractise/#/offers");
		
		driver.findElement(By.xpath("//*[@id=\"sortableTable\"]/thead/tr/th[3]/b")).click(); //descending
		//driver.findElement(By.xpath("//*[@id=\"sortableTable\"]/thead/tr/th[3]/b")).click(); //ascending
		
		List<WebElement> priceElement = driver.findElements(By.xpath("//tr/td[3]"));
		
		ArrayList<String> originalList = new ArrayList<String>();
		for(WebElement e : priceElement) {
			originalList.add(e.getText());
		}
		ArrayList<String> sortedList = new ArrayList<String>();
		sortedList = sortColumn(originalList,"desc","integers");		
		assertTrue(originalList.equals(sortedList));
		
		driver.close();		
		
	}
	
	public static ArrayList<String> sortColumn(ArrayList<String> originalList, String order, String format) {		
		
		ArrayList<String> sortedList = new ArrayList<String>();
		
		sortedList.addAll(originalList);
		
		if(format.equals("integers")) {
			Arrays.sort(sortedList.toArray()); //This works for integers
			if(order.equals("desc")) {
				Arrays.sort(sortedList.toArray(),Collections.reverseOrder());
			}
		} else {
			Collections.sort(sortedList); //This works for string		
			if(order.equals("desc")) {
			Collections.sort(sortedList, Collections.reverseOrder());
			}
		}		
		return sortedList;
	}
	
 }

