package BtlJava.Nhom2.Files;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import BtlJava.Nhom2.dao.DepartmentDAOImpl;
import BtlJava.Nhom2.dao.EmployeeDAOImpl;
import BtlJava.Nhom2.database.ConnectionPool;
import BtlJava.Nhom2.database.ConnectionPoolImpl;
import  BtlJava.Nhom2.model.Department;
import  BtlJava.Nhom2.model.Employee;

public class ExportFileEmployee {
	public static void main(String[] args) {
        exportDepartmentCountToExcel();
    }
	private static ConnectionPool cp = new ConnectionPoolImpl();
    public static void exportDepartmentCountToExcel() {
       // Tạo Workbook (file Excel)
        		try (Workbook workbook = new XSSFWorkbook();
        	             FileOutputStream outputStream = new FileOutputStream("employee_list.xlsx")) { 
        	            Sheet sheet = workbook.createSheet("Danh sách nhân viên");

        	            // Tạo tiêu đề cột
        	            Row headerRow = sheet.createRow(0);
        	            headerRow.createCell(0).setCellValue("Mã Nhân viên");
        	            headerRow.createCell(1).setCellValue("Tên Nhân viên");
        	            headerRow.createCell(2).setCellValue("Email");  
        	            headerRow.createCell(3).setCellValue("Số điện thoại "); 
        	            headerRow.createCell(4).setCellValue("Địa chỉ"); 
        	            headerRow.createCell(5).setCellValue("Ngày bắt đầu"); 
        	            headerRow.createCell(6).setCellValue(" Lương"); 
        	            headerRow.createCell(7).setCellValue(" Trạng thái"); 
        	            // Lấy danh sách Khoa từ DAO
        	          
        	           ArrayList<Employee> arrayList = EmployeeDAOImpl.getInstance(cp).findAll();
        	            
        	            ArrayList<Department> departmentList = DepartmentDAOImpl.getInstance(cp).findAll();

        	            // Thêm dữ liệu vào sheet
        	            int rowNum = 1;
        	            for (Employee employee : arrayList) {
        	                Row dataRow = sheet.createRow(rowNum++);
        	                dataRow.createCell(0).setCellValue(employee.getEmployeeId());
        	                dataRow.createCell(1).setCellValue(employee.getName());
        	                dataRow.createCell(2).setCellValue(employee.getEmail());
        	                dataRow.createCell(3).setCellValue(employee.getPhoneNumber());
        	                dataRow.createCell(4).setCellValue(employee.getAddress());
        	                dataRow.createCell(5).setCellValue(employee.getStartDate());
        	                dataRow.createCell(6).setCellValue(employee.getSalary());
        	                dataRow.createCell(7).setCellValue(employee.getStatus());
        	                
        	        
        	            }

        	            workbook.write(outputStream);
        	        } catch (IOException e) {
        	            e.printStackTrace();
        	        }
    }
}
