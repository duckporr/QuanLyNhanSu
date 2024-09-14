package BtlJava.Nhom2.view;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Desktop;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.mysql.cj.x.protobuf.MysqlxCrud.Update;

import BtlJava.Nhom2.dao.UserDAOImpl;
import BtlJava.Nhom2.model.User;
import BtlJava.Nhom2.view.AddUser;
import BtlJava.Nhom2.view.UpdateUser;

import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

public class UserView2 extends JInternalFrame {

	private static final long serialVersionUID = 1L;
    private JTextField txtTimKiem;
    private JTable tblUser;
    private AddUser addUser;
    UserDAOImpl userDAO = new UserDAOImpl(); 
	private DefaultTableModel tableModel;
	private int selectedRow = -1;
	private JTable ListUser;
	private UpdateUser updateUser;

    /**
     * Launch the application.
     */
	public void setAddUser(AddUser addUser) {
        this.addUser = addUser;
    }
	
	public void setUpdateUser(UpdateUser updateUser) {
        this.updateUser = updateUser;
    }
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UserView2 frame = new UserView2();
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
    public UserView2() {
    	BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);
    	
        setBounds(0, 0, 776, 533);
        getContentPane().setLayout(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);
        panel.setBounds(0, 0, 762, 60);
        getContentPane().add(panel);

        JLabel lblQltk = new JLabel("Quản lý tài khoản");
        lblQltk.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblQltk.setBounds(303, 2, 146, 21);
        panel.add(lblQltk);

        txtTimKiem = new JTextField();
        txtTimKiem.setColumns(10);
        txtTimKiem.setBounds(260, 33, 236, 20);
        panel.add(txtTimKiem);

        JLabel lblNewLabel_1 = new JLabel("Tìm kiếm");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblNewLabel_1.setBounds(191, 37, 59, 11);
        panel.add(lblNewLabel_1);
        
        JComboBox cbChoice = new JComboBox();
        cbChoice.setBounds(643, 32, 78, 21);
        panel.add(cbChoice);
        
        
        JButton btnSapXep = new JButton("Sắp xếp");
        btnSapXep.setForeground(Color.WHITE);
        btnSapXep.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnSapXep.setBackground(new Color(0, 128, 255));
        btnSapXep.setBounds(541, 29, 92, 24);
        panel.add(btnSapXep);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 61, 762, 269);
        getContentPane().add(scrollPane);

        tableModel = new DefaultTableModel();
        String[] headerTbl = new String[]{"STT", "Full name", "UserName","Email", "Creation Date"};
        tableModel.setColumnIdentifiers(headerTbl);
        tblUser = new JTable(tableModel);
        scrollPane.setViewportView(tblUser);
        setWidthTable();
        
        tblUser.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                selectedRow = tblUser.getSelectedRow();
            }
        });
        
        JLabel lblPage = new JLabel(userDAO.getCurrentPage() + " / " + userDAO.getTotalPages(txtTimKiem.getText()));
        lblPage.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblPage.setBounds(678, 342, 48, 21);
        getContentPane().add(lblPage);
        
        JButton btnPrev = new JButton("<");
        btnPrev.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                if (userDAO.getCurrentPage() == 1) {
                    return;
                }
                userDAO.setCurrentPage(userDAO.getCurrentPage() - 1);
                lblPage.setText(userDAO.getCurrentPage() + " / " + userDAO.getTotalPages(txtTimKiem.getText()));
                loadUserList();
            }
        });
        btnPrev.setFont(new Font("Tahoma", Font.PLAIN, 18));
        btnPrev.setBounds(618, 340, 50, 23);
        getContentPane().add(btnPrev);

        JButton btnNext = new JButton(">");
        btnNext.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                if (userDAO.getCurrentPage() == userDAO.getTotalPages(txtTimKiem.getText())) {
                    return;
                }
                userDAO.setCurrentPage(userDAO.getCurrentPage() + 1);
                lblPage.setText(userDAO.getCurrentPage() + " / " + userDAO.getTotalPages(txtTimKiem.getText()));
                loadUserList();
            }
        });
        btnNext.setFont(new Font("Tahoma", Font.PLAIN, 18));
        btnNext.setBounds(712, 340, 50, 23);
        getContentPane().add(btnNext);

        

        addUser = new AddUser();
        addUser.setUserView(this);
        updateUser = new UpdateUser();
        updateUser.setUserView(this);
        
        JButton btnSua = new JButton("Sửa");
        btnSua.addActionListener(new ActionListener() {
        	 public void actionPerformed(ActionEvent e) {
                 if (selectedRow == -1) {
                     JOptionPane.showMessageDialog(null, "Vui lòng chọn người dùng để sửa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                     return;
                 }

                 int userId = (int) tableModel.getValueAt(selectedRow, 0);
                 String fullName = (String) tableModel.getValueAt(selectedRow, 1);
                 String userName = (String) tableModel.getValueAt(selectedRow, 2);
                 String email = (String) tableModel.getValueAt(selectedRow, 3);

                 // Hiển thị thông tin lên JFrame UpdateUser
                 updateUser.setVisible(true);
                 updateUser.setUserInfo(userId, fullName, userName, email); // Gọi phương thức để thiết lập thông tin
             }
        });
        btnSua.setForeground(Color.WHITE);
        btnSua.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnSua.setBackground(Color.RED);
        btnSua.setBounds(149, 383, 78, 24);
        getContentPane().add(btnSua);

        
        
        JButton btnXoa = new JButton("Xóa");
        btnXoa.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn người dùng để xóa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int userId = (int) tableModel.getValueAt(selectedRow, 0); 
                System.out.println(userId);
                int result = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa người dùng này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    UserDAOImpl userDAO = new UserDAOImpl();
                    boolean deleteResult = userDAO.deleteById(userId);

                    if (deleteResult != false) {
                        JOptionPane.showMessageDialog(null, "Xóa người dùng thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        loadUserList();
                    } else {
                        JOptionPane.showMessageDialog(null, "Xóa người dùng thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        btnXoa.setForeground(Color.WHITE);
        btnXoa.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnXoa.setBackground(Color.MAGENTA);
        btnXoa.setBounds(280, 383, 78, 24);
        getContentPane().add(btnXoa);

        JButton btnXuatExcel = new JButton("Xuất Excel");
        btnXuatExcel.addActionListener(new ActionListener() {
        	 public void actionPerformed(ActionEvent e) {
                 try {
                     JFileChooser jFileChooser = new JFileChooser();
                     jFileChooser.showSaveDialog(btnXuatExcel);
                     File saveFile = jFileChooser.getSelectedFile();

                     if (saveFile != null) {
                         if (!saveFile.getName().toLowerCase().endsWith(".xlsx")) {
                             saveFile = new File(saveFile.getAbsolutePath() + ".xlsx");
                         }

                         XSSFWorkbook wb = new XSSFWorkbook();
                         Sheet sheet = wb.createSheet("Users");

                         Row headerRow = sheet.createRow(0);
                         String[] columnNames = {"UserID", "FullName", "UserName", "Email", "Creation Date"};
                         for (int i = 0; i < columnNames.length; i++) {
                             Cell cell = headerRow.createCell(i);
                             cell.setCellValue(columnNames[i]);
                         }
                         List<User> userList = userDAO.findAll();
                         for (int j = 0; j < userList.size(); j++) {
                             Row row = sheet.createRow(j + 1);
                             User user = userList.get(j);
                             row.createCell(0).setCellValue(user.getUserId());
                             row.createCell(1).setCellValue(user.getFullName());
                             row.createCell(2).setCellValue(user.getUserName());
                             row.createCell(3).setCellValue(user.getPassword());
                             row.createCell(4).setCellValue(user.getEmail());

                             DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); 
                             String formattedDate = user.getCreationDate().format(formatter);
                             row.createCell(5).setCellValue(formattedDate);
                         }

                         FileOutputStream out = new FileOutputStream(saveFile);
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
        btnXuatExcel.setForeground(Color.WHITE);
        btnXuatExcel.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnXuatExcel.setBackground(Color.BLUE);
        btnXuatExcel.setBounds(419, 383, 123, 24);
        getContentPane().add(btnXuatExcel);

        JButton btnThem = new JButton("Thêm");
        btnThem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	addUser.setVisible(true);
            	addUser.resetFields(); 
				loadUserList();
            }
        });
        btnThem.setForeground(Color.WHITE);
        btnThem.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnThem.setBackground(new Color(0, 128, 255));
        btnThem.setBounds(31, 383, 78, 24);
        getContentPane().add(btnThem);
        loadUserList();
        
        txtTimKiem.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateTable();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateTable();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateTable();
            }

            private void updateTable() {
            	String searchTerm = txtTimKiem.getText().trim();
                userDAO.setCurrentPage(1); 
                lblPage.setText(userDAO.getCurrentPage() + " / " + userDAO.getTotalPages(txtTimKiem.getText()));
                loadUserList(); 
            }
        });
    }

    public void setWidthTable() {
        //model = new DefaultTableModel(); // Không cần tạo lại model
        //model.setColumnIdentifiers(headerTbl); // Không cần set lại headers
        //tblUser.setModel(model); // Không cần set lại model
        tblUser.getColumnModel().getColumn(0).setPreferredWidth(5);
        tblUser.getColumnModel().getColumn(1).setPreferredWidth(100); // Điều chỉnh cho phù hợp
        tblUser.getColumnModel().getColumn(2).setPreferredWidth(100); // Điều chỉnh cho phù hợp
        tblUser.getColumnModel().getColumn(3).setPreferredWidth(100); // Điều chỉnh cho phù hợp
        tblUser.getColumnModel().getColumn(4).setPreferredWidth(100); // Điều chỉnh cho phù hợp
    }
    public void loadUserList() {
	    ArrayList<User> userList;
	    String searchTerm = txtTimKiem.getText();
	    if (!searchTerm.isEmpty()) { 
	        userList = userDAO.findByFullNameLike(searchTerm); 
	    } else {
	        userList = userDAO.findByPagination(); 
	    }
	    tableModel.setRowCount(0); 
	    for (int i = 0; i < userList.size(); i++) {
	        User user = userList.get(i);
	        tableModel.addRow(new Object[] {
	                user.getUserId(),
	                user.getFullName(),
	                user.getUserName(),
	                user.getEmail(),
	                user.getCreationDate()
	        });
	    }
	    tblUser.setModel(tableModel);
	}
    
    private void openFile(String file) {
        try {
            File path = new File(file);
            Desktop.getDesktop().open(path);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
