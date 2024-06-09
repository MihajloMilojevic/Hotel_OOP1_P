package views.admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategorySeries.CategorySeriesRenderStyle;
import org.knowm.xchart.XChartPanel;

import controllers.ReportsController;
import controllers.ReportsController.Revenue;

public class ReportsTab extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public ReportsTab() {
		int y = 0;
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		panel.setForeground(new Color(255, 255, 255));
		panel.setBackground(new Color(73, 73, 73));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gridBagLayout);
		
		JLabel lblNewLabel = new JLabel("Reports");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = y++;
		panel.add(lblNewLabel, gbc_lblNewLabel);
		
		Component verticalStrut = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
		gbc_verticalStrut.insets = new Insets(0, 0, 5, 0);
		gbc_verticalStrut.gridx = 0;
		gbc_verticalStrut.gridy = y++;
		panel.add(verticalStrut, gbc_verticalStrut);
		
		JLabel lblRevenueInLast = new JLabel("Revenue in last 12 months:");
		lblRevenueInLast.setForeground(Color.WHITE);
		lblRevenueInLast.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		GridBagConstraints gbc_lblRevenueInLast = new GridBagConstraints();
		gbc_lblRevenueInLast.insets = new Insets(0, 0, 5, 0);
		gbc_lblRevenueInLast.anchor = GridBagConstraints.WEST;
		gbc_lblRevenueInLast.gridx = 0;
		gbc_lblRevenueInLast.gridy = y++;
		panel.add(lblRevenueInLast, gbc_lblRevenueInLast);
		
		Component verticalStrut_1 = Box.createVerticalStrut(5);
		GridBagConstraints gbc_verticalStrut_1 = new GridBagConstraints();
		gbc_verticalStrut_1.insets = new Insets(0, 0, 5, 0);
		gbc_verticalStrut_1.gridx = 0;
		gbc_verticalStrut_1.gridy = y++;
		panel.add(verticalStrut_1, gbc_verticalStrut_1);

		ArrayList<Revenue> revenues = ReportsController.getRevenue();
		CategoryChart revenueChart = new CategoryChart(1300, 600);
		revenueChart.getStyler().setDefaultSeriesRenderStyle(CategorySeriesRenderStyle.Line);
		revenueChart.getStyler().setMarkerSize(5);
		revenueChart.setCustomXAxisTickLabelsFormatter(i -> revenues.get(0).getMonths()[(int) (double)i]);
		XChartPanel<CategoryChart> revenueChartPanel = new XChartPanel<>(revenueChart);
		double xData[] = new double[12];
		for (int i = 0; i < 12; i++) {
			xData[i] = i;
		}
		for (Revenue revenue : revenues) {
			revenueChart.addSeries(revenue.getType().getName(), xData, revenue.getMonthlyRevenue());
        }
		revenueChartPanel.setSize((int)(panel.getSize().width*0.8), (int)(panel.getSize().height*0.8));
		GridBagConstraints gbc_revenueChart = new GridBagConstraints();
		gbc_revenueChart.insets = new Insets(0, 0, 5, 0);
		gbc_revenueChart.gridx = 0;
		gbc_revenueChart.gridy = y++;
		panel.add(revenueChartPanel, gbc_revenueChart);
		
		setLayout(new BorderLayout(0, 0));
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(panel);
		add(scrollPane, BorderLayout.CENTER);
	}

}
