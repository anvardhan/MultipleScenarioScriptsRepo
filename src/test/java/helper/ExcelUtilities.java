package helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Color;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelUtilities {

	//Read data from excel
		public static String[][] readXL(String fPath, String fSheetName) throws IOException{

			//String fPath = "C:\\data\\TestData.xlsx";
			//String fSheetName = "Sheet1";
			
			XSSFWorkbook wb;
			XSSFSheet ws;
			XSSFRow row;
			XSSFCell cell;
			String[][] vXLData;
			int vXL_rowNum;
			int vXL_colNum;		
					
			//Specify the source file path which we want to create or point
			File f = new File(fPath);	
			
			//create inputstream i.e create a connection with source file. or load file to input stream.
			FileInputStream fis = new FileInputStream(f);		
			
			//load the workbook of the file
			wb = new XSSFWorkbook(fis);
			
			//get the sheet from which we need to read data
			ws = wb.getSheet(fSheetName);
			
			//String valueFromCell = ws.getRow(1).getCell(0).getStringCellValue();

			
			//Get the row and column counts in work sheet
			vXL_rowNum = ws.getLastRowNum()+1; //9
			System.out.println("rowCnt in excel utilities "+vXL_rowNum);
			vXL_colNum = ws.getRow(0).getLastCellNum();//6
			System.out.println("colCnt in excel utilities "+vXL_colNum);
			
			//Create 2D array of type string with row and col counts -this will be used to store excel data in it.		
			vXLData = new String[vXL_rowNum][vXL_colNum];
			

			//System.out.println("Started reading data from inputstream");
			//Use for loop to read the excel data and store in vXLData array.
			
			//Traverse all rows
			for (int i=0; i < vXL_rowNum; i++)
			{			
					row = ws.getRow(i); //This will get the one row with all columns in it.
					
					//Traverse all cell data of the given row
					for(int j = 0;j < vXL_colNum;j++)
					{
						cell = row.getCell(j); //This will get the one cell data
						
						//String vCell="-";
						String vCell= null;
						
						if (cell!=null)
						{
							//vCell=cell.getStringCellValue().toString();
							vCell = cellToString(cell);						
						}
						//else if()
						vXLData[i][j] = vCell;
						//System.out.println(vCell);
						//System.out.println(fSheetName +": "+vCell);
					}
			}	
			//	System.out.println("Completed reading data");
			fis.close();
			wb.close();
			return vXLData;
		}
		
		
		// This function will convert an object of type excel cell to a string value
		public static String cellToString(XSSFCell cell) { 
			
				//Getting type of the string from excel
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

	
	//Writing data to excel
	public static void writeXL(String fPath, String fSheet, String[][] xData) throws Exception
	{
		
    	File outFile = new File(fPath);
        XSSFWorkbook wb = new XSSFWorkbook();      
        XSSFSheet osheet = wb.createSheet(fSheet);
        
        int xRowCnt = xData.length;
       //System.out.println(xData.length);
        int xColCnt = xData[0].length;
        
        //Styling
        XSSFCellStyle  headerCellStyle = osheet.getWorkbook().createCellStyle();
		XSSFCellStyle  bodyCellStyle = osheet.getWorkbook().createCellStyle();
		
		XSSFFont headerfont = osheet.getWorkbook().createFont();
		headerfont.setBold(true); //set the cell value to bold //This is working
		headerfont.setFontHeightInPoints((short) 14); //set the cell value to increased size
		//headerfont.setColor((short) 2); //This works-sets the font to Red
//headerfont.setColor(new XSSFColor(Color.blue)); //This works-sets the font to Red -- XSSFColor deprecated
		
		XSSFFont bodyfont = osheet.getWorkbook().createFont();
		bodyfont.setFontName("Calibri");  //This is working
		
		//Header and Body Font setup
		headerCellStyle.setFont(headerfont); //for header
		bodyCellStyle.setFont(bodyfont); //for body
		
		//header Color
		headerCellStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());	//This worked		
		headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND); //This worked with foreground
		//headerCellStyle.setFillBackgroundColor(IndexedColors.LIGHT_ORANGE.getIndex()); //This worked
		//headerCellStyle.setFillPattern(FillPatternType.LESS_DOTS); //This worked with backgound			
		
		//header Border
		headerCellStyle.setBorderBottom(BorderStyle.DOUBLE);
		headerCellStyle.setBottomBorderColor(IndexedColors.BLUE.getIndex());
	   // cellStyle.setBorderTop(BorderStyle.THICK); //This worked
		headerCellStyle.setBorderRight(BorderStyle.THIN);
		
		//header Alignment
		headerCellStyle.setAlignment(HorizontalAlignment.LEFT);
		headerCellStyle.setWrapText(true); //Working
		
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
        
        //Error Cell Style for FAIL
		XSSFCellStyle  CellStyle = osheet.getWorkbook().createCellStyle();
    	CellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
    	CellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND); //This worked with foreground
    	XSSFFont font = osheet.getWorkbook().createFont();
    	font.setBold(true);
    	CellStyle.setFont(font);
		
       //System.out.println("Writing data to xl");
    	for (int i = 0; i < xRowCnt; i++) 
    	{
	        XSSFRow row = osheet.createRow(i);
	        
	        for (int j = 0; j < xColCnt; j++) 
	        {
	        	XSSFCell cell = row.createCell(j);
	        	cell.setCellValue(xData[i][j]);
	        	osheet.setColumnWidth(j, 5000);  //Setting column Width
	        	
	        	if(i==0)
			       	cell.setCellStyle(headerCellStyle);
			    else
			       	cell.setCellStyle(bodyCellStyle);
	        	
	        	String val = xData[i][j];
	        	if(val != null) {
		        	if(val.equalsIgnoreCase("FAIL")) {
		        		cell.setCellStyle(CellStyle);
		        	}
	        	}
	        	
	        	//Merged
	        	//osheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));
	        	/*if(val.contains("<Record") && j==2) {
	        		osheet.addMergedRegion(new CellRangeAddress(i, i, 1, 2));
	        	} */
	        	  
	        	
	        }
    	}
    	FileOutputStream fOut = new FileOutputStream(outFile);
        
		wb.write(fOut); //This writes data to OutputStreaam
	        
		fOut.flush(); //This writes data to Actual File from OutputStreaam
	        
		fOut.close();
    	
    	wb.close();
	}

	
	public static int findColumnIndex(String[][] data, String colName) {
		
		int colIndex=-1;
		int colSize = data[0].length; //column size of header row which contains column name
		
		for (int i = 0;i<colSize;i++) {			
			if(data[0][i].equals(colName)) {
				colIndex=i;
				break;
			}			
		 }
		return colIndex;
	}
	
	
	//Read XL Using HashMap and ArrayList - Yet to work
	
	static XSSFWorkbook ExcelWBook = null;
	static XSSFSheet ExcelWSheet = null;
	
	public static ArrayList<HashMap<String,String >> getTestDataFromDataSheet(String FilePath, String SheetName)    throws Exception

	{  	 
		   
		   //HashMap<key,value > TestData = new HashMap<String,String>();
	       HashMap<String,String > TestData = new HashMap<String,String>();

	       ArrayList<HashMap<String,String >> Excelvalues = new ArrayList<HashMap<String,String >>();

	       //CommonFunction com = new CommonFunction();
	  

	   try{	 

	          FileInputStream ExcelFile = new FileInputStream(FilePath);	 

	          // Access the required test data sheet	 

	          ExcelWBook = new XSSFWorkbook(ExcelFile);	 

	          ExcelWSheet = ExcelWBook.getSheet(SheetName);	         

	          int noofRows = ExcelWSheet.getPhysicalNumberOfRows();
	          //System.out.println("no of rows "+noofRows);

	         
	          int startCol = 0;	 

	          int ci=0,cj=0;	         

	          int usedCells = 0;	 

	          //int totalRows = 1;	 

	          int totalCols = ExcelWSheet.getRow(0).getLastCellNum()-1;
	          
	          //System.out.println("total cols "+totalCols);

	//Loop to get only used cells from Excel - getPhysicalNumberofCells not working        

	                 for (int j=startCol;j<=totalCols;j++){

	                        String cellValue = getCellData(0,j);	                       

	                              usedCells++;               
	                 }	      
	                 
	                 //System.out.println("usedCells: "+usedCells);

	//Create array to store values                          

	        //  tabArray=new String[1][usedCells];

	                

	for(int i=1;i<noofRows;i++)
	{    
	          for (int k=startCol;k<usedCells;k++, cj++)       
	          {
	                String Heading = getCellData(0,k);
	                //System.out.println("Heading: "+Heading);

	                String[] head = Heading.split("\\.");

	                Heading=head[1];
	                //System.out.println("Heading After Split: "+Heading);
	               

	              XSSFCell  Cell = ExcelWSheet.getRow(i).getCell(k);      	             

	              String Value ="";	             

	              if(Cell != null)

	              //if(!(Cell.getCellType()==Cell.CELL_TYPE_BLANK))

	              {
	            	  //Value  = com.cellToString(Cell);

	                Value  = ExcelUtilities.cellToString(Cell);

	                 //System.out.println(Value);

	              }

	              else
	              {
	                     Value  = "";
	              }

	       //     String Value  = getCellData(iTestCaseRow,k);

	               TestData.put(Heading,Value);
	               
	          }
	          Excelvalues.add(TestData);
	          
	}
			System.out.println(Excelvalues);
	          return Excelvalues;

	       }
	       catch (FileNotFoundException e)
	       {	 

	              System.out.println("Could not read the Excel sheet");	 

	              e.printStackTrace();

	                 return Excelvalues;             

	       }

	   	   catch (IOException e)	 

	       {	 

	              System.out.println("Could not read the Excel sheet");	 

	              e.printStackTrace();

	                 return Excelvalues;

	       }

	}
	
	
	
	//Using for Header
	public static String getCellData(int RowNum, int ColNum) throws Exception {		 

        try{

              XSSFCell Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);

              String CellData = Cell.getStringCellValue();

              return CellData;

              } catch (Exception e){

                  return "";

                  }
            }
	


	//Compare Two Sheets
	public static boolean compareTwoSheets(String fPath1, String fPath2) throws IOException {
    
		// get input excel files
        FileInputStream excelFile1 = new FileInputStream(new File(fPath1));
        FileInputStream excelFile2 = new FileInputStream(new File(fPath2));

        // Create Workbook instance holding reference to .xlsx file
        XSSFWorkbook workbook1 = new XSSFWorkbook(excelFile1);
        XSSFWorkbook workbook2 = new XSSFWorkbook(excelFile2);

        // Get first/desired sheet from the workbook
        XSSFSheet sheet1 = workbook1.getSheetAt(0);
        XSSFSheet sheet2 = workbook2.getSheetAt(0);
		
		int firstRow1 = sheet1.getFirstRowNum();
	    int lastRow1 = sheet1.getLastRowNum();
	    boolean equalSheets = true;
	    for(int i=firstRow1+1; i <= lastRow1; i++) {        
	        System.out.println("\n\nComparing Row "+i);	        
	        XSSFRow row1 = sheet1.getRow(i);
	        XSSFRow row2 = sheet2.getRow(i);
	        if(!compareTwoRows(row1, row2)) {
	            equalSheets = false;
	            System.out.println("Row "+i+" - Not Equal");
	            break;
	        } else {
	            System.out.println("Row "+i+" - Equal");
	        }
	    }
        return equalSheets;
	}

	// Compare Two Rows
	public static boolean compareTwoRows(XSSFRow row1, XSSFRow row2) {
	    if((row1 == null) && (row2 == null)) {
	        return true;
	    } else if((row1 == null) || (row2 == null)) {
	        return false;
	    }
	    
	    int firstCell1 = row1.getFirstCellNum();
	    //System.out.println("First Cell "+firstCell1);
	    int lastCell1 = row1.getLastCellNum();
	    //System.out.println("Total No of Cell "+lastCell1);
	    boolean equalRows = true;
	    
	    // Compare all cells in a row
	    for(int i=firstCell1; i <= lastCell1; i++) {
	        XSSFCell cell1 = row1.getCell(i);
	        XSSFCell cell2 = row2.getCell(i);
	        if(!compareTwoCells(cell1, cell2)) {
	            equalRows = false;
	            System.err.println("       Cell "+i+" - NOt Equal");
	            break;
	        } else {
	            System.out.println("       Cell "+i+" - Equal");
	        }
	    }
	    return equalRows;
	}

	// Compare Two Cells
	public static boolean compareTwoCells(XSSFCell cell1, XSSFCell cell2) {
	    if((cell1 == null) && (cell2 == null)) {
	        return true;
	    } //else if((cell1 == null) || (cell2 == null)) {
	        //return false;
	    //}
	    
	    boolean equalCells = false;
	    
	    CellType type1 = cell1.getCellTypeEnum();
	    CellType type2 = cell2.getCellTypeEnum();
	    
	    if (type1 == type2) {
	        //if (cell1.getCellStyle().equals(cell2.getCellStyle())) {
	            // Compare cells based on its type
	            switch (type1) {
		            case FORMULA:
		                if (cell1.getStringCellValue().equals(cell2.getStringCellValue())) {
		                    equalCells = true;
		                }
		                break;
		            case NUMERIC:
		                if (cell1.getNumericCellValue() == cell2.getNumericCellValue()) {
		                    equalCells = true;
		                }
		                break;
		            case STRING:
		                if (cell1.getStringCellValue().equals(cell2.getStringCellValue())) {
		                    equalCells = true;
		                }
		                break;
		            case BLANK:
		            	//cell1.setCellValue("NULL");
		            	//System.out.println("Blank Value in Sheet1");
		                if (cell1.getStringCellValue().equals(cell2.getStringCellValue())) {
		                    equalCells = true;
		                }
		                break;
		            case BOOLEAN:
		                if (cell1.getBooleanCellValue() == cell2.getBooleanCellValue()) {
		                    equalCells = true;
		                }
		                break;
		            case ERROR:
		                if (cell1.getErrorCellValue() == cell2.getErrorCellValue()) {
		                    equalCells = true;
		                }
		                break;
		            default:
		                if (cell1.getStringCellValue().equals(cell2.getStringCellValue())) {
		                    equalCells = true;
		                }
		                break;
	             }
	       /* } else {
	            return false;
	        }*/
	    } else {
	        return false;
	    }
	    return equalCells;
	}
} //End of Class ExcelUtilities





