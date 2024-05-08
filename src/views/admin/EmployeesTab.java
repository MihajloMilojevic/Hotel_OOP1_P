package views.admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import controllers.UserController;
import exceptions.DuplicateIndexException;
import exceptions.NoElementException;
import models.Employee;
import models.enums.Gender;
import models.enums.UserRole;
import utils.Pair;

public class EmployeesTab extends JPanel {

	private static final long serialVersionUID = 1L;

	private JTable table;
	
	/**
	 * Create the panel.
	 */
	public EmployeesTab() {
		setBackground(new Color(73, 73, 73));
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel.setBackground(new Color(73, 73, 73));
		add(panel, BorderLayout.NORTH);
		
		JButton addBtn = new JButton("Hire Employee");
		addBtn.setMnemonic(KeyEvent.VK_H);
		addBtn.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		addBtn.setIcon(new ImageIcon("./assets/icons/plus.png"));
		addBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		panel.add(addBtn);
		
		JButton btnEditEmployee = new JButton("Edit Employee");
		btnEditEmployee.setEnabled(false);
		btnEditEmployee.setMnemonic(KeyEvent.VK_E);
		btnEditEmployee.setIcon(new ImageIcon("./assets/icons/edit.png"));
		btnEditEmployee.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		panel.add(btnEditEmployee);
		
		JButton btnFireEmployee = new JButton("Fire Employee");
		
		btnFireEmployee.setEnabled(false);
		btnFireEmployee.setMnemonic(KeyEvent.VK_F);
		btnFireEmployee.setIcon(new ImageIcon("./assets/icons/delete.png"));
		btnFireEmployee.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		panel.add(btnFireEmployee);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.getViewport().setBackground(new Color(73, 73, 73));
		add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable(new CustomTableModel());
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setRowHeight(30);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		TableColumnModel columnModel = table.getColumnModel();
		columnModel.getColumn(0).setMinWidth(150);
		columnModel.getColumn(1).setMinWidth(150);
		columnModel.getColumn(2).setMinWidth(100);
		columnModel.getColumn(3).setMinWidth(100);
		columnModel.getColumn(4).setMinWidth(100);
		columnModel.getColumn(5).setMinWidth(100);
		columnModel.getColumn(6).setMinWidth(100);
		columnModel.getColumn(7).setMinWidth(300);
		columnModel.getColumn(8).setMinWidth(150);
		columnModel.getColumn(9).setMinWidth(200);
		columnModel.getColumn(10).setMinWidth(150);
		columnModel.getColumn(11).setMinWidth(100);
		
		table.setForeground(Color.WHITE);
		table.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		table.setBackground(new Color(73, 73, 73));
		
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				btnEditEmployee.setEnabled(table.getSelectedRow() != -1);
				btnFireEmployee.setEnabled(table.getSelectedRow() != -1);
			}
		});
		
		scrollPane.setViewportView(table);
		
		addBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddEmployeeDialog dialog = new AddEmployeeDialog();
				dialog.setVisible(true);
				dialog.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosed(WindowEvent e) {
						if (!dialog.isOk())
							return;
						try {
							((CustomTableModel) table.getModel()).add(dialog.getEmployee());
						} catch (DuplicateIndexException e1) {
							JOptionPane.showMessageDialog(EmployeesTab.this, "User with this username already exists",
									"Error", JOptionPane.ERROR_MESSAGE);
							e1.printStackTrace();
						}
						table.updateUI();
					}
				});
			}
		});
		
		btnEditEmployee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Employee employee = ((CustomTableModel) table.getModel()).employees.get(table.getSelectedRow());
				EditEmployeeDialog dialog = new EditEmployeeDialog(employee);
				dialog.setVisible(true);
				dialog.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosed(WindowEvent e) {
						if (!dialog.isOk()) return;
						try {
							((CustomTableModel) table.getModel()).edit(employee);;
						} catch (NoElementException e1) {
						    JOptionPane.showMessageDialog(EmployeesTab.this, "Employee not found", "Error", JOptionPane.ERROR_MESSAGE);
						    e1.printStackTrace();
						}
						table.updateUI();
					}
				});
			}
		});
		
		btnFireEmployee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int confirm = JOptionPane.showConfirmDialog(EmployeesTab.this, "Are you sure you want to fire this employee?", "Fire employee", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (confirm != JOptionPane.YES_OPTION) return;
				((CustomTableModel) table.getModel()).remove(table.getSelectedRow());
				table.updateUI();
			}
		});
	}
	private class CustomTableModel implements TableModel {
		private ArrayList<Employee> employees;
		private ArrayList<Pair<String, String>> columns = new ArrayList<Pair<String, String>>() {/**
			 * 
			 */
			private static final long serialVersionUID = 4215012548686790091L;

		{
			add(new Pair<String, String>("ID", "id"));																// 0
			add(new Pair<String, String>("Role", "role"));															// 1
			add(new Pair<String, String>("Name", "name"));	                            							// 2
			add(new Pair<String, String>("Surname", "surname"));			    									// 3	
			add(new Pair<String, String>("Username", "username"));													// 4
			add(new Pair<String, String>("Gender", "gender"));														// 5
			add(new Pair<String, String>("Date of birth", "birthdate"));											// 6
			add(new Pair<String, String>("Address", "address"));													// 7
			add(new Pair<String, String>("Phone", "phone"));														// 8
			add(new Pair<String, String>("Level of professional education", "levelOfProfessionalEducation"));		// 9  
			add(new Pair<String, String>("Years of work experience", "yearsOfWorkExperience"));						// 10
			add(new Pair<String, String>("Salary", "salary"));														// 11
		}};
		
		public CustomTableModel() {
			employees = UserController.getEmployees();
		}
		
		public void edit(Employee employee) throws NoElementException {
			UserController.updateUser(employee);
            employees = UserController.getEmployees();
		}

		public void remove(int selectedRow) {
			UserController.deleteUser(employees.get(selectedRow));
            employees = UserController.getEmployees();
		}

		public void add(Employee employee) throws DuplicateIndexException {
			UserController.addUser(employee);
            employees = UserController.getEmployees();
		}
		

		@Override
		public int getRowCount() {
			return employees.size();
		}

		@Override
		public int getColumnCount() {
			return columns.size();
		}

		@Override
		public String getColumnName(int columnIndex) {
			return columns.get(columnIndex).getKey();
		}

		@Override
		public Class<?> getColumnClass(int columnIndex) {
			switch (columnIndex) {
				case 1:  		// for Role
					return UserRole.class;
				case 5:			// for gender
					return Gender.class;
				case 6:			//  for date of birth
					return LocalDate.class;
				case 11:        // for salary
					return Double.class;
				default: 		// for all other columns
					return String.class;
			}
			
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			// no cell is editable, it is handled by buttons and dialogs
			return false;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			return employees.get(rowIndex).get(columns.get(columnIndex).getValue());
		}

		@Override
		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			// no need to implement, cells are not editable it is handled by buttons and dialogs
		}

		@Override
		public void addTableModelListener(TableModelListener l) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void removeTableModelListener(TableModelListener l) {
			// TODO Auto-generated method stub
			
		}

	}
}
