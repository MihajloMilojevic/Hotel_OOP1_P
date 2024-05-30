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
import java.lang.ModuleLayer.Controller;
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
import controllers.ControllerActionStatus;
import controllers.ReservationController;
import controllers.RoomController;
import controllers.UserController;
import exceptions.DuplicateIndexException;
import exceptions.NoElementException;
import models.Admin;
import models.Employee;
import models.Guest;
import models.Reservation;
import models.ReservationAddition;
import models.Room;
import models.RoomAddition;
import models.RoomType;
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
import views.dialogs.reservation_additions.AddReservationAdditionDialog;
import views.dialogs.reservations.AddReservationDialog;
import views.dialogs.reservations.EditReservationDialog;
import views.dialogs.room_additions.AddRoomAdditionDialog;
import views.dialogs.room_additions.EditRoomAdditionDialog;
import views.dialogs.room_types.AddRoomTypesDialog;
import views.dialogs.room_types.EditRoomTypeDialog;
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
		addRoomAdditionsTab();
		addRoomTypesTab();
		addReservationAdditionsTab();
		addReservationsTab();

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
						ControllerActionStatus status = UserController.updateUser(updatedUser);
						switch (status) {
						case SUCCESS:
							AppState.getInstance().setUser(updatedUser);
							JOptionPane.showMessageDialog(contentPane, "Profile updated successfully", "Success",
									JOptionPane.INFORMATION_MESSAGE);
						case NO_RECORD:
							JOptionPane.showMessageDialog(contentPane, "User not found", "Error",
									JOptionPane.ERROR_MESSAGE);
							break;
						case ERROR:
							JOptionPane.showMessageDialog(contentPane, "An error occured", "Error",
									JOptionPane.ERROR_MESSAGE);
							break;
							default:
							JOptionPane.showMessageDialog(contentPane, "An error occured", "Error",
									JOptionPane.ERROR_MESSAGE);
							break;
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
				if (columns.get(columnIndex).getSecond().equals("roomAdditions")) {
					return String.join(", ", ((Room) data.get(rowIndex)).getRoomAdditions().stream()
							.map(ra -> ra.getName()).toArray(String[]::new));
				}
				if (columns.get(columnIndex).getSecond().equals("type")) {
					if (((Room) data.get(rowIndex)).getType() == null)
						return "";
					return ((Room) data.get(rowIndex)).getType().getName();
				}
				return super.getValueAt(rowIndex, columnIndex);
			}

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				if (columns.get(columnIndex).getSecond().equals("roomAdditions")) {
					return String.class;
				}
				if (columns.get(columnIndex).getSecond().equals("type")) {
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
			// @SuppressWarnings("unchecked")
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
					e1.printStackTrace();
				}
				dataPanel.getTable().updateUI();
			}
		});

		tabbedPane.addTab("Rooms", new ImageIcon("./assets/icons/rooms.png"), dataPanel, null);
	}

	private void addRoomAdditionsTab() {
		ArrayList<Pair<String, String>> columns = new ArrayList<Pair<String, String>>() {
			/**
			* 
			*/
			private static final long serialVersionUID = 4215012548686790091L;

			{
				add(new Pair<String, String>("ID", "id")); // 0
				add(new Pair<String, String>("Name", "name")); // 1
			}
		};
		CustomTableModel<RoomAddition> model = new CustomTableModel<RoomAddition>(columns,
				new CustomTableModel.TableDataManiplations<RoomAddition>() {

					@Override
					public ArrayList<RoomAddition> getData() {
						return RoomController.getRoomAdditions();
					}

					@Override
					public void edit(RoomAddition model) throws NoElementException {
						RoomController.updateRoomAddition(model);
					}

					@Override
					public void remove(RoomAddition model) throws NoElementException {
						RoomController.deleteRoomAddition(model);
					}

					@Override
					public void add(RoomAddition model) throws DuplicateIndexException {
						RoomController.addRoomAddition(model);
					}

				}, new RoomAddition());
		DataPanel<RoomAddition> dataPanel = new DataPanel<RoomAddition>(model);
		TableColumnModel columnModel = dataPanel.getTable().getColumnModel();
		columnModel.getColumn(0).setMinWidth(150);
		columnModel.getColumn(1).setMinWidth(200);

		dataPanel.getAddBtn().setText("Add Room Addition");
		dataPanel.getEditBtn().setText("Edit Room Addition");
		dataPanel.getDeleteBtn().setText("Delete Room Addition");

		dataPanel.getRefreshBtn().addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {
				((CustomTableModel<RoomAddition>) dataPanel.getTable().getModel()).refresh();
				dataPanel.getTable().updateUI();
			}
		});

		dataPanel.getAddBtn().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				AddRoomAdditionDialog dialog = new AddRoomAdditionDialog();
				dialog.setVisible(true);
				dialog.addWindowListener(new WindowAdapter() {
					@SuppressWarnings("unchecked")
					@Override
					public void windowClosed(WindowEvent e) {
						if (!dialog.isOk())
							return;
						try {
							((CustomTableModel<RoomAddition>) dataPanel.getTable().getModel())
									.add(new RoomAddition(dialog.getValue()));
						} catch (DuplicateIndexException e1) {
							JOptionPane.showMessageDialog(contentPane, "Room addition with this name already exists",
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
				CustomTableModel<RoomAddition> customTableModel = (CustomTableModel<RoomAddition>) dataPanel.getTable()
						.getModel();
				RoomAddition roomAddition = (RoomAddition) customTableModel.get(dataPanel.getTable().getSelectedRow());
				EditRoomAdditionDialog dialog = new EditRoomAdditionDialog(roomAddition.getName());
				dialog.setVisible(true);
				dialog.addWindowListener(new WindowAdapter() {
					@SuppressWarnings("rawtypes")
					@Override
					public void windowClosed(WindowEvent e) {
						if (!dialog.isOk())
							return;
						try {
							roomAddition.setName(dialog.getValue());
							((CustomTableModel) dataPanel.getTable().getModel()).edit(roomAddition);
							;
						} catch (NoElementException e1) {
							JOptionPane.showMessageDialog(contentPane, "Room addition not found", "Error",
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
				CustomTableModel<RoomAddition> customTableModel = (CustomTableModel<RoomAddition>) dataPanel.getTable()
						.getModel();
				int res = JOptionPane.showConfirmDialog(contentPane,
						"Are you sure you want to delete this room addition?", "Delete room addition",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (res != JOptionPane.YES_OPTION)
					return;
				try {
					customTableModel.remove(dataPanel.getTable().getSelectedRow());
				} catch (NoElementException e1) {
					e1.printStackTrace();
				}
				dataPanel.getTable().updateUI();
			}
		});
		tabbedPane.addTab("Room Additions", new ImageIcon("./assets/icons/room_additions.png"), dataPanel, null);
	}

	private void addRoomTypesTab() {
		ArrayList<Pair<String, String>> columns = new ArrayList<Pair<String, String>>() {
			/**
			* 
			*/
			private static final long serialVersionUID = 4215012548686790091L;

			{
				add(new Pair<String, String>("ID", "id")); // 0
				add(new Pair<String, String>("Name", "name")); // 1
			}
		};
		CustomTableModel<RoomType> model = new CustomTableModel<RoomType>(columns,
				new CustomTableModel.TableDataManiplations<RoomType>() {

					@Override
					public ArrayList<RoomType> getData() {
						return RoomController.getRoomTypes();
					}

					@Override
					public void edit(RoomType model) throws NoElementException {
						RoomController.updateRoomType(model);
					}

					@Override
					public void remove(RoomType model) throws NoElementException {
						RoomController.deleteRoomType(model);
					}

					@Override
					public void add(RoomType model) throws DuplicateIndexException {
						RoomController.addRoomType(model);
					}

				}, new RoomType());
		DataPanel<RoomType> dataPanel = new DataPanel<RoomType>(model);
		TableColumnModel columnModel = dataPanel.getTable().getColumnModel();
		columnModel.getColumn(0).setMinWidth(150);
		columnModel.getColumn(1).setMinWidth(200);

		dataPanel.getAddBtn().setText("Add Room Type");
		dataPanel.getEditBtn().setText("Edit Room Type");
		dataPanel.getDeleteBtn().setText("Delete Room Type");

		dataPanel.getRefreshBtn().addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {
				((CustomTableModel<RoomAddition>) dataPanel.getTable().getModel()).refresh();
				dataPanel.getTable().updateUI();
			}
		});

		dataPanel.getAddBtn().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				AddRoomTypesDialog dialog = new AddRoomTypesDialog();
				dialog.setVisible(true);
				dialog.addWindowListener(new WindowAdapter() {
					@SuppressWarnings("unchecked")
					@Override
					public void windowClosed(WindowEvent e) {
						if (!dialog.isOk())
							return;
						try {
							((CustomTableModel<RoomType>) dataPanel.getTable().getModel())
									.add(new RoomType(dialog.getValue()));
						} catch (DuplicateIndexException e1) {
							JOptionPane.showMessageDialog(contentPane, "Room type with this name already exists",
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
				CustomTableModel<RoomType> customTableModel = (CustomTableModel<RoomType>) dataPanel.getTable()
						.getModel();
				RoomType roomType = (RoomType) customTableModel.get(dataPanel.getTable().getSelectedRow());
				EditRoomTypeDialog dialog = new EditRoomTypeDialog(roomType.getName());
				dialog.setVisible(true);
				dialog.addWindowListener(new WindowAdapter() {
					@SuppressWarnings("rawtypes")
					@Override
					public void windowClosed(WindowEvent e) {
						if (!dialog.isOk())
							return;
						try {
							roomType.setName(dialog.getValue());
							((CustomTableModel) dataPanel.getTable().getModel()).edit(roomType);
							;
						} catch (NoElementException e1) {
							JOptionPane.showMessageDialog(contentPane, "Room type not found", "Error",
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
				CustomTableModel<RoomType> customTableModel = (CustomTableModel<RoomType>) dataPanel.getTable()
						.getModel();
				int res = JOptionPane.showConfirmDialog(contentPane, "Are you sure you want to delete this room type?",
						"Delete room type", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (res != JOptionPane.YES_OPTION)
					return;
				try {
					if (RoomController.isRoomTypeUsed(customTableModel.get(dataPanel.getTable().getSelectedRow()))) {
						JOptionPane.showMessageDialog(contentPane,
								"Room type is used in some rooms. Change those room's type first and try again.",
								"Error", JOptionPane.ERROR_MESSAGE);
					}
					customTableModel.remove(dataPanel.getTable().getSelectedRow());
				} catch (NoElementException e1) {
					e1.printStackTrace();
				}
				dataPanel.getTable().updateUI();
			}
		});
		tabbedPane.addTab("Room Types", new ImageIcon("./assets/icons/room_types.png"), dataPanel, null);
	}

	private void addReservationAdditionsTab() {
		ArrayList<Pair<String, String>> columns = new ArrayList<Pair<String, String>>() {
			/**
			* 
			*/
			private static final long serialVersionUID = 4215012548686790091L;

			{
				add(new Pair<String, String>("ID", "id")); // 0
				add(new Pair<String, String>("Name", "name")); // 1
			}
		};
		CustomTableModel<ReservationAddition> model = new CustomTableModel<ReservationAddition>(columns,
				new CustomTableModel.TableDataManiplations<ReservationAddition>() {

					@Override
					public ArrayList<ReservationAddition> getData() {
						return ReservationController.getReservationAdditions();
					}

					@Override
					public void edit(ReservationAddition model) throws NoElementException {
						ReservationController.updateReservationAddition(model);
					}

					@Override
					public void remove(ReservationAddition model) throws NoElementException {
						ReservationController.deleteReservationAddition(model);
					}

					@Override
					public void add(ReservationAddition model) throws DuplicateIndexException {
						ReservationController.addReservationAddition(model);
					}

				}, new ReservationAddition());
		DataPanel<ReservationAddition> dataPanel = new DataPanel<ReservationAddition>(model);
		TableColumnModel columnModel = dataPanel.getTable().getColumnModel();
		columnModel.getColumn(0).setMinWidth(150);
		columnModel.getColumn(1).setMinWidth(200);

		dataPanel.getAddBtn().setText("Add Reservation Addition");
		dataPanel.getEditBtn().setText("Edit Reservation Addition");
		dataPanel.getDeleteBtn().setText("Delete Reservation Addition");

		dataPanel.getRefreshBtn().addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {
				((CustomTableModel<ReservationAddition>) dataPanel.getTable().getModel()).refresh();
				dataPanel.getTable().updateUI();
			}
		});

		dataPanel.getAddBtn().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				AddReservationAdditionDialog dialog = new AddReservationAdditionDialog();
				dialog.setVisible(true);
				dialog.addWindowListener(new WindowAdapter() {
					@SuppressWarnings("unchecked")
					@Override
					public void windowClosed(WindowEvent e) {
						if (!dialog.isOk())
							return;
						try {
							((CustomTableModel<ReservationAddition>) dataPanel.getTable().getModel())
									.add(new ReservationAddition(dialog.getValue()));
						} catch (DuplicateIndexException e1) {
							JOptionPane.showMessageDialog(contentPane, "Reservation addition with this name already exists",
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
				CustomTableModel<ReservationAddition> customTableModel = (CustomTableModel<ReservationAddition>) dataPanel.getTable()
						.getModel();
				ReservationAddition reservationAddition = (ReservationAddition) customTableModel.get(dataPanel.getTable().getSelectedRow());
				EditRoomAdditionDialog dialog = new EditRoomAdditionDialog(reservationAddition.getName());
				dialog.setVisible(true);
				dialog.addWindowListener(new WindowAdapter() {
					@SuppressWarnings("rawtypes")
					@Override
					public void windowClosed(WindowEvent e) {
						if (!dialog.isOk())
							return;
						try {
							reservationAddition.setName(dialog.getValue());
							((CustomTableModel) dataPanel.getTable().getModel()).edit(reservationAddition);
							;
						} catch (NoElementException e1) {
							JOptionPane.showMessageDialog(contentPane, "Reservation addition not found", "Error",
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
				CustomTableModel<ReservationAddition> customTableModel = (CustomTableModel<ReservationAddition>) dataPanel.getTable()
						.getModel();
				int res = JOptionPane.showConfirmDialog(contentPane,
						"Are you sure you want to delete this reservation addition?", "Delete reservation addition",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (res != JOptionPane.YES_OPTION)
					return;
				try {
					customTableModel.remove(dataPanel.getTable().getSelectedRow());
				} catch (NoElementException e1) {
					e1.printStackTrace();
				}
				dataPanel.getTable().updateUI();
			}
		});
		tabbedPane.addTab("Room Additions", new ImageIcon("./assets/icons/reservation_additions.png"), dataPanel, null);
	}

	private void addReservationsTab() {
		ArrayList<Pair<String, String>> columns = new ArrayList<Pair<String, String>>() {
			/**
			* 
			*/
			private static final long serialVersionUID = 4215012548686790091L;

			{
				add(new Pair<String, String>("ID", "id")); // 0
				add(new Pair<String, String>("Status", "status")); // 1
				add(new Pair<String, String>("Guest", "guest")); // 2
				add(new Pair<String, String>("Room Type", "roomType")); // 3
				add(new Pair<String, String>("Start Date", "startDate")); // 4
				add(new Pair<String, String>("End Date", "endDate")); // 5
				add(new Pair<String, String>("Room Additions", "roomAdditions")); // 6
				add(new Pair<String, String>("Reservation Additions", "reservationAdditions")); // 7
				add(new Pair<String, String>("Price", "price")); // 8
			}
		};
		CustomTableModel<Reservation> model = new CustomTableModel<Reservation>(columns,
				new CustomTableModel.TableDataManiplations<Reservation>() {

					@Override
					public ArrayList<Reservation> getData() {
						return ReservationController.getReservations();
					}

					@Override
					public void edit(Reservation model) throws NoElementException {
						ReservationController.updateReservation(model);
					}

					@Override
					public void remove(Reservation model) throws NoElementException {
						ReservationController.deleteReservation(model);
					}

					@Override
					public void add(Reservation model) throws DuplicateIndexException {
						ReservationController.addReservation(model);
					}

				}, new Reservation()) {

					private static final long serialVersionUID = -172170127567309241L;
					
					@Override
					public Object getValueAt(int rowIndex, int columnIndex) {
						if (columns.get(columnIndex).getSecond().equals("roomAdditions")) {
							return String.join(", ", ((Reservation) data.get(rowIndex)).getRoomAdditions().stream()
									.map(ra -> ra.getName()).toArray(String[]::new));
						}
						if (columns.get(columnIndex).getSecond().equals("reservationAdditions")) {
							return String.join(", ", ((Reservation) data.get(rowIndex)).getReservationAdditions().stream()
									.map(ra -> ra.getName()).toArray(String[]::new));
						}
						if (columns.get(columnIndex).getSecond().equals("roomType")) {
							if (((Reservation) data.get(rowIndex)).getRoomType() == null)
								return "";
							return ((Reservation) data.get(rowIndex)).getRoomType().getName();
						}
						if (columns.get(columnIndex).getSecond().equals("guest")) {
							Guest g = ((Guest) data.get(rowIndex).getGuest());
							return g.getName() + " " + g.getSurname();
						}
						return super.getValueAt(rowIndex, columnIndex);
					}

					@Override
					public Class<?> getColumnClass(int columnIndex) {
						if (columns.get(columnIndex).getSecond().equals("roomAdditions")) {
							return String.class;
						}
						if (columns.get(columnIndex).getSecond().equals("reservationAdditions")) {
							return String.class;
						}
						if (columns.get(columnIndex).getSecond().equals("roomType")) {
							return String.class;
						}
						if (columns.get(columnIndex).getSecond().equals("guest")) {
							return String.class;
						}
						return super.getColumnClass(columnIndex);
					}
			
		};
		DataPanel<Reservation> dataPanel = new DataPanel<Reservation>(model);
		TableColumnModel columnModel = dataPanel.getTable().getColumnModel();
		columnModel.getColumn(0).setMinWidth(150);
		columnModel.getColumn(1).setMinWidth(150);
		columnModel.getColumn(2).setMinWidth(250);
		columnModel.getColumn(3).setMinWidth(200);
		columnModel.getColumn(4).setMinWidth(150);
		columnModel.getColumn(5).setMinWidth(150);
		columnModel.getColumn(6).setMinWidth(350);
		columnModel.getColumn(7).setMinWidth(350);
		columnModel.getColumn(8).setMinWidth(150);

		dataPanel.getAddBtn().setText("Add Reservation");
		dataPanel.getEditBtn().setText("Edit Reservation");
		dataPanel.getDeleteBtn().setText("Delete Reservation");

		dataPanel.getRefreshBtn().addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {
				((CustomTableModel<ReservationAddition>) dataPanel.getTable().getModel()).refresh();
				dataPanel.getTable().updateUI();
			}
		});

		dataPanel.getAddBtn().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddReservationDialog dialog = new AddReservationDialog(null);
				dialog.setVisible(true);
				dialog.addWindowListener(new WindowAdapter() {
					@SuppressWarnings("unchecked")
					@Override
					public void windowClosed(WindowEvent e) {
						if (!dialog.isOk())
							return;
						try {
							((CustomTableModel<Reservation>) dataPanel.getTable().getModel())
									.add(dialog.getReservation());
						} catch (DuplicateIndexException e1) {
							JOptionPane.showMessageDialog(contentPane, "Reservation with this ID already exists",
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
				CustomTableModel<Reservation> customTableModel = (CustomTableModel<Reservation>) dataPanel.getTable()
						.getModel();
				Reservation reservation = (Reservation) customTableModel.get(dataPanel.getTable().getSelectedRow());
				EditReservationDialog dialog = new EditReservationDialog(reservation);
				dialog.setVisible(true);
				dialog.addWindowListener(new WindowAdapter() {
					@SuppressWarnings("rawtypes")
					@Override
					public void windowClosed(WindowEvent e) {
						if (!dialog.isOk())
							return;
						try {
							((CustomTableModel) dataPanel.getTable().getModel()).edit(reservation);
							;
						} catch (NoElementException e1) {
							JOptionPane.showMessageDialog(contentPane, "Reservation not found", "Error",
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
				CustomTableModel<Reservation> customTableModel = (CustomTableModel<Reservation>) dataPanel.getTable()
						.getModel();
				int res = JOptionPane.showConfirmDialog(contentPane,
						"Are you sure you want to delete this reservation?", "Delete reservation",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (res != JOptionPane.YES_OPTION)
					return;
				try {
					customTableModel.remove(dataPanel.getTable().getSelectedRow());
				} catch (NoElementException e1) {
					e1.printStackTrace();
				}
				dataPanel.getTable().updateUI();
			}
		});
		
		tabbedPane.addTab("Reservations", new ImageIcon("./assets/icons/reservations.png"), dataPanel, null);
	}
	
}
