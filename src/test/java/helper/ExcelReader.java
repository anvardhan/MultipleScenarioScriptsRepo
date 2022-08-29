package helper;

import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.*;

import java.io.*;

public class ExcelReader {
	
	public  String path;
	public  FileInputStream fis = null;
	public  FileOutputStream fileOut =null;
	private XSSFWorkbook workbook = null;
	private XSSFSheet sheet = null;
	private XSSFRow row   =null;
	private XSSFCell cell = null;
	
	public ExcelReader(String path) {
		
		this.path=path;
		try {
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);
			sheet = workbook.getSheetAt(0);
			fis.close();
		} catch (Exception e) {			
			e.printStackTrace();
		} 
	}
	
	// returns the row count in a sheet
	public int getRowCount(String sheetName){
		int index = workbook.getSheetIndex(sheetName);
		if(index==-1)
			return 0;
		else{
		sheet = workbook.getSheetAt(index);
		int number=sheet.getLastRowNum()+1;
		return number;
		}
	}
	
	// returns number of columns in a sheet	
	public int getColumnCount(String sheetName){
			// check if sheet exists
			if(!isSheetExist(sheetName))
			 return -1;
			
			sheet = workbook.getSheet(sheetName);
			row = sheet.getRow(0);
			
			if(row==null)
				return -1;
			
			return row.getLastCellNum();
		}
		
	// returns the data from a cell
	public String getCellData(String sheetName,String colName,int rowNum){
		try{
			if(rowNum <=0)
				return "";
		
		int index = workbook.getSheetIndex(sheetName);
		int col_Num=-1;
		if(index==-1)
			return "";
		
		sheet = workbook.getSheetAt(index);
		
		/*
		 *Get the header row from excel which has column names.
		 *Iterate each column of header from header row and get its value and 
		 *compare it with column name which will be provided by user.
		 *
		 */
		row=sheet.getRow(0);
		
		for(int i=0;i<row.getLastCellNum();i++){
			//System.out.println(row.getCell(i).getStringCellValue().trim());
			if(row.getCell(i).getStringCellValue().trim().equals(colName.trim()))
				col_Num=i;
		}
		if(col_Num==-1)
			return "";
		
		/*
		 *Get the cell data of the specified row and specified column number (column number 
		 *of the corresponding column name will be retrieved from above for loop.)
		 *		 
		 */		
		
		//Get the XSSFRow object. since index of row starts from 0 so to get 2nd row object, 
		//index should be 1 (therefore rowNum-1 where rowNum will come from main program)
		row = sheet.getRow(rowNum-1); 
		if(row==null)
			return "";
		
		//Get the XSSFCell Object. Index of column number starts from 0. col_Num is calculated in above for loop
		cell = row.getCell(col_Num);		
		if(cell==null)
			return "";
		
		//Get cell value based on type of cell using CellType object
		CellType type = cell.getCellTypeEnum();
		
		Object result;
		
		switch (type) 
		{			
		case NUMERIC:
			result = (int)cell.getNumericCellValue();	
						
		 /*  if (DateUtil.isCellDateFormatted(cell)) {
				//Converting string into date
				SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	            //SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
	            result = dateFormat.format(cell.getDateCellValue());
	            //System.out.print(dateFormat.format(cell.getDateCellValue()) + "\t\t");
			} else {
	        	result = (int)cell.getNumericCellValue();  
	        	//System.out.print(cell.getNumericCellValue() + "\t\t");
	        }*/
			break;                       
		case STRING: //1 
			result = cell.getStringCellValue();                                
			break;    
		case FORMULA: //2 
			result = cell.getStringCellValue();
			//throw new RuntimeException("We can't evaluate formulas in Java");  	
			break;
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
		
		}
		catch(Exception e){
			
			e.printStackTrace();
			return "row "+rowNum+" or column "+colName +" does not exist in xls";
		}
	}
	
	// returns the data from a cell
	public String getCellData(String sheetName,int colNum,int rowNum){
		try{
			if(rowNum <=0)
				return "";
		
		int index = workbook.getSheetIndex(sheetName);

		if(index==-1)
			return "";
		
		sheet = workbook.getSheetAt(index);
		//rows are 0 indexed. So to get data of 2nd row, indexed should be 1
		row = sheet.getRow(rowNum-1);
		if(row==null)
			return "";
		
		//column number is 0 indexed so 3 means 4th column
		cell = row.getCell(colNum);
		if(cell==null)
			return "";
		
		//Get cell value based on type of cell using CellType object 
		CellType type = cell.getCellTypeEnum();
				
		Object result;
				
		switch (type) 
		{			
		case NUMERIC:
			result = (int)cell.getNumericCellValue();	
								
			/*  if (DateUtil.isCellDateFormatted(cell)) {
				//Converting string into date
				SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			  //SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
			    result = dateFormat.format(cell.getDateCellValue());
			    //System.out.print(dateFormat.format(cell.getDateCellValue()) + "\t\t");
			     } else {
			        result = (int)cell.getNumericCellValue();  
			        //System.out.print(cell.getNumericCellValue() + "\t\t");
			       }*/
			break;                       
		case STRING: //1 
			result = cell.getStringCellValue();                                
			break;    
		case FORMULA: //2 
			result = cell.getStringCellValue();
			//throw new RuntimeException("We can't evaluate formulas in Java");  	
			break;
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
		}
		catch(Exception e){
			
			e.printStackTrace();
			return "row "+rowNum+" or column "+colNum +" does not exist  in xls";
		}
	}
	
	// returns true if data is set successfully else false
	public boolean setCellData(String sheetName,String colName,int rowNum, String data){
		try{
		fis = new FileInputStream(path); 
		workbook = new XSSFWorkbook(fis);

		if(rowNum<=0)
			return false;
		
		int index = workbook.getSheetIndex(sheetName);
		int colNum=-1;
		if(index==-1)
			return false;
		
		sheet = workbook.getSheetAt(index);		

		row=sheet.getRow(0);
		for(int i=0;i<row.getLastCellNum();i++){
			//System.out.println(row.getCell(i).getStringCellValue().trim());
			if(row.getCell(i).getStringCellValue().trim().equals(colName))
				colNum=i;
		}
		if(colNum==-1)
			return false;

		sheet.autoSizeColumn(colNum); 
		
		row = sheet.getRow(rowNum-1);
		if (row == null)
			row = sheet.createRow(rowNum-1);
		
		cell = row.getCell(colNum);	
		if (cell == null)
	        cell = row.createCell(colNum);

	    cell.setCellValue(data);

	    fileOut = new FileOutputStream(path);

		workbook.write(fileOut);
		
		fileOut.flush();

	    fileOut.close();	

		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	// returns true if data is set successfully else false
	public boolean setCellData(String sheetName,String colName,int rowNum, String data,String url){
		
		try{
		fis = new FileInputStream(path); 
		workbook = new XSSFWorkbook(fis);

		if(rowNum<=0)
			return false;
		
		int index = workbook.getSheetIndex(sheetName);
		int colNum=-1;
		if(index==-1)
			return false;
		
		sheet = workbook.getSheetAt(index);
		
		row=sheet.getRow(0);
		for(int i=0;i<row.getLastCellNum();i++){
			
			if(row.getCell(i).getStringCellValue().trim().equalsIgnoreCase(colName))
				colNum=i;
		}
		
		if(colNum==-1)
			return false;
		sheet.autoSizeColumn(colNum); 
		row = sheet.getRow(rowNum-1);
		if (row == null)
			row = sheet.createRow(rowNum-1);
		
		cell = row.getCell(colNum);	
		if (cell == null)
	        cell = row.createCell(colNum);
			
	    cell.setCellValue(data);
	    
	    XSSFCreationHelper createHelper = workbook.getCreationHelper();

	    //cell style for hyperlinks
	    
	    CellStyle hlink_style = workbook.createCellStyle();
	    XSSFFont hlink_font = workbook.createFont();
	    hlink_font.setUnderline(XSSFFont.U_SINGLE);
	    hlink_font.setColor(IndexedColors.BLUE.getIndex());
	    hlink_style.setFont(hlink_font);
	    //hlink_style.setWrapText(true);

	    //XSSFHyperlink link = createHelper.createHyperlink(XSSFHyperlink.LINK_FILE);
	    XSSFHyperlink link = createHelper.createHyperlink(HyperlinkType.FILE);
	    link.setAddress(url);
	    cell.setHyperlink(link);
	    cell.setCellStyle(hlink_style);
	      
	    fileOut = new FileOutputStream(path);
		workbook.write(fileOut);
		fileOut.flush();
	    fileOut.close();	

		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	// returns true if sheet is created successfully else false
	public boolean addSheet(String  sheetname){		
		
		FileOutputStream fileOut;
		try {
			 workbook.createSheet(sheetname);	
			 fileOut = new FileOutputStream(path);
			 workbook.write(fileOut);
		     fileOut.close();		    
		} catch (Exception e) {			
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	// returns true if sheet is removed successfully else false if sheet does not exist
	public boolean removeSheet(String sheetName){		
		int index = workbook.getSheetIndex(sheetName);
		if(index==-1)
			return false;
		
		FileOutputStream fileOut;
		try {
			workbook.removeSheetAt(index);
			fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
		    fileOut.close();		    
		} catch (Exception e) {			
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	// returns true if column is created successfully
	public boolean addColumn(String sheetName,String colName){
		
		try{				
			fis = new FileInputStream(path); 
			workbook = new XSSFWorkbook(fis);
			int index = workbook.getSheetIndex(sheetName);
			if(index==-1)
				return false;
			
		XSSFCellStyle style = workbook.createCellStyle();
		
		//XSSFColor color = new XSSFColor(new java.awt.Color(128, 0, 128));
		//style.setFillForegroundColor(color);
		style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());		
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		//style.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
		//style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		
		sheet=workbook.getSheetAt(index);
		
		row = sheet.getRow(0);
		if (row == null)
			row = sheet.createRow(0);
		
		if(row.getLastCellNum() == -1)
			cell = row.createCell(0);
		else
			cell = row.createCell(row.getLastCellNum());
	        
	        cell.setCellValue(colName);
	        cell.setCellStyle(style);
	        
	        fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			fileOut.flush();
		    fileOut.close();		    

		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
		return true;
		
	}
	
	// removes a column and all the contents--Modify and accept column name instead of column number
	public boolean removeColumn(String sheetName, int colNum) {
		try{
		if(!isSheetExist(sheetName))
			return false;
		fis = new FileInputStream(path); 
		workbook = new XSSFWorkbook(fis);
		sheet=workbook.getSheet(sheetName);
		XSSFCellStyle style = workbook.createCellStyle();		
		style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());		
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		//style.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
		XSSFCreationHelper createHelper = workbook.getCreationHelper();
		style.setFillPattern(FillPatternType.NO_FILL);
			    
		for(int i =0;i<getRowCount(sheetName);i++){
			row=sheet.getRow(i);	
			if(row!=null){
				cell=row.getCell(colNum);
				if(cell!=null){
					cell.setCellStyle(style);
					row.removeCell(cell);
				}
			}
		}
		fileOut = new FileOutputStream(path);
		workbook.write(fileOut);
		fileOut.flush();
	    fileOut.close();
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	// removes a column and all the contents
	public boolean removeColumnByColumnName(String sheetName, String colName) {
			try{
			if(!isSheetExist(sheetName))
				return false;
			fis = new FileInputStream(path); 
			workbook = new XSSFWorkbook(fis);
			sheet=workbook.getSheet(sheetName);
			XSSFCellStyle style = workbook.createCellStyle();		
			style.setFillPattern(FillPatternType.NO_FILL);
			
			int colNum=-1;
			row=sheet.getRow(0);
			for(int i=0;i<row.getLastCellNum();i++){
				//System.out.println(row.getCell(i).getStringCellValue().trim());
				if(row.getCell(i).getStringCellValue().trim().equals(colName))
					colNum=i;
			}
			if(colNum==-1)
				return false;
				    
			for(int i =0;i<getRowCount(sheetName);i++){
				row=sheet.getRow(i);	
				if(row!=null){
					cell=row.getCell(colNum);
					if(cell!=null){
						cell.setCellStyle(style);
						row.removeCell(cell);
					}
				}
			}
			fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			fileOut.flush();
		    fileOut.close();
			}
			catch(Exception e){
				e.printStackTrace();
				return false;
			}
			return true;
		}
		
	
	// find whether sheets exists	
	public boolean isSheetExist(String sheetName){
		int index = workbook.getSheetIndex(sheetName);
		if(index==-1){
			index=workbook.getSheetIndex(sheetName.toUpperCase());
				if(index==-1)
					return false;
				else
					return true;
		}
		else
			return true;
	}
	
	//String sheetName, String testCaseName,String keyword ,String URL,String message
	public boolean addHyperLink(String sheetName,String screenShotColName,String testCaseName,int index,String url,String message){
		
		url=url.replace('\\', '/');
		if(!isSheetExist(sheetName))
			 return false;
		
	    sheet = workbook.getSheet(sheetName);
	    
	    for(int i=2;i<=getRowCount(sheetName);i++){
	    	if(getCellData(sheetName, 0, i).equalsIgnoreCase(testCaseName)){
	    		
	    		setCellData(sheetName, screenShotColName, i+index, message,url);
	    		break;
	    	}
	    }

	    return true; 
	}
	
	public int getCellRowNum(String sheetName,String colName,String cellValue){
		
		for(int i=2;i<=getRowCount(sheetName);i++){
	    	if(getCellData(sheetName,colName , i).equalsIgnoreCase(cellValue)){
	    		return i;
	    	}
	    }
		return -1;
	}
		
	
	// to run this on stand alone
	public static void main(String arg[]) throws IOException{
		
		
		/*ExcelReader datatable = null;
		

			 datatable = new ExcelReader("C:\\CM3.0\\app\\test\\Framework\\AutomationBvt\\src\\config\\xlfiles\\Controller.xlsx");
				for(int col=0 ;col< datatable.getColumnCount("TC5"); col++){
					System.out.println(datatable.getCellData("TC5", col, 1));
				}*/
	}
	
	
}
