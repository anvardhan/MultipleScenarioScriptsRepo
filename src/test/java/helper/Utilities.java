package helper;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Utilities {
	
	public static WebDriver driver;
	public static Properties prop;
	
	public static WebDriver openBrowser() throws IOException {
		
		prop=Utilities.readConfig();
		String browser = prop.getProperty("browser");	
		
		switch(browser) {		
			case "chrome": 
				WebDriverManager.chromedriver().setup();
				//System.setProperty("webdriver.chrome.driver", ".\\src\\main\\resources\\browserdrivers\\chromedriver.exe");		
				driver = new ChromeDriver();
				driver.manage().window().maximize();
				break;
			case "ie": 
				System.setProperty("webdriver.ie.driver", ".\\src\\main\\resources\\browserdrivers\\IEDriverServer.exe");
				driver = new InternetExplorerDriver();
				driver.manage().window().maximize();
				break;		
			case "firefox": 
			System.setProperty("webdriver.gecko.driver", ".\\src\\main\\resources\\browserdrivers\\geckodriver.exe");
			driver = new FirefoxDriver();
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
			break;			
		}
		return driver;		
	}
	
	
	public static Properties readConfig() throws IOException {
		
		File f = new File("config.properties");		
		FileInputStream fis = new FileInputStream(f);		
		prop = new Properties();		
		prop.load(fis);		
		fis.close();		
		return prop;		
	}
	
	public static String decodePassword(String encodedPassword) throws Base64DecodingException {		
		byte[] decodedPassword = Base64.decode(encodedPassword);
		return new String(decodedPassword);
	}
	
	
	public static void highlightElement(WebDriver driver, WebElement element) {
		JavascriptExecutor js=(JavascriptExecutor)driver; 		 
		js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);
		 
		try {
		Thread.sleep(1000);
		} 
		catch (InterruptedException e) {		 
		System.out.println(e.getMessage());
		}  
		js.executeScript("arguments[0].setAttribute('style','border: solid 2px white;');", element); 
		 
	}
	
	
	public static String getScreenshot(WebDriver driver, String screenShotName) throws IOException {
		
		SimpleDateFormat customDateFormat = new SimpleDateFormat("yyyyMMdd_hhmmss");
		Date currentDate = new Date();
		String customDateName = customDateFormat.format(currentDate);
		
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		
		//FileUtils.copyFile(source, new File("./Screenshots/"+result.getName()+".png"));
		
		//Screenshots will go under Screenshots folder.
		String destinationPath = System.getProperty("user.dir") + "/Screenshots/" + screenShotName+"_"+customDateName+".png";
		FileUtils.copyFile(source, new File(destinationPath));
		
		return destinationPath;		
	}


	//Below Methods are used in DatabaseOperations.java file
	
	//Pass SQL and Table name - Not useful
	public static void exportDBToExcel(String tableName, String sql, String excelFilePath) throws ClassNotFoundException {
        
		Connection con = DbConnection.getConnection();        
        ResultSet rs = DbConnection.getDataFromDB(con, sql);
         
        try {
        	XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Sheet1");
 
            writeHeaderLine(tableName, sheet); 
            writeDataLines(tableName, rs, workbook, sheet);
 
            FileOutputStream outputStream = new FileOutputStream(excelFilePath);
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
            workbook.close();
 
        } catch (SQLException e) {
            System.out.println("Datababse error:");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("File IO error:");
            e.printStackTrace();
        }
        
        try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }	
	
    private static void writeHeaderLine(String tableName, XSSFSheet sheet) {
 
        if(tableName.equalsIgnoreCase("Student")) {
	    	WriteHeaderLine_Student(sheet);
        } else if (tableName.equalsIgnoreCase("Address")) {
        	WriteHeaderLine_Address(sheet);
        }
    }
 
    private static void WriteHeaderLine_Student(XSSFSheet sheet) {
    	Row headerRow = sheet.createRow(0);
   	 
        Cell headerCell = headerRow.createCell(0);
        headerCell.setCellValue("idStudent");
 
        headerCell = headerRow.createCell(1);
        headerCell.setCellValue("StudentFirstName");
 
        headerCell = headerRow.createCell(2);
        headerCell.setCellValue("StudentLastName");
 
        headerCell = headerRow.createCell(3);
        headerCell.setCellValue("StudentAge");
 
        headerCell = headerRow.createCell(4);
        headerCell.setCellValue("StudentDOB");
        
        headerCell = headerRow.createCell(5);
        headerCell.setCellValue("StudentUserName");
		
	}

    private static void WriteHeaderLine_Address(XSSFSheet sheet) {
    	Row headerRow = sheet.createRow(0);
   	 
        Cell headerCell = headerRow.createCell(0);
        headerCell.setCellValue("id");
        
        headerCell = headerRow.createCell(1);
        headerCell.setCellValue("address1");
 
        headerCell = headerRow.createCell(2);
        headerCell.setCellValue("address2");
 
        headerCell = headerRow.createCell(3);
        headerCell.setCellValue("city");
		
	}
      
	private static void writeDataLines(String tableName, ResultSet result, XSSFWorkbook workbook,
            XSSFSheet sheet) throws SQLException {
        
    	if(tableName.equalsIgnoreCase("Student")) {
    		writeDataLines_Student(result, workbook, sheet);
    	} else if(tableName.equalsIgnoreCase("Address")) {
    		writeDataLines_Address(result, workbook, sheet);
    	}
    }
	
	private static void writeDataLines_Student(ResultSet result, XSSFWorkbook workbook,
            XSSFSheet sheet) throws SQLException {

		int rowCount = 1;

        while (result.next()) {
            int stuId = result.getInt("idStudent");
            String stuFirstName = result.getString("StudentFirstName");
            String stuLastName = result.getString("StudentLastName");
            int stuAge = result.getInt("StudentAge");
            String stuDob = result.getString("StudentDOB");
            String stuUserName = result.getString("StudentUserName");
 
            Row row = sheet.createRow(rowCount++);
 
            int columnCount = 0;
            Cell cell = row.createCell(columnCount++);
            cell.setCellValue(stuId);
 
            cell = row.createCell(columnCount++);
            cell.setCellValue(stuFirstName);
 
            cell = row.createCell(columnCount++); 
            cell.setCellValue(stuLastName);
 
            cell = row.createCell(columnCount++);
            cell.setCellValue(stuAge);
 
            cell = row.createCell(columnCount++);
            cell.setCellValue(stuDob);
            
            cell = row.createCell(columnCount);
            cell.setCellValue(stuUserName);
 
        }
	
		
	}
	
	private static void writeDataLines_Address(ResultSet result, XSSFWorkbook workbook,
            XSSFSheet sheet) throws SQLException {

		int rowCount = 1;

        while (result.next()) {
            int addId = result.getInt("id");
            String add1 = result.getString("address1");
            String add2 = result.getString("address2");
            String city = result.getString("city");
                        
            Row row = sheet.createRow(rowCount++);
 
            int columnCount = 0;
            Cell cell = row.createCell(columnCount++);
            cell.setCellValue(addId);
 
            cell = row.createCell(columnCount++);
            cell.setCellValue(add1);
 
            cell = row.createCell(columnCount++); 
            cell.setCellValue(add2);
 
            cell = row.createCell(columnCount++);
            cell.setCellValue(city);
  
        }
	}

//Need to consider Pkey into consideration before comparison
	public static void CompareTwoArrays_Student(String[][] inputData, String[][] dbData) {
		
		for(int i = 1; i<inputData.length; i++) {
			
			int stuIdExcel = Integer.parseInt(inputData[i][0]);
			String stuFirstNameExcel = inputData[i][1];
			String stuDobExcel = inputData[i][4];			
			
			int stuIdDb = Integer.parseInt(dbData[i][0]);
			String stuFirstNameDb = dbData[i][1];
			String stuDobDb = dbData[i][4];
			
			if(stuIdExcel == stuIdDb) {
				System.out.println("Student id value is same in input file and db");
			}
			
			if(stuFirstNameExcel.equals(stuFirstNameDb)) {
				System.out.println("Student First Name value is same in input file and db");
			}
			
			if(stuDobExcel != null) {
				if(stuDobExcel.equals(stuDobDb)) {
					System.out.println("Student Dob value is same in input file and db");
				} else {
					System.out.println("Student Dob value is NOT same in input file and db");
				}
			} else if(stuDobDb.equals("NULL")) {
				System.out.println("Student Dob value is empty in input file and NULL in db");
			} else {
				//System.out.println("stuDobExcel: "+stuDobExcel);
				//System.out.println("stuDobDb: "+stuDobDb);
				System.out.println("Student Dob value is empty in input file but value in db is not null");
			}
		}
		
	}
	
	public static void CompareTwoArrays_Address(String[][] inputData, String[][] dbData) {
		
		for(int i = 1; i<inputData.length; i++) {
			
			String addIdExcel = inputData[i][0];
			String addAddress1Excel = inputData[i][1];
			String addAddress2Excel = inputData[i][2];	
			String addCityExcel = inputData[i][3];
			
			String addIdDb = dbData[i][0];
			String addAddress1Db = dbData[i][1];
			String addAddress2Db = dbData[i][2];	
			String addCityDb = dbData[i][3];
			
			if(addIdExcel != null) {
				if(addIdExcel.equals(addIdDb)) {
					System.out.println("Address id value is same in input file and db");
				} else {
					System.out.println("Address id value is NOT same in input file and db");
				}
			} else if(addIdDb.equals("NULL")) {
				System.out.println("Address id value is empty in input file and NULL in db");
			} else {
				System.out.println("Address id value is empty in input file but value in db is not null");
			}
			
						
			if(addAddress1Excel.equals(addAddress1Db)) {
				System.out.println("Address1 value is same in input file and db");
			}
			
			if(addAddress2Excel != null) {
				if(addAddress2Excel.equals(addAddress2Db)) {
					System.out.println("Address2 value is same in input file and db");
				} else {
					System.out.println("Address2 value is NOT same in input file and db");
				}
			} else if(addAddress2Db.equals("NULL")) {
				System.out.println("Address2 value is empty in input file and NULL in db");
			} else {
				System.out.println("Address2 value is empty in input file but value in db is not null");
			}
			
			if(addCityExcel != null) {
				if(addCityExcel.equals(addCityDb)) {
					System.out.println("City value is same in input file and db");
				} else {
					System.out.println("City value is NOT same in input file and db");
				}
			} else if(addCityDb.equals("NULL")) {
				System.out.println("City value is empty in input file and NULL in db");
			} else {
				System.out.println("City value is empty in input file but value in db is not null");
			}
		}
	}

	public static void compareExcelAndDB_Student(String[][] inputData, ResultSet rs) throws SQLException {
		
		for(int i = 1; i<inputData.length; i++) {
			
			String stuIdExcel = inputData[i][0];
			String stuFirstNameExcel = inputData[i][1];
			String stuDobExcel = inputData[i][4];			
			
			rs.next();
			String stuIdDb = rs.getString("idStudent");
			String stuFirstNameDb = rs.getString("StudentFirstName");
			String stuDobDb = rs.getString("StudentDOB");
			
			if(stuIdExcel == stuIdDb) {
				System.out.println("Student id value is same in input file and db");
			}
			
			if(stuFirstNameExcel.equals(stuFirstNameDb)) {
				System.out.println("Student First Name value is same in input file and db");
			}
			
			if(stuDobExcel != null) {
				if(stuDobExcel.equals(stuDobDb)) {
					System.out.println("Student Dob value is same in input file and db");
				} else {
					System.out.println("Student Dob value is NOT same in input file and db");
				}
			} else if(stuDobDb.equals("NULL")) {
				System.out.println("Student Dob value is empty in input file and NULL in db");
			} else {
				//System.out.println("stuDobExcel: "+stuDobExcel);
				//System.out.println("stuDobDb: "+stuDobDb);
				System.out.println("Student Dob value is empty in input file but value in db is not null");
			}
		}
	}
	
	public static void compareExcelAndDB_Address(String[][] inputData, ResultSet rs) throws SQLException {
		
		for(int i = 1; i<inputData.length; i++) {
			
			String addIdExcel = inputData[i][0];
			String addAddress1Excel = inputData[i][1];
			addAddress1Excel = maskData(addAddress1Excel);
			String addAddress2Excel = inputData[i][2];	
			String addCityExcel = inputData[i][3];
			
			rs.next();
			String addIdDb = rs.getString("id");
            String addAddress1Db = rs.getString("address1");
            String addAddress2Db = rs.getString("address2");
            String addCityDb = rs.getString("city");
			
			if(addIdExcel != null) {
				if(addIdExcel.equals(addIdDb)) {
					System.out.println("Address id value is same in input file and db");
				} else {
					System.out.println("Address id value is NOT same in input file and db");
				}
			} else if(addIdDb.equals("NULL")) {
				System.out.println("Address id value is empty in input file and NULL in db");
			} else {
				System.out.println("Address id value is empty in input file but value in db is not null");
			}
			
						
			if(addAddress1Excel.equals(addAddress1Db)) {
				System.out.println("Address1 value is same in input file and db");
			} else {
				System.out.println("Address1 value is NOT same in input file and db "+addAddress1Excel+" -- "+addAddress1Db);
			}
			
			if(addAddress2Excel != null) {
				if(addAddress2Excel.equals(addAddress2Db)) {
					System.out.println("Address2 value is same in input file and db");
				} else {
					System.out.println("Address2 value is NOT same in input file and db");
				}
			} else if(addAddress2Db.equals("NULL")) {
				System.out.println("Address2 value is empty in input file and NULL in db");
			} else {
				System.out.println("Address2 value is empty in input file but value in db is not null");
			}
			
			if(addCityExcel != null) {
				if(addCityExcel.equals(addCityDb)) {
					System.out.println("City value is same in input file and db");
				} else {
					System.out.println("City value is NOT same in input file and db");
				}
			} else if(addCityDb.equals("NULL")) {
				System.out.println("City value is empty in input file and NULL in db");
			} else {
				System.out.println("City value is empty in input file but value in db is not null");
			}
		}
	}
	
	public static void compareExcelAndDB_Dynamic(String[][] inputData, ResultSet rs) throws SQLException {
		
		ResultSetMetaData metaData = rs.getMetaData();
        int numberOfColumns = metaData.getColumnCount();
        System.out.println("numberOfColumns: "+numberOfColumns);
        
        String[] columnName = new String[numberOfColumns];
        
        System.out.print("Validatiing: ");
        // Start from first column therefore i=1
        for (int i = 1; i <= numberOfColumns; i++) {
            columnName[i-1] = metaData.getColumnName(i);
            System.out.print(columnName[i-1]+" ");
        }
        System.out.println();
        System.out.println();	
		for(int i = 1; i<inputData.length; i++) {
			System.out.println("<<<<<<Record "+i+" validation>>>>>>");
			System.out.println();
			rs.next();
			
			for(int j = 0; j < numberOfColumns; j++) {
				String dbFieldName = columnName[j];
				String excelFieldName = null;
				//String excelFieldName = getFieldName(dbFieldName);
				excelFieldName = dbFieldName;
				
				String excelFieldValue = inputData[i][ExcelUtilities.findColumnIndex(inputData, excelFieldName)];
				//System.out.println("excelFieldValue before mask "+excelFieldValue);
				
				String dbFieldValue = rs.getString(dbFieldName);
				
				if(excelFieldValue != null) {
					excelFieldValue = maskData(excelFieldValue);
					//System.out.println("excelFieldValue after mask "+excelFieldValue);
					if(excelFieldValue.equals(dbFieldValue)) {
						System.out.println("PASS-->'"+excelFieldName+"' from input file: "+excelFieldValue+" and '"+dbFieldName+"' from db: "+dbFieldValue);
					} else {
						System.out.println("FAIL-->'"+excelFieldName+"' from input file: "+excelFieldValue+" and '"+dbFieldName+"' from db: "+dbFieldValue);
					}
				} else if(dbFieldValue.equals("NULL")) {
					System.out.println("PASS-->'"+excelFieldName+"' from input file: "+excelFieldValue+" and '"+dbFieldName+"' from db: "+dbFieldValue);
				} else {
					System.out.println("FAIL-->'"+excelFieldName+"' from input file: "+excelFieldValue+" and '"+dbFieldName+"' from db: "+dbFieldValue);
				}
				
			}
			System.out.println("--------------------------------");	
		}
	}
	
	public static String[][] compareExcelAndDB_DynamicWithOutput(String[][] inputData, ResultSet rs) throws SQLException {
		
		ResultSetMetaData metaData = rs.getMetaData();
        int numberOfColumns = metaData.getColumnCount();
        System.out.println("numberOfColumns: "+numberOfColumns);
        
        String[] columnName = new String[numberOfColumns];
        
        System.out.print("Validatiing: ");
        // Start from first column therefore i=1
        for (int i = 1; i <= numberOfColumns; i++) {
            columnName[i-1] = metaData.getColumnName(i);
            System.out.print(columnName[i-1]+" ");
        }
        
        int totalRecords = inputData.length-1;
      //Output file - comparison file
      	//String[][] outputData = new String[(numberOfColumns*totalRecords)+1][4];
      	String[][] outputData = new String[((numberOfColumns+1)*totalRecords)+1][4];
      	outputData[0][0] = "FieldName";
      	outputData[0][1] = "Expected value (excel)";
      	outputData[0][2] = "Actual value (DB)";
      	outputData[0][3] = "Result";
      		
        System.out.println();
        System.out.println();
        int k = 1;
        
		for(int i = 1; i<inputData.length; i++) {
			System.out.println("<<<<<<Record "+i+" validation>>>>>>");
			//outputData[k][0] = "<<<<<<Record "+i+" validation>>>>>>";
			outputData[k][1] = "<Record "+i+" validation>";
			outputData[k][2] = "<Record "+i+" validation>";
			System.out.println();
			rs.next();
			k++;
			for(int j = 0; j < numberOfColumns; j++) {
				String dbFieldName = columnName[j];
				String excelFieldName = null;
				//String excelFieldName = getFieldName(dbFieldName);
				//excelFieldName = excelFieldName.split(".")[1];
				excelFieldName = dbFieldName;
				outputData[k][0] = excelFieldName;
				
				String excelFieldValue = inputData[i][ExcelUtilities.findColumnIndex(inputData, excelFieldName)];
				//System.out.println("excelFieldValue before mask "+excelFieldValue);
				
				String dbFieldValue = rs.getString(dbFieldName);
				
			     if(excelFieldValue != null) {
					excelFieldValue = maskData(excelFieldValue);
					//System.out.println("excelFieldValue after mask "+excelFieldValue);
					if(excelFieldValue.equals(dbFieldValue)) {
						System.out.println("PASS-->'"+excelFieldName+"' from input file: "+excelFieldValue+" and '"+dbFieldName+"' from db: "+dbFieldValue);
						outputData[k][1] = excelFieldValue;
						outputData[k][2] = dbFieldValue;
						outputData[k][3] = "Pass";
						
					} else {
						System.out.println("FAIL-->'"+excelFieldName+"' from input file: "+excelFieldValue+" and '"+dbFieldName+"' from db: "+dbFieldValue);
						outputData[k][1] = excelFieldValue;
						outputData[k][2] = dbFieldValue;
						outputData[k][3] = "Fail";
					}
				} else if(dbFieldValue.equals("NULL")) {
					System.out.println("PASS-->'"+excelFieldName+"' from input file: "+excelFieldValue+" and '"+dbFieldName+"' from db: "+dbFieldValue);
					outputData[k][1] = excelFieldValue;
					outputData[k][2] = dbFieldValue;
					outputData[k][3] = "Pass";
				} else {
					System.out.println("FAIL-->'"+excelFieldName+"' from input file: "+excelFieldValue+" and '"+dbFieldName+"' from db: "+dbFieldValue);
					outputData[k][1] = excelFieldValue;
					outputData[k][2] = dbFieldValue;
					outputData[k][3] = "Fail";
				}
				k++;
			}
			System.out.println("--------------------------------");	
			
		}
		return outputData;
	}
	
	public static String[][] compareExcelAndDB_DynamicWithOutput1(String[][] inputData, ResultSet rs) throws SQLException {
		
		//Considering input file has column name [tablename.columnname]
		//add fields which is OOO
		
		//Store Database columns
		ResultSetMetaData metaData = rs.getMetaData();
        int numberOfColumns = metaData.getColumnCount();
        System.out.println("numberOfColumns: "+numberOfColumns);
        
        String[] columnName = new String[numberOfColumns];
        
        System.out.print("Validatiing(DB Fields): ");
        // Start from first column therefore i=1
        for (int i = 1; i <= numberOfColumns; i++) {
            columnName[i-1] = metaData.getColumnName(i);
            System.out.print(columnName[i-1]+" ");
        }
        System.out.println();
        
        String[] excelFields = new String[inputData[0].length];
        
        //Store InputFile Columns
        System.out.print("Field Name in excel file: ");
        for (int i = 0; i < inputData[0].length; i++) {
        	excelFields[i] = inputData[0][i];
        	System.out.print(inputData[0][i]+" ");
        	
        }
        System.out.println();
        
                
        //Result Excel
        int totalRecords = inputData.length-1;
      //Output file - comparison file
      	//String[][] outputData = new String[(numberOfColumns*totalRecords)+1][4];
      	String[][] outputData = new String[((numberOfColumns+1)*totalRecords)+1][4];
      	outputData[0][0] = "FieldName";
      	outputData[0][1] = "Expected value (excel)";
      	outputData[0][2] = "Actual value (DB)";
      	outputData[0][3] = "Result";
      		
        System.out.println();
        System.out.println();
        int k = 1;
        
        //Validation
		for(int i = 1; i<inputData.length; i++) {
			System.out.println("<<<<<<Record "+i+" validation>>>>>>");
			//outputData[k][0] = "<<<<<<Record "+i+" validation>>>>>>";
			outputData[k][1] = "<Record "+i+" validation>";
			outputData[k][2] = "<Record "+i+" validation>";
			System.out.println();
			
			rs.next();
			k++;
			for(int j = 0; j < numberOfColumns; j++) {
				String dbFieldName = columnName[j];	
				String str = excelFields[j];
				String[] excelFieldNames = excelFields[j].split("\\.");
				//System.out.println(excelFieldNames[0]); //value before .
				//System.out.println("excelFieldName after split: "+excelFieldNames[1]);
								
				//String excelFieldName = excelFieldNames[1].toLowerCase();
				//System.out.println("excelFieldName after converting to lowercase: "+excelFieldName);
				
				//String excelFieldName = excelFields[j];
				String excelFieldName = "address."+dbFieldName.toUpperCase();
				System.out.println(excelFieldName);
				
				outputData[k][0] = excelFieldName;
				
				String excelFieldValue = inputData[i][ExcelUtilities.findColumnIndex(inputData, excelFieldName)];
				//System.out.println("excelFieldValue before mask "+excelFieldValue);
				
				String dbFieldValue = rs.getString(dbFieldName);
				
				outputData[k][1] = excelFieldValue;
				outputData[k][2] = dbFieldValue;
				
				if(excelFieldName.equalsIgnoreCase("AddressIdentifier")) {
					outputData[k][3] = "Pass";
				}
				
				else if(excelFieldValue != null) {
					excelFieldValue = maskData(excelFieldValue);
					//System.out.println("excelFieldValue after mask "+excelFieldValue);
					if(excelFieldValue.equals(dbFieldValue)) {
						System.out.println("PASS-->'"+excelFieldName+"' from input file: "+excelFieldValue+" and '"+dbFieldName+"' from db: "+dbFieldValue);
						outputData[k][3] = "Pass";
						
					} else {
						System.out.println("FAIL-->'"+excelFieldName+"' from input file: "+excelFieldValue+" and '"+dbFieldName+"' from db: "+dbFieldValue);
						outputData[k][3] = "Fail";
					}
				} else if(dbFieldValue.equals("NULL")) {
					System.out.println("PASS-->'"+excelFieldName+"' from input file: "+excelFieldValue+" and '"+dbFieldName+"' from db: "+dbFieldValue);
					outputData[k][3] = "Pass";
				} else {
					System.out.println("FAIL-->'"+excelFieldName+"' from input file: "+excelFieldValue+" and '"+dbFieldName+"' from db: "+dbFieldValue);
					outputData[k][3] = "Fail";
				}
				k++;
			}
			System.out.println("--------------------------------");	
			
		}
		return outputData;
	}
	
	//Not Working - Excel read using HashMap and passed arraylist from calling method to this calling method
	public static String[][] compareExcelAndDB_DynamicWithOutput1_HashMap(ArrayList<HashMap<String, String>> inputData, ResultSet rs) throws SQLException {
		
		//Store Database columns
		ResultSetMetaData metaData = rs.getMetaData();
        int numberOfColumns = metaData.getColumnCount();
        System.out.println("numberOfColumns: "+numberOfColumns);
        
        String[] columnName = new String[numberOfColumns];
        
        System.out.print("Validatiing(DB Fields): ");
        // Start from first column therefore i=1
        for (int i = 1; i <= numberOfColumns; i++) {
            columnName[i-1] = metaData.getColumnName(i);
            System.out.print(columnName[i-1]+" ");
        }
        System.out.println();
       
        //Result Excel
        int totalRecords = inputData.size();
        //Output file - comparison file
      	//String[][] outputData = new String[(numberOfColumns*totalRecords)+1][4];
      	String[][] outputData = new String[((numberOfColumns+1)*totalRecords)+1][4];
      	outputData[0][0] = "FieldName";
      	outputData[0][1] = "Expected value (excel)";
      	outputData[0][2] = "Actual value (DB)";
      	outputData[0][3] = "Result";
      		
        System.out.println();
        System.out.println();
        int k = 1;
        
        //Validation - Iterating List of HashMap
        
              
        
        
        
        //Validation - Not working
	/*	for(int i = 1; i<=totalRecords; i++) {
			System.out.println("<<<<<<Record "+i+" validation>>>>>>");
			//outputData[k][0] = "<<<<<<Record "+i+" validation>>>>>>";
			outputData[k][1] = "<Record "+i+" validation>";
			outputData[k][2] = "<Record "+i+" validation>";
			System.out.println();
			
			rs.next();
			k++;
			
			HashMap<String, String> excelFieldValues = inputData.get(i-1);
			System.out.println(excelFieldValues);
			for(int j = 0; j < numberOfColumns; j++) {
				String dbFieldName = columnName[j];	
				String excelFieldNameKey = dbFieldName.toUpperCase();
				System.out.println("excelFieldNameKey: "+excelFieldNameKey);
				
				outputData[k][0] = excelFieldNameKey;				
				
				String excelFieldValue = excelFieldValues.get(excelFieldNameKey);
				//System.out.println("excelFieldValue "+excelFieldValue);
				
				String dbFieldValue = rs.getString(dbFieldName);
				
				if(excelFieldNameKey.equalsIgnoreCase("AddressIdentifier")) {
					//Out of scope fields- Just store data in result sheet
					outputData[k][1] = excelFieldValue;
					outputData[k][2] = dbFieldValue;
					outputData[k][3] = "Pass";
				}
				
				else if(excelFieldValue != null) {
					excelFieldValue = maskData(excelFieldValue);
					//System.out.println("excelFieldValue after mask "+excelFieldValue);
					if(excelFieldValue.equals(dbFieldValue)) {
						System.out.println("PASS-->'"+excelFieldNameKey+"' from input file: "+excelFieldValue+" and '"+dbFieldName+"' from db: "+dbFieldValue);
						outputData[k][1] = excelFieldValue;
						outputData[k][2] = dbFieldValue;
						outputData[k][3] = "Pass";
						
					} else {
						System.out.println("FAIL-->'"+excelFieldNameKey+"' from input file: "+excelFieldValue+" and '"+dbFieldName+"' from db: "+dbFieldValue);
						outputData[k][1] = excelFieldValue;
						outputData[k][2] = dbFieldValue;
						outputData[k][3] = "Fail";
					}
				} else if(dbFieldValue.equals("NULL")) {
					System.out.println("PASS-->'"+excelFieldNameKey+"' from input file: "+excelFieldValue+" and '"+dbFieldName+"' from db: "+dbFieldValue);
					outputData[k][1] = excelFieldValue;
					outputData[k][2] = dbFieldValue;
					outputData[k][3] = "Pass";
				} else {
					System.out.println("FAIL-->'"+excelFieldNameKey+"' from input file: "+excelFieldValue+" and '"+dbFieldName+"' from db: "+dbFieldValue);
					outputData[k][1] = excelFieldValue;
					outputData[k][2] = dbFieldValue;
					outputData[k][3] = "Fail";
				}
				k++;
			}
			System.out.println("--------------------------------");	
			
		}*/
		return outputData;
	}
	
	public static String[][] compareExcelAndDB_DynamicWithOutput2(String[][] inputData, ResultSet rs) throws SQLException {
		
		ResultSetMetaData metaData = rs.getMetaData();
        int numberOfColumns = metaData.getColumnCount();
        System.out.println("numberOfColumns: "+numberOfColumns);
        
        String[] columnName = new String[numberOfColumns];
        
        System.out.print("Validatiing: ");
        // Start from first column therefore i=1
        for (int i = 1; i <= numberOfColumns; i++) {
            columnName[i-1] = metaData.getColumnName(i);
            System.out.print(columnName[i-1]+" ");
        }
        
        int totalRecords = inputData.length;
      //Output file - comparison file
      	String[][] outputData = new String[totalRecords][(numberOfColumns*3)+1];
      	outputData[0][0] = "Record#";
        		
        System.out.println();
        System.out.println();        
                
		for(int i = 1; i<inputData.length; i++) {
			System.out.println("<<<<<<Record "+i+" validation>>>>>>");
			outputData[i][0] = String.valueOf(i);
			System.out.println();
			rs.next();			
			
			int k = 1;
			for(int j = 0; j < numberOfColumns; j++) {
				String dbFieldName = columnName[j];
				String excelFieldName = null;
				//String excelFieldName = getFieldName(dbFieldName);
				excelFieldName = dbFieldName;				
				
				
				String excelFieldValue = inputData[i][ExcelUtilities.findColumnIndex(inputData, excelFieldName)];
				//System.out.println("excelFieldValue before mask "+excelFieldValue);
				
				String dbFieldValue = rs.getString(dbFieldName);
				
				if(excelFieldValue != null) {
					excelFieldValue = maskData(excelFieldValue);
					//System.out.println("excelFieldValue after mask "+excelFieldValue);
					if(excelFieldValue.equals(dbFieldValue)) {
						System.out.println("PASS-->'"+excelFieldName+"' from input file: "+excelFieldValue+" and '"+dbFieldName+"' from db: "+dbFieldValue);
						outputData[0][k] = excelFieldName+"-excel";
						outputData[i][k] = excelFieldValue;
						k=k+1;
						outputData[0][k] = dbFieldName+"-DB";
						outputData[i][k] = dbFieldValue;
						k=k+1;
						outputData[0][k] = "Result";
						outputData[i][k] = "Pass";
						k=k+1;
						
					} else {
						System.out.println("FAIL-->'"+excelFieldName+"' from input file: "+excelFieldValue+" and '"+dbFieldName+"' from db: "+dbFieldValue);
						outputData[0][k] = excelFieldName+"-excel";
						outputData[i][k] = excelFieldValue;
						k=k+1;
						outputData[0][k] = dbFieldName+"-DB";
						outputData[i][k] = dbFieldValue;
						k=k+1;
						outputData[0][k] = "Result";
						outputData[i][k] = "FAIL";
						k=k+1;
					}
				} else if(dbFieldValue.equals("NULL")) {
					System.out.println("PASS-->'"+excelFieldName+"' from input file: "+excelFieldValue+" and '"+dbFieldName+"' from db: "+dbFieldValue);
					outputData[0][k] = excelFieldName+"-excel";
					outputData[i][k] = excelFieldValue;
					k=k+1;
					outputData[0][k] = dbFieldName+"-DB";
					outputData[i][k] = dbFieldValue;
					k=k+1;
					outputData[0][k] = "Result";
					outputData[i][k] = "Pass";
					k=k+1;
				} else {
					System.out.println("FAIL-->'"+excelFieldName+"' from input file: "+excelFieldValue+" and '"+dbFieldName+"' from db: "+dbFieldValue);
					outputData[0][k] = excelFieldName+"-excel";
					outputData[i][k] = excelFieldValue;
					k=k+1;
					outputData[0][k] = dbFieldName+"-DB";
					outputData[i][k] = dbFieldValue;
					k=k+1;
					outputData[0][k] = "Result";
					outputData[i][k] = "FAIL";
					k=k+1;
				}
			}
			System.out.println("--------------------------------");	
			
		}
		return outputData;
	}
	
	public static void compareLinkedColumnsInDB(ResultSet rs) throws SQLException {
		
		ResultSetMetaData metaData = rs.getMetaData();
        int numberOfColumns = metaData.getColumnCount();
        System.out.println("numberOfColumns -->: "+numberOfColumns);
        
        String[] columnName = new String[numberOfColumns];
        
        while(rs.next()) {
        	
        	 String idStudentFromAddress = rs.getString("address.idStudent");
        	 String idStudentFromStudent = rs.getString("student.idStudent");
        	 String stuLNameFromAddress = rs.getString("address.StudentLastName");
        	 String stuLNameFromStudent = rs.getString("student.StudentLastName");
        	 
        	 if((idStudentFromAddress.equals(idStudentFromStudent)) && (stuLNameFromAddress.equals(stuLNameFromStudent))) {
        		 System.out.println("Pass--> idStudent(address table): "+idStudentFromAddress+" And idStudent(student table): "+idStudentFromStudent+ " for LastName "+stuLNameFromAddress);
        	 } else {
        		 System.out.println("Pass--> idStudent(address table): "+idStudentFromAddress+" And idStudent(student table): "+idStudentFromStudent+ " for LastName "+stuLNameFromAddress);
        	 }  	
        	
        }
               
	}
	
	public static String maskData(String text) {
		
		String maskValue=text;
		
		if(text.contains("43 FountainHead")) {
			maskValue =  "43 FH";
		}
		
		return maskValue;
		
	}



	
	
	//Export entire DB data based on table name - Just pass table name and extract data for any table
		public static void exportDBDataToExcel(String tableName) throws ClassNotFoundException, SQLException {
	        
			String excelFilePath = "ExportFromDb\\"+getFileName(tableName.concat("_Export"))+".xlsx";
			String sql = "SELECT * FROM ".concat(tableName);
			
			//Get data from database
			Connection con = DbConnection.getConnection();        
	        ResultSet rs = DbConnection.getDataFromDB(con, sql);
	         
	        try {
	        	XSSFWorkbook workbook = new XSSFWorkbook();
	            XSSFSheet sheet = workbook.createSheet(tableName); //Making sheet name as table name
	 
	            writeHeaderLineAdvanced(rs, sheet); 
	            writeDataLinesAdvanced(rs, workbook, sheet);
	 
	            FileOutputStream outputStream = new FileOutputStream(excelFilePath);
	            workbook.write(outputStream);
	            outputStream.flush();
	            outputStream.close();
	            workbook.close();
	 
	        } catch (IOException e) {
	            System.out.println("File IO error:");
	            e.printStackTrace();
	        }
	        
	        try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    }
		
		private static void writeHeaderLineAdvanced(ResultSet rs, XSSFSheet sheet) throws SQLException {
			
			// write header line containing column names
	        ResultSetMetaData metaData = rs.getMetaData();
	        int numberOfColumns = metaData.getColumnCount();
	 
	        Row headerRow = sheet.createRow(0);
	 
	        // Start from first column therefore i=1
	        for (int i = 1; i <= numberOfColumns; i++) {
	            String columnName = metaData.getColumnName(i);
	            Cell headerCell = headerRow.createCell(i - 1);
	            headerCell.setCellValue(columnName);
	        }
		}

		private static void writeDataLinesAdvanced(ResultSet rs, XSSFWorkbook workbook, XSSFSheet sheet) throws SQLException {
			
			ResultSetMetaData metaData = rs.getMetaData();
	        int numberOfColumns = metaData.getColumnCount();
	 
	        int rowCount = 1;
	 
	        while (rs.next()) {
	            Row row = sheet.createRow(rowCount++);
	 
	            for (int i = 1; i <= numberOfColumns; i++) {
	                Object valueObject = rs.getObject(i); //get data
	 
	                Cell cell = row.createCell(i - 1);
	                
	                if (valueObject instanceof Integer)
	                    cell.setCellValue((Integer) valueObject);
	                else if (valueObject instanceof Boolean)
	                    cell.setCellValue((Boolean) valueObject);
	                else if (valueObject instanceof Double)
	                    cell.setCellValue((double) valueObject);
	                else if (valueObject instanceof Float)
	                    cell.setCellValue((float) valueObject);
	                else if (valueObject instanceof Date) {
	                    cell.setCellValue((Date) valueObject);
	                    formatDateCell(workbook, cell);
	                } else cell.setCellValue((String) valueObject);
	 
	            }
	 
	        }
		}

		private static void formatDateCell(XSSFWorkbook workbook, Cell cell) {
			CellStyle cellStyle = workbook.createCellStyle();
	        CreationHelper creationHelper = workbook.getCreationHelper();
	        cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-MM-dd HH:mm:ss"));
	        cell.setCellStyle(cellStyle);
		}

		public static String getFileName(String fileName) {
	        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
	        String dateTimeInfo = dateFormat.format(new Date());
	        return fileName.concat(String.format("_%s", dateTimeInfo));
	    }
	

}
