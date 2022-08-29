package MultipleScenario;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import org.testng.annotations.Test;

import helper.ExcelUtilities;

public class WorkingWithCSV {
	
		
	@Test(enabled=true)
	public void CreateCSVFromExcel() throws Exception {
		
		String fileName = "StudentDetails";
		//String sheetName = "Student";
		String sheetName = "Address";
		
		String fPath = "C:\\SeleniumTraining\\Project_Code\\Data\\" +fileName+".xlsx";		
	    
	    String outPath = "C:\\SeleniumTraining\\Project_Code\\Data\\" +sheetName+".csv";
		
	    File file = new File(outPath);
	    FileOutputStream fos = new FileOutputStream(file, true);
	    PrintWriter pw = new PrintWriter(fos);	    
	    
		String[][] testData = ExcelUtilities.readXL(fPath, sheetName);  		
				
		for(int i = 0; i < testData.length ; i++ ) {
			
			String line = "";
			
			for (int j = 0; j < testData[0].length; j++) {
				
				line = line.concat(testData[i][j]).concat(",");
				
			}
			
			line = line.substring(0,line.length()-1);
			//System.out.println(line);
			pw.println(line);
		}
		
		pw.close();
		
		System.out.println("CSV file is written");
	    
	}
	
	@Test(enabled=false)
	public void ReadCSV() throws Exception {
		
		String fPath = "C:\\SeleniumTraining\\Project_Code\\Data\\StudentDetails.csv";
		System.out.println("Started reading");
		String[][] testData = ExcelUtilities.readXL(fPath, "Sheet1");    
	    
	   System.out.println(testData[0][1]);
		
	    
	}
	

}
