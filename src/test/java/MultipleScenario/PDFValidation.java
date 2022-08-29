package MultipleScenario;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Set;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import helper.Utilities;

public class PDFValidation {
	
	/*
	 * https://pdfbox.apache.org/
	 * The Apache PDFBox® library is an open source Java tool for working with PDF documents. 
	 * This allows creation of new PDF documents, manipulation of existing documents 
	 * and the ability to extract content from documents. Apache PDFBox also includes several 
	 * command-line utilities. 
	 * 
	 * Setup - Type "maven pdfbox" in google search and click on Apache PDFBox and select any version 
	 * which we need. Ex- select 2.0.12 and then copy depedency and paste over pom.xml file and we
	 * good to go.
	 * Also we can download JARs from above and add to project using build path.
	 * 
	 * Note - File should be .pdf file or else this will not work
	 * 
	 */
	
	WebDriver driver;
	
	@BeforeMethod
	public void setUp() throws IOException {
		driver= Utilities.openBrowser();		
	}
	
	@Test (enabled=true)
	public void PdfValidation1() throws IOException {		
		
		//Read PDF
		//https://www.princexml.com/samples/
		//http://www.axmag.com/
		
		URL TestURL = new URL("http://www.axmag.com/download/pdfurl-guide.pdf");

		InputStream is = TestURL.openStream();
		BufferedInputStream TestFileParse = new BufferedInputStream(is);
		PDDocument document = null;
		
		document = PDDocument.load(TestFileParse);
		
		PDFTextStripper pdftextstripper = new PDFTextStripper();		
		String pdfContent = pdftextstripper.getText(document);
		
		System.out.println(pdfContent);
		
		Assert.assertTrue(pdfContent.contains(".pdf"));
		Assert.assertTrue(pdfContent.contains("Thank you for using the “Download PDF file” feature"));
		
		driver.quit();
		
	}
	
	@Test (enabled=false)
	public void PdfValidation_axmag_UserGuides() throws IOException, InterruptedException {		
		
		//Open Website and click on pdf download and validate pdf content and close pdf window.
		
		driver.get("https://www.axmag.com/"); //This Site is not working now
		Thread.sleep(3000);
		String parentWindow = driver.getWindowHandle();
		driver.findElement(By.xpath("//*[text()='Download']")).click();
		driver.findElement(By.xpath("(//*[text()='Learn more'])[2]")).click(); //Learn More is download link, when we lick, pdf file will open
		
		Set<String> allWindow = driver.getWindowHandles();
		
		for(String eachWindow : allWindow) {
			
			if(eachWindow.equals(parentWindow)) {
				continue;
			}
			
			driver.switchTo().window(eachWindow);
			
			String currentUrl = driver.getCurrentUrl();
			
			URL TestURL = new URL(currentUrl);

			InputStream is = TestURL.openStream();
			BufferedInputStream TestFileParse = new BufferedInputStream(is);
			PDDocument document = null;
			
			document = PDDocument.load(TestFileParse);
			
			PDFTextStripper pdftextstripper = new PDFTextStripper();		
			String pdfContent = pdftextstripper.getText(document);
			
			System.out.println("pdfContent: \n"+pdfContent);
			
			//System.out.println(pdfContent);
			Assert.assertTrue(pdfContent.contains(".pdf"));
			Assert.assertTrue(pdfContent.contains("User Guide"));
			Assert.assertTrue(pdfContent.contains("What is aXmag"));
			Assert.assertTrue(pdfContent.contains("info@axmag.com"));
			Assert.assertTrue(pdfContent.contains("Please refer to the link below for more information"));
			Assert.assertTrue(pdfContent.contains("http://learn.adobe.com/wiki/download/attachments/52658564/acrobat_reader9_flash_security.pdf?version=1"));
			
			driver.close();
			
		}
		
		driver.switchTo().window(parentWindow);
		driver.close();
	}
	
	@Test (enabled=false)
	public void PdfValidation_axmag_UserGuides_FromLocal() throws IOException, InterruptedException {		
		
		//Read PDF from local system
			URL TestURL = new URL("file:///C:/Users/Anand/Downloads/UserGuide.pdf");
		

			InputStream is = TestURL.openStream();
			BufferedInputStream TestFileParse = new BufferedInputStream(is);
			PDDocument document = null;
			
			document = PDDocument.load(TestFileParse);
			
			PDFTextStripper pdftextstripper = new PDFTextStripper();		
			String pdfContent = pdftextstripper.getText(document);
			
			System.out.println("pdfContent: \n"+pdfContent);
			
			//System.out.println(pdfContent);
			Assert.assertTrue(pdfContent.contains(".pdf"));
			Assert.assertTrue(pdfContent.contains("User Guide"));
			Assert.assertTrue(pdfContent.contains("What is aXmag"));
			Assert.assertTrue(pdfContent.contains("info@axmag.com"));
			Assert.assertTrue(pdfContent.contains("Please refer to the link below for more information"));
			Assert.assertTrue(pdfContent.contains("http://learn.adobe.com/wiki/download/attachments/52658564/acrobat_reader9_flash_security.pdf?version=1"));
	}
	
	
	
	
	

}
