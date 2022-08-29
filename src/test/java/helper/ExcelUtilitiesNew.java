package helper;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.record.cf.BorderFormatting;
import org.apache.poi.sl.usermodel.TextParagraph.TextAlign;
import org.apache.poi.sl.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtilitiesNew {

	public static String[][] readXL(String fPath, String fSheetName) throws IOException{

		//Purpose: 		//IP:		//OP:		//Author:
		//initialize variables to create instances for an excel file
		XSSFWorkbook wb;
		XSSFSheet ws;
		int vXL_rowNum;
		int vXL_colNum;
		String[][] vXLData;
		XSSFRow row;
		XSSFCell cell;
		
		//System.out.println(fPath);
		
	//Providing file path and Create input stream with excel sheet to read data	
		File f = new File(fPath);		
		FileInputStream fis = new FileInputStream(f);		
		wb = new XSSFWorkbook(fis);
		ws = wb.getSheet(fSheetName);	
		
	//Get the row and column counts of a work sheet
		vXL_rowNum = ws.getLastRowNum()+1;
		//System.out.println("rowCnt in excel utilities "+vXL_rowNum);
		vXL_colNum = ws.getRow(0).getLastCellNum();
		//System.out.println("colCnt in excel utilities "+vXL_colNum);
		
		
	// Create 2D array of type string with row and col counts -this will be used to store excel data in it.		
		vXLData = new String[vXL_rowNum][vXL_colNum];
	
		//System.out.println("Started reading data");
	//Use for loop to to read the excel data and store in vXLData array.
		for (int i=0; i< vXL_rowNum; i++)
		{			
				row = ws.getRow(i);
				for(int j = 0;j < vXL_colNum;j++)
				{
					cell = row.getCell(j);
					
					String vCell="-";
					
					if (cell!=null)
					{
						vCell = cellToString(cell);
						//vCell=cell.getStringCellValue().toString();
						
					}
					//else if()
					vXLData[i][j] = vCell;
					//System.out.println(vCell);
					//System.out.println(fSheetName +": "+vCell);
				}
		}//end of for loop	
		//	System.out.println("Completed reading data");
		
		wb.close();
		return vXLData; 
		
		
}//end readXL

	
	public static String cellToString(XSSFCell cell) { 
		
		// This function will convert an object of type excel cell to a string value
		
		CellType type = cell.getCellTypeEnum();   
	//type.STRING
		
		Object result;
		
		switch (type) 
		{			
		case NUMERIC:
			result = (int)cell.getNumericCellValue();           
			break;                       
		case STRING: //1 
			result = cell.getStringCellValue();                                
			break;    
		case FORMULA: //2 
			result = cell.getStringCellValue();
			//throw new RuntimeException("We can't evaluate formulas in Java");  	
		case BLANK: //3                                		
			result = "";                                		
			break;                            	
		case BOOLEAN: //4     		
			result = cell.getBooleanCellValue();       		
			break;                            	
		case ERROR: //5       		
			throw new RuntimeException ("This cell has an error");    	
		default:                  		
			throw new RuntimeException("We don't support this cell type: " + type); 
		}                        
		
		return result.toString();      
		
}//end of cellToString
	
	
//Old way of implementing.. getCellType() is deprecated
/*	public static String cellToString(XSSFCell cell) { 
		
		// This function will convert an object of type excel cell to a string value
		
		int type = cell.getCellType();   
		
		Object result;
		
		switch (type) 
		{			
		case XSSFCell.CELL_TYPE_NUMERIC: //0 
			result = (int)cell.getNumericCellValue();           
			break;                       
		case XSSFCell.CELL_TYPE_STRING: //1 
			result = cell.getStringCellValue();                                
			break;    
		case XSSFCell.CELL_TYPE_FORMULA: //2 
			System.out.println(cell);
			throw new RuntimeException("We can't evaluate formulas in Java");  	
		case XSSFCell.CELL_TYPE_BLANK: //3                                		
			result = "";                                		
			break;                            	
		case XSSFCell.CELL_TYPE_BOOLEAN: //4     		
			result = cell.getBooleanCellValue();       		
			break;                            	
		case XSSFCell.CELL_TYPE_ERROR: //5       		
			throw new RuntimeException ("This cell has an error");    	
		default:                  		
			throw new RuntimeException("We don't support this cell type: " + type); 
		}                        
		
		return result.toString();      
		
}//end of cellToString
*/

	
	public static void writeXL(String fPath, String fSheet, String[][] xData) throws Exception
	{
		
    	File outFile = new File(fPath);
        XSSFWorkbook wb = new XSSFWorkbook();      
        XSSFSheet osheet = wb.createSheet(fSheet);
       // System.out.println(xData.length);
        System.out.println("Writing back to xl");
        
        int xR_TS = xData.length;
        int xC_TS = xData[0].length;
    	for (int i = 0; i < xR_TS; i++) 
    	{
	        XSSFRow row = osheet.createRow(i);
	        for (int j = 0; j < xC_TS; j++) 
	        {
	        	XSSFCell cell = row.createCell(j);
	        	//cell.setCellType(XSSFCell.CELL_TYPE_STRING);
	        	cell.setCellValue(xData[i][j]);
	        }
	        
		FileOutputStream fOut = new FileOutputStream(outFile);
	        
		wb.write(fOut);
	        
		fOut.flush();
	        
		fOut.close();
    	
	}
    	
		wb.close();
	
	}//end of writeXL
	
	//Method to write result into multiple sheets in the same excel file.
	public static void writeXLMultipleSheet(String fPath, String fSheet,int sheetIndex, String[][] xData) throws Exception
	{
		System.out.println("Writing back to xl");
		XSSFWorkbook wb,newWb;
		XSSFSheet osheet=null,newsheet=null;
    	File outFile = new File(fPath);
    	
    	//if File exist
    	if ((outFile.isFile() == true)&&(outFile.exists()==true))
    	{
    		final InputStream is = new FileInputStream(outFile);
    		try {
    			wb = new XSSFWorkbook(is);
    			
    			//Check if number of sheets equal to sheetindex value then create new sheet    			
    			if((wb.getNumberOfSheets())== sheetIndex)    			
    				osheet = wb.createSheet();
    			
    				//if sheet exist in existing workbook at given index then remove and add
    				if((wb.getSheetName(sheetIndex)).equalsIgnoreCase(fSheet))    				
    				{
    					wb.removeSheetAt(sheetIndex);    					
    					osheet=wb.createSheet(fSheet);
    					wb.setSheetOrder(fSheet, sheetIndex);    				
    				
    					int xR_TS = xData.length;
    					int xC_TS = xData[0].length;
    					for (int myrow = 0; myrow < xR_TS; myrow++)
    					{
    				       XSSFRow row = osheet.createRow(myrow);
    				       for (int mycol = 0; mycol < xC_TS; mycol++)
    				       {
    				       	XSSFCell cell = row.createCell(mycol);    				       	
    				       	cell.setCellValue(xData[myrow][mycol]);
    				       }
    				       FileOutputStream fOut = new FileOutputStream(outFile);
    				       wb.write(fOut);
    				       fOut.flush();
    				       fOut.close();
    					} 
    				
    				} //end of if sheet exist    			
    			
    			//if sheet does not exist in existing workbook then add sheet at given sheetIndex
    			else 
    			{
    				osheet=wb.createSheet(fSheet);
    				wb.setSheetOrder(fSheet, sheetIndex);    				
    				
    				int xR_TS = xData.length;
    			    int xC_TS = xData[0].length;
    			    
    			   	for (int myrow = 0; myrow < xR_TS; myrow++)
    			   	{
    				       XSSFRow row = osheet.createRow(myrow);
    				       for (int mycol = 0; mycol < xC_TS; mycol++)
    				       {
    				       	XSSFCell cell = row.createCell(mycol);    				       	
    				       	cell.setCellValue(xData[myrow][mycol]);
    				       FileOutputStream fOut = new FileOutputStream(outFile);
    				       wb.write(fOut);
    				       fOut.flush();
    				       fOut.close();
    			   	       }
    			   }
    		  	} //end of else (if sheet does not exist)
    		   } //end of try
    			finally 
    			{ 
    				is.close();
    			}

		}//end of first if file exists
    	
    	else if (outFile.isFile() == false)
    		{
    			//Create new file i.e wb and sheet if workbook is not available
    			
    		newWb = new XSSFWorkbook();
    		newsheet = newWb.createSheet(fSheet);
    		int xR_TS = xData.length;
    		int xC_TS = xData[0].length;
    		
    		   	for (int myrow = 0; myrow < xR_TS; myrow++) 
    		   	{
    			   XSSFRow row = newsheet.createRow(myrow);
    			   for (int mycol = 0; mycol < xC_TS; mycol++) 
    			   {
    			       	XSSFCell cell = row.createCell(mycol);
    			       //	cell.setCellType(XSSFCell.CELL_TYPE_STRING);
    			       	cell.setCellValue(xData[myrow][mycol]);
    			    }
    			       FileOutputStream fOut = new FileOutputStream(outFile);
    			       newWb.write(fOut);
    			       fOut.flush();
    			       fOut.close();
    		   	}
    		}
    		 
    	//wb.close();	
	} //end of writeXL
          
  
	//Method to write result into multiple sheets in the same excel file.
	public static void writeXLSheetsFormatted(String fPath, String fSheet,int sheetIndex, String[][] xData) throws Exception
	{
		System.out.println("Writing back to xl");
		XSSFWorkbook wb,newWb;
		XSSFSheet osheet=null,newsheet=null;
    	File outFile = new File(fPath);
    	
    	//if File exist
    	if ((outFile.isFile() == true)&&(outFile.exists()==true))
    	{
    		final InputStream is = new FileInputStream(outFile);
    		try {
    			wb = new XSSFWorkbook(is);
    			//Check if number of sheets equal to sheetindex value then create new sheet
    			
    			if((wb.getNumberOfSheets())== sheetIndex)	
    				osheet = wb.createSheet();
    			
    				//if sheet exist at given sheetIndex in existing workbook then remove it and add new
    				if((wb.getSheetName(sheetIndex)).equalsIgnoreCase(fSheet))
    				{
    					wb.removeSheetAt(sheetIndex);
    					osheet=wb.createSheet(fSheet);
    					wb.setSheetOrder(fSheet, sheetIndex);
    					
    					XSSFCellStyle  headerCellStyle = osheet.getWorkbook().createCellStyle();
    		    		XSSFCellStyle  bodyCellStyle = osheet.getWorkbook().createCellStyle();
    		    		
    					XSSFFont headerfont = osheet.getWorkbook().createFont();
    					headerfont.setBold(true); //set the cell value to bold //This is working
    					headerfont.setFontHeightInPoints((short) 14); //set the cell value to increased size
    					//headerfont.setColor((short) 2); //This works-sets the font to Red
    					headerfont.setColor(new XSSFColor(Color.blue)); //This works-sets the font to Red
    					
    					XSSFFont bodyfont = osheet.getWorkbook().createFont();
    					bodyfont.setFontName("Bell MT"); //Courier New //This is working
    					
    					//Header and Body Font setup
    					headerCellStyle.setFont(headerfont); //for header
    					bodyCellStyle.setFont(bodyfont); //for body
    					
    					//header Color
    					headerCellStyle.setFillForegroundColor(IndexedColors.ORANGE.getIndex());	//This worked		
    					headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND); //This worked with foreground
    					//headerCellStyle.setFillBackgroundColor(IndexedColors.LIGHT_ORANGE.getIndex()); //This worked
    					//headerCellStyle.setFillPattern(FillPatternType.LESS_DOTS); //This worked with backgound			
    					
    					//header Border
    					headerCellStyle.setBorderBottom(BorderStyle.DOUBLE);
    					headerCellStyle.setBottomBorderColor(IndexedColors.BLUE.getIndex());
    				   // cellStyle.setBorderTop(BorderStyle.THICK); //This worked
    					headerCellStyle.setBorderRight(BorderStyle.THIN);
    					
    					//header Alignment
    					headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
    					
    					
    					//Body Color
    					bodyCellStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
    					bodyCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND); //This worked
    					//bodyCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
    					
    					//Body Border
    					bodyCellStyle.setBorderBottom(BorderStyle.THIN);
    					bodyCellStyle.setBorderTop(BorderStyle.THIN);
    					bodyCellStyle.setBorderRight(BorderStyle.THIN);
    					bodyCellStyle.setBorderLeft(BorderStyle.THIN);
    					//header Alignment
    					bodyCellStyle.setAlignment(HorizontalAlignment.LEFT); //This worked
    					//bodyCellStyle.setVerticalAlignment(VerticalAlignment.TOP);//Not Working
    					//bodyCellStyle.setVerticalAlignment(TextAllignmentType.TOP); 
    					bodyCellStyle.setWrapText(true); //Working
    					
    					int xR_TS = xData.length;
    					int xC_TS = xData[0].length;
    					for (int myrow = 0; myrow < xR_TS; myrow++)
    					{
    				       XSSFRow row = osheet.createRow(myrow);
    				       for (int mycol = 0; mycol < xC_TS; mycol++)
    				       {
    				       	XSSFCell cell = row.createCell(mycol);
    				       	//cell.setCellType(XSSFCell.CELL_TYPE_STRING);
    				       	cell.setCellValue(xData[myrow][mycol]);
    				       	
    				       	osheet.setColumnWidth(mycol, 4000); //working --This sets column width
    				       	
    				       	if(myrow==0)
    					       	cell.setCellStyle(headerCellStyle);
    					    else
    					       	cell.setCellStyle(bodyCellStyle);
    				       }
    				       FileOutputStream fOut = new FileOutputStream(outFile);
    				       wb.write(fOut);
    				       fOut.flush();
    				       fOut.close();
    					}//outerfor 
    				
    				}//end of if sheet exist
    			
    			
    			//if sheet doesnot exist in existing workbook
    			else 
    			{
    				osheet=wb.createSheet(fSheet);
    				wb.setSheetOrder(fSheet, sheetIndex);	
    				
    				XSSFCellStyle  headerCellStyle = osheet.getWorkbook().createCellStyle();
    	    		XSSFCellStyle  bodyCellStyle = osheet.getWorkbook().createCellStyle();
    	    		
    				XSSFFont headerfont = osheet.getWorkbook().createFont();
    				headerfont.setBold(true); //set the cell value to bold //This is working
    				headerfont.setFontHeightInPoints((short) 14); //set the cell value to increased size
    				//headerfont.setColor((short) 2); //This works-sets the font to Red
    				headerfont.setColor(new XSSFColor(Color.blue)); //This works-sets the font to Red
    				
    				XSSFFont bodyfont = osheet.getWorkbook().createFont();
    				bodyfont.setFontName("Bell MT"); //Courier New //This is working
    				
    				//Header and Body Font setup
    				headerCellStyle.setFont(headerfont); //for header
    				bodyCellStyle.setFont(bodyfont); //for body
    				
    				//header Color
    				headerCellStyle.setFillForegroundColor(IndexedColors.ORANGE.getIndex());	//This worked		
    				headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND); //This worked with foreground
    				//headerCellStyle.setFillBackgroundColor(IndexedColors.LIGHT_ORANGE.getIndex()); //This worked
    				//headerCellStyle.setFillPattern(FillPatternType.LESS_DOTS); //This worked with backgound			
    				
    				//header Border
    				headerCellStyle.setBorderBottom(BorderStyle.DOUBLE);
    				headerCellStyle.setBottomBorderColor(IndexedColors.BLUE.getIndex());
    			   // cellStyle.setBorderTop(BorderStyle.THICK); //This worked
    				headerCellStyle.setBorderRight(BorderStyle.THIN);
    				
    				//header Alignment
    				headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
    				
    				
    				//Body Color
    				bodyCellStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
    				bodyCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND); //This worked
    				//bodyCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
    				
    				//Body Border
    				bodyCellStyle.setBorderBottom(BorderStyle.THIN);
    				bodyCellStyle.setBorderTop(BorderStyle.THIN);
    				bodyCellStyle.setBorderRight(BorderStyle.THIN);
    				bodyCellStyle.setBorderLeft(BorderStyle.THIN);
    				//header Alignment
    				bodyCellStyle.setAlignment(HorizontalAlignment.LEFT); //This worked
    				//bodyCellStyle.setVerticalAlignment(VerticalAlignment.TOP);//Not Working
    				//bodyCellStyle.setVerticalAlignment(TextAllignmentType.TOP); 
    				bodyCellStyle.setWrapText(true); //Working
    				
    				int xR_TS = xData.length;
    			       int xC_TS = xData[0].length;
    			   	for (int myrow = 0; myrow < xR_TS; myrow++)
    			   	{
    				       XSSFRow row = osheet.createRow(myrow);
    				       for (int mycol = 0; mycol < xC_TS; mycol++)
    				       {
    				       	XSSFCell cell = row.createCell(mycol);
    				       	//cell.setCellType(XSSFCell.CELL_TYPE_STRING);
    				       	cell.setCellValue(xData[myrow][mycol]);
    				       	
    				       	osheet.setColumnWidth(mycol, 4000); //working --This sets column width
    				       	
    				       	if(myrow==0)
    					       	cell.setCellStyle(headerCellStyle);
    					    else
    					       	cell.setCellStyle(bodyCellStyle);
    				       	
    				       FileOutputStream fOut = new FileOutputStream(outFile);
    				       wb.write(fOut);
    				       fOut.flush();
    				       fOut.close();
    			   	       }
    			   }
    		  	} //end of else (if sheet doesnot exist)
    		   } //end of try
    			finally 
    			{ 
    				is.close();
    			}

		}//end of first if file exists
    	
    	else if (outFile.isFile() == false)
    		{
    			//Create new file i.e wb and sheet if workbook is not available
    			
    		newWb = new XSSFWorkbook();
    		newsheet = newWb.createSheet(fSheet);
    		int xR_TS = xData.length;
    		int xC_TS = xData[0].length;
    		
    		XSSFCellStyle  headerCellStyle = newsheet.getWorkbook().createCellStyle();
    		XSSFCellStyle  bodyCellStyle = newsheet.getWorkbook().createCellStyle();
    		
			XSSFFont headerfont = newsheet.getWorkbook().createFont();
			headerfont.setBold(true); //set the cell value to bold //This is working
			headerfont.setFontHeightInPoints((short) 14); //set the cell value to increased size
			//headerfont.setColor((short) 2); //This works-sets the font to Red
			headerfont.setColor(new XSSFColor(Color.blue)); //This works-sets the font to Red
			
			XSSFFont bodyfont = newsheet.getWorkbook().createFont();
			bodyfont.setFontName("Bell MT"); //Courier New //This is working
			
			//Header and Body Font setup
			headerCellStyle.setFont(headerfont); //for header
			bodyCellStyle.setFont(bodyfont); //for body
			
			//header Color
			headerCellStyle.setFillForegroundColor(IndexedColors.ORANGE.getIndex());	//This worked		
			headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND); //This worked with foreground
			//headerCellStyle.setFillBackgroundColor(IndexedColors.LIGHT_ORANGE.getIndex()); //This worked
			//headerCellStyle.setFillPattern(FillPatternType.LESS_DOTS); //This worked with backgound			
			
			//header Border
			headerCellStyle.setBorderBottom(BorderStyle.DOUBLE);
			headerCellStyle.setBottomBorderColor(IndexedColors.BLUE.getIndex());
		   // cellStyle.setBorderTop(BorderStyle.THICK); //This worked
			headerCellStyle.setBorderRight(BorderStyle.THIN);
			
			//header Alignment
			headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
			
			
			//Body Color
			bodyCellStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
			bodyCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND); //This worked
			//bodyCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
			
			//Body Border
			bodyCellStyle.setBorderBottom(BorderStyle.THIN);
			bodyCellStyle.setBorderTop(BorderStyle.THIN);
			bodyCellStyle.setBorderRight(BorderStyle.THIN);
			bodyCellStyle.setBorderLeft(BorderStyle.THIN);
			//header Alignment
			bodyCellStyle.setAlignment(HorizontalAlignment.LEFT); //This worked
			//bodyCellStyle.setVerticalAlignment(VerticalAlignment.TOP);//Not Working
			//bodyCellStyle.setVerticalAlignment(TextAllignmentType.TOP); 
			bodyCellStyle.setWrapText(true); //Working
					
    		   	for (int myrow = 0; myrow < xR_TS; myrow++) 
    		   	{
    			   XSSFRow row = newsheet.createRow(myrow);
    			   
    			   //row.setHeight((short) 800);
    			   
    			   for (int mycol = 0; mycol < xC_TS; mycol++) 
    			   {
    			       	XSSFCell cell = row.createCell(mycol);
    			       //	cell.setCellType(XSSFCell.CELL_TYPE_STRING);
    			       	cell.setCellValue(xData[myrow][mycol]);
    			       	
    			      newsheet.setColumnWidth(mycol, 4000); //working --This sets column width
    			          			       	
    			       	if(myrow==0) {
					       	cell.setCellStyle(headerCellStyle);
    			       	}
					    else {
					       	cell.setCellStyle(bodyCellStyle);
					    }
    			       	
    			       /*	if(xData[myrow][mycol].equalsIgnoreCase("Pass"))
    			       	{
    			       		cell.setCellStyle(PassStyle);
    			       	}
    			       	else if (xData[myrow][mycol].equalsIgnoreCase("Fail"))
    			       	{
    			       		cell.setCellStyle(FailStyle);
    			       	}*/
    			  }
    			       FileOutputStream fOut = new FileOutputStream(outFile);
    			       newWb.write(fOut);
    			       fOut.flush();
    			       fOut.close();
    		   	}
    		}
    		 
    	//wb.close();	
	} //end of writeXL
		
	
/*	
	//Method to write result into multiple sheets in the same excel file
	public static void writeXLSheets(String sPath, String iSheet,int sheetIndex, String[][] xData)
			throws Exception
{
		System.out.println("Writing back to xl");
		XSSFWorkbook  wb,newWB;
		XSSFSheet osheet;
		
		File outFile = new File(sPath);
		if ((outFile.isFile() == true)&&(outFile.exists()==true))
		{
	final InputStream is = new FileInputStream(outFile);
	try {
		wb = new XSSFWorkbook(is);
		if((wb.getNumberOfSheets())== sheetIndex)
		wb.createSheet();
		if((wb.getSheetName(sheetIndex)).equalsIgnoreCase(iSheet))
		{
			
			CellStyle style = wb.createCellStyle();
			CellStyle style1 = wb.createCellStyle();
			XSSFFont font =wb.createFont();
			XSSFFont font1 =wb.createFont();
			
			wb.removeSheetAt(sheetIndex);
			osheet=wb.createSheet(iSheet);
			wb.setSheetOrder(iSheet, sheetIndex);
			
			font.setBold(true);
			font1.setFontName("Courier New");
			style.setFillForegroundColor(IndexedColors.LIGHT_ORANGE .getIndex());
			//style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			style.setFont(font);
			//style.setBorderBottom(CellStyle.BORDER_THIN);
			//style.setBorderTop(CellStyle.BORDER_THIN);
			//style.setBorderRight(CellStyle.BORDER_THIN);
			//style.setBorderLeft(CellStyle.BORDER_THIN);
			
			style1.setFillForegroundColor(IndexedColors.LIGHT_YELLOW .getIndex());
			//style1.setFillPattern(CellStyle.SOLID_FOREGROUND);
			style1.setFont(font1);
			//style1.setBorderBottom(CellStyle.BORDER_THIN);
			//style1.setBorderTop(CellStyle.BORDER_THIN);
			//style1.setBorderRight(CellStyle.BORDER_THIN);
			//style1.setBorderLeft(CellStyle.BORDER_THIN);
			
			int xR_TS = xData.length;
		       int xC_TS = xData[0].length;
		   	for (int myrow = 0; myrow < xR_TS; myrow++)
		   	{
			       XSSFRow row = osheet.createRow(myrow);
			       for (int mycol = 0; mycol < xC_TS; mycol++)
			       {
			       	XSSFCell cell = row.createCell(mycol);
			       	//cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			       	cell.setCellValue(xData[myrow][mycol]);
			       	if(myrow==0)
			       	cell.setCellStyle(style);
			       	else
			       		cell.setCellStyle(style1);
			       }
			       FileOutputStream fOut = new FileOutputStream(outFile);
			       wb.write(fOut);
			       fOut.flush();
			       fOut.close();
		   	}//outerfor 
			
		}//end if sheet exists
		else 
		{
			osheet=wb.createSheet(iSheet);
			wb.setSheetOrder(iSheet, sheetIndex);
			
			
			CellStyle style = wb.createCellStyle();
			CellStyle style1 = wb.createCellStyle();
			XSSFFont font =wb.createFont();
			XSSFFont font1 =wb.createFont();
			font.setBold(true);
			font1.setFontName("Courier New");
			style.setFillForegroundColor(IndexedColors.LIGHT_ORANGE .getIndex());
			//style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			style.setFont(font);
			//style.setBorderBottom(CellStyle.BORDER_THIN);
			//style.setBorderTop(CellStyle.BORDER_THIN);
			//style.setBorderRight(CellStyle.BORDER_THIN);
			//style.setBorderLeft(CellStyle.BORDER_THIN);
			
			style1.setFillForegroundColor(IndexedColors.LIGHT_YELLOW .getIndex());
			//style1.setFillPattern(CellStyle.SOLID_FOREGROUND);
			style1.setFont(font1);
			//style1.setBorderBottom(CellStyle.BORDER_THIN);
			//style1.setBorderTop(CellStyle.BORDER_THIN);
			//style1.setBorderRight(CellStyle.BORDER_THIN);
			//style1.setBorderLeft(CellStyle.BORDER_THIN);
			
			
			
			int xR_TS = xData.length;
		       int xC_TS = xData[0].length;
		   	for (int myrow = 0; myrow < xR_TS; myrow++)
		   	{
			       XSSFRow row = osheet.createRow(myrow);
			       for (int mycol = 0; mycol < xC_TS; mycol++)
			       {
			       	XSSFCell cell = row.createCell(mycol);
			       	//cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			       	cell.setCellValue(xData[myrow][mycol]);
			       	if(myrow==0)
				       	cell.setCellStyle(style);
				       	else
				       	cell.setCellStyle(style1);
			       }
			       FileOutputStream fOut = new FileOutputStream(outFile);
			       wb.write(fOut);
			       fOut.flush();
			       fOut.close();
		   	}//outerfor 
		}
	  	} 
	finally 
		{ is.close();}

		}//end of first if file exists
		else if (outFile.isFile() == false)
		{
			//System.out.println("need to create new file and wb and sheet");
			
		       newWB = new XSSFWorkbook();
		       XSSFSheet newsheet = newWB.createSheet(iSheet);
		       
		       CellStyle style = newWB.createCellStyle();
				CellStyle style1 = newWB.createCellStyle();
				XSSFFont font =newWB.createFont();
				XSSFFont font1 =newWB.createFont();
				font.setBold(true);
				font1.setFontName("Courier New");
				style.setFillForegroundColor(IndexedColors.ORANGE.getIndex()); //LIGHT_ORANGE
				//style.setFillPattern(CellStyle.SOLID_FOREGROUND);
				style.setFont(font);
				//style.setBorderBottom(CellStyle.BORDER_THIN);
				//style.setBorderTop(CellStyle.BORDER_THIN);
				//style.setBorderRight(CellStyle.BORDER_THIN);
				//style.setBorderLeft(CellStyle.BORDER_THIN);
				
				style1.setFillForegroundColor(IndexedColors.LIGHT_YELLOW .getIndex());
				//style1.setFillPattern(CellStyle.SOLID_FOREGROUND);
				style1.setFont(font1);
				//style1.setBorderBottom(CellStyle.BORDER_THIN);
				//style1.setBorderTop(CellStyle.BORDER_THIN);
				//style1.setBorderRight(CellStyle.BORDER_THIN);
				//style1.setBorderLeft(CellStyle.BORDER_THIN);
		       int xR_TS = xData.length;
		       int xC_TS = xData[0].length;
		   	    for (int myrow = 0; myrow < xR_TS; myrow++) {
			       XSSFRow row = newsheet.createRow(myrow);
			       for (int mycol = 0; mycol < xC_TS; mycol++) {
			       	XSSFCell cell = row.createCell(mycol);
			       //	cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			       	cell.setCellValue(xData[myrow][mycol]);
			       	if(myrow==0)
				       	cell.setCellStyle(style);
				       	else
				       	cell.setCellStyle(style1);
			       }
			       FileOutputStream fOut = new FileOutputStream(outFile);
			       newWB.write(fOut);
			       fOut.flush();
			       fOut.close();
		   	}
		}
		 
	
}
*/
	
	/*
	public static void setCellData(String sPath, String result,int rowNum, int colNum) throws Exception
	{
		
		
		
		
		row = ws.getRow(rowNum);
		cell=row.getCell(colNum);
		
		if(cell==null)
		{
			//cell=row.createCell(colNum);
			cell=row.createCell(row.getLastCellNum());
			cell.setCellValue(result);
		}
		else
		{
			cell.setCellValue(result);
		}
		
		//create FileOutputStream to write data back to excel
		
		//Select directory where excel input file is saved or will be saved
		
		//File fo = new File("D:\\Selenium\\TestData\\TestData_Result.xlsx"); --> File is passing from main program
		FileOutputStream fos = new FileOutputStream(fo);		
		wb.write(fos);
				
		fos.flush();
		//close the stream
		fos.close();
		
	}

	*/
	

}//end of class