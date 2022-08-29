package helper;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcel{

    
    public static final String EXCELPATH="C:\\SeleniumTraining\\Project_Code\\InputFile\\Address.xlsx";

    private static FileInputStream fis;

    private static XSSFWorkbook workbook;

    private  static XSSFSheet sheet;

    private static XSSFRow row;

   

    public static void loadExcel() throws Exception {

          

           File file=new File(EXCELPATH);

           fis=new FileInputStream(file);

           workbook=new XSSFWorkbook(fis);

           sheet=workbook.getSheet("sheet2");

           fis.close();

    }

   

    public static List<Map<String,String>> getDataMap() throws Exception{

    //public static Map<String, Map<String,String>> getDataMap() throws Exception{

           if(sheet==null) {

                  loadExcel();

           }

          

           List<Map<String,String>> mapLists=new ArrayList<>();

          

           //Map<String, Map<String,String>> superMap=new HashMap<String, Map<String,String>>();

           //Map<String,String> myMap=new HashMap<String,String>();

          

           //for(int i = 1; i< sheet.getLastRowNum() + 1; i++)

           //{

                  row=sheet.getRow(0);

                  //String keyvalue=row.getCell(0).getStringCellValue();

                 

                  int colNum=row.getLastCellNum();

                  for(int j =1;j<sheet.getLastRowNum()+1;j++)

                  {
                	    Row r=CellUtil.getRow(j, sheet);
                        Map<String,String> myMap=new HashMap<>();

                        for(int i = 1; i< colNum; i++) {                             
                               
                                                              
                               //String key=CellUtil.getCell(r, 0).getStringCellValue();
                               
                               XSSFCell cellkey = (XSSFCell) CellUtil.getCell(r, 0);
                               
                               String key = ExcelUtilities.cellToString(cellkey);
                                                              
                               //String value=CellUtil.getCell(r, j).getStringCellValue();
                               
                               XSSFCell cellvalue = (XSSFCell) CellUtil.getCell(r, j);
                               
                               String value = ExcelUtilities.cellToString(cellvalue);

                        //String value=row.getCell(i).getStringCellValue();

                        myMap.put(key, value);

                  }

                  //superMap.put("MASTERDATA", myMap);

                        mapLists.add(myMap);

           }

           return mapLists;

    }

   

    //public static String getValue(String key) throws Exception {

           //Map<String,String> myVal=getDataMap().get("MASTERDATA");

           //String retVal=myVal.get(key);

          

           //return retVal;

    //}

   

    public static void main(String[] args) throws Exception {

        //   System.out.println(getDataMap());
    	List<Map<String,String>> list = getDataMap();
    	System.out.println(list);
    }

}
