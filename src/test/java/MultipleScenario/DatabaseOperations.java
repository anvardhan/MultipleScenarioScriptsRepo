package MultipleScenario;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import helper.DbConnection;
import helper.ExcelUtilities;
import helper.ExcelUtilitiesNew;
import helper.Utilities;

public class DatabaseOperations {
	
	//public static Connection con=null;
	//public static ResultSet rs = null;
	
	
	
	@Test (enabled=false)
	public void compareDbtoExcelApproach2_Dynamic_WithOutput2() throws Exception {
					
		String fileName = "Address"; //Read from testdata file	
		//String tableName = "Student"; //Read from testdata file			
		
		//Input file name and path
		String inputFilePath = "InputFile\\"+fileName+".xlsx";			
		String sheetName = "Sheet3"; //Read from testdata file
		
		//Output file name and path
		String outputFilePath = "sourceToDBResult\\"+fileName+"_Comparision.xlsx";	
		String outPutData[][] = null;
		
		//Create SQL statement		
		//String sql = "select * from student where idStudent >= 10"+id; //Get the query from testdata file
		//String sql = "select StudentDOB, StudentUserName from student where idStudent >= 10";
		
		//String sql = "select * from address where id >= 1";
		String sql = "select address1,address2,city,StudentLastName from address where id>=1";
		//System.out.println(sql);
		
    	//Reading data from database 
		Connection con = DbConnection.getConnection();
		ResultSet rs = DbConnection.getDataFromDB(con, sql);
		//rs.next();
		//Get the Records count in DB
		rs.last(); 
		int dbRecordCount = rs.getRow(); 
		rs.beforeFirst();		
		System.out.println("Total Records in DB: "+dbRecordCount);
		
		//Read from input excel file
		String[][] inputData = ExcelUtilities.readXL(inputFilePath, sheetName);		
		System.out.println("Total Records in Excel: "+(inputData.length-1));
		
		//Compare input excel file with DB
		
		if(dbRecordCount==(inputData.length-1)) {
			
			outPutData = Utilities.compareExcelAndDB_DynamicWithOutput2(inputData,rs);
					
		} else {
			System.out.println("Records in input file and db is not same");
			System.exit(1);
		}
		
		ExcelUtilities.writeXL(outputFilePath, "Sheet1", outPutData);
		
		//Compare linked columns between tables in DB
		
		/*if(tableName.equalsIgnoreCase("Address")) {
			
			String sql2 = "select address.StudentLastName, address.idStudent, student.StudentLastName, student.idStudent from address,student\r\n" + 
					"where address.StudentLastName = student.StudentLastName";
			rs = DbConnection.getDataFromDB(con, sql2);
			
			Utilities.compareLinkedColumnsInDB(rs);
			
		}	*/	
		con.close();		
	}
	
	
	@Test (enabled=true)
	public void compareDbtoExcelApproach2_Dynamic_WithOutput1() throws Exception {
					
		String fileName = "Address"; //Read from testdata file	
		//String tableName = "Student"; //Read from testdata file			
		
		//Input file name and path
		String inputFilePath = "InputFile\\"+fileName+".xlsx";			
		String inputSheetName = "Sheet3"; //Read from testdata file
		
		//Output file name and path
		//String planSponsorName = "PNC";
		String outputFilePath = "sourceToDBResult\\"+fileName+"_ComparisionAddNew1.xlsx";	
		String outPutData[][] = null;
		String outputSheetName = "MyNewSheetX";
		int sheetIndex = 2;
		
		//Create SQL statement		
		//String sql = "select * from student where idStudent >= 10"+id; //Get the query from testdata file
		//String sql = "select StudentDOB, StudentUserName from student where idStudent >= 10";
		
		String sql = "select * from address where id >= 1";
		//String sql = "select address1,address2,city,StudentLastName from address where id<=1";
		//System.out.println(sql);
		
    	//Reading data from database 
		Connection con = DbConnection.getConnection();
		ResultSet rs = DbConnection.getDataFromDB(con, sql);
		//rs.next();
		//Get the Records count in DB
		rs.last(); 
		int dbRecordCount = rs.getRow(); 
		rs.beforeFirst();		
		System.out.println("Total Records in DB: "+dbRecordCount);
		
		//Read from input excel file
		String[][] inputData = ExcelUtilities.readXL(inputFilePath, inputSheetName);		
		System.out.println("Total Records in Excel: "+(inputData.length-1));
		
		//Compare input excel file with DB
		
		if(dbRecordCount==(inputData.length-1)) {
			
			//outPutData = Utilities.compareExcelAndDB_DynamicWithOutput(inputData,rs);
			outPutData = Utilities.compareExcelAndDB_DynamicWithOutput1(inputData,rs);
					
		} else {
			System.out.println("Records in input file and db is not same");
			System.exit(1);
		}
		
		//ExcelUtilities.writeXL(outputFilePath, outputSheetName, outPutData);
		try {
		ExcelUtilitiesNew.writeXLMultipleSheet(outputFilePath, outputSheetName, sheetIndex, outPutData);
		} catch(IllegalArgumentException e) {
			System.out.println("The workbook already contains a sheet named '"+ outputSheetName+"' at another index");
			e.getMessage();
			System.exit(1);
		}
		//Compare linked columns between tables in DB
		
		/*if(tableName.equalsIgnoreCase("Address")) {
			
			String sql2 = "select address.StudentLastName, address.idStudent, student.StudentLastName, student.idStudent from address,student\r\n" + 
					"where address.StudentLastName = student.StudentLastName";
			rs = DbConnection.getDataFromDB(con, sql2);
			
			Utilities.compareLinkedColumnsInDB(rs);
			
		}	*/	
		con.close();		
	}
	
	@Test (enabled=false) //Need to work on HashMap
	public void compareDbtoExcelApproach2_Dynamic_WithOutput1_UseHashMapToReadXLData() throws Exception {
					
		String fileName = "Address"; //Read from testdata file	
		//String tableName = "Student"; //Read from testdata file			
		
		//Input file name and path
		String inputFilePath = "InputFile\\"+fileName+".xlsx";			
		String inputSheetName = "Sheet3"; //Read from testdata file
		
		//Output file name and path
		String outputFilePath = "sourceToDBResult\\"+fileName+"_Comparision.xlsx";	
		String outPutData[][] = null;
		String outputSheetName = "Sheet1";
		
		//Create SQL statement		
		//String sql = "select * from student where idStudent >= 10"+id; //Get the query from testdata file
		//String sql = "select StudentDOB, StudentUserName from student where idStudent >= 10";
		
		String sql = "select * from address where id <= 4";
		//String sql = "select address1,address2,city,StudentLastName from address where id<=1";
		//System.out.println(sql);
		
    	//Reading data from database 
		Connection con = DbConnection.getConnection();
		ResultSet rs = DbConnection.getDataFromDB(con, sql);
		//rs.next();
		//Get the Records count in DB
		rs.last(); 
		int dbRecordCount = rs.getRow(); 
		rs.beforeFirst();		
		System.out.println("Total Records in DB: "+dbRecordCount);
		
		//Read from input excel file
		//String[][] inputData = ExcelUtilities.readXL(inputFilePath, inputSheetName);		
		//System.out.println("Total Records in Excel: "+(inputData.length-1));
		
		//Read from input file using Hashmap
		ArrayList<HashMap<String,String>> inputData = ExcelUtilities.getTestDataFromDataSheet(inputFilePath,inputSheetName);
		//System.out.println(inputData);
		
		int rowCountInputFile = inputData.size();
				
		System.out.println("Total Records in Excel: "+rowCountInputFile);
		
		//Compare input excel file with DB
		
		if(dbRecordCount==rowCountInputFile) {
			System.out.println("Count Matches");
			outPutData = Utilities.compareExcelAndDB_DynamicWithOutput1_HashMap(inputData,rs);
					
		} else {
			System.out.println("Records in input file and db is not same");
			System.exit(1);
		}
		
		//ExcelUtilities.writeXL(outputFilePath, outputSheetName, outPutData);
		
			
		con.close();		
	}
	
	
	@Test (enabled=false)
	public void compareDbtoExcelApproach2_Dynamic_sql() throws ClassNotFoundException, IOException, SQLException {
		
			
		String tableName = "Address"; //Read from testdata file	
		//String tableName = "Student"; //Read from testdata file			
		
		//Input file name and path
		String inputFilePath = "InputFile\\"+tableName+".xlsx";			
		String sheetName = "Sheet2"; //Read from testdata file
				
		//Create SQL statement		
		//String sql = "select * from student where idStudent >= 10"+id; //Get the query from testdata file
		//String sql = "select StudentDOB, StudentUserName from student where idStudent >= 10";
		
		//String sql = "select * from address where id >= 1";
		String sql = "select address2,city,StudentLastName from address where id>=1";
		//System.out.println(sql);
		
    	//Reading data from database 
		Connection con = DbConnection.getConnection();
		ResultSet rs = DbConnection.getDataFromDB(con, sql);
		//rs.next();
		//Get the Records count in DB
		rs.last(); 
		int dbRecordCount = rs.getRow(); 
		rs.beforeFirst();		
		System.out.println("Total Records in DB: "+dbRecordCount);
		
		//Read from input excel file
		String[][] inputData = ExcelUtilities.readXL(inputFilePath, sheetName);		
		System.out.println("Total Records in Excel: "+(inputData.length-1));
		
		
		//Compare input excel file with DB
		
		if(dbRecordCount==(inputData.length-1)) {
			
			Utilities.compareExcelAndDB_Dynamic(inputData,rs);
					
		} else {
			System.out.println("Records in input file and db is not same");
			System.exit(1);
		}
		
		
		//Compare linked columns between tables in DB
		
		/*if(tableName.equalsIgnoreCase("Address")) {
			
			String sql2 = "select address.StudentLastName, address.idStudent, student.StudentLastName, student.idStudent from address,student\r\n" + 
					"where address.StudentLastName = student.StudentLastName";
			rs = DbConnection.getDataFromDB(con, sql2);
			
			Utilities.compareLinkedColumnsInDB(rs);
			
		}	*/	
		con.close();		
	}
	
	
	@Test (enabled=false)
	public void compareDbtoExcelApproach2_Dynamic_Columns() throws ClassNotFoundException, IOException, SQLException {
		
		String tableName = "Address"; //Read from testdata file	
		//String tableName = "Student"; //Read from testdata file			
		
		int id = 1;
		
		String fieldName = "address.address2;address.city;address.idStudent;address.StudentLastName";
		
		String[] splitedFields = fieldName.split(";");		
		System.out.println(splitedFields.length);
		String selectList = "";
		for(int i = 0; i < splitedFields.length; i++) {
			if(!splitedFields[i].equals("address.idStudent"))
				selectList = selectList.concat(",").concat(splitedFields[i]);
		}
		selectList = selectList.substring(1);
		System.out.println(selectList);
		
		//Input file name and path
		String inputFilePath = "InputFile\\"+tableName+".xlsx";		
		
		String sheetName = "Sheet2"; //Read from testdata file
		
		//Create SQL statement		
		//String sql = "select * from student where idStudent >= 10"+id; //Get the query from testdata file
		//String sql = "select StudentDOB, StudentUserName from student where idStudent >= 10";
		
		//String sql = "select * from address where id >= 1";
		String sql = "select "+ selectList + " from address where id>="+id;
		//System.out.println(sql);
		
    	//Reading data from database 
		Connection con = DbConnection.getConnection();
		ResultSet rs = DbConnection.getDataFromDB(con, sql);
		//rs.next();
		//Get the Records count in DB
		rs.last(); 
		int dbRecordCount = rs.getRow(); 
		rs.beforeFirst();		
		System.out.println("Total Records in DB: "+dbRecordCount);
		
		//Read from input excel file
		String[][] inputData = ExcelUtilities.readXL(inputFilePath, sheetName);		
		System.out.println("Total Records in Excel: "+(inputData.length-1));
		
		
		//Compare input excel file with DB
		
		if(dbRecordCount==(inputData.length-1)) {
			
			Utilities.compareExcelAndDB_Dynamic(inputData,rs);
					
		} else {
			System.out.println("Records in input file and db is not same");
			System.exit(1);
		}
		
		
		//Compare linked columns between tables in DB
		
		if(tableName.equalsIgnoreCase("Address")) {
			for(int i = 0; i < splitedFields.length; i++) {
				if(splitedFields[i].equals("address.idStudent")) {
					String sql2 = "select address.StudentLastName, address.idStudent, student.StudentLastName, student.idStudent from address,student\r\n" + 
							"where address.StudentLastName = student.StudentLastName";
					rs = DbConnection.getDataFromDB(con, sql2);
					
					Utilities.compareLinkedColumnsInDB(rs);
					break;				
				}					
			}			
		}
		
		con.close();		
	}
	
	
	@Test (enabled=false)
	public void compareDbtoExcelApproach2() throws ClassNotFoundException, IOException, SQLException {
		
		int id = 10;	//Provide from testdata file		
		String tableName = "Address"; //Read from testdata file		
		//Input file name and path
		String inputFilePath = "InputFile\\"+tableName+".xlsx";		
		
		//Create SQL statement		
		//String sql = "select * from student where idStudent >= "+id; //Get the query from testdata file
		String sql = "select * from address where id >= 1";
		//System.out.println(sql);
		
    	//Reading data from database 
		Connection con = DbConnection.getConnection();
		ResultSet rs = DbConnection.getDataFromDB(con, sql);
		//rs.next();
		//Get the Records count in DB
		rs.last(); 
		int dbRecordCount = rs.getRow(); 
		rs.beforeFirst();		
		System.out.println("Total Records in DB: "+dbRecordCount);
		
		//Read from input excel file
		String[][] inputData = ExcelUtilities.readXL(inputFilePath, "Sheet1");		
		System.out.println("Total Records in Excel: "+(inputData.length-1));
		
		
		//Compare input excel file with DB
		if(dbRecordCount==(inputData.length-1)) {
			
			if(tableName.equalsIgnoreCase("Student")) {
				Utilities.compareExcelAndDB_Student(inputData,rs);
			} else if(tableName.equalsIgnoreCase("Address")) {
				Utilities.compareExcelAndDB_Address(inputData,rs);
			}
			
		} else {
			System.out.println("Records in input file and db is not same");
			System.exit(1);
		}
		
		con.close();		
	}

	
		
	@Test (enabled=false)
	public void exportFromDBAndcompareDbwithExcel_Approach1() throws ClassNotFoundException, IOException {
		
		int id = 1;	//Provide from testdata file		
		String tableName = "Address"; //Read from testdata file		
		//Input file name and path
		String inputFilePath = "InputFile\\"+tableName+".xlsx";		
		//Export file name and path
		String excelExportFilePath = "ExportFromDB\\"+tableName+"-export.xlsx";	
		//Create SQL statement		
		String sql = "select * from address where id >= "+id; //Get the query from testdata file
		//System.out.println(sql);
		
    	//Reading data from database and export to Excel     
		//https://www.codejava.net/coding/java-code-example-to-export-data-from-database-to-excel-file
		//Used 1st way, 2nd way is much better
		Utilities.exportDBToExcel(tableName, sql, excelExportFilePath); 
		
		//Read from excel file
		String[][] inputData = ExcelUtilities.readXL(inputFilePath, "Sheet1");		
		String[][] dbData = ExcelUtilities.readXL(excelExportFilePath, "Sheet1");
		
		//System.out.println(inputData.length);
		//System.out.println(dbData.length);
		
		//Compare two excel
		if(inputData.length == dbData.length) {
			if(tableName.equalsIgnoreCase("Student")) {
			Utilities.CompareTwoArrays_Student(inputData, dbData);
			} else if(tableName.equalsIgnoreCase("Address")) {
				Utilities.CompareTwoArrays_Address(inputData, dbData);
			}			
		} else {
			System.out.println("Length of input file and db file is not same");
			System.exit(1);
		}
		
		
		/*boolean flag = ExcelUtilities.compareTwoSheets(inputFilePath, excelExportFilePath);		
		System.out.println("Two Sheets are "+flag); */
		
	}
	
		
	@Test (enabled=false)
	public void exportFromDBAnyTable() throws ClassNotFoundException, IOException, SQLException {
				
		//Reading data from database and export to Excel     
		//https://www.codejava.net/coding/java-code-example-to-export-data-from-database-to-excel-file
		//This is 2nd way to export table data - Generic Way and works with any table
		Utilities.exportDBDataToExcel("address"); 
		Utilities.exportDBDataToExcel("student"); 		
	}
	
		
	@Test (enabled=false)
	public void readDataFromDatabaseAndExport() throws ClassNotFoundException {
		
		int id = 12;		
		
    	//Reading data from database        
		getStudentDataFromDB(id);        
		  
	}	
	public void getStudentDataFromDB(int id) throws ClassNotFoundException {
		
		Connection con = DbConnection.getConnection();
		ResultSet rs = null;
		
		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement("select * from student where idStudent >= ?",ResultSet.TYPE_SCROLL_INSENSITIVE,
			         ResultSet.CONCUR_UPDATABLE);
			stmt.setInt(1, id);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			rs = stmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			while(rs.next()) {
				
				Integer stuId = rs.getInt("idStudent");
			    String stuFName = rs.getString("StudentFirstName");
			    String stuLName = rs.getString("StudentLastName");
			    Integer stuAge = rs.getInt("StudentAge");
			    String stuDob = rs.getString("StudentDOB");
			    String stuUser = rs.getString("StudentUserName");
			    
			    System.out.println(stuId+", "+stuFName+", "+stuLName+", "+stuAge+", "+stuDob
			    		+", "+stuUser);
			    						  
			  }
		} catch (SQLException e) {
			e.printStackTrace();
		}
	   
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	@Test(enabled=false)
	public void readDataFromDatabase() throws ClassNotFoundException {
		
		int idStudent = 14;
		Connection con=null;
		
    	//Reading data from database
        con = DbConnection.getConnection();
        PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement("select * from student where idStudent = ?",ResultSet.TYPE_SCROLL_INSENSITIVE,
			         ResultSet.CONCUR_UPDATABLE);
			stmt.setFloat(1, idStudent);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//Create resultset object - execute query with database and returns the object of ResultSet 
		  ResultSet rs = null;
		try {
			rs = stmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		  
		  try {
			while(rs.next()) {
				
				Integer stuId = rs.getInt("idStudent");
			    String stuFName = rs.getString("StudentFirstName");
			    String stuLName = rs.getString("StudentLastName");
			    Integer stuAge = rs.getInt("StudentAge");
			    String stuDob = rs.getString("StudentDOB");
			    String stuUser = rs.getString("StudentUserName");
			    
			    System.out.println(idStudent+", "+stuFName+", "+stuLName+", "+stuAge+", "+stuDob
			    		+", "+stuUser);
			    						  
			  }
		} catch (SQLException e) {
			e.printStackTrace();
		}
	   
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	

}
