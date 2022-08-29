package MultipleScenario;

import java.io.IOException;
import java.util.Hashtable;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import helper.ExcelReader;
import helper.ExcelUtilities;
import helper.Utilities;

public class ExcelTest {
	
	WebDriver driver;
	
	
	@Test(enabled=true)
	public void ExcelUtilitiesTest_ReadLargeExcel() throws Exception {
		
		String fPath = "C:\\SeleniumTraining\\Project_Code\\Data\\LargeData.xlsx";
		System.out.println("Started reading");
		//Read data from excel using readXL method and store into 2d array - testData
		String[][] testData = ExcelUtilities.readXL(fPath, "Sheet1");
		
		System.out.println("Completed reading1");	
		
		int rowSize = testData.length;		
		System.out.println("rowSize1 "+rowSize);
		
		int colSize = testData[0].length;		
		System.out.println("colSize1 "+colSize);
		
	    //Read data from 2d array - testData using column name
		//System.out.println(testData[1][ExcelTest.findColumnNumber(testData, "FirstName")]);	    
	    
	    //Loop testData array to fetch all records 	    
	    for(int rowNum = 1; rowNum<rowSize;rowNum++) {
	    	//System.out.println("==========");
	    	//String firstName = testData[rowNum][0];
	    	String firstName = testData[rowNum][ExcelTest.findColumnNumber(testData, "Data1")];
	    	//System.out.println("firstName: "+firstName);
	    	
	    	//String lastName = testData[rowNum][1];
	    	String lastName = testData[rowNum][ExcelTest.findColumnNumber(testData, "Data2")];
	    	//System.out.println("lastName: "+lastName);
	    	
	    	

	    
	    }
	    
	    
	    String[][] testData1= ExcelUtilities.readXL(fPath, "Sheet1");
		
		System.out.println("Completed reading2");	
		
		int rowSize1 = testData.length;		
		System.out.println("rowSize2 "+rowSize1);
		
		int colSize1 = testData[0].length;		
		System.out.println("colSize2 "+colSize1);
	}
	
	
	@BeforeMethod
	public void setUp() throws IOException {
		//driver= Utilities.openBrowser();		
	}
	
	@Test (enabled=false)
	public void ExcelReadFromSingleRow() {
		
		String fPath = "C:\\SeleniumTraining\\Project_Code\\TestData.xlsx";
		
		ExcelReader reader = new ExcelReader(fPath);
		
		int rowCount = reader.getRowCount("UserData");		
		System.out.println("Row Count: "+rowCount);
		
		int colCount = reader.getColumnCount("UserData");
		System.out.println("Column Count: "+colCount);
		
		//Get Test Data From single row of specified column using column name.
		//reader.getCellData(sheetName, colName, rowNum)
		String firstName = reader.getCellData("UserData", "FirstName", 2);
		System.out.println("First Name: "+firstName);
		
		String lastName = reader.getCellData("UserData", "LastName", 2);
		System.out.println("Last Name: "+lastName);
		
		String userName = reader.getCellData("UserData", "UserName", 2);
		System.out.println("User Name: "+userName);
		
		
		//get Test data based on column number (column number is 0 indexed so 3 means 4th column)
		//reader.getCellData(sheetName, colNum, rowNum);
		System.out.println(reader.getCellData("UserData", 3, 2));
			
	}
	
	
	@Test (enabled=false)
	public void ExcelReadAllRecordsAndWrite() {
		
		String fPath = "C:\\SeleniumTraining\\Project_Code\\TestData.xlsx";
		
		ExcelReader reader = new ExcelReader(fPath);
		
		int rowCount = reader.getRowCount("UserData");		
		System.out.println("Row Count: "+rowCount);
		
		int colCount = reader.getColumnCount("UserData");
		System.out.println("Column Count: "+colCount);
		
		//reader.removeColumn("UserData", 3);
		reader.removeColumnByColumnName("UserData", "Status");
		reader.addColumn("UserData", "Status");
		
		//Get Test Data From all rows of specified column.
		//reader.getCellData(sheetName, colName, rowNum)
		
		for (int rowNum=2;rowNum<=rowCount;rowNum++) {			
			String firstName = reader.getCellData("UserData", "FirstName", rowNum);
			System.out.println("First Name: "+firstName);
			
			String lastName = reader.getCellData("UserData", "LastName", rowNum);
			System.out.println("Last Name: "+lastName);
			
			String userName = reader.getCellData("UserData", "UserName", rowNum);
			System.out.println("User Name: "+userName);		
			
			//Writing data to excel
			//reader.setCellData(sheetName, colName, rowNum, data)
			//reader.setCellData("UserData", "Status", rowNum, "Pass");
			
			//reader.setCellData(sheetName, colName, rowNum, data, url)
			reader.setCellData("UserData", "Status", rowNum, "Pass","test-output");
		}
		
	}

	@Test(enabled=false)
	public void ExcelUtilitiesTest() throws Exception {
		
		String fPath = "C:\\SeleniumTraining\\Project_Code\\TestData.xlsx";
		
		//Read data from excel using readXL method and store into 2d array - testData
		String[][] testData = ExcelUtilities.readXL(fPath, "UserData");
		
		//System.out.println(data[0][0]); --->FirstName		
		
		int rowSize = testData.length;		
		System.out.println("rowSize "+rowSize);
		
		int colSize = testData[0].length;		
		System.out.println("colSize "+colSize);
		
	    //Read data from 2d array - testData using column name
		//System.out.println(testData[1][ExcelTest.findColumnNumber(testData, "FirstName")]);	    
	    
	    //Loop testData array to fetch all records 	    
	    for(int rowNum = 1; rowNum<rowSize;rowNum++) {
	    	System.out.println("==========");
	    	//String firstName = testData[rowNum][0];
	    	String firstName = testData[rowNum][ExcelTest.findColumnNumber(testData, "FirstName")];
	    	System.out.println("firstName: "+firstName);
	    	
	    	//String lastName = testData[rowNum][1];
	    	String lastName = testData[rowNum][ExcelTest.findColumnNumber(testData, "LastName")];
	    	System.out.println("lastName: "+lastName);
	    	
	    	//String age = testData[rowNum][2];
	    	String age = testData[rowNum][ExcelTest.findColumnNumber(testData, "Age")];
	    	System.out.println("age: "+age);
	    	
	    	//String userName = testData[rowNum][3];
	    	String userName = testData[rowNum][ExcelTest.findColumnNumber(testData, "UserName")];
	    	System.out.println("userName: "+userName);
	    	
	    	    	
	    //Write data to 2d array - testData
	    testData[rowNum][ExcelTest.findColumnNumber(testData, "Status")]="PASS";
	    
	    }
	    
	  //Write data from testData array to excel using writeXL method 
	    ExcelUtilities.writeXL(fPath, "UserData", testData);
	    
   }
	
	public static int findColumnNumber(String[][] data, String colName) {
		
		int colNum=-1;
		int colSize = data[0].length; //column size of header row which contains column name
		
		for (int i = 0;i<colSize;i++) {			
			if(data[0][i].contains(colName)) {
				colNum=i;
				break;
			}			
		 }
		return colNum;
	}
	
	@Test(enabled=false)
	public void ExcelUtilitiesTest_HashTable() throws Exception {
		
		String fPath = "C:\\SeleniumTraining\\Project_Code\\TestData.xlsx";
		
		//Read data from excel using readXL method and store into 2d array - testData
		String[][] testData = ExcelUtilities.readXL(fPath, "UserData");
		
		//System.out.println(testData[0][0]); --->FirstName		
		
		int rowSize = testData.length;		
		System.out.println("rowSize "+rowSize);
		
		int colSize = testData[0].length;		
		System.out.println("colSize "+colSize);
		
		Hashtable<String,String> table = null; 
		
		for(int rowNum = 1; rowNum<rowSize;rowNum++) {
			
			table = new Hashtable<String,String>();
			for(int colNum = 0; colNum<colSize;colNum++) {
				table.put(testData[0][colNum], testData[rowNum][colNum]);
			}
			
			System.out.println("-----------");
			System.out.println(table.get("FirstName"));
			System.out.println(table.get("LastName"));
			System.out.println(table.get("UserName"));
			System.out.println(table.get("Age"));
			
			
			//table.put(key, "PASS");
			
			//Use Assertion and set to PASS/FAIL
			//testData[rowNum][5] = "tttt";
			testData[rowNum][ExcelTest.findColumnNumber(testData, "Status")]="PASS";
			
			//Write data from testData array to excel using writeXL method 
			ExcelUtilities.writeXL(fPath, "UserData", testData);
		}
		
    }
	
}
