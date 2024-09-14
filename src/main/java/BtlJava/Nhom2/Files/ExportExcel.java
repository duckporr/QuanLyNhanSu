package BtlJava.Nhom2.Files;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import BtlJava.Nhom2.dao.DepartmentDAOImpl;
import BtlJava.Nhom2.database.ConnectionPool;
import BtlJava.Nhom2.database.ConnectionPoolImpl;
import BtlJava.Nhom2.model.Department;

public class ExportExcel {
	  public static void main(String[] args) {
	        exportDepartmentCountToExcel();
	    }

	  private static ConnectionPool cp = new ConnectionPoolImpl();
	    public static void exportDepartmentCountToExcel() {
	    
	        		try (Workbook workbook = new XSSFWorkbook();
	        	             FileOutputStream outputStream = new FileOutputStream("department_list.xlsx")) { 
	        	            Sheet sheet = workbook.createSheet("Danh sách Khoa");

	        	        
	        	            Row headerRow = sheet.createRow(0);
	        	            headerRow.createCell(0).setCellValue("Mã Khoa");
	        	            headerRow.createCell(1).setCellValue("Tên Khoa");
	        	            headerRow.createCell(2).setCellValue("Mô tả");

	        	     
	        	            ArrayList<Department> departmentList = DepartmentDAOImpl.getInstance(cp).findAll();

	        	          
	        	            int rowNum = 1;
	        	            for (Department department : departmentList) {
	        	                Row dataRow = sheet.createRow(rowNum++);
	        	                dataRow.createCell(0).setCellValue(department.getDepartmentId());
	        	                dataRow.createCell(1).setCellValue(department.getDepartmentName());
	        	                dataRow.createCell(2).setCellValue(department.getDescription());
	        	            }

	        	            workbook.write(outputStream);
	        	        } catch (IOException e) {
	        	            e.printStackTrace();
	        	        }
	    }

}
