package views.dialogs.room_types;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import models.RoomType;

public class AddRoomTypesDialog extends JDialog {

	public RoomType getRoomType() {
		return roomType;
	}
	
	public double getInitialPrice() {
		return initialPrice;
	}

	/**
	 * @return the ok
	 */
	public boolean isOk() {
		return ok;
	}

	private static final long serialVersionUID = 1L;
	private boolean ok = false;
	private final JPanel contentPanel = new JPanel();
	private JTextField valueTf;
	private JSpinner capacitySp;
	private JSpinner priceSp;
	private JLabel titleLb;
	private RoomType roomType;
	private double initialPrice;

	/**
	 * Create the dialog.
	 */
	public AddRoomTypesDialog() {
		roomType = new RoomType();
		setTitle("Add Room Type");
		setResizable(false);
		setModal(true);
		setIconImage(new ImageIcon("./assets/icons/room_types.png").getImage());
		getContentPane().setForeground(new Color(255, 255, 255));
		setForeground(new Color(255, 255, 255));
		getContentPane().setBackground(new Color(73, 73, 73));
		setBackground(new Color(73, 73, 73));
		setBounds(100, 100, 450, 250);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setForeground(new Color(255, 255, 255));
		contentPanel.setBackground(new Color(73, 73, 73));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			titleLb = new JLabel("Add Room Type");
			titleLb.setHorizontalAlignment(SwingConstants.CENTER);
			titleLb.setForeground(Color.WHITE);
			titleLb.setFont(new Font("Times New Roman", Font.PLAIN, 24));
			GridBagConstraints gbc_titleLb = new GridBagConstraints();
			gbc_titleLb.fill = GridBagConstraints.BOTH;
			gbc_titleLb.gridwidth = 2;
			gbc_titleLb.insets = new Insets(0, 0, 5, 0);
			gbc_titleLb.gridx = 0;
			gbc_titleLb.gridy = 0;
			contentPanel.add(titleLb, gbc_titleLb);
		}
		{
			Component verticalStrut = Box.createVerticalStrut(15);
			GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
			gbc_verticalStrut.insets = new Insets(0, 0, 5, 5);
			gbc_verticalStrut.gridx = 0;
			gbc_verticalStrut.gridy = 1;
			contentPanel.add(verticalStrut, gbc_verticalStrut);
		}
		{
			JLabel lblCategoryName = new JLabel("Name: ");
			lblCategoryName.setHorizontalAlignment(SwingConstants.LEFT);
			lblCategoryName.setForeground(Color.WHITE);
			lblCategoryName.setFont(new Font("Times New Roman", Font.PLAIN, 14));
			GridBagConstraints gbc_lblCategoryName = new GridBagConstraints();
			gbc_lblCategoryName.insets = new Insets(0, 0, 0, 5);
			gbc_lblCategoryName.anchor = GridBagConstraints.EAST;
			gbc_lblCategoryName.gridx = 0;
			gbc_lblCategoryName.gridy = 4;
			contentPanel.add(lblCategoryName, gbc_lblCategoryName);
		}
		{
			valueTf = new JTextField();
			valueTf.setColumns(10);
			GridBagConstraints gbc_valueTf = new GridBagConstraints();
			gbc_valueTf.fill = GridBagConstraints.HORIZONTAL;
			gbc_valueTf.gridx = 1;
			gbc_valueTf.gridy = 4;
			contentPanel.add(valueTf, gbc_valueTf);
		}
		{
			Component verticalStrut = Box.createVerticalStrut(5);
			GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
			gbc_verticalStrut.insets = new Insets(0, 0, 5, 5);
			gbc_verticalStrut.gridx = 0;
			gbc_verticalStrut.gridy = 5;
			contentPanel.add(verticalStrut, gbc_verticalStrut);
		}
		{
			JLabel lblCategoryName = new JLabel("Max Capacity: ");
			lblCategoryName.setHorizontalAlignment(SwingConstants.LEFT);
			lblCategoryName.setForeground(Color.WHITE);
			lblCategoryName.setFont(new Font("Times New Roman", Font.PLAIN, 14));
			GridBagConstraints gbc_lblCategoryName = new GridBagConstraints();
			gbc_lblCategoryName.insets = new Insets(0, 0, 0, 5);
			gbc_lblCategoryName.anchor = GridBagConstraints.EAST;
			gbc_lblCategoryName.gridx = 0;
			gbc_lblCategoryName.gridy = 6;
			contentPanel.add(lblCategoryName, gbc_lblCategoryName);
		}
		{
			capacitySp = new JSpinner();
			capacitySp.setModel(new SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));
			capacitySp.setFont(new Font("Times New Roman", Font.PLAIN, 14));
			GridBagConstraints gbc_spinner = new GridBagConstraints();
			gbc_spinner.fill = GridBagConstraints.HORIZONTAL;
			gbc_spinner.insets = new Insets(0, 0, 5, 0);
			gbc_spinner.gridx = 1;
			gbc_spinner.gridy = 6;
			contentPanel.add(capacitySp, gbc_spinner);
		}
		{
			Component verticalStrut = Box.createVerticalStrut(5);
			GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
			gbc_verticalStrut.insets = new Insets(0, 0, 5, 5);
			gbc_verticalStrut.gridx = 0;
			gbc_verticalStrut.gridy = 7;
			contentPanel.add(verticalStrut, gbc_verticalStrut);
		}
		{
			JLabel lblCategoryName = new JLabel("Initial Price: ");
			lblCategoryName.setHorizontalAlignment(SwingConstants.LEFT);
			lblCategoryName.setForeground(Color.WHITE);
			lblCategoryName.setFont(new Font("Times New Roman", Font.PLAIN, 14));
			GridBagConstraints gbc_lblCategoryName = new GridBagConstraints();
			gbc_lblCategoryName.insets = new Insets(0, 0, 0, 5);
			gbc_lblCategoryName.anchor = GridBagConstraints.EAST;
			gbc_lblCategoryName.gridx = 0;
			gbc_lblCategoryName.gridy = 8;
			contentPanel.add(lblCategoryName, gbc_lblCategoryName);
		}
		{
			priceSp = new JSpinner();
			priceSp.setModel(new SpinnerNumberModel(Double.valueOf(0), Double.valueOf(0), null, Double.valueOf(50)));
			priceSp.setFont(new Font("Times New Roman", Font.PLAIN, 14));
			GridBagConstraints gbc_spinner = new GridBagConstraints();
			gbc_spinner.fill = GridBagConstraints.HORIZONTAL;
			gbc_spinner.insets = new Insets(0, 0, 5, 0);
			gbc_spinner.gridx = 1;
			gbc_spinner.gridy = 8;
			contentPanel.add(priceSp, gbc_spinner);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setForeground(new Color(255, 255, 255));
			buttonPane.setBackground(new Color(73, 73, 73));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setFont(new Font("Times New Roman", Font.PLAIN, 14));
				okButton.setActionCommand("OK");
				okButton.addActionListener(e -> {
					if (valueTf.getText().trim().isBlank()) {
						JOptionPane.showMessageDialog(this, "Room type's name cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
					if ((int) capacitySp.getValue() < 1) {
						JOptionPane.showMessageDialog(this, "Room type's capacity must be greater than 0.", "Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
					if ((double) priceSp.getValue() < 1) {
						JOptionPane.showMessageDialog(this, "Room type's price must be greater than 0.", "Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
					roomType.setName(valueTf.getText());
					roomType.setMaxCapacity((int) capacitySp.getValue());
					initialPrice = (double) priceSp.getValue();
					ok = true;
					dispose();
				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setFont(new Font("Times New Roman", Font.PLAIN, 14));
				cancelButton.setActionCommand("Cancel");
				cancelButton.addActionListener(e -> {
					dispose();
				});
				buttonPane.add(cancelButton);
			}
		}

	}
	
}
