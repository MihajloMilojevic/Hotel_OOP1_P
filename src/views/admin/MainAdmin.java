package views.admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableColumnModel;

import app.AppState;
import controllers.RoomController;
import controllers.UserController;
import exceptions.DuplicateIndexException;
import exceptions.NoElementException;
import models.Admin;
import models.Employee;
import models.Guest;
import models.Room;
import models.User;
import utils.CustomTableModel;
import utils.Pair;
import utils.WindowUtils;
import views.Login;
import views.components.DataPanel;
import views.dialogs.employees.AddEmployeeDialog;
import views.dialogs.employees.EditEmployeeDialog;
import views.dialogs.guests.AddGuestDialog;
import views.dialogs.guests.EditGuestDialog;
import views.dialogs.profile.UserProfileDialog;
import views.dialogs.rooms.AddRoomDialog;
import views.dialogs.rooms.EditRoomDialog;
import views.dialogs.settings.SettingsDialog;

public class MainAdmin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTabbedPane tabbedPane;

	/**
	 * Create the frame.
	 */
	public MainAdmin() {
		setIconImage(WindowUtils.getIconImage());
		setTitle("MHotelify | Admin");
		setForeground(new Color(255, 255, 255));
		setBackground(new Color(73, 73, 73));
		setBounds(200, 100, 600, 600);
		setLocationRelativeTo(null);
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		addMenu();

		contentPane = new JPanel();
		contentPane.setForeground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPane.setBackground(new Color(73, 73, 73));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		tabbedPane.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		tabbedPane.setForeground(new Color(255, 255, 255));
		tabbedPane.setBackground(new Color(73, 73, 73));
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		contentPane.add(tabbedPane, BorderLayout.CENTER);

		addEmpoloyeesTab();
		addGuestsTab();
		addRoomsTab();

		addWindowListener(WindowUtils.getWindowClosing());

	}

	private void addMenu() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu menu;
		JMenuItem item;

		menu = new JMenu("User");
		menu.setMnemonic(KeyEvent.VK_U);
		menuBar.add(menu);

		item = new JMenuItem("Profile");
		item.setMnemonic(KeyEvent.VK_P);
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				UserProfileDialog dialog = new UserProfileDialog(AppState.getInstance().getUser());
				dialog.setVisible(true);
				dialog.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosed(WindowEvent windowEvent) {
						if (!dialog.isOk())
							return;
						User updatedUser = dialog.getUser();
						try {
							UserController.updateUser(updatedUser);
							AppState.getInstance().setUser(updatedUser);
							JOptionPane.showMessageDialog(contentPane, "Profile updated successfully", "Success",
									JOptionPane.INFORMATION_MESSAGE);
						} catch (NoElementException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
			}
		});
		menu.add(item);

		item = new JMenuItem("Logout");
		item.setMnemonic(KeyEvent.VK_L);
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int res = JOptionPane.showConfirmDialog(contentPane, "Are you sure you want to logout?", "Logout",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (res != JOptionPane.YES_OPTION)
					return;
				UserController.logout();
				dispose();
				new Login().setVisible(true);
			}
		});
		menu.add(item);

		menu = new JMenu("Settings");
		menu.setMnemonic(KeyEvent.VK_S);
		menuBar.add(menu);

		item = new JMenuItem("Edit");
		item.setMnemonic(KeyEvent.VK_E);
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new SettingsDialog().setVisible(true);
			}
		});
		menu.add(item);
	}

	private void addGuestsTab() {
		ArrayList<Pair<String, String>> columns = new ArrayList<Pair<String, String>>() {
			/**
			* 
			*/
			private static final long serialVersionUID = 4215012548686790091L;

			{
				add(new Pair<String, String>("ID", "id")); // 0
				add(new Pair<String, String>("Role", "role")); // 1
				add(new Pair<String, String>("Name", "name")); // 2
				add(new Pair<String, String>("Surname", "surname")); // 3
				add(new Pair<String, String>("Username", "username")); // 4
				add(new Pair<String, String>("Gender", "gender")); // 5
				add(new Pair<String, String>("Date of birth", "birthdate")); // 6
				add(new Pair<String, String>("Address", "address")); // 7
				add(new Pair<String, String>("Phone", "phone")); // 8 // 11
			}
		};

		CustomTableModel<Guest> model = new CustomTableModel<Guest>(columns,
				new CustomTableModel.TableDataManiplations<Guest>() {

					@Override
					public ArrayList<Guest> getData() {
						return UserController.getGuests();
					}

					@Override
					public void edit(Guest model) throws NoElementException {
						UserController.updateUser(model);
					}

					@Override
					public void remove(Guest model) {
						UserController.deleteUser(model);
					}

					@Override
					public void add(Guest model) throws DuplicateIndexException {
						UserController.addUser(model);
					}

				}, new Guest());

		DataPanel<Guest> dataPanel = new DataPanel<Guest>(model);

		TableColumnModel columnModel = dataPanel.getTable().getColumnModel();
		columnModel.getColumn(0).setMinWidth(150);
		columnModel.getColumn(1).setMinWidth(150);
		columnModel.getColumn(2).setMinWidth(100);
		columnModel.getColumn(3).setMinWidth(100);
		columnModel.getColumn(4).setMinWidth(100);
		columnModel.getColumn(5).setMinWidth(100);
		columnModel.getColumn(6).setMinWidth(100);
		columnModel.getColumn(7).setMinWidth(300);
		columnModel.getColumn(8).setMinWidth(150);

		dataPanel.getAddBtn().setText("Add Guest");
		dataPanel.getEditBtn().setText("Edit Guest");
		dataPanel.getDeleteBtn().setText("Delete Guest");

		dataPanel.getRefreshBtn().addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {
				((CustomTableModel<Guest>) dataPanel.getTable().getModel()).refresh();
				dataPanel.getTable().updateUI();
			}
		});
		dataPanel.getAddBtn().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				AddGuestDialog dialog = new AddGuestDialog();
				dialog.setVisible(true);
				dialog.addWindowListener(new WindowAdapter() {
					@SuppressWarnings("unchecked")
					@Override
					public void windowClosed(WindowEvent e) {
						if (!dialog.isOk())
							return;
						try {
							((CustomTableModel<Guest>) dataPanel.getTable().getModel()).add(dialog.getGuest());
						} catch (DuplicateIndexException e1) {
							JOptionPane.showMessageDialog(contentPane, "User with this username already exists",
									"Error", JOptionPane.ERROR_MESSAGE);
							e1.printStackTrace();
						}
						dataPanel.getTable().updateUI();
					}
				});

			}
		});
		dataPanel.getEditBtn().addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {

				CustomTableModel<Guest> customTableModel = (CustomTableModel<Guest>) dataPanel.getTable().getModel();
				Guest guest = (Guest) customTableModel.get(dataPanel.getTable().getSelectedRow());
				EditGuestDialog dialog = new EditGuestDialog(guest);
				dialog.setVisible(true);
				dialog.addWindowListener(new WindowAdapter() {
					@SuppressWarnings("rawtypes")
					@Override
					public void windowClosed(WindowEvent e) {
						if (!dialog.isOk())
							return;
						try {
							((CustomTableModel) dataPanel.getTable().getModel()).edit(guest);
							;
						} catch (NoElementException e1) {
							JOptionPane.showMessageDialog(contentPane, "Guest not found", "Error",
									JOptionPane.ERROR_MESSAGE);
							e1.printStackTrace();
						}
						dataPanel.getTable().updateUI();
					}
				});

			}
		});
		dataPanel.getDeleteBtn().addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {
				CustomTableModel<Guest> customTableModel = (CustomTableModel<Guest>) dataPanel.getTable().getModel();
				int res = JOptionPane.showConfirmDialog(contentPane, "Are you sure you want to delete this user?",
						"Delete user", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (res != JOptionPane.YES_OPTION)
					return;
				try {
					customTableModel.remove(dataPanel.getTable().getSelectedRow());
				} catch (NoElementException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				dataPanel.getTable().updateUI();
			}
		});
		tabbedPane.addTab("Guests", new ImageIcon("./assets/icons/guests.png"), dataPanel, null);
	}

	private void addEmpoloyeesTab() {
		ArrayList<Pair<String, String>> columns = new ArrayList<Pair<String, String>>() {
			/**
			* 
			*/
			private static final long serialVersionUID = 4215012548686790091L;

			{
				add(new Pair<String, String>("ID", "id")); // 0
				add(new Pair<String, String>("Role", "role")); // 1
				add(new Pair<String, String>("Name", "name")); // 2
				add(new Pair<String, String>("Surname", "surname")); // 3
				add(new Pair<String, String>("Username", "username")); // 4
				add(new Pair<String, String>("Gender", "gender")); // 5
				add(new Pair<String, String>("Date of birth", "birthdate")); // 6
				add(new Pair<String, String>("Address", "address")); // 7
				add(new Pair<String, String>("Phone", "phone")); // 8
				add(new Pair<String, String>("Level of professional education", "levelOfProfessionalEducation")); // 9
				add(new Pair<String, String>("Years of work experience", "yearsOfWorkExperience")); // 10
				add(new Pair<String, String>("Salary", "salary")); // 11
			}
		};
		CustomTableModel<Employee> model = new CustomTableModel<Employee>(columns,
				new CustomTableModel.TableDataManiplations<Employee>() {

					@Override
					public ArrayList<Employee> getData() {
						return UserController.getEmployees();
					}

					@Override
					public void edit(Employee model) throws NoElementException {
						UserController.updateUser(model);
					}

					@Override
					public void remove(Employee model) {
						UserController.deleteUser(model);
					}

					@Override
					public void add(Employee model) throws DuplicateIndexException {
						UserController.addUser(model);
					}

				}, new Admin());
		DataPanel<Employee> dataPanel = new DataPanel<Employee>(model);

		TableColumnModel columnModel = dataPanel.getTable().getColumnModel();
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

		dataPanel.getAddBtn().setText("Hire Employee");
		dataPanel.getEditBtn().setText("Edit Employee");
		dataPanel.getDeleteBtn().setText("Fire Employee");

		dataPanel.getRefreshBtn().addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {
				((CustomTableModel<Employee>) dataPanel.getTable().getModel()).refresh();
				dataPanel.getTable().updateUI();
			}
		});
		dataPanel.getAddBtn().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				AddEmployeeDialog dialog = new AddEmployeeDialog();
				dialog.setVisible(true);
				dialog.addWindowListener(new WindowAdapter() {
					@SuppressWarnings("unchecked")
					@Override
					public void windowClosed(WindowEvent e) {
						if (!dialog.isOk())
							return;
						try {
							((CustomTableModel<Employee>) dataPanel.getTable().getModel()).add(dialog.getEmployee());
						} catch (DuplicateIndexException e1) {
							JOptionPane.showMessageDialog(contentPane, "User with this username already exists",
									"Error", JOptionPane.ERROR_MESSAGE);
							e1.printStackTrace();
						}
						dataPanel.getTable().updateUI();
					}
				});

			}
		});
		dataPanel.getEditBtn().addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {

				CustomTableModel<Employee> customTableModel = (CustomTableModel<Employee>) dataPanel.getTable()
						.getModel();
				Employee employee = (Employee) customTableModel.get(dataPanel.getTable().getSelectedRow());
				EditEmployeeDialog dialog = new EditEmployeeDialog(employee);
				dialog.setVisible(true);
				dialog.addWindowListener(new WindowAdapter() {
					@SuppressWarnings("rawtypes")
					@Override
					public void windowClosed(WindowEvent e) {
						if (!dialog.isOk())
							return;
						try {
							((CustomTableModel) dataPanel.getTable().getModel()).edit(employee);
							;
						} catch (NoElementException e1) {
							JOptionPane.showMessageDialog(contentPane, "Employee not found", "Error",
									JOptionPane.ERROR_MESSAGE);
							e1.printStackTrace();
						}
						dataPanel.getTable().updateUI();
					}
				});

			}
		});
		dataPanel.getDeleteBtn().addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {
				CustomTableModel<Employee> customTableModel = (CustomTableModel<Employee>) dataPanel.getTable()
						.getModel();
				int res = JOptionPane.showConfirmDialog(contentPane, "Are you sure you want to delete this user?",
						"Delete user", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (res != JOptionPane.YES_OPTION)
					return;
				try {
					customTableModel.remove(dataPanel.getTable().getSelectedRow());
				} catch (NoElementException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				dataPanel.getTable().updateUI();
			}
		});
		tabbedPane.addTab("Employees", new ImageIcon("./assets/icons/employees.png"), dataPanel, null);
	}

	private void addRoomsTab() {
		ArrayList<Pair<String, String>> columns = new ArrayList<Pair<String, String>>() {
			/**
			* 
			*/
			private static final long serialVersionUID = 4215012548686790091L;

			{
				add(new Pair<String, String>("ID", "id")); // 0
				add(new Pair<String, String>("Number", "number")); // 1
				add(new Pair<String, String>("Type", "type")); // 2
				add(new Pair<String, String>("Status", "status")); // 3
				add(new Pair<String, String>("Additions", "roomAdditions")); // 4
			}
		};
		CustomTableModel<Room> model = new CustomTableModel<Room>(columns,
				new CustomTableModel.TableDataManiplations<Room>() {

					@Override
					public ArrayList<Room> getData() {
						return RoomController.getRooms();
					}

					@Override
					public void edit(Room model) throws NoElementException {
						RoomController.updateRoom(model);
					}

					@Override
					public void remove(Room model) throws NoElementException {
						RoomController.deleteRoom(model);
					}

					@Override
					public void add(Room model) throws DuplicateIndexException {
						RoomController.addRoom(model);
					}

				}, new Room()) {

			private static final long serialVersionUID = -4762274401058873540L;

			@Override
			public Object getValueAt(int rowIndex, int columnIndex) {
				if (columns.get(columnIndex).getValue().equals("roomAdditions")) {
					return String.join(", ", ((Room) data.get(rowIndex)).getRoomAdditions().stream()
							.map(ra -> ra.getName()).toArray(String[]::new));
				}
				if (columns.get(columnIndex).getValue().equals("type")) {
					return ((Room) data.get(rowIndex)).getType().getName();
				}
				return super.getValueAt(rowIndex, columnIndex);
			}

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				if (columns.get(columnIndex).getValue().equals("roomAdditions")) {
					return String.class;
				}
				if (columns.get(columnIndex).getValue().equals("type")) {
					return String.class;
				}
				return super.getColumnClass(columnIndex);
			}
		};
		DataPanel<Room> dataPanel = new DataPanel<Room>(model);

		TableColumnModel columnModel = dataPanel.getTable().getColumnModel();
		columnModel.getColumn(0).setMinWidth(150);
		columnModel.getColumn(1).setMinWidth(50);
		columnModel.getColumn(2).setMinWidth(150);
		columnModel.getColumn(3).setMinWidth(75);
		columnModel.getColumn(4).setMinWidth(350);

		dataPanel.getAddBtn().setText("Add Room");
		dataPanel.getEditBtn().setText("Edit Room");
		dataPanel.getDeleteBtn().setText("Delete Room");

		dataPanel.getRefreshBtn().addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {
				((CustomTableModel<Room>) dataPanel.getTable().getModel()).refresh();
				dataPanel.getTable().updateUI();
			}
		});
		dataPanel.getAddBtn().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				AddRoomDialog dialog = new AddRoomDialog();
				dialog.setVisible(true);
				dialog.addWindowListener(new WindowAdapter() {
					@SuppressWarnings("unchecked")
					@Override
					public void windowClosed(WindowEvent e) {
						if (!dialog.isOk())
							return;
						try {
							((CustomTableModel<Room>) dataPanel.getTable().getModel()).add(dialog.getRoom());
						} catch (DuplicateIndexException e1) {
							JOptionPane.showMessageDialog(contentPane, "Room with this number already exists", "Error",
									JOptionPane.ERROR_MESSAGE);
							e1.printStackTrace();
						}
						dataPanel.getTable().updateUI();
					}
				});

			}
		});

		dataPanel.getEditBtn().addActionListener(new ActionListener() {
			//@SuppressWarnings("unchecked")
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {
				CustomTableModel<Room> customTableModel = (CustomTableModel<Room>) dataPanel.getTable().getModel();
				Room room = (Room) customTableModel.get(dataPanel.getTable().getSelectedRow());
				EditRoomDialog dialog = new EditRoomDialog(room);
				dialog.setVisible(true);
				dialog.addWindowListener(new WindowAdapter() {
					@SuppressWarnings("rawtypes")
					@Override
					public void windowClosed(WindowEvent e) {
						if (!dialog.isOk())
							return;
						try {
							((CustomTableModel) dataPanel.getTable().getModel()).edit(room);
							;
						} catch (NoElementException e1) {
							JOptionPane.showMessageDialog(contentPane, "Room not found", "Error",
									JOptionPane.ERROR_MESSAGE);
							e1.printStackTrace();
						}
						dataPanel.getTable().updateUI();
					}
				});
			}
		});
		
		dataPanel.getDeleteBtn().addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {
				CustomTableModel<Room> customTableModel = (CustomTableModel<Room>) dataPanel.getTable().getModel();
				int res = JOptionPane.showConfirmDialog(contentPane, "Are you sure you want to delete this room?",
						"Delete room", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (res != JOptionPane.YES_OPTION)
					return;
				try {
					customTableModel.remove(dataPanel.getTable().getSelectedRow());
				} catch (NoElementException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				dataPanel.getTable().updateUI();
			}
		});

		tabbedPane.addTab("Rooms", new ImageIcon("./assets/icons/rooms.png"), dataPanel, null);
	}
}
