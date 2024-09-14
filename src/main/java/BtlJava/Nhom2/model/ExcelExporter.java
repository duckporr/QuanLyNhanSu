package BtlJava.Nhom2.model;

import java.io.File;

import javax.swing.JTable;
import javax.swing.table.TableModel;

import jxl.*;
import jxl.write.*;
public class ExcelExporter {
	 public void fillData(JTable table, File file) {

	        try {

	            WritableWorkbook workbook1 = Workbook.createWorkbook(file);
	            WritableSheet sheet1 = workbook1.createSheet("First Sheet", 0);
	            TableModel model = table.getModel();

	            for (int i = 0; i < model.getColumnCount(); i++) {
	                Label column = new Label(i, 0, model.getColumnName(i));
	                sheet1.addCell(column);
	            }
	            int j = 0;
	            for (int i = 0; i < model.getRowCount(); i++) {
	                for (j = 0; j < model.getColumnCount(); j++) {
	                    Label row = new Label(j, i + 1,
	                            model.getValueAt(i, j).toString());
	                    sheet1.addCell(row);
	                }
	            }
	            workbook1.write();
	            workbook1.close();
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }

	 }
}

