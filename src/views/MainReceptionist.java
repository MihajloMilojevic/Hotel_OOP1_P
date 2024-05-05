package views;

import java.awt.Color;
import java.awt.Frame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import utils.WindowUtils;

public class MainReceptionist extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public MainReceptionist() {
		setIconImage(WindowUtils.getIconImage());
		setTitle("MHotelify | Receptionist");
		setForeground(new Color(255, 255, 255));
		setBackground(new Color(73, 73, 73));
		setBounds(200, 100, 600, 600);
		setLocationRelativeTo(null);
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		addWindowListener(WindowUtils.getWindowClosing());
	}

}
