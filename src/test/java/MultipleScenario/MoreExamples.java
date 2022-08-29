package MultipleScenario;
import static org.testng.Assert.assertEquals;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import helper.Utilities;

public class MoreExamples {
	
	WebDriver driver;
	Properties prop;
	
	@BeforeMethod
	public void setUp() throws IOException {
		//System.setProperty("webdriver.chrome.driver", "C:\\BrowserDrivers\\chromedriver_win32\\chromedriver.exe");		
		//driver = new ChromeDriver();	
		prop=Utilities.readConfig();
		driver = Utilities.openBrowser();		
	}
	
	@Test (enabled = false)
	public void GoogleSearchElements() throws InterruptedException {
		
		//driver.get(prop.getProperty("url"));
		driver.get("https://www.google.com");
		driver.manage().window().maximize();
		
		driver.findElement(By.xpath("//*[@name='q']")).sendKeys("java tutorial for beginners 2018");
		
		Thread.sleep(5000);
		
		List<WebElement> searchElements = driver.findElements(By.xpath("//ul[@role='listbox']//li/div/div/div/span"));
		//List<WebElement> searchElements = driver.findElements(By.xpath("//ul[@role='listbox']//li/descendant::span"));
		//List<WebElement> searchElements = driver.findElements(By.xpath("//ul[@role='listbox']//li/descendant::div[@class='sbl1']/span"));
		
		for (int i=0;i<searchElements.size();i++) {
			WebElement searchElement = searchElements.get(i);
			String searchElementtext = searchElement.getText();
			
			System.out.println(searchElementtext);
			
			if(searchElement.getText().contains("java eclipse tutorial")) {
				searchElement.click();
				break;
			}
		}
	}
	
	
	@Test (enabled = false)
	public void CalenderTest() {
		
		driver.get("https://www.redbus.in/");
		driver.manage().window().maximize();
		
		String inputStrDate = "Aug/08/2022";
		String[] splitedStrDate = inputStrDate.split("/");
		int inputYear = Integer.parseInt(splitedStrDate[2]);
		String inputMonth = splitedStrDate[0];
		String date = splitedStrDate[1];
				
		WebElement onwardDatePicker = driver.findElement(By.xpath("//*[@id='search']/div/div[3]/span"));
		onwardDatePicker.click();
		
		WebElement monthAndYear = driver.findElement(By.xpath("//*[@id='rb-calendar_onward_cal']/table/tbody/tr[1]/td[2]"));
		String strMonthAndYear = monthAndYear.getText();
		String[] splitStrMonthAndYear = strMonthAndYear.split("\\s+");
		String month=splitStrMonthAndYear[0];
		int year = Integer.parseInt(splitStrMonthAndYear[1]);
		
		//System.out.println(splitStrMonthAndYear[0]+" "+splitStrMonthAndYear[1]);
		
		WebElement nextArrow = driver.findElement(By.xpath("//*[@id='rb-calendar_onward_cal']/table/tbody/tr[1]/td[3]/button"));
		
		//System.out.println(month+" "+year);
		if (inputYear==year) {			
			while (!inputMonth.equalsIgnoreCase(month)) {
				nextArrow.click();
				nextArrow = driver.findElement(By.xpath("//*[@id='rb-calendar_onward_cal']/table/tbody/tr[1]/td[3]/button"));
				monthAndYear = driver.findElement(By.xpath("//*[@id='rb-calendar_onward_cal']/table/tbody/tr[1]/td[2]"));
				strMonthAndYear = monthAndYear.getText();
				splitStrMonthAndYear = strMonthAndYear.split("\\s+");
				month=splitStrMonthAndYear[0];
			 }
			
		} else if (inputYear>year) {
			
			//System.out.println(month+" "+year);
			while (inputYear!=year) {
			nextArrow.click();
			nextArrow = driver.findElement(By.xpath("//*[@id='rb-calendar_onward_cal']/table/tbody/tr[1]/td[3]/button"));
			monthAndYear = driver.findElement(By.xpath("//*[@id='rb-calendar_onward_cal']/table/tbody/tr[1]/td[2]"));
			strMonthAndYear = monthAndYear.getText();
			splitStrMonthAndYear = strMonthAndYear.split("\\s+");
			month = splitStrMonthAndYear[0];
			year = Integer.parseInt(splitStrMonthAndYear[1]);
			}
			//System.out.println(month+" "+year);
			while (!inputMonth.equalsIgnoreCase(month)) {
				nextArrow.click();
				nextArrow = driver.findElement(By.xpath("//*[@id='rb-calendar_onward_cal']/table/tbody/tr[1]/td[3]/button"));
				monthAndYear = driver.findElement(By.xpath("//*[@id='rb-calendar_onward_cal']/table/tbody/tr[1]/td[2]"));
				strMonthAndYear = monthAndYear.getText();
				splitStrMonthAndYear = strMonthAndYear.split("\\s+");
				month=splitStrMonthAndYear[0];
			 }
		}
		
		List<WebElement> dates = driver.findElements(By.xpath("//*[@id='rb-calendar_onward_cal']/table//td"));
		System.out.println(dates.size());
		
		for (int i=1;i<dates.size();i++)
		{
			WebElement dateElement = dates.get(i);
			String tDate=dateElement.getText();
			if (StringUtils.leftPad(tDate, 2, "0").equalsIgnoreCase(date))
			{
				dateElement.click();
				break;
			}
		}
		
		assertEquals(true, false);
	}
	
	@Test (enabled=false)
	public void XpathExample() throws InterruptedException {
		
		        driver.get("https://classic.crmpro.com/");		
				driver.manage().window().maximize();				
				driver.findElement(By.xpath("//*[@id='loginForm']/div/input[1]")).sendKeys("Avardhan");
				driver.findElement(By.xpath("//*[@name='password']")).sendKeys("Acts2019");
				driver.findElement(By.xpath("//*[@id='loginForm']/div/div/input")).click();
				WebElement frameElement = driver.findElement(By.xpath("//*[@name='mainpanel']"));
				driver.switchTo().frame(frameElement);
				Thread.sleep(3000);						
				driver.findElement(By.xpath("//*[@id='navmenu']/ul/li[4]/a")).click();
								
				//WebElement contactChkBox = driver.findElement(By.xpath("//a[text()='first2 last2']//parent::td//preceding-sibling::td//input")); 
				//contactChkBox.click();
				
				boolean flag = selectContactName("first2 last2");
				driver.switchTo().defaultContent();
				
	}
	
	public boolean selectContactName(String name)
	{
		driver.findElement(By.xpath("//a[text()='"+name+"']//parent::td//preceding-sibling::td//input")).click();
		boolean flag = driver.findElement(By.xpath("//a[text()='"+name+"']//parent::td//preceding-sibling::td//input")).isSelected();
		return flag;
	}
	

	
	@AfterMethod
	public void tearDown(ITestResult result) throws IOException {
		
		if(ITestResult.FAILURE==result.getStatus()) {			
			TakesScreenshot ts = (TakesScreenshot) driver;
			File source = ts.getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(source, new File("./Screenshots/"+result.getName()+".png"));
			System.out.println("Screenshot taken for "+result.getName());
		}
		
		//driver.close();
	}
	
	
	
}
