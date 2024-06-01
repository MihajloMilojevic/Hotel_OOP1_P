package views.admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import utils.WindowUtils;
import views.components.Tab;

public class MainAdmin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTabbedPane tabbedPane;
	private ArrayList<Tab<?>> tabs;

	@SuppressWarnings("unchecked")
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

		tabs = new ArrayList<Tab<?>>();
		
		addEmpoloyeesTab();
		addGuestsTab();
		addRoomsTab();
		addRoomAdditionsTab();
		addRoomTypesTab();
		//addReservationAdditionsTab();
		//addReservationsTab();
		
		for (Tab<?> tab : tabs) {
			tab.setTabs((ArrayList<Tab<?>>) tabs.clone());
			tab.removeTab(tab);
		}

		addWindowListener(WindowUtils.getWindowClosing());

	}

	private void addMenu() {
		setJMenuBar(new AdminMenu(this));
	}

	private void addGuestsTab() {
		GuestsTab guestsTab = new GuestsTab(this);
		tabs.add(guestsTab);
		tabbedPane.addTab("Guests", new ImageIcon("./assets/icons/guests.png"), guestsTab.getDataPanel(), null);
	}

	private void addEmpoloyeesTab() {
		EmployeesTab employeesTab = new EmployeesTab(this);
		tabs.add(employeesTab);
		tabbedPane.addTab("Employees", new ImageIcon("./assets/icons/employees.png"), employeesTab.getDataPanel(),
				null);
	}

	private void addRoomsTab() {
		RoomsTab roomsTab = new RoomsTab(this);
		tabs.add(roomsTab);
		tabbedPane.addTab("Rooms", new ImageIcon("./assets/icons/rooms.png"), roomsTab.getDataPanel(), null);
	}

	private void addRoomAdditionsTab() {
		RoomAdditionsTab roomsAdditionsTab = new RoomAdditionsTab(this);
		tabs.add(roomsAdditionsTab);
		tabbedPane.addTab("Room Additions", new ImageIcon("./assets/icons/room_additions.png"),
				roomsAdditionsTab.getDataPanel(), null);
	}

	private void addRoomTypesTab() {
		RoomTypesTab roomTypesTab = new RoomTypesTab(this);
		tabs.add(roomTypesTab);
		tabbedPane.addTab("Room Types", new ImageIcon("./assets/icons/room_types.png"), roomTypesTab.getDataPanel(), null);
	}
/*
	private void addReservationAdditionsTab() {
		ReservationAdditionsTab reservationAdditionsTab = new ReservationAdditionsTab(this);
		tabs.add(reservationAdditionsTab);
		tabbedPane.addTab("Reservation Additions", new ImageIcon("./assets/icons/reservation_additions.png"),
				reservationAdditionsTab.getDataPanel(), null);
	}
*/
/*
	private void addReservationsTab() {
		ArrayList<Pair<String, String>> columns = new ArrayList<Pair<String, String>>() {

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
*/
}
