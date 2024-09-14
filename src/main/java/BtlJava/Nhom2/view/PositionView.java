package BtlJava.Nhom2.view;

import java.awt.EventQueue;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;

import BtlJava.Nhom2.dao.DepartmentDAOImpl;
import BtlJava.Nhom2.dao.PositionDAOImpl;
import BtlJava.Nhom2.database.ConnectionPool;
import BtlJava.Nhom2.database.ConnectionPoolImpl;
import BtlJava.Nhom2.model.Department;
import BtlJava.Nhom2.model.Position;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.util.ArrayList;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PositionView extends JInternalFrame {

    private static final long serialVersionUID = 1L;
    private JTextField txtSearch;
    private DefaultTableModel model;
    private int count = 1;
    
    private ConnectionPool cp = new ConnectionPoolImpl();
    private JTextField txtID;
    private JTextField txtName;
    private JTextField txtDes;
    public static Position positionSelected;
    private JTable tblChucVu;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    PositionView frame = new PositionView();
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
    public PositionView() {
    	setBorder(null);
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);
        setBounds(0, 0, 776, 533);
        getContentPane().setLayout(null);
        
        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 776, 60);
        panel.setBackground(new Color(255, 255, 255));
        getContentPane().add(panel);
        panel.setLayout(null);
        tblChucVu = new JTable();
        
        JLabel lblNewLabel = new JLabel("Danh sách vị trí");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblNewLabel.setBounds(272, 0, 146, 21);
        panel.add(lblNewLabel);
        JLabel pageLabel = new JLabel("1");
        pageLabel.setBounds(676, 350, 18, 23);
        getContentPane().add(pageLabel);
        
        txtSearch = new JTextField();
        txtSearch.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyReleased(KeyEvent e) {
        		String key = txtSearch.getText();
        		String oldPage = pageLabel.getText();
				int currentPage = Integer.parseInt(oldPage);
				int total = 7;
	            try {
					loadDataToTableSearch(PositionDAOImpl.getInstance(cp).searchPositionPage(key, currentPage, total));
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
        	}
        });
        txtSearch.setBounds(236, 32, 236, 20);
        panel.add(txtSearch);
        txtSearch.setColumns(10);
        
        JLabel lblNewLabel_1 = new JLabel("Tìm kiếm");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblNewLabel_1.setBounds(167, 37, 59, 11);
        panel.add(lblNewLabel_1);

        tblChucVu.setBackground(new Color(255, 255, 255));
        tblChucVu.setBorder(new LineBorder(new Color(0, 0, 0)));
        tblChucVu.setDefaultEditor(Object.class, null);
        
        
        JLabel lblNewLabel_2 = new JLabel("ID:");
        lblNewLabel_2.setBounds(123, 354, 46, 14);
        getContentPane().add(lblNewLabel_2);
        
        JLabel lblNewLabel_3 = new JLabel("Position Name:");
        lblNewLabel_3.setBounds(123, 384, 88, 14);
        getContentPane().add(lblNewLabel_3);
        
        JLabel lblNewLabel_4 = new JLabel("Description:");
        lblNewLabel_4.setBounds(123, 414, 70, 14);
        getContentPane().add(lblNewLabel_4);
        
        JButton btnPrev = new JButton("<");
        btnPrev.setFont(new Font("Tahoma", Font.PLAIN, 18));
        btnPrev.setBounds(606, 347, 50, 23);
        getContentPane().add(btnPrev);
        
        
        
        JButton btnNext = new JButton(">");
        btnNext.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		count++;
				
				String oldPage = pageLabel.getText();
				int page = Integer.parseInt(oldPage);
				page++;
				String newPage = String.valueOf(page);
				pageLabel.setText(newPage);
				String key = txtSearch.getText();
				int currentPage = page;
				int total = 7;
				if(!key.equalsIgnoreCase("")) {
					try {
						loadDataToTableSearch(PositionDAOImpl.getInstance(cp).searchPositionPage(key, currentPage, total));
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else {
					loadPositionData(PositionDAOImpl.getInstance(cp).paginationPosition(currentPage, total));
				}
        	}
        });
        btnNext.setFont(new Font("Tahoma", Font.PLAIN, 18));
        btnNext.setBounds(704, 348, 50, 23);
        getContentPane().add(btnNext);
        
        JButton btnXoa = new JButton("Xóa");
        btnXoa.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		if (tblChucVu.getSelectedRow() == -1) {
		            JOptionPane.showMessageDialog(null, "Vui lòng chọn Vị trí muốn xóa!");
		        } else {
		            int output = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xoá vị trí này?", "Xác nhận xoá nhà cung cấp", JOptionPane.YES_NO_OPTION);
		            if (output == JOptionPane.YES_OPTION) {
		                PositionDAOImpl.getInstance(cp).deleteById(getVitriSelect().getPositionId());
		                loadPositionData(PositionDAOImpl.getInstance(cp).paginationPosition(count, 7));
		                txtID.setText("");
						txtName.setText("");
						txtDes.setText("");
		                
		            }
		        }
        	}
        });
        btnXoa.setBounds(429, 439, 78, 24);
        getContentPane().add(btnXoa);
        btnXoa.setForeground(Color.WHITE);
        btnXoa.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnXoa.setBackground(new Color(255, 0, 0));
        
        JButton btnThem = new JButton("Thêm");
        btnThem.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		String name = txtName.getText();
        		String des = txtDes.getText();
        		if (name.equals("") || des.equals("")) {
		            JOptionPane.showMessageDialog(btnThem, "Vui lòng nhập đầy đủ thông tin !");
		        }else {
		        	Position position = new Position();
		        	position.setTitle(name);
		        	position.setDescription(des);
		        	boolean isExist = PositionDAOImpl.getInstance(cp).PositionExist(position);
		        	if(isExist) {
		        		JOptionPane.showMessageDialog(btnThem, "Vị trí này đã tồn tại !");
		        	}else {
		        		boolean result = PositionDAOImpl.getInstance(cp).insert(position);
		        		if (result == true) {
			            	txtID.setText("");
							txtName.setText("");
							txtDes.setText("");
			                loadPositionData(PositionDAOImpl.getInstance(cp).paginationPosition(1, 7));
			                JOptionPane.showMessageDialog(btnThem, "Thêm mới vị trí thành công!");

			            } else {
			                JOptionPane.showMessageDialog(btnThem, "Thêm mới vị trí thất bại!");
			            }
		        	}
	        		
		        }
        		
        		
        	}
        });
        btnThem.setBounds(170, 439, 78, 24);
        getContentPane().add(btnThem);
        btnThem.setForeground(new Color(255, 255, 255));
        btnThem.setBackground(new Color(0, 128, 255));
        btnThem.setFont(new Font("Tahoma", Font.BOLD, 12));
        
        txtID = new JTextField();
        txtID.setEditable(false);
        txtID.setBounds(221, 351, 86, 20);
        getContentPane().add(txtID);
        txtID.setColumns(10);
        
        txtName = new JTextField();
        txtName.setBounds(221, 378, 203, 20);
        getContentPane().add(txtName);
        txtName.setColumns(10);
        
        txtDes = new JTextField();
        txtDes.setBounds(221, 406, 304, 20);
        getContentPane().add(txtDes);
        txtDes.setColumns(10);
        
        JButton btnCapNhat = new JButton("Sửa");
        btnCapNhat.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		if (tblChucVu.getSelectedRow() == -1) {
		            JOptionPane.showMessageDialog(null, "Vui lòng chọn Vị trí muốn sửa!");
		        } else {
		        	Integer id = getVitriSelect().getPositionId();
			        String name = txtName.getText();
			        String des = txtDes.getText();
			        if (id.toString().isEmpty() || name.equals("") || des.equals("")) {
			            JOptionPane.showMessageDialog(btnCapNhat, "Vui lòng nhập đầy đủ thông tin !");
			        } else {
			            Position pb = new Position(id, name, des);
			            PositionDAOImpl.getInstance(cp).updateById(id, pb);
			            boolean result = PositionDAOImpl.getInstance(cp).updateById(id, pb);
			            if (result == true) {
			            	txtID.setText("");
							txtName.setText("");
							txtDes.setText("");
							
							String oldPage = pageLabel.getText();
							int page = Integer.parseInt(oldPage);
							
			                loadPositionData(PositionDAOImpl.getInstance(cp).paginationPosition(page, 7));
			                JOptionPane.showMessageDialog(btnCapNhat, "Cập nhật vị trí thành công!");

			            } else {
			                JOptionPane.showMessageDialog(btnCapNhat, "Cập nhật vị trí không thành công!");
			            }
			        }
		        }
        	}
        });
        btnCapNhat.setForeground(new Color(0, 0, 0));
        btnCapNhat.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnCapNhat.setBackground(new Color(255, 255, 0));
        btnCapNhat.setBounds(299, 439, 78, 24);
        getContentPane().add(btnCapNhat);
        
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 71, 776, 269);
        getContentPane().add(scrollPane);
        
        
        tblChucVu.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		if(e.getClickCount() == 1) {
        			positionSelected = getVitriSelect();
    	   			txtID.setText(String.valueOf(getVitriSelect().getPositionId()));
    	   			txtID.setEnabled(false);
    	   			txtID.setDisabledTextColor(Color.BLACK);
    	   			txtName.setText(getVitriSelect().getTitle());
    	   			txtDes.setText(getVitriSelect().getDescription());
    	   		}
        		
        	}
        });
        scrollPane.setViewportView(tblChucVu);
        
        JButton btnReset = new JButton("Làm mới");
        btnReset.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		txtID.setText("");
	   			txtID.setEnabled(false);
	   			txtID.setDisabledTextColor(Color.BLACK);
	   			txtName.setText("");
	   			txtDes.setText("");
	   			loadPositionData(PositionDAOImpl.getInstance(cp).paginationPosition(1, 7));

        	}
        });
        btnReset.setForeground(Color.WHITE);
        btnReset.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnReset.setBackground(new Color(0, 255, 64));
        btnReset.setBounds(563, 439, 93, 24);
        getContentPane().add(btnReset);
        
        
        btnPrev.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		if(count == 1) count = 1;
				else count--;
				
				String key = txtSearch.getText();
				String oldPage = pageLabel.getText();
				int page = Integer.parseInt(oldPage);
				if(page == 1) page = 1;
				else page--;
				int currentPage = page;
				int total = 7;
				String newPage = String.valueOf(page);
				pageLabel.setText(newPage);
				if(!key.equalsIgnoreCase("")) {
					try {
						loadDataToTableSearch(PositionDAOImpl.getInstance(cp).searchPositionPage(key, currentPage, total));
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else {
					loadPositionData(PositionDAOImpl.getInstance(cp).paginationPosition(currentPage, total));
				}
        	}
        });
        
        
        setWidthTable();
        loadPositionData(PositionDAOImpl.getInstance(cp).paginationPosition(1, 7));
    }
    
    public void setWidthTable() {
        model = new DefaultTableModel();
        String[] headerTbl = new String[]{"Id", "Position Name", "Description"};
        model.setColumnIdentifiers(headerTbl);
        tblChucVu.setModel(model);
        tblChucVu.getColumnModel().getColumn(0).setPreferredWidth(3);
        tblChucVu.getColumnModel().getColumn(1).setPreferredWidth(100);
        tblChucVu.getColumnModel().getColumn(2).setPreferredWidth(250);
    }
    
    private void loadPositionData(ArrayList<Position> positions) {
    	
        try {
            model.setRowCount(0);    
            for (Position i : positions) {
                model.addRow(new Object[]{
                    i.getPositionId(), i.getTitle(), i.getDescription()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public Position getVitriSelect() {
		
        int i_row = tblChucVu.getSelectedRow();
        Position pb = PositionDAOImpl.getInstance(cp).paginationPosition(count, 7).get(i_row);
        return pb;
	 }
    
    public void loadDataToTableSearch(ArrayList<Position> positions) {
        try {
            model.setRowCount(0);
            for (Position p : positions) {
                model.addRow(new Object[]{
                    p.getPositionId(), p.getTitle(), p.getDescription()
                });
            }
        } catch (Exception e) {
        	
        }
    }
}
