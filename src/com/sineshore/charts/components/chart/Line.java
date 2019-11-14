package com.sineshore.charts.components.chart;

import static com.sineshore.charts.utilities.Utilities.*;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import com.sineshore.charts.utilities.Format;

public class Line {

	private static final int HOVER_MARGIN = 20;

	private double lineValue;
	private Color highlight;
	protected Color color;
	protected int y;

	private boolean hover;

	public Line(double lineValue, Color color) {
		this.lineValue = lineValue;
		this.color = color;
		highlight = Color.WHITE;
	}

	public void draw(Graphics2D g, Chart chart) {
		y = chart.yValueToPixel(lineValue);
		int width = chart.chart.getWidth();
		g.setFont(Chart.FONT.deriveFont(16f));
		FontMetrics metrics = g.getFontMetrics();

		if (inBoundary(chart.getCursor().y, y - HOVER_MARGIN, HOVER_MARGIN * 2) && inBoundary(chart.getCursor().x, 0, width)) {
			hover = true;
		} else {
			hover = false;
		}

		String label = Format.format(lineValue);

		g.setColor(color);
		g.drawLine(0, y, width, y);
		g.fillRect(0, y, metrics.stringWidth(label), metrics.getHeight());

		if (hover) {
			g.setColor(highlight);
			g.drawLine(0, y + 1, width, y + 1);
		}

		g.setColor(Color.WHITE);
		g.drawString(label, 0, y + metrics.getHeight() - 4);
	}

}
