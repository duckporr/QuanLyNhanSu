package BtlJava.Nhom2.view;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JTabbedPane;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import BtlJava.Nhom2.dao.DepartmentDAOImpl;
import BtlJava.Nhom2.dao.EmployeeDAOImpl;
import BtlJava.Nhom2.dao.PositionDAOImpl;
import BtlJava.Nhom2.dao.SalaryDAOImpl;
import BtlJava.Nhom2.database.ConnectionPool;
import BtlJava.Nhom2.database.ConnectionPoolImpl;
import BtlJava.Nhom2.model.Department;
import BtlJava.Nhom2.model.Employee;
import BtlJava.Nhom2.model.Salary;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Desktop;

import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

public class SalaryView extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JTextField search;
	private JTable tblLuongthuong;
	private JTextField txtAdjustment;
	private JTable tblLuong;
	private JTextField txtEmpId;
	private JTable tblPhuCap;
	private JTextField txtAllowance;
	private JTextField empIdAllowance;
	private JTextField txtDeduction;
	private JTextField empIdDeduction;
	private JTable tblLuongCapNhat;
	private JTextField txtSalaryUpdate;
	private JTextField idUpdate;
	private JTable tblLuongChot;
	private DefaultTableModel model;
	private DefaultTableModel model2;
	private DefaultTableModel model3;
	private DefaultTableModel model4;
	private DefaultTableModel model5;
	private ConnectionPool cp = new ConnectionPoolImpl();
	private int count = 1;
	private int count2 = 1;
	private int count3 = 1;
	private int count4 = 1;
	private int count5 = 1;
	private JTextField txtYear;
	private static Salary salarySelected;
	private static Salary salaryUpdatedSelect;
	private JTextField txtSearch5;
	private DepartmentDAOImpl departmentDAOImpl;
	private EmployeeDAOImpl employeeDAOImpl;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SalaryView frame = new SalaryView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SalaryView() {
		BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);
		setBorder(null);
		setBounds(0, 0, 774, 533);
		getContentPane().setLayout(null);
		
		
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 776, 504);
		getContentPane().add(tabbedPane);
		
		JPanel LuongThang = new JPanel();
		LuongThang.setBackground(new Color(255, 255, 255));
		tabbedPane.addTab("BẢNG LƯƠNG THÁNG", null, LuongThang, null);
		tabbedPane.setBackgroundAt(0, new Color(255, 255, 255));
		tabbedPane.setForegroundAt(0, new Color(0, 0, 0));
		LuongThang.setLayout(null);
		
		
		
		JPanel panel_5 = new JPanel();
		panel_5.setBackground(new Color(255, 255, 255));
		panel_5.setBounds(0, 0, 773, 407);
		LuongThang.add(panel_5);
		panel_5.setLayout(null);
		
		JPanel panel_10 = new JPanel();
		panel_10.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_10.setBackground(new Color(255, 255, 255));
		panel_10.setBounds(8, 24, 755, 75);
		panel_5.add(panel_10);
		panel_10.setLayout(null);
		
		JLabel pageLabel = new JLabel("1");
		pageLabel.setBounds(683, 421, 18, 23);
		LuongThang.add(pageLabel);
		
		JButton btnPrev = new JButton("<");
		
		btnPrev.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnPrev.setBounds(604, 418, 50, 23);
		LuongThang.add(btnPrev);
		
		JButton btnNext = new JButton(">");
		
		btnNext.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnNext.setBounds(711, 418, 50, 23);
		LuongThang.add(btnNext);
		
		
		
		search = new JTextField();
		
		
		search.setBounds(395, 29, 199, 29);
		panel_10.add(search);
		search.setColumns(10);
		
		String[] months = {"Tháng", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
		JComboBox cbbMonth = new JComboBox<>(months);
		cbbMonth.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String key = search.getText().trim();
		        int totalMonth;
		        String months = String.valueOf(cbbMonth.getSelectedItem());
				if(months.equals("Tháng")) months = "-1";
				totalMonth = Integer.parseInt(months);
				String years = txtYear.getText();
				int totalYear = Integer.parseInt(years);
		        
		        String oldPage = pageLabel.getText();
				int currentPage = Integer.parseInt(oldPage);
				int total = 15;
				int startIndex = (currentPage - 1) * total + 1;
		       
		        try {
		        	loadSalaryData(SalaryDAOImpl.getInstance(cp).filterSalaryPage(totalMonth, totalYear , key, currentPage, total), startIndex);
				} catch (SQLException e1) {
		        } catch (Exception e1) {
		            e1.printStackTrace();
		        }
			}
		});
		cbbMonth.setBounds(66, 34, 60, 19);
		panel_10.add(cbbMonth);
		
		txtYear = new JTextField();
		txtYear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String key = search.getText().trim();
		        int totalMonth;
		        String months = String.valueOf(cbbMonth.getSelectedItem());
				if(months.equals("Tháng")) months = "-1";
				totalMonth = Integer.parseInt(months);
				String years = txtYear.getText();
				int totalYear = Integer.parseInt(years);
		        
		        String oldPage = pageLabel.getText();
				int currentPage = Integer.parseInt(oldPage);
				int total = 15;
				int startIndex = (currentPage - 1) * total + 1;
		       
		        try {
		        	loadSalaryData(SalaryDAOImpl.getInstance(cp).filterSalaryPage(totalMonth, totalYear , key, currentPage, total), startIndex);
				} catch (SQLException e1) {
		        } catch (Exception e1) {
		            e1.printStackTrace();
		        }
			}
		});
		txtYear.setBounds(169, 33, 53, 20);
		panel_10.add(txtYear);
		txtYear.setColumns(10);
		int currentYear = LocalDateTime.now().getYear();
		txtYear.setText(String.valueOf(currentYear));
		
		JLabel lblNewLabel_1 = new JLabel("Tìm kiếm:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1.setBounds(303, 38, 68, 11);
		panel_10.add(lblNewLabel_1);
		
		JButton btnExportExcel = new JButton("Xuất excel");
		btnExportExcel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
		            JFileChooser jFileChooser = new JFileChooser();
		            jFileChooser.showSaveDialog(btnExportExcel);
		            File saveFile = jFileChooser.getSelectedFile();
		            if (saveFile != null) {
		                saveFile = new File(saveFile.toString() + ".xlsx");
		                Workbook wb = new XSSFWorkbook();
		                
		                Sheet sheet = wb.createSheet("Salary");

		                Row headerRow = sheet.createRow(0);
		                headerRow.createCell(0).setCellValue("Mã Lương");
		                headerRow.createCell(1).setCellValue("Tháng");
		                headerRow.createCell(2).setCellValue("năm");
		                headerRow.createCell(3).setCellValue("Luơng cơ bản");
		                headerRow.createCell(4).setCellValue("Phụ cấp");
		                headerRow.createCell(5).setCellValue("Phạt");
		                headerRow.createCell(6).setCellValue("Thưởng");
		                headerRow.createCell(7).setCellValue("Thực lãnh");
		                headerRow.createCell(8).setCellValue("Nhân viên");
		                headerRow.createCell(9).setCellValue("Phòng ban");
		                headerRow.createCell(10).setCellValue("Vị trí");

		                ArrayList<Salary> salaries = SalaryDAOImpl.getInstance(cp).findAll();
		                int rowNum = 1;
		                for (Salary salary : salaries) {
		                    Row row = sheet.createRow(rowNum++);
		                    row.createCell(0).setCellValue(salary.getSalaryId());
		                    row.createCell(1).setCellValue(salary.getMonth());
		                    row.createCell(2).setCellValue(salary.getYear());
		                    row.createCell(3).setCellValue(salary.getBaseSalary());
		                    row.createCell(4).setCellValue(salary.getAllowance());
		                    row.createCell(5).setCellValue(salary.getDeduction());
		                    row.createCell(6).setCellValue(salary.getAdjustment());
		                    row.createCell(7).setCellValue(salary.getTotalSalary());
		                    row.createCell(8).setCellValue(salary.getEmployee().getName());
		                    row.createCell(9).setCellValue(salary.getDepartment().getDepartmentName());
		                    row.createCell(10).setCellValue(salary.getPosition().getTitle());
		                }
		                for (int i = 0; i <= 10; i++) {
		                    sheet.autoSizeColumn(i);
		                }
		                FileOutputStream out = new FileOutputStream(new File(saveFile.toString()));
		                wb.write(out);
		                wb.close();
		                out.close();
		                openFile(saveFile.toString());
		            }
		        } catch (Exception ev) {
		            ev.printStackTrace();
		        }
			}
		});
		btnExportExcel.setBackground(new Color(0, 128, 255));
		btnExportExcel.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnExportExcel.setBounds(621, 30, 108, 24);
		panel_10.add(btnExportExcel);
		
		
		JLabel lblNewLabel_8 = new JLabel("Tháng:");
		lblNewLabel_8.setBounds(22, 36, 46, 14);
		panel_10.add(lblNewLabel_8);
		
		JLabel lblNewLabel_9 = new JLabel("Năm:");
		lblNewLabel_9.setBounds(136, 36, 46, 14);
		panel_10.add(lblNewLabel_9);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(8, 119, 755, 261);
		panel_5.add(scrollPane);
		
		tblLuong = new JTable();
		scrollPane.setViewportView(tblLuong);
		tblLuong.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		JLabel lblNewLabel = new JLabel("BẢNG LƯƠNG NHÂN VIÊN");
		lblNewLabel.setBounds(8, 0, 156, 21);
		panel_5.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));

		
		JPanel LuongThuong = new JPanel();
		LuongThuong.setBackground(new Color(255, 255, 255));
		tabbedPane.addTab("LƯƠNG THƯỞNG", null, LuongThuong, null);
		tabbedPane.setForegroundAt(1, new Color(0, 0, 0));
		tabbedPane.setBackgroundAt(1, new Color(255, 255, 255));
		LuongThuong.setLayout(null);
		
		JPanel panel_6 = new JPanel();
		panel_6.setBackground(new Color(255, 255, 255));
		panel_6.setBounds(0, 0, 771, 479);
		LuongThuong.add(panel_6);
		panel_6.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("BẢNG LƯƠNG THƯỞNG THEO THÁNG");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblNewLabel_2.setBounds(8, 8, 201, 11);
		panel_6.add(lblNewLabel_2);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(8, 37, 753, 261);
		panel_6.add(scrollPane_2);
		
		tblLuongthuong = new JTable();
		scrollPane_2.setViewportView(tblLuongthuong);
		tblLuongthuong.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
			}
		));
		tblLuongthuong.setBackground(new Color(250, 250, 250));
		
		JPanel panel_11 = new JPanel();
		panel_11.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_11.setBackground(new Color(255, 255, 255));
		panel_11.setBounds(8, 305, 753, 152);
		panel_6.add(panel_11);
		panel_11.setLayout(null);
		
		JLabel lblNewLabel_3 = new JLabel("Thêm mới lương thưởng");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_3.setBounds(0, 0, 178, 20);
		panel_11.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4_1 = new JLabel("Phòng ban:");
		lblNewLabel_4_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_4_1.setBounds(70, 35, 78, 11);
		panel_11.add(lblNewLabel_4_1);
		
		
		ArrayList<String> listDpm = new ArrayList<String>();
		
		ArrayList<Department> dp = DepartmentDAOImpl.getInstance(cp).findAll();
		for(Department d : dp) {
			listDpm.add(d.getDepartmentName());
		}
		String[] listD = new String[listDpm.size() + 1];
		listD[0] = "Không áp dụng";
		for(int i = 0 ; i<listDpm.size();i++) {
			listD[i+1] = listDpm.get(i);
		}
		
		JComboBox cbbDepartment = new JComboBox(listD);
		
		cbbDepartment.setBounds(158, 31, 222, 19);
		panel_11.add(cbbDepartment);
		
		JLabel lblNewLabel_4_1_1 = new JLabel("Mức thưởng(%):");
		lblNewLabel_4_1_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_4_1_1.setBounds(262, 66, 97, 11);
		panel_11.add(lblNewLabel_4_1_1);
		
		txtAdjustment = new JTextField();
		txtAdjustment.setBounds(363, 62, 52, 17);
		panel_11.add(txtAdjustment);
		txtAdjustment.setColumns(10);
		
		JButton btnNewButton_1 = new JButton("Xác nhận");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int currentMonth = LocalDateTime.now().getMonthValue();
				ArrayList<Salary> salaries = SalaryDAOImpl.getInstance(cp).filterSalaryByTime(currentMonth, currentYear);
				String SalaryDpm = String.valueOf(cbbDepartment.getSelectedItem());
				double adjustment;
				try {
			            adjustment = Double.parseDouble(txtAdjustment.getText());
			        } catch (NumberFormatException ex) {
			            JOptionPane.showMessageDialog(null, "Vui lòng nhập số hợp lệ cho % Lương thưởng!");
			            return;
			        }
				if(adjustment < 0 ) {
					JOptionPane.showMessageDialog(null, "% Lương thưởng phải lớn hơn 0!");
				}else {
					for(Salary s : salaries) {
						if(SalaryDpm.equalsIgnoreCase("Không áp dụng")) {
							int empId = Integer.parseInt(txtEmpId.getText());
							if(s.getEmployee().getEmployeeId() == empId) {
								System.out.println(s.getEmployee().getEmployeeId() + " " + s.getMonth());
								SalaryDAOImpl.getInstance(cp).updateSalaryAdjustment(s.getSalaryId(), adjustment, currentMonth);
							}
						}else {
							if(s.getDepartment().getDepartmentName().equals(SalaryDpm)) {
								System.out.println(s.getDepartment().getDepartmentName() + " " + s.getSalaryId() + " " + adjustment);
								SalaryDAOImpl.getInstance(cp).updateSalaryAdjustment(s.getSalaryId(), adjustment, currentMonth);
							}
						}
					}
					JOptionPane.showMessageDialog(null, "Cập nhật thành công!");
					salaries = SalaryDAOImpl.getInstance(cp).filterSalaryByTime(currentMonth, currentYear);
					loadAdjustmentData(SalaryDAOImpl.getInstance(cp).paginate(salaries, 1, 15), 1);
					
				}
				
			}

		});
		
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 9));
		btnNewButton_1.setBackground(new Color(0, 128, 255));
		btnNewButton_1.setBounds(225, 103, 88, 28);
		panel_11.add(btnNewButton_1);
		
		JLabel lblNewLabel_4_1_2 = new JLabel("Mã NV:");
		lblNewLabel_4_1_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_4_1_2.setBounds(70, 66, 52, 11);
		panel_11.add(lblNewLabel_4_1_2);
		
		JLabel pageLabel2 = new JLabel("1");
		pageLabel2.setBounds(666, 6, 20, 23);
		panel_11.add(pageLabel2);
		
		txtEmpId = new JTextField();
		txtEmpId.setColumns(10);
		txtEmpId.setBounds(158, 62, 77, 17);
		panel_11.add(txtEmpId);
		
		JButton btnPrev2 = new JButton("<");
		btnPrev2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(count2 == 1) count2 = 1;
				else count2--;
				int totalMonth = LocalDateTime.now().getMonthValue();
				int totalYear = LocalDateTime.now().getYear();				
				String oldPage = pageLabel.getText();
				int page = Integer.parseInt(oldPage);
				if(page == 1) page = 1;
				else page--;
				int currentPage = page;
				int total = 15;
				String newPage = String.valueOf(page);
				pageLabel2.setText(newPage);
				int startIndex = (currentPage - 1) * total + 1;
				ArrayList<Salary> salaries = SalaryDAOImpl.getInstance(cp).filterSalaryByTime(totalMonth, currentYear);
				loadAdjustmentData(SalaryDAOImpl.getInstance(cp).paginate(salaries, currentPage, total), startIndex);
			}
		});
		btnPrev2.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnPrev2.setBounds(601, 3, 50, 23);
		panel_11.add(btnPrev2);
		
		
		
		JButton btnNext2 = new JButton(">");
		btnNext2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				count2++;
				
				int totalMonth = LocalDateTime.now().getMonthValue();
				int totalYear = LocalDateTime.now().getYear();
				
				String oldPage = pageLabel.getText();
				int page = Integer.parseInt(oldPage);
				page++;
				int currentPage = page;
				int total = 15;
				String newPage = String.valueOf(page);
				pageLabel2.setText(newPage);
				int startIndex = (currentPage - 1) * total + 1;
				ArrayList<Salary> salaries = SalaryDAOImpl.getInstance(cp).filterSalaryByTime(totalMonth, currentYear);
				loadAdjustmentData(SalaryDAOImpl.getInstance(cp).paginate(salaries, currentPage, total), startIndex);
			}
		});
		btnNext2.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnNext2.setBounds(691, 3, 52, 23);
		panel_11.add(btnNext2);
		
		JLabel lbTime = new JLabel("tháng");
		lbTime.setBounds(211, 6, 135, 14);
		panel_6.add(lbTime);
		int currentMonth = LocalDateTime.now().getMonthValue();
		lbTime.setText("Tháng " + String.valueOf(currentMonth) + " năm " + String.valueOf(currentYear) );
		
		JPanel PhuCap = new JPanel();
		PhuCap.setBackground(new Color(255, 255, 255));
		tabbedPane.addTab("PHỤ CẤP", null, PhuCap, null);
		tabbedPane.setBackgroundAt(2, new Color(255, 255, 255));
		tabbedPane.setForegroundAt(2, new Color(0, 0, 0));
		PhuCap.setLayout(null);
		
		JPanel panel_7 = new JPanel();
		panel_7.setBounds(0, 0, 771, 479);
		panel_7.setBackground(new Color(255, 255, 255));
		PhuCap.add(panel_7);
		panel_7.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		
		scrollPane_1.setBounds(8, 21, 755, 261);
		panel_7.add(scrollPane_1);
		
		tblPhuCap = new JTable();
		tblPhuCap.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 1) {
					salarySelected = getSalarySelect();
					empIdAllowance.setText(String.valueOf(getSalarySelect().getEmployee().getEmployeeId()));
					txtAllowance.setText(String.valueOf(getSalarySelect().getAllowance()));
					empIdDeduction.setText(String.valueOf(getSalarySelect().getEmployee().getEmployeeId()));
					txtDeduction.setText(String.valueOf(getSalarySelect().getDeduction()));
				}
			}
		});
		scrollPane_1.setViewportView(tblPhuCap);
		
		JPanel panel_11_1_1 = new JPanel();
		panel_11_1_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_11_1_1.setLayout(null);
		panel_11_1_1.setBackground(Color.WHITE);
		panel_11_1_1.setBounds(10, 322, 753, 146);
		panel_7.add(panel_11_1_1);
		
		JLabel lblNewLabel_3_1_1 = new JLabel("Khoản trừ");
		lblNewLabel_3_1_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_3_1_1.setBounds(383, 0, 178, 20);
		panel_11_1_1.add(lblNewLabel_3_1_1);
		
		JLabel lblNewLabel_4_1_1_1_1 = new JLabel("Khoản trừ khác");
		lblNewLabel_4_1_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_4_1_1_1_1.setBounds(394, 82, 97, 11);
		panel_11_1_1.add(lblNewLabel_4_1_1_1_1);
		
		txtDeduction = new JTextField();
		txtDeduction.setColumns(10);
		txtDeduction.setBounds(490, 78, 122, 17);
		panel_11_1_1.add(txtDeduction);
		
		JButton btnNewButton_1_1_1 = new JButton("Xác nhận");
		btnNewButton_1_1_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int id = Integer.parseInt(empIdDeduction.getText());
				double deduction = Double.parseDouble(txtDeduction.getText());
				if(deduction < 0) {
					JOptionPane.showMessageDialog(null, "Khoản trừ phải lớn hơn 0!");
				}else {
					boolean result = SalaryDAOImpl.getInstance(cp).updateDeductionById(id, deduction);
					if(result == true) {
						ArrayList<Salary> salaries = SalaryDAOImpl.getInstance(cp).filterSalaryByTime(currentMonth, currentYear);
						loadAllowanceAndDeductionData(SalaryDAOImpl.getInstance(cp).paginate(salaries, 1, 15), 1);
						JOptionPane.showMessageDialog(null, "Cập nhật thành công!");
					}else {
						 JOptionPane.showMessageDialog(null, "Cập nhật không thành công!");
					}
				}
				                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    
			}
		});
		btnNewButton_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 9));
		btnNewButton_1_1_1.setBackground(new Color(0, 128, 255));
		btnNewButton_1_1_1.setBounds(622, 56, 95, 28);
		panel_11_1_1.add(btnNewButton_1_1_1);
		
		JLabel lblNewLabel_4_1_2_1_1 = new JLabel("Mã NV:");
		lblNewLabel_4_1_2_1_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_4_1_2_1_1.setBounds(393, 42, 52, 11);
		panel_11_1_1.add(lblNewLabel_4_1_2_1_1);
		
		empIdDeduction = new JTextField();
		empIdDeduction.setColumns(10);
		empIdDeduction.setBounds(490, 38, 122, 17);
		panel_11_1_1.add(empIdDeduction);
		
		JLabel lblNewLabel_3_1 = new JLabel("Phụ cấp nhân viên");
		lblNewLabel_3_1.setBounds(0, 0, 178, 20);
		panel_11_1_1.add(lblNewLabel_3_1);
		lblNewLabel_3_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JLabel lblNewLabel_4_1_2_1 = new JLabel("Mã NV:");
		lblNewLabel_4_1_2_1.setBounds(10, 42, 52, 11);
		panel_11_1_1.add(lblNewLabel_4_1_2_1);
		lblNewLabel_4_1_2_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		empIdAllowance = new JTextField();
		empIdAllowance.setBounds(103, 38, 122, 17);
		panel_11_1_1.add(empIdAllowance);
		empIdAllowance.setColumns(10);
		
		JLabel lblNewLabel_4_1_1_1 = new JLabel("Phụ cấp khác");
		lblNewLabel_4_1_1_1.setBounds(10, 82, 97, 11);
		panel_11_1_1.add(lblNewLabel_4_1_1_1);
		lblNewLabel_4_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		txtAllowance = new JTextField();
		txtAllowance.setBounds(103, 78, 122, 17);
		panel_11_1_1.add(txtAllowance);
		txtAllowance.setColumns(10);
		
		JButton btnNewButton_1_1 = new JButton("Xác nhận");
		btnNewButton_1_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int id = Integer.parseInt(empIdAllowance.getText());
				double allowance = Double.parseDouble(txtAllowance.getText());
				if(allowance < 0) {
					JOptionPane.showMessageDialog(null, "Phụ cấp phải lớn hơn 0!");
				}else {
					boolean result = SalaryDAOImpl.getInstance(cp).updateAllowanceById(id, allowance);
					if(result == true) {
						ArrayList<Salary> salaries = SalaryDAOImpl.getInstance(cp).filterSalaryByTime(currentMonth, currentYear);
						loadAllowanceAndDeductionData(SalaryDAOImpl.getInstance(cp).paginate(salaries, 1, 15), 1);
						JOptionPane.showMessageDialog(null, "Cập nhật thành công!");
					}else {
						 JOptionPane.showMessageDialog(null, "Cập nhật không thành công!");
					}
				}
				
			}
		});
		btnNewButton_1_1.setBounds(250, 77, 115, 20);
		panel_11_1_1.add(btnNewButton_1_1);
		btnNewButton_1_1.setFont(new Font("Tahoma", Font.BOLD, 9));
		btnNewButton_1_1.setBackground(new Color(0, 128, 255));
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(375, 11, 10, 104);
		panel_11_1_1.add(separator);
		
		JButton btnEnter = new JButton("Nhập tự động");
		btnEnter.setBackground(new Color(0, 128, 255));
		btnEnter.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				SalaryDAOImpl.getInstance(cp).updateAllowance();
				ArrayList<Salary> salaries = SalaryDAOImpl.getInstance(cp).filterSalaryByTime(currentMonth, currentYear);
				loadAllowanceAndDeductionData(SalaryDAOImpl.getInstance(cp).paginate(salaries, 1, 15), 1);
			}
		});
		btnEnter.setBounds(250, 36, 115, 23);
		panel_11_1_1.add(btnEnter);
		
		JLabel lblNewLabel_5 = new JLabel("Phụ cấp và các khoản trừ");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_5.setBounds(8, 8, 194, 11);
		panel_7.add(lblNewLabel_5);
		
		JLabel lblTime2 = new JLabel("New label");
		lblTime2.setBounds(177, 2, 125, 23);
		panel_7.add(lblTime2);
		lblTime2.setText("Tháng " + String.valueOf(currentMonth) + " năm " + String.valueOf(currentYear) );
		
		JLabel pageLabel3 = new JLabel("1");
		pageLabel3.setBounds(670, 288, 18, 23);
		panel_7.add(pageLabel3);
		
		JButton btnPrev3 = new JButton("<");
		btnPrev3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(count3 == 1) count3 = 1;
				else count3--;
				int totalMonth = LocalDateTime.now().getMonthValue();
				int totalYear = LocalDateTime.now().getYear();				
				String oldPage = pageLabel.getText();
				int page = Integer.parseInt(oldPage);
				if(page == 1) page = 1;
				else page--;
				int currentPage = page;
				int total = 15;
				String newPage = String.valueOf(page);
				pageLabel3.setText(newPage);
				int startIndex = (currentPage - 1) * total + 1;
				ArrayList<Salary> salaries = SalaryDAOImpl.getInstance(cp).filterSalaryByTime(totalMonth, currentYear);
				loadAllowanceAndDeductionData(SalaryDAOImpl.getInstance(cp).paginate(salaries, currentPage, total), startIndex);
			}
		});
		btnPrev3.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnPrev3.setBounds(599, 288, 50, 23);
		panel_7.add(btnPrev3);
		
		
		
		JButton btnNext3 = new JButton(">");
		btnNext3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				count3++;
				
				int totalMonth = LocalDateTime.now().getMonthValue();
				int totalYear = LocalDateTime.now().getYear();
				
				String oldPage = pageLabel.getText();
				int page = Integer.parseInt(oldPage);
				page++;
				int currentPage = page;
				int total = 15;
				String newPage = String.valueOf(page);
				pageLabel3.setText(newPage);
				int startIndex = (currentPage - 1) * total + 1;
				ArrayList<Salary> salaries = SalaryDAOImpl.getInstance(cp).filterSalaryByTime(totalMonth, currentYear);
				loadAllowanceAndDeductionData(SalaryDAOImpl.getInstance(cp).paginate(salaries, currentPage, total), startIndex);
			}
		});
		btnNext3.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnNext3.setBounds(698, 288, 50, 23);
		panel_7.add(btnNext3);
		
		JPanel TangLuong = new JPanel();
		TangLuong.setBackground(new Color(255, 255, 255));
		tabbedPane.addTab("CẬP NHẬT", null, TangLuong, null);
		tabbedPane.setBackgroundAt(3, new Color(255, 255, 255));
		tabbedPane.setForegroundAt(3, new Color(0, 0, 0));
		TangLuong.setLayout(null);
		
		JPanel panel_8 = new JPanel();
		panel_8.setBackground(new Color(255, 255, 255));
		panel_8.setBounds(0, 0, 771, 479);
		TangLuong.add(panel_8);
		panel_8.setLayout(null);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(8, 28, 753, 263);
		panel_8.add(scrollPane_3);
		
		tblLuongCapNhat = new JTable();
		tblLuongCapNhat.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 1) {
					DecimalFormat df = new DecimalFormat("#,##0");
					String formattedSalary = df.format(getSalaryUpdateSelect().getEmployee().getSalary());
					salaryUpdatedSelect = getSalaryUpdateSelect();
					idUpdate.setText(String.valueOf(getSalaryUpdateSelect().getEmployee().getEmployeeId()));
					txtSalaryUpdate.setText(formattedSalary);
				}
			}
		});
		scrollPane_3.setViewportView(tblLuongCapNhat);
		
		JLabel lblNewLabel_6 = new JLabel("Bảng lương nhân viên");
		lblNewLabel_6.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_6.setBounds(8, 9, 170, 21);
		panel_8.add(lblNewLabel_6);
		
		JPanel panel_11_1_2 = new JPanel();
		panel_11_1_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_11_1_2.setLayout(null);
		panel_11_1_2.setBackground(Color.WHITE);
		panel_11_1_2.setBounds(8, 329, 753, 126);
		panel_8.add(panel_11_1_2);
		
		JLabel lblNewLabel_3_1_2 = new JLabel("Điều chỉnh lương");
		lblNewLabel_3_1_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_3_1_2.setBounds(10, 11, 178, 20);
		panel_11_1_2.add(lblNewLabel_3_1_2);
		
		JLabel lblNewLabel_4_1_1_1_2 = new JLabel("Lương điều chỉnh:");
		lblNewLabel_4_1_1_1_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_4_1_1_1_2.setBounds(172, 75, 122, 11);
		panel_11_1_2.add(lblNewLabel_4_1_1_1_2);
		
		txtSalaryUpdate = new JTextField();
		txtSalaryUpdate.setColumns(10);
		txtSalaryUpdate.setBounds(297, 71, 122, 17);
		panel_11_1_2.add(txtSalaryUpdate);
		
		JButton btnNewButton_1_1_2 = new JButton("Xác nhận");
		btnNewButton_1_1_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int id = Integer.parseInt(idUpdate.getText());
				String slr = txtSalaryUpdate.getText();
				slr = slr.replaceAll(",","");
				System.out.print(slr);
				double baseSalary = Double.parseDouble(slr);
				if(baseSalary < 0) {
					JOptionPane.showMessageDialog(null, "Lương cơ bản phải lớn hơn 0!");
				}else {
					boolean result = SalaryDAOImpl.getInstance(cp).updateBaseSalaryById(id, baseSalary);
					if(result == true) {
						JOptionPane.showMessageDialog(null, "Cập nhật thành công!");
						ArrayList<Salary> salaries = SalaryDAOImpl.getInstance(cp).filterSalaryByTime(currentMonth, currentYear);
						loadSalaryUpdateData(SalaryDAOImpl.getInstance(cp).paginate(salaries, 1, 15), 1);				
					}else {
						JOptionPane.showMessageDialog(null, "Cập nhật không thành công!");
					}
				}
				
				
			}
		});
		btnNewButton_1_1_2.setFont(new Font("Tahoma", Font.BOLD, 9));
		btnNewButton_1_1_2.setBackground(new Color(0, 128, 255));
		btnNewButton_1_1_2.setBounds(497, 42, 92, 28);
		panel_11_1_2.add(btnNewButton_1_1_2);
		
		JLabel lblNewLabel_4_1_2_1_2 = new JLabel("Mã NV:");
		lblNewLabel_4_1_2_1_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_4_1_2_1_2.setBounds(172, 42, 52, 11);
		panel_11_1_2.add(lblNewLabel_4_1_2_1_2);
		
		idUpdate = new JTextField();
		idUpdate.setColumns(10);
		idUpdate.setBounds(297, 38, 122, 17);
		panel_11_1_2.add(idUpdate);
		
		JLabel pageLabel4 = new JLabel("1");
		pageLabel4.setBounds(683, 300, 18, 23);
		panel_8.add(pageLabel4);
		
		JButton btnPrev4 = new JButton("<");
		btnPrev4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(count4 == 1) count4 = 1;
				else count4--;
				int totalMonth = LocalDateTime.now().getMonthValue();
				int totalYear = LocalDateTime.now().getYear();				
				String oldPage = pageLabel.getText();
				int page = Integer.parseInt(oldPage);
				if(page == 1) page = 1;
				else page--;
				int currentPage = page;
				int total = 15;
				String newPage = String.valueOf(page);
				pageLabel4.setText(newPage);
				int startIndex = (currentPage - 1) * total + 1;
				ArrayList<Salary> salaries = SalaryDAOImpl.getInstance(cp).filterSalaryByTime(totalMonth, currentYear);
				loadSalaryUpdateData(SalaryDAOImpl.getInstance(cp).paginate(salaries, currentPage, total), startIndex);
			}
		});
		btnPrev4.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnPrev4.setBounds(613, 298, 50, 23);
		panel_8.add(btnPrev4);
		
		JButton btnNext4 = new JButton(">");
		btnNext4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				count4++;
				
				int totalMonth = LocalDateTime.now().getMonthValue();
				int totalYear = LocalDateTime.now().getYear();
				
				String oldPage = pageLabel.getText();
				int page = Integer.parseInt(oldPage);
				page++;
				int currentPage = page;
				int total = 15;
				String newPage = String.valueOf(page);
				pageLabel4.setText(newPage);
				int startIndex = (currentPage - 1) * total + 1;
				ArrayList<Salary> salaries = SalaryDAOImpl.getInstance(cp).filterSalaryByTime(totalMonth, currentYear);
				loadSalaryUpdateData(SalaryDAOImpl.getInstance(cp).paginate(salaries, currentPage, total), startIndex);
			}
		});
		btnNext4.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnNext4.setBounds(711, 298, 50, 23);
		panel_8.add(btnNext4);
		
		JPanel ChotLuong = new JPanel();
		ChotLuong.setBackground(new Color(255, 255, 255));
		tabbedPane.addTab("DỰ KIẾN", null, ChotLuong, null);
		tabbedPane.setBackgroundAt(4, new Color(255, 255, 255));
		tabbedPane.setForegroundAt(4, new Color(0, 0, 0));
		ChotLuong.setLayout(null);
		
		JPanel panel_9 = new JPanel();
		panel_9.setBackground(new Color(255, 255, 255));
		panel_9.setBounds(0, 0, 771, 479);
		ChotLuong.add(panel_9);
		panel_9.setLayout(null);
		
		JScrollPane scrollPane_4 = new JScrollPane();
		scrollPane_4.setBounds(8, 63, 753, 261);
		panel_9.add(scrollPane_4);
		
		tblLuongChot = new JTable();
		scrollPane_4.setViewportView(tblLuongChot);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(8, 8, 753, 47);
		panel_9.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_7 = new JLabel("Bảng lương cuối:");
		lblNewLabel_7.setBounds(8, 8, 125, 23);
		panel.add(lblNewLabel_7);
		lblNewLabel_7.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		JLabel lblTime3 = new JLabel("Tháng 6 năm 2024");
		lblTime3.setBounds(130, 8, 125, 23);
		panel.add(lblTime3);
		lblTime3.setText("Tháng " + String.valueOf(currentMonth) + " năm " + String.valueOf(currentYear) );
		
		JLabel lblNewLabel_1_1 = new JLabel("Tìm kiếm:");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1_1.setBounds(309, 17, 68, 11);
		panel.add(lblNewLabel_1_1);
		
		txtSearch5 = new JTextField();
		txtSearch5.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String key = txtSearch5.getText().trim();
		        int totalMonth;
		        String months = String.valueOf(cbbMonth.getSelectedItem());
				if(months.equals("Tháng")) months = "-1";
				totalMonth = Integer.parseInt(months);
				String years = txtYear.getText();
				int totalYear = Integer.parseInt(years);
		        
		        String oldPage = pageLabel.getText();
				int currentPage = Integer.parseInt(oldPage);
				int total = 15;
				int startIndex = (currentPage - 1) * total + 1;
		        try {
		        	loadSalaryNextMonthData(SalaryDAOImpl.getInstance(cp).filterSalaryPage(totalMonth, totalYear , key, currentPage, total), startIndex);
				} catch (SQLException e1) {
		        } catch (Exception e1) {
		            e1.printStackTrace();
		        }
			}
		});
		txtSearch5.setColumns(10);
		txtSearch5.setBounds(401, 8, 199, 29);
		panel.add(txtSearch5);
		
		JLabel pageLabel5 = new JLabel("1");
		pageLabel5.setBounds(673, 348, 18, 23);
		panel_9.add(pageLabel5);
		
		JButton btnPrev5 = new JButton("<");
		btnPrev5.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(count5 == 1) count5 = 1;
				else count5--;
				int totalMonth = LocalDateTime.now().getMonthValue();
				int totalYear = LocalDateTime.now().getYear();				
				String oldPage = pageLabel.getText();
				int page = Integer.parseInt(oldPage);
				if(page == 1) page = 1;
				else page--;
				int currentPage = page;
				int total = 15;
				String newPage = String.valueOf(page);
				pageLabel5.setText(newPage);
				int startIndex = (currentPage - 1) * total + 1;
				ArrayList<Salary> salaries = SalaryDAOImpl.getInstance(cp).filterSalaryByTime(totalMonth, currentYear);
				loadSalaryNextMonthData(SalaryDAOImpl.getInstance(cp).paginate(salaries, currentPage, total), startIndex);
			}
		});
		btnPrev5.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnPrev5.setBounds(589, 345, 50, 23);
		panel_9.add(btnPrev5);
		
		
		
		JButton btnNext5 = new JButton(">");
		btnNext5.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				count5++;
				
				int totalMonth = LocalDateTime.now().getMonthValue();
				int totalYear = LocalDateTime.now().getYear();
				
				String oldPage = pageLabel.getText();
				int page = Integer.parseInt(oldPage);
				page++;
				int currentPage = page;
				int total = 15;
				String newPage = String.valueOf(page);
				pageLabel5.setText(newPage);
				int startIndex = (currentPage - 1) * total + 1;
				ArrayList<Salary> salaries = SalaryDAOImpl.getInstance(cp).filterSalaryByTime(totalMonth, currentYear);
				loadSalaryNextMonthData(SalaryDAOImpl.getInstance(cp).paginate(salaries, currentPage, total), startIndex);
			}
		});
		btnNext5.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnNext5.setBounds(711, 345, 50, 23);
		panel_9.add(btnNext5);
		
		search.addKeyListener(new KeyAdapter() {
		    @Override
		    public void keyReleased(KeyEvent e) {
		        String key = search.getText().trim();
		        int totalMonth;
		        String months = String.valueOf(cbbMonth.getSelectedItem());
				if(months.equals("Tháng")) months = "-1";
				totalMonth = Integer.parseInt(months);
				String years = txtYear.getText();
				int totalYear = Integer.parseInt(years);
		        
		        String oldPage = pageLabel.getText();
				int currentPage = Integer.parseInt(oldPage);
				int total = 15;
				int startIndex = (currentPage - 1) * total + 1;
		        try {
		        	loadSalaryData(SalaryDAOImpl.getInstance(cp).filterSalaryPage(totalMonth, totalYear , key, currentPage, total), startIndex);
				} catch (SQLException e1) {
		        } catch (Exception e1) {
		            e1.printStackTrace();
		        }
		    }
		});

		btnPrev.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(count == 1) count = 1;
				else count--;
				
				String key = search.getText();
				String months = String.valueOf(cbbMonth.getSelectedItem());
				if(months.equals("Tháng")) months = "-1";
				int totalMonth = Integer.parseInt(months);
				String years = txtYear.getText();
				int totalYear = Integer.parseInt(years);
				
				String oldPage = pageLabel.getText();
				int page = Integer.parseInt(oldPage);
				if(page == 1) page = 1;
				else page--;
				int currentPage = page;
				int total = 15;
				String newPage = String.valueOf(page);
				pageLabel.setText(newPage);
				int startIndex = (currentPage - 1) * total + 1;
				if(!key.equalsIgnoreCase("")) {
					try {
						loadSalaryData(SalaryDAOImpl.getInstance(cp).filterSalaryPage(totalMonth, totalYear , key, currentPage, total), startIndex);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else {
					ArrayList<Salary> salaryPrev = SalaryDAOImpl.getInstance(cp).filterSalaryTimeBelow(currentMonth, currentYear);
					loadSalaryData(SalaryDAOImpl.getInstance(cp).paginate(salaryPrev, currentPage, total), 1);
				}
			}
		});
		
		btnNext.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				count++;
				
				String key = search.getText();
				String months = String.valueOf(cbbMonth.getSelectedItem());
				if(months.equals("Tháng")) months = "-1";
				int totalMonth = Integer.parseInt(months);
				String years = txtYear.getText();
				int totalYear = Integer.parseInt(years);
				
				String oldPage = pageLabel.getText();
				int page = Integer.parseInt(oldPage);
				page++;
				int currentPage = page;
				int total = 15;
				String newPage = String.valueOf(page);
				pageLabel.setText(newPage);
				int startIndex = (currentPage - 1) * total + 1;
				if(!key.equalsIgnoreCase("")) {
					try {
						loadSalaryData(SalaryDAOImpl.getInstance(cp).filterSalaryPage(totalMonth, totalYear , key, currentPage, total), startIndex);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else {
					ArrayList<Salary> salaryPrev = SalaryDAOImpl.getInstance(cp).filterSalaryTimeBelow(currentMonth, currentYear);
					loadSalaryData(SalaryDAOImpl.getInstance(cp).paginate(salaryPrev, currentPage, total), 1);
				}
			}
		});
		
		setWidthTable();
		ArrayList<Salary> salaryPrev = SalaryDAOImpl.getInstance(cp).filterSalaryTimeBelow(currentMonth, currentYear);
		loadSalaryData(SalaryDAOImpl.getInstance(cp).paginate(salaryPrev, 1, 15), 1);
	
		setWidthTable2();
		ArrayList<Salary> salaries = SalaryDAOImpl.getInstance(cp).filterSalaryByTime(currentMonth, currentYear);
		System.out.print(currentMonth + " " + currentYear);
		loadAdjustmentData(SalaryDAOImpl.getInstance(cp).paginate(salaries, 1, 15), 1);
		
		setWidthTable3();
		loadAllowanceAndDeductionData(SalaryDAOImpl.getInstance(cp).paginate(salaries, 1, 15), 1);
		
		setWidthTable4();
		loadSalaryUpdateData(SalaryDAOImpl.getInstance(cp).paginate(salaries, 1, 15), 1);
		
		setWidthTable5();
		loadSalaryNextMonthData(SalaryDAOImpl.getInstance(cp).paginate(salaries, 1, 15), 1);
	}
	
	
	public void setWidthTable() {
		model = new DefaultTableModel();
        String[] headerTbl = new String[]{"STT", "Tên nhân viên", "Thời gian",
        		"Lương cơ bản", "Phụ cấp", "Khoản trừ", "Thưởng", "Lương thực lãnh"};
        model.setColumnIdentifiers(headerTbl);
        tblLuong.setModel(model);
        TableColumnModel columnModel = tblLuong.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);  // STT
        columnModel.getColumn(1).setPreferredWidth(150); // Tên nhân viên
        columnModel.getColumn(2).setPreferredWidth(100); // Thời gian
        columnModel.getColumn(3).setPreferredWidth(100); // Lương cơ bản
        columnModel.getColumn(4).setPreferredWidth(100); // Phụ cấp
        columnModel.getColumn(5).setPreferredWidth(100); // Phạt
        columnModel.getColumn(6).setPreferredWidth(100); // Thưởng
        columnModel.getColumn(7).setPreferredWidth(150); // Lương thực lãnh
    }
	
	public void setWidthTable2() {
		model2 = new DefaultTableModel();
        String[] headerTbl = new String[]{"STT", "Nhân viên", "Thời gian",
        		"Lương cơ bản", "%Lương thưởng", "Lương Thưởng"};
        model2.setColumnIdentifiers(headerTbl);
        tblLuongthuong.setModel(model2);
        TableColumnModel columnModel = tblLuongthuong.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(30);  // STT
        columnModel.getColumn(1).setPreferredWidth(150); // Tên nhân viên
        columnModel.getColumn(2).setPreferredWidth(100); // Thời gian
        columnModel.getColumn(3).setPreferredWidth(100); // Lương cơ bản
        columnModel.getColumn(4).setPreferredWidth(100); // % Thưởng
        columnModel.getColumn(5).setPreferredWidth(150); // Lương thưởng
    }
	
	public void setWidthTable3() {
		model3 = new DefaultTableModel();
        String[] headerTbl = new String[]{"STT", "Nhân viên", "Thời gian",
        		"Phụ cấp", "Khoản trừ"};
        model3.setColumnIdentifiers(headerTbl);
        tblPhuCap.setModel(model3);
        TableColumnModel columnModel = tblPhuCap.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(30);  
        columnModel.getColumn(1).setPreferredWidth(150); 
        columnModel.getColumn(2).setPreferredWidth(100); 
        columnModel.getColumn(3).setPreferredWidth(100); 
        columnModel.getColumn(4).setPreferredWidth(100); 

    }
	
	public void setWidthTable4() {
		model4 = new DefaultTableModel();
        String[] headerTbl = new String[]{"STT", "Nhân viên", "Luơng cơ bản"};
        model4.setColumnIdentifiers(headerTbl);
        tblLuongCapNhat.setModel(model4);
        TableColumnModel columnModel = tblLuongCapNhat.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(30);  
        columnModel.getColumn(1).setPreferredWidth(250); 
        columnModel.getColumn(2).setPreferredWidth(200); 

    }
	
	public void setWidthTable5() {
		model5 = new DefaultTableModel();
        String[] headerTbl = new String[]{"STT", "Tên nhân viên", "Thời gian",
        		"Lương cơ bản", "Phụ cấp", "Khoản trừ", "Thưởng", "Lương thực lãnh"};
        model5.setColumnIdentifiers(headerTbl);
        tblLuongChot.setModel(model5);
        TableColumnModel columnModel = tblLuongChot.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);  // STT
        columnModel.getColumn(1).setPreferredWidth(150); // Tên nhân viên
        columnModel.getColumn(2).setPreferredWidth(100); // Thời gian
        columnModel.getColumn(3).setPreferredWidth(100); // Lương cơ bản
        columnModel.getColumn(4).setPreferredWidth(100); // Phụ cấp
        columnModel.getColumn(5).setPreferredWidth(100); // Phạt
        columnModel.getColumn(6).setPreferredWidth(100); // Thưởng
        columnModel.getColumn(7).setPreferredWidth(150); // Lương thực lãnh
    }

    
	
	private void loadSalaryData(ArrayList<Salary> salaries, int startIndex) {
	    try {
	        model.setRowCount(0);    
	        int i = startIndex;
	        DecimalFormat df = new DecimalFormat("#,##0");
	        int currentMonth = LocalDateTime.now().getMonthValue();
	        for (Salary s : salaries) {
	            String date = String.valueOf(s.getMonth()) + " / " + String.valueOf(s.getYear());
	            String formattedSalary = df.format(s.getBaseSalary());
	            String formattedAllowance = df.format(s.getAllowance());
	            String formattedDeduction = df.format(s.getDeduction());
	            String formattedAdjustment = df.format(s.getAdjustment() / 100 * s.getBaseSalary());
	            String formattedTotalSalary = df.format(s.calTotalSalary(
	                s.getBaseSalary(),
	                s.getAllowance(),
	                s.getDeduction(),
	                s.getAdjustment()
	            ));	 
	            if(s.getMonth() < currentMonth) {
	            	model.addRow(new Object[]{
	    	                i++, s.getEmployee().getEmployeeId() + " - "+ s.getEmployee().getName(), date, formattedSalary,
	    	                formattedAllowance, formattedDeduction, formattedAdjustment, 
	    	                formattedTotalSalary
	    	            });
	            }
	            
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	
	private void loadSalaryNextMonthData(ArrayList<Salary> salaries, int startIndex) {
	    try {
	        model5.setRowCount(0);    
	        int i = startIndex;
	        DecimalFormat df = new DecimalFormat("#,##0");
	        int currentMonth = LocalDateTime.now().getMonthValue();
	        int currentYear = LocalDateTime.now().getYear();
	        ArrayList<Employee> ems = employeeDAOImpl.getInstance(cp).findAll();
	        for(Employee e : ems) {
	        	Salary s = new Salary();
	        	s.setMonth(currentMonth);
	        	s.setYear(currentYear);
	        	s.setBaseSalary(e.getSalary());
	        	s.setAllowance(0);
	        	s.setDeduction(0);
	        	s.setAdjustment(0);
	        	s.setTotalSalary(e.getSalary());
	        	s.setEmployee(e);
	        	s.setDepartment(e.getDepartment());
	        	s.setPosition(e.getPosition());
//	        	boolean isExist = SalaryDAOImpl.getInstance(cp).checkExist(s, currentMonth);
//	        	if(!isExist) {
//	        		SalaryDAOImpl.getInstance(cp).insert(s);
//	        	}
	        	
	        }
	        for (Salary s : salaries) {
	            String date = String.valueOf(s.getMonth()) + " / " + String.valueOf(s.getYear());
	            String formattedSalary = df.format(s.getBaseSalary());
	            String formattedAllowance = df.format(s.getAllowance());
	            String formattedDeduction = df.format(s.getDeduction());
	            String formattedAdjustment = df.format(s.getAdjustment() / 100 * s.getBaseSalary());
	            String formattedTotalSalary = df.format(s.calTotalSalary(
	            		s.getBaseSalary(),
	                s.getAllowance(),
	                s.getDeduction(),
	                s.getAdjustment()
	            ));	 
            	model5.addRow(new Object[]{
    	                i++, s.getEmployee().getEmployeeId() + " - "+ s.getEmployee().getName(), date, formattedSalary,
    	                formattedAllowance, formattedDeduction, formattedAdjustment, 
    	                formattedTotalSalary
    	            });
	            
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	private void loadAllowanceAndDeductionData(ArrayList<Salary> salaries, int startIndex) {
	    try {
	        model3.setRowCount(0);    
	        int i = startIndex;
	        DecimalFormat df = new DecimalFormat("#,##0");
	        int currentMonth = LocalDateTime.now().getMonthValue();
	        
//	        "STT", "Nhân viên", "Thời gian",
//    		"Phụ cấp", "Khoản trừ"
	        for (Salary s : salaries) {
	            String date = String.valueOf(s.getMonth()) + " / " + String.valueOf(s.getYear());
	            String formattedAllowance = df.format(s.getAllowance());
	            String formattedDeduction = df.format(s.getDeduction());
            	model3.addRow(new Object[]{
    	                i++, s.getEmployee().getEmployeeId() + " - "+ s.getEmployee().getName(), date,
    	                formattedAllowance, formattedDeduction
    	            });
	            
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	private void openFile(String file) {
        try {
            File path = new File(file);
            Desktop.getDesktop().open(path);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
	
	private void loadAdjustmentData(ArrayList<Salary> salaries, int startIndex) {
	    try {
	        model2.setRowCount(0);    
	        int i = startIndex;
	        DecimalFormat df = new DecimalFormat("#,##0");
	        int currentMonth = LocalDateTime.now().getMonthValue();
	        for (Salary s : salaries) {
	            String date = String.valueOf(s.getMonth()) + " / " + String.valueOf(s.getYear());
	            String formattedSalary = df.format(s.getBaseSalary());
	            String formattedAdjustment = df.format(s.getAdjustment());
	            String formattedTotalSalary = df.format(s.calTotalSalary(
	            		s.getBaseSalary(),
	                s.getAllowance(),
	                s.getDeduction(),
	                s.getAdjustment()
	            ));
	            
	            if(s.getMonth() == currentMonth) {
		            model2.addRow(new Object[]{
		                i++, s.getEmployee().getEmployeeId() + " - "+ s.getEmployee().getName(), date, formattedSalary,
		                formattedAdjustment, 
		                formattedTotalSalary
		            });
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	private void loadSalaryUpdateData(ArrayList<Salary> salaries, int startIndex) {
	    try {
	        model4.setRowCount(0);    
	        int i = startIndex;
	        DecimalFormat df = new DecimalFormat("#,##0");
	        int currentMonth = LocalDateTime.now().getMonthValue();
//	        "STT", "Nhân viên", "Luơng cơ bản"
	        for (Salary s : salaries) {
	            String formattedSalary = df.format(s.getBaseSalary());
            	model4.addRow(new Object[]{
    	                i++, s.getEmployee().getEmployeeId() + " - "+ s.getEmployee().getName(), 
    	                formattedSalary,

    	            });
	            
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public Salary getSalarySelect() {
		int currentMonth = LocalDateTime.now().getMonthValue();
		int currentYear = LocalDateTime.now().getYear();
		int i_row = tblPhuCap.getSelectedRow();
		ArrayList<Salary> salaries = SalaryDAOImpl.getInstance(cp).filterSalaryByTime(currentMonth, currentYear);
		Salary s = SalaryDAOImpl.getInstance(cp).paginate(salaries, 1, 15).get(i_row);
		return s;
	}
	
	public Salary getSalaryUpdateSelect() {
		int currentMonth = LocalDateTime.now().getMonthValue();
		int currentYear = LocalDateTime.now().getYear();
		int i_row = tblLuongCapNhat.getSelectedRow();
		ArrayList<Salary> salaries = SalaryDAOImpl.getInstance(cp).filterSalaryByTime(currentMonth, currentYear);
		Salary s = SalaryDAOImpl.getInstance(cp).paginate(salaries, 1, 15).get(i_row);
		return s;
	}
}
