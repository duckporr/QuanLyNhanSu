package BtlJava.Nhom2.view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
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
import BtlJava.Nhom2.dao.PositionDAOImpl;
import BtlJava.Nhom2.database.ConnectionPool;
import BtlJava.Nhom2.database.ConnectionPoolImpl;
import BtlJava.Nhom2.model.ExcelExporter;
import BtlJava.Nhom2.model.Group;

public class GroupView extends JInternalFrame {

    private ConnectionPool cp = new ConnectionPoolImpl();
	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private JTextField txtName;
	private JTextField txtDescription;
	private DefaultTableModel tableModel;
	private JTable ListGroup;
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
                    GroupView frame = new GroupView();
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
	public GroupView() {
        setBorder(null);
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);
        setBounds(0, 0, 774, 533);
		getContentPane().setLayout(null);
       
		panel = new JPanel();
        panel.setBounds(0, 0, 774, 506);
        getContentPane().add(panel);
		panel.setLayout(null);

		
		JLabel lblNewLabel = new JLabel("Quản lý Group");
		lblNewLabel.setBounds(74, 9, 273, 50);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Tên group");
		lblNewLabel_1.setBounds(54, 120, 75, 16);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel.add(lblNewLabel_1);
		
		txtName = new JTextField();
		txtName.setBounds(180, 117, 113, 28);
		panel.add(txtName);
		txtName.setColumns(10);
		
		JLabel lblNewLabel_1_1 = new JLabel("Mô tả");
		lblNewLabel_1_1.setBounds(54, 170, 98, 16);
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel.add(lblNewLabel_1_1);
		
		txtDescription = new JTextField();
		txtDescription.setBounds(180, 167, 113, 28);
		txtDescription.setColumns(10);
		panel.add(txtDescription);
		
		
		JButton btnThem = new JButton("Thêm");
		btnThem.setBounds(54, 374, 98, 28);
		btnThem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = txtName.getText();
                String description = txtDescription.getText();
            
                JOptionPane optionPane = new JOptionPane();
                if (name.isEmpty() || description.isEmpty() ) {
                    optionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Group group = new Group(name, description);
                boolean result = GroupDAOImpl.getInstance(cp).insert(group);
            

                if (result == true) {
                	optionPane.showMessageDialog(null, "Tạo group thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                	loadGroupList(page);
                	return;
                } else  {
                	optionPane.showMessageDialog(null, "Tạo group thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                	return;
                }
             
			}
		});
		btnThem.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel.add(btnThem);
		
		JButton btnSua = new JButton("Sửa");
		btnSua.setBounds(162, 374, 113, 28);
		btnSua.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn group để sửa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String name = txtName.getText();
                String description = txtDescription.getText();
               
                if (name.isEmpty() || description.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Group group = new Group(name, description);
                
                int groupId = (int) tableModel.getValueAt(selectedRow, 0); // Lấy groupId từ cột STT
                
                GroupDAOImpl.getInstance(cp).updateById(groupId, group);
                boolean result = GroupDAOImpl.getInstance(cp).updateById(groupId, group);

                if (result == true) {
                    JOptionPane.showMessageDialog(null, "Cập nhật group thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    loadGroupList(page);
                    clearInputFields();
                    return;
                } else  {
                    JOptionPane.showMessageDialog(null, "Cập nhật group thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
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
	                    JOptionPane.showMessageDialog(null, "Vui lòng chọn group để xóa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
	                    return;
	                }

	                int groupId = (int) tableModel.getValueAt(selectedRow, 0); 

	                int result = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa group này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
	                if (result == JOptionPane.YES_OPTION) {
	                    boolean deleteResult = GroupDAOImpl.getInstance(cp).deleteById(groupId);

	                    if (deleteResult ==true) {
	                        JOptionPane.showMessageDialog(null, "Xóa group thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
	                        loadGroupList(page);
	                        clearInputFields();
	                    } else {
	                        JOptionPane.showMessageDialog(null, "Xóa group thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
	                    }
	                }
	            }
		});
		btnXoa.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel.add(btnXoa);
		

		tableModel = new DefaultTableModel();
		ListGroup = new JTable(tableModel); 
        JScrollPane pane = new JScrollPane(ListGroup);
        pane.setBounds(322, 122, 442, 150);
        panel.add(pane);
       
        txtTimKiem = new JTextField();
        txtTimKiem.setBounds(322, 58, 294, 28);
        txtTimKiem.setColumns(10);
        panel.add(txtTimKiem);
        
        JButton btnTimKiem = new JButton("Tìm kiếm");
        btnTimKiem.setBounds(642, 55, 122, 31);
        btnTimKiem.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                String nameToFind = txtTimKiem.getText(); // Lấy tên từ txtTimKiem
                if (!nameToFind.isEmpty()) {
                    Group foundGroup =GroupDAOImpl.getInstance(cp).findByName(nameToFind);

                    if (foundGroup != null) {
                 
                        tableModel.setRowCount(0);
                        tableModel.addRow(new Object[] {
                            1,
                            foundGroup.getName(),
                            foundGroup.getDescription(),
      
                        });
                        ListGroup.setModel(tableModel);
                    } else {
                        JOptionPane.showMessageDialog(null, "Không tìm thấy group với tên này!", "Thông báo", JOptionPane.WARNING_MESSAGE);
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
        		loadGroupList(page);
        	}
        });
        btnLamMoi.setFont(new Font("Tahoma", Font.PLAIN, 16));
        panel.add(btnLamMoi);
        
        JButton btnExcel = new JButton("Xuất Excel");
        btnExcel.setBounds(162, 415, 113, 31);
        btnExcel.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		 try {
                     ExcelExporter exp = new ExcelExporter();
                     
         
                     ArrayList<Group> groupList = GroupDAOImpl.getInstance(cp).findAll();
                     String stringGroup[][] = new String[groupList.size()][3];
                     for(int i=0;i<groupList.size();++i)
                    	 {
                    	 stringGroup[i][0]=String.valueOf(groupList.get(i).getGroupId());
                    	 stringGroup[i][1]=groupList.get(i).getName();
                    	 stringGroup[i][2]=groupList.get(i).getDescription();
                    	 }
                    	 
                     JTable excelTable = new JTable(stringGroup,new String[] {"STT","Tên","Mô tả"});
                     exp.fillData(excelTable, new File("D:\\groupresult.xls"));
                     JOptionPane.showMessageDialog(null, "Data saved at " +
                             "'D: \\ groupresult.xls' successfully", "Message",
                             JOptionPane.INFORMATION_MESSAGE);
                 } catch (Exception ex) {
                     ex.printStackTrace();
                 }
        	}
        });
        btnExcel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        panel.add(btnExcel);
        
        
       
        
        String[] pages = new String[GroupDAOImpl.getInstance(cp).findAll().size()/10+1];
		for(int i=0;i<GroupDAOImpl.getInstance(cp).findAll().size()/10+1;i++)
			pages[i]=String.valueOf(i+1);
		txtPage = new JComboBox(pages);
		txtPage.setBounds(438, 315, 98, 28);
		txtPage.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	 page=	Integer.valueOf((String)txtPage.getSelectedItem());
        	 loadGroupList(page);
		}
		});
		panel.add(txtPage);
        
        tableModel.addColumn("STT");
        tableModel.addColumn("Tên");
        tableModel.addColumn("Mô tả");
        
      
        loadGroupList(page);
        
        ListGroup.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                selectedRow = ListGroup.getSelectedRow();
                if (selectedRow != -1) {
                    txtName.setText((String) tableModel.getValueAt(selectedRow, 1));
                    txtDescription.setText((String) tableModel.getValueAt(selectedRow, 2));
                    
                }
            }
        });
      	
	}

	private void loadGroupList(int page) {
        ArrayList<Group> groupList = GroupDAOImpl.getInstance(cp).findAll();
       
        txtPage.setSelectedIndex(page-1);
        tableModel.setRowCount(0); // Xóa hết dữ liệu hiện tại
        
        if(page==GroupDAOImpl.getInstance(cp).findAll().size()/10+1)
        {
        	for (int i = (page-1)*10+1; i <=GroupDAOImpl.getInstance(cp).findAll().size() ; i++) {
                Group group = groupList.get(i-1);
                tableModel.addRow(new Object[] {
                    group.getGroupId(), 
                    group.getName(), 
                    group.getDescription(),
                   
                });
        }
        }
        else
        {
        	for (int i = (page-1)*10+1; i <= page*10; i++) {
                Group group = groupList.get(i-1);
                tableModel.addRow(new Object[] {
                		group.getGroupId(), 
                    group.getName(), 
                    group.getDescription(),
                   
                });
            }  
        }
        
             	        
        
                 
                 
        ListGroup.setModel(tableModel);
	}
	
	private void clearInputFields() {
        txtName.setText("");
        txtDescription.setText("");
       
        selectedRow = -1; 
    }
	
	

}

