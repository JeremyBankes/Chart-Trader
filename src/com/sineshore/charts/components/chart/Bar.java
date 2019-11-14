package com.sineshore.charts.components.chart;

import static com.sineshore.charts.utilities.Utilities.*;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Calendar;

public class Bar {

	private long relativeTo;

	private long time;
	public double open;
	public double high;
	public double low;
	public double close;

	public Bar(long time, double open, double high, double low, double close) {
		this.time = time;
		this.open = open;
		this.high = high;
		this.low = low;
		this.close = close;

		Calendar date = Calendar.getInstance();
		date.set(2015, 0, 1);
		relativeTo = date.getTime().getTime() / 1000;
	}

	public synchronized void draw(Graphics2D g, Chart chart) {
		int highPixel = chart.yValueToPixel(high);
		int lowPixel = chart.yValueToPixel(low);
		int openPixel = chart.yValueToPixel(open);
		int closePixel = chart.yValueToPixel(close);
		int xDraw = chart.xValueToPixel(relativeTime());

		int barWidth = (int) chart.barWidth();
		g.setColor(Chart.BAR_HIGHLIGHT_COLOR);
		g.drawLine(xDraw, highPixel, xDraw, lowPixel);
		boolean hover = inBoundary(chart.getCursor().x, xDraw - barWidth / 2, barWidth);
		if (close < open) {
			if (hover)
				g.setColor(Chart.RED_BAR_COLOR);
			else
				g.setColor(Chart.RED_BAR_COLOR);
		} else if (open < close) {
			if (hover)
				g.setColor(Chart.GREEN_BAR_COLOR);
			else
				g.setColor(Chart.GREEN_BAR_COLOR);
		} else {
			if (hover)
				g.setColor(Chart.YELLOW_BAR_COLOR);
			else
				g.setColor(Chart.YELLOW_BAR_COLOR);
		}
		if (open > close) {
			g.fillRect(xDraw - barWidth / 2, openPixel, barWidth, Math.max(1, closePixel - openPixel));
		} else {
			g.fillRect(xDraw - barWidth / 2, closePixel, barWidth, Math.max(1, openPixel - closePixel));
		}
		if (hover) {
			g.setColor(Color.WHITE);
			if (open > close) {
				g.drawRect(xDraw - barWidth / 2, openPixel, barWidth, Math.max(1, closePixel - openPixel));
			} else {
				g.drawRect(xDraw - barWidth / 2, closePixel, barWidth, Math.max(1, openPixel - closePixel));
			}
		}
	}

	public long relativeTime() {
		return time - relativeTo;
	}

}
