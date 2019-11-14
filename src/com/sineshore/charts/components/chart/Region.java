package com.sineshore.charts.components.chart;

import java.awt.Color;
import java.awt.Graphics2D;

public class Region {

	private Line top;
	private Line bottom;

	public Region(double top, double bottom, Color color) {
		if (top > bottom) {
			this.top = new Line(top, color);
			this.bottom = new Line(bottom, color);
		} else {
			this.top = new Line(bottom, color);
			this.bottom = new Line(top, color);
		}
	}

	public void draw(Graphics2D g, Chart chart) {
		g.setColor(new Color(top.color.getRed(), top.color.getGreen(), top.color.getBlue(), 90));
		g.fillRect(0, top.y, chart.chart.getWidth() - Chart.RIGHT_WIDTH, bottom.y - top.y);
		top.draw(g, chart);
		bottom.draw(g, chart);
	}

}
