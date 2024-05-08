package views.admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

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

import controllers.UserController;
import utils.WindowUtils;
import views.Login;
import views.settings.SettingsDialog;

public class MainAdmin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

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
				JOptionPane.showMessageDialog(contentPane, "Profile");	
			}
		});
		menu.add(item);
	
		
		item = new JMenuItem("Logout");
		item.setMnemonic(KeyEvent.VK_L);
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int res = JOptionPane.showConfirmDialog(contentPane, "Are you sure you want to logout?", "Logout",  JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (res != JOptionPane.YES_OPTION) return;
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
		
		contentPane = new JPanel();
		contentPane.setForeground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPane.setBackground(new Color(73, 73, 73));
		
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		tabbedPane.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		tabbedPane.setForeground(new Color(255, 255, 255));
		tabbedPane.setBackground(new Color(73, 73, 73));
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		contentPane.add(tabbedPane, BorderLayout.CENTER);

		tabbedPane.addTab("Employees", new ImageIcon("./assets/icons/employees.png"), new EmployeesTab(), null);
		tabbedPane.setEnabledAt(0, true);
		tabbedPane.setBackgroundAt(0, new Color(73, 73, 73));
		tabbedPane.setForegroundAt(0, new Color(255, 255, 255));
		

		tabbedPane.addTab("Guests", new ImageIcon("./assets/icons/guests.png"), new GuestsTab(), null);
		tabbedPane.setEnabledAt(1, true);
		tabbedPane.setBackgroundAt(1, new Color(73, 73, 73));
		tabbedPane.setForegroundAt(1, new Color(255, 255, 255));
		
		addWindowListener(WindowUtils.getWindowClosing());
		
	}

	
}
