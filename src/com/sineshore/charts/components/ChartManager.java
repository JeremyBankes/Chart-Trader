package com.sineshore.charts.components;

import static com.sineshore.charts.utilities.Utilities.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.MatteBorder;

import com.sineshore.charts.Main;
import com.sineshore.charts.components.chart.Chart;
import com.sineshore.charts.utilities.ComponentFactory;

public class ChartManager {

	public static final int OPEN_CHARTS_HEIGHT = 40;
	public static final int CHART_LIST_PADDING = 40;
	public static final int INTERNAL_BORDER_WIDTH = 2;
	public static final Font FONT = new Font("Consolas", Font.PLAIN, 16);

	private static final Color BUTTON_COLOR = new Color(0xFFFFFF);
	private static final Color SELECTED_BUTTON_COLOR = new Color(0xe8faff);

	public OpenChart chart;

	public final JPanel content, parent, chartSelecton;
	public final JButton newChart;

	public ChartManager(JPanel parent) {
		this.parent = parent;

		content = new JPanel(new BorderLayout());
		chartSelecton = new JPanel(new FlowLayout(0, 0, 0));
		newChart = ComponentFactory.createButton(ComponentFactory.getIcon("/add.png"), OPEN_CHARTS_HEIGHT - INTERNAL_BORDER_WIDTH,
				OPEN_CHARTS_HEIGHT - INTERNAL_BORDER_WIDTH);

		chartSelecton.setPreferredSize(new Dimension(0, OPEN_CHARTS_HEIGHT));

		chartSelecton.setBorder(new MatteBorder(INTERNAL_BORDER_WIDTH, 0, 0, 0, Color.BLACK));
		newChart.setBorder(new MatteBorder(0, 0, 0, INTERNAL_BORDER_WIDTH, Color.BLACK));

		ComponentFactory.addAction(newChart, () -> {
			addChart();
			Main.window.validate();
		});

		chartSelecton.add(newChart);
		content.add(chartSelecton, BorderLayout.SOUTH);
		parent.add(content, BorderLayout.CENTER);
	}

	public void addChart() {
		OpenChart openChart = new OpenChart(new Chart(), "#" + chartSelecton.getComponentCount());
		chartSelecton.add(openChart, 0);

		setChart(openChart);
	}

	public void setChart(OpenChart chart) {
		if (this.chart != null)
			this.chart.setBackground(BUTTON_COLOR);
		chart.setBackground(SELECTED_BUTTON_COLOR);
		this.chart = chart;

		for (Component component : content.getComponents())
			if (component.getName() != null && component.getName().equals("Chart"))
				content.remove(component);

		content.add(chart.chart.content);
		content.repaint();
	}

	private class OpenChart extends JButton {

		private static final long serialVersionUID = 1L;

		public Chart chart;

		public OpenChart(Chart chart, String text) {
			super(text);
			this.chart = chart;
			setContentAreaFilled(false);
			setFocusPainted(false);
			setBackground(BUTTON_COLOR);
			setBorder(new MatteBorder(0, 0, 0, INTERNAL_BORDER_WIDTH, Color.BLACK));
			setFont(FONT);

			ComponentFactory.addAction(this, () -> {
				setChart(this);
			});

			setComponentPopupMenu(new JPopupMenu());

			setPreferredSize(new Dimension(getFontMetrics(FONT).stringWidth(text) + 100, chartSelecton.getHeight() - INTERNAL_BORDER_WIDTH));
		}

		protected void paintComponent(Graphics g) {
			if (model.isPressed()) {
				g.setColor(tinge(getBackground(), Color.BLACK, 0.2f));
			} else if (model.isRollover()) {
				g.setColor(tinge(getBackground(), Color.BLACK, 0.1f));
			} else {
				g.setColor(getBackground());
			}
			g.fillRect(0, 0, getWidth(), getHeight());
			super.paintComponent(g);
		}

	}

}
