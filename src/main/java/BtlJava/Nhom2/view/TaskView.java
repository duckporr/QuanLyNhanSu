package BtlJava.Nhom2.view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;

import BtlJava.Nhom2.dao.GroupDAOImpl;
import BtlJava.Nhom2.dao.TaskDAOImpl;
import BtlJava.Nhom2.database.ConnectionPool;
import BtlJava.Nhom2.database.ConnectionPoolImpl;
import BtlJava.Nhom2.model.ExcelExporter;
import BtlJava.Nhom2.model.Group;
import BtlJava.Nhom2.model.Task;
import com.toedter.calendar.JDayChooser;
import com.toedter.calendar.JDateChooser;

public class TaskView extends JInternalFrame {

    private ConnectionPool cp = new ConnectionPoolImpl();
	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private JTextField txtTitle;
	private JTextField txtDescription;
	private JDateChooser txtDueDate;
	private JTextField txtPriority;
	private JComboBox txtStatus;
	private JTable table;
	private DefaultTableModel tableModel;
	private JTable ListTask;
	private int selectedRow = -1;
	private JTextField txtTimKiem;
	private JComboBox txtPage;
	public static int page =1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TaskView frame = new TaskView();
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
	public TaskView() {

		setBorder(null);
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);
        setBounds(0, 0, 774, 533);
		getContentPane().setLayout(null);
       
		panel = new JPanel();
        panel.setBounds(0, 0, 774, 506);
        getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Quản lý Task");
		lblNewLabel.setBounds(74, 9, 273, 50);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Title");
		lblNewLabel_1.setBounds(54, 120, 75, 16);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel.add(lblNewLabel_1);
		
		txtTitle = new JTextField();
		txtTitle.setBounds(180, 117, 134, 28);
		panel.add(txtTitle);
		txtTitle.setColumns(10);
		
		JLabel lblNewLabel_1_1 = new JLabel("Mô tả");
		lblNewLabel_1_1.setBounds(54, 170, 98, 16);
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel.add(lblNewLabel_1_1);
		
		txtDescription = new JTextField();
		txtDescription.setBounds(180, 167, 134, 28);
		txtDescription.setColumns(10);
		panel.add(txtDescription);
		
		txtDueDate = new JDateChooser();
		txtDueDate.setBounds(180, 216, 134, 28);
		panel.add(txtDueDate);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Ngày đáo hạn");
		lblNewLabel_1_1_1.setBounds(54, 223, 98, 16);
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel.add(lblNewLabel_1_1_1);
		
		JLabel lblNewLabel_1_1_1_1 = new JLabel("Priority");
		lblNewLabel_1_1_1_1.setBounds(54, 270, 98, 16);
		lblNewLabel_1_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel.add(lblNewLabel_1_1_1_1);
		
		txtPriority = new JTextField();
		txtPriority.setBounds(180, 267, 134, 28);
		txtPriority.setColumns(10);
		panel.add(txtPriority);
		String[] status = {"Hiệu lực","Hủy"};
		txtStatus = new JComboBox(status);
		txtStatus.setBounds(180, 315, 134, 28);
		panel.add(txtStatus);
		
		JLabel lblNewLabel_2 = new JLabel("Trạng thái");
		lblNewLabel_2.setBounds(54, 315, 98, 20);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel.add(lblNewLabel_2);
		
		JButton btnThem = new JButton("Thêm");
		btnThem.setBounds(54, 374, 98, 28);
		btnThem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String title = txtTitle.getText();
                String desciprtion = txtDescription.getText();
                Date dueDate = txtDueDate.getDate();
                String priority = txtPriority.getText();
                String status = (String)txtStatus.getSelectedItem();
                JOptionPane optionPane = new JOptionPane();
                if (title.isEmpty() || desciprtion.isEmpty() || priority.isEmpty()) {
                    optionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Task task = new Task(title, desciprtion, dueDate, priority,status);
                
                boolean result = TaskDAOImpl.getInstance(cp).insert(task);

                if (result == true) {
                	optionPane.showMessageDialog(null, "Tạo task thànhg công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                	loadTaskList(page);
                	return;
                } else {
                	optionPane.showMessageDialog(null, "Tạo task thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                	return;
                } 
			}
		});
		btnThem.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel.add(btnThem);
		
		JButton btnSua = new JButton("Sửa");
		btnSua.setBounds(162, 374, 116, 28);
		btnSua.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn Task để sửa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String title = txtTitle.getText();
                String desciprtion = txtDescription.getText();
                Date dueDate = txtDueDate.getDate();
                String priority = txtPriority.getText();
                String status = (String)txtStatus.getSelectedItem();
                if (title.isEmpty() || desciprtion.isEmpty() || priority.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Task task = new Task(title, desciprtion, dueDate, priority,status);
                
                int taskId = (int) tableModel.getValueAt(selectedRow, 0); // Lấy taskId từ cột STT
                TaskDAOImpl.getInstance(cp).updateById(taskId, task);
                boolean result = TaskDAOImpl.getInstance(cp).updateById(taskId, task);

                if (result == true) {
                    JOptionPane.showMessageDialog(null, "Cập nhật Task thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    loadTaskList(page); 
                    clearInputFields();
                    return;
                } else  {
                    JOptionPane.showMessageDialog(null, "Cập nhật Task thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }	
            }
		});
		btnSua.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel.add(btnSua);
		
		JButton btnXoa = new JButton("Xóa");
		btnXoa.setBounds(54, 416, 98, 28);
		btnXoa.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
	                if (selectedRow == -1) {
	                    JOptionPane.showMessageDialog(null, "Vui lòng chọn Task để xóa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
	                    return;
	                }

	                int taskId = (int) tableModel.getValueAt(selectedRow, 0); 

	                int result = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa người dùng này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
	                if (result == JOptionPane.YES_OPTION) {
	                  
	                    boolean deleteResult = TaskDAOImpl.getInstance(cp).deleteById(taskId);

	                    if (deleteResult == true) {
	                        JOptionPane.showMessageDialog(null, "Xóa Task thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
	                        loadTaskList(page);
	                        clearInputFields();
	                    } else {
	                        JOptionPane.showMessageDialog(null, "Xóa Task thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
	                    }
	                }
	            }
		});
		btnXoa.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel.add(btnXoa);
		

		tableModel = new DefaultTableModel();
		
		ListTask = new JTable(tableModel); 
        JScrollPane pane = new JScrollPane(ListTask);
        pane.setBounds(347, 122, 417, 150);
        panel.add(pane);
        
        
        txtTimKiem = new JTextField();
        txtTimKiem.setBounds(347, 58, 273, 28);
        txtTimKiem.setColumns(10);
        panel.add(txtTimKiem);
        
        JButton btnTimKiem = new JButton("Tìm kiếm");
        btnTimKiem.setBounds(642, 55, 122, 31);
        btnTimKiem.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                String titleToFind = txtTimKiem.getText(); // Lấy tên từ txtTimKiem
                if (!titleToFind.isEmpty()) {
                	
                    Task foundTask = TaskDAOImpl.getInstance(cp).findByName(titleToFind);

                    if (foundTask != null) {
                        // Cập nhật JTable với thông tin người dùng tìm được
                        tableModel.setRowCount(0);
                        tableModel.addRow(new Object[] {
                            1,
                            foundTask.getTitle(),
                            foundTask.getDescription(),
                            foundTask.getDueDate(),
                            foundTask.getPriority(),
                            foundTask.getStatus()
                        });
                        ListTask.setModel(tableModel);
                    } else {
                        JOptionPane.showMessageDialog(null, "Không tìm thấy Task với tên này!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });
        btnTimKiem.setFont(new Font("Tahoma", Font.PLAIN, 16));
        panel.add(btnTimKiem);
        
        JButton btnLamMoi = new JButton("Làm mới");
        btnLamMoi.setBounds(831, 55, 103, 31);
        btnLamMoi.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		loadTaskList(page);
        	}
        });
        btnLamMoi.setFont(new Font("Tahoma", Font.PLAIN, 16));
        panel.add(btnLamMoi);
        
        JButton btnExcel = new JButton("Xuất Excel");
        btnExcel.setBounds(162, 415, 116, 31);
        btnExcel.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		 try {
                     ExcelExporter exp = new ExcelExporter();
                     exp.fillData(ListTask, new File("D:\\taskresult.xls"));
                     JOptionPane.showMessageDialog(null, "Data saved at " +
                             "'D: \\ taskresult.xls' successfully", "Message",
                             JOptionPane.INFORMATION_MESSAGE);
                 } catch (Exception ex) {
                     ex.printStackTrace();
                 }
        	}
        });
        btnExcel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        panel.add(btnExcel);
 
        String[] pages = new String[TaskDAOImpl.getInstance(cp).findAll().size()/10+1];
		for(int i=0;i<TaskDAOImpl.getInstance(cp).findAll().size()/10+1;i++)
			pages[i]=String.valueOf(i+1);
		txtPage = new JComboBox(pages);
		txtPage.setBounds(347, 315, 103, 28);
		txtPage.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	 page=	Integer.valueOf((String)txtPage.getSelectedItem());
        	 loadTaskList(page);
		}
		});
		panel.add(txtPage);
        
        tableModel.addColumn("STT");
        tableModel.addColumn("Tiêu đề");
        tableModel.addColumn("Mô tả");
        tableModel.addColumn("Ngày đáo hạn");
        tableModel.addColumn("Priority");
        tableModel.addColumn("Trạng thái");
        loadTaskList(page);
        
        ListTask.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                selectedRow = ListTask.getSelectedRow();
                if (selectedRow != -1) {
                    txtTitle.setText((String) tableModel.getValueAt(selectedRow, 1));
                    txtDescription.setText((String) tableModel.getValueAt(selectedRow, 2));
                    txtDueDate.setDate((Date)tableModel.getValueAt(selectedRow, 3));
                    txtPriority.setText((String) tableModel.getValueAt(selectedRow, 4));
                    txtStatus.setSelectedItem((int)(tableModel.getValueAt(selectedRow, 5))==1?"Hiệu lực" : "Hủy");
                }
            }
        });
	}
	private void loadTaskList(int page) {
      
        ArrayList<Task> taskList = TaskDAOImpl.getInstance(cp).findAll();
        txtPage.setSelectedIndex(page-1);
        
        tableModel.setRowCount(0); // Xóa hết dữ liệu hiện tại
       
        if(page==TaskDAOImpl.getInstance(cp).findAll().size()/10+1)
        {
        	for (int i = (page-1)*10+1; i <=TaskDAOImpl.getInstance(cp).findAll().size() ; i++) {
                Task task = taskList.get(i-1);
                tableModel.addRow(new Object[] {
                		task.getTaskId(), 
                    task.getTitle(), 
                    task.getDescription(),
                    task.getDueDate(),
                    task.getPriority(),
                    task.getStatus()
                   
                });
        }
        }
        else
        {
        	for (int i = (page-1)*10+1; i <=page*10; i++) {
        		Task task = taskList.get(i-1);
                tableModel.addRow(new Object[] {
                    task.getTaskId(), 
                    task.getTitle(), 
                    task.getDescription(),
                    task.getDueDate(),
                    task.getPriority(),
                    task.getStatus()
                   
                });
            }  
        }
       
        ListTask.setModel(tableModel);
    }
	
	private void clearInputFields() {
        txtTitle.setText("");
        txtDescription.setText("");
         txtPriority.setText("");
        selectedRow = -1; 
    }
}

