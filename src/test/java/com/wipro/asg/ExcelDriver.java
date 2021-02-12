package com.wipro.asg;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import jdk.jfr.internal.Logger;


public class ExcelDriver {

	public String input_sheet;
	public String output_sheet;





	
	
	public ExcelDriver(String input,String output) {
		super();
		this.input_sheet = input;
		this.output_sheet = output;
		try {
			//Deleting rows in output sheet
			FileInputStream fis = new FileInputStream(new File(output));
			XSSFWorkbook wbook = new XSSFWorkbook(fis);
			XSSFSheet wsheet = wbook.getSheet("Result");
			wsheet.removeRow(wsheet.getRow(1));
			wsheet.removeRow(wsheet.getRow(2));
			FileOutputStream fileOut = new FileOutputStream(output_sheet);
			wbook.write(fileOut);			
			fileOut.close();
			wbook.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("No rows");
			
		}
		
	
		
	}


	public Object[][] dataprovider(int sheetname){
		
		
		try {
			FileInputStream fis = new FileInputStream(new File(input_sheet));
	
			XSSFWorkbook wbook = new XSSFWorkbook(fis);
	
			XSSFSheet wsheet = wbook.getSheetAt(sheetname);
			
			
			int rowcount=wsheet.getPhysicalNumberOfRows();
			int colcount=wsheet.getRow(0).getPhysicalNumberOfCells();

			
			Object excel_data[][]=new Object[rowcount-1][colcount];
					
			for(int i=1;i<rowcount;i++){
				for(int j=0;j<colcount;j++){
					try{
						System.out.println(i+"----"+j);
					 excel_data[i-1][j] = wsheet.getRow(i).getCell(j).getStringCellValue();
					}
					 catch(IllegalStateException e){
						 excel_data[i-1][j] = wsheet.getRow(i).getCell(j).getNumericCellValue();
					 }
		
				}
			}
			wbook.close();
			return excel_data;
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
		
	}

	@SuppressWarnings("resource")
	public boolean setCellData(String sheetName, String colName, int rowNum, String data) {
		try {
						
			File file1 = new File(output_sheet);
			FileInputStream fis1 = new FileInputStream(file1);
			XSSFWorkbook wbook1 = new XSSFWorkbook(fis1);

			int index = wbook1.getSheetIndex(sheetName);
			int colNum = -1;
			if (index == -1)
				return false;

			XSSFSheet sheet = wbook1.getSheetAt(index);

			
			XSSFRow row = sheet.getRow(0);
			for (int i = 0; i < row.getLastCellNum(); i++) {
				// System.out.println(row.getCell(i).getStringCellValue().trim());
				if (row.getCell(i).getStringCellValue().trim().equals(colName))
					colNum = i;
			}
			if (colNum == -1)
				return false;

			sheet.autoSizeColumn(colNum);
			row = sheet.getRow(rowNum - 1);
			if (row == null)
				row = sheet.createRow(rowNum - 1);

			XSSFCell cell = row.getCell(colNum);
			if (cell == null)
				cell = row.createCell(colNum);

			cell.setCellValue(data);

			FileOutputStream fileOut = new FileOutputStream(output_sheet);

			wbook1.write(fileOut);
			
			fileOut.close();

			wbook1.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	
	

}
