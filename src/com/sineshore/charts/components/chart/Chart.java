package com.sineshore.charts.components.chart;

import static java.awt.RenderingHints.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import javax.swing.JPanel;

import com.sineshore.charts.components.DropDownMenu;

public class Chart {

	public static final float MAX_SCALE = 4f;
	public static final float MIN_SCALE = 0.05f;

	public static final Color CHART_COLOR = new Color(0xFFFFFF);
	public static final Color LINE_COLOR = new Color(0x2B5B7C);
	public static final Color SIDE_COLOR = new Color(0x759fa8);
	public static final Color GRID_COLOR = new Color(0xAAAAAA);
	public static final Color PEG_COLOR = new Color(0x000000);
	public static final Color LABEL_COLOR = new Color(0x000000);
	public static final Color TAG_COLOR = new Color(0xFFFFFF);
	public static final Color RED_BAR_COLOR = new Color(0xDD0000);
	public static final Color GREEN_BAR_COLOR = new Color(0x00DD00);
	public static final Color YELLOW_BAR_COLOR = new Color(0xDDDD00);
	public static final Color BAR_HIGHLIGHT_COLOR = new Color(0xFFFFFF);

	public static final Font FONT = new Font("Consolas", Font.PLAIN, 16);

	protected static final SimpleDateFormat MIN_SEC = new SimpleDateFormat("mm:ss");
	protected static final SimpleDateFormat HOUR_MIN_SEC = new SimpleDateFormat("HH:mm:ss");
	protected static final DecimalFormat DECIMAL_4_PLACES = new DecimalFormat("#0.0000");
	protected static final DecimalFormat DECIMAL_5_PLACES = new DecimalFormat("#0.00000");

	public static final int BOTTOM_HEIGHT = 50;
	public static final int RIGHT_WIDTH = 100;

	public static final int INTERNAL_BORDER_WIDTH = 2;
	public static final int TAG_PADDING = 2;
	public static final int RIGHT_LABEL_MARGIN_SIDE = 8;
	public static final int RIGHT_LABEL_MARGIN_TOP = 5;

	public static final float SCALE_SPEED = 0.05f;

	public static final Cursor EMPTY_CURSOR = Toolkit.getDefaultToolkit().createCustomCursor(new BufferedImage(1, 1, 2), new Point(), "Empty");

	private int xOffset, yOffset;
	private float xScale, yScale;
	private int xPixels, yPixels;
	private long xInterval;
	private double yInterval;
	private double pip = 0.0001;

	public final JPanel content, chart, bottom, right;
	public final DropDownMenu contextMenu;

	private Point cursor;

	private boolean hoverChart;
	private Point dragStart;
	private Point offsetStart;

	private ArrayList<Peg> xPegs;
	private ArrayList<Peg> yPegs;

	private ArrayList<Bar> bars;

	public Chart() {

		bars = new ArrayList<Bar>();
		xPegs = new ArrayList<Peg>();
		yPegs = new ArrayList<Peg>();
		cursor = new Point();

		content = new JPanel(new BorderLayout()) {

			private static final long serialVersionUID = 1L;

			protected void paintComponent(Graphics g) {
				before();
				super.paintComponent(g);
			}

		};

		chart = new JPanel() {

			private static final long serialVersionUID = 1L;

			protected void paintComponent(Graphics g) {
				drawChart((Graphics2D) g);
				drawBars((Graphics2D) g);
			}

		};

		bottom = new JPanel() {

			private static final long serialVersionUID = 1L;

			protected void paintComponent(Graphics g) {
				drawBottom((Graphics2D) g);
			}

		};

		right = new JPanel() {

			private static final long serialVersionUID = 1L;

			protected void paintComponent(Graphics g) {
				drawRight((Graphics2D) g);
			}

		};

		contextMenu = new DropDownMenu();

		chart.addMouseListener(new MouseAdapter() {

			public void mouseEntered(MouseEvent event) {
				hoverChart = true;
			}

			public void mouseExited(MouseEvent event) {
				hoverChart = false;
				content.repaint();
			}

			public void mousePressed(MouseEvent event) {
				if (event.getButton() == MouseEvent.BUTTON1) {
					dragStart = event.getPoint();
					offsetStart = new Point(xOffset, yOffset);
				}
			}

			public void mouseReleased(MouseEvent event) {
				dragStart = null;
				offsetStart = null;
			}

		});

		chart.addMouseMotionListener(new MouseAdapter() {
			public void mouseMoved(MouseEvent event) {
				cursor.setLocation(event.getPoint());
				content.repaint();
			}

			public void mouseDragged(MouseEvent event) {
				if (dragStart != null && offsetStart != null) {
					xOffset = event.getX() - dragStart.x + offsetStart.x;
					yOffset = event.getY() - dragStart.y + offsetStart.y;
				}
				cursor.setLocation(event.getPoint());
				content.repaint();
			}
		});

		chart.addMouseWheelListener(new MouseWheelListener() {

			public void mouseWheelMoved(MouseWheelEvent event) {

				int xPixelBefore = xValueToPixel(xPixelToValue(cursor.x)) + xOffset;

				float rotation = event.getWheelRotation();
				xScale -= xScale * rotation * SCALE_SPEED;
				yScale -= yScale * rotation * SCALE_SPEED;

				xOffset += xPixelBefore - xValueToPixel(xPixelToValue(cursor.x)) - xOffset;

				content.repaint();

			}
		});

		content.setName("Chart");
		chart.setCursor(EMPTY_CURSOR);
		right.setPreferredSize(new Dimension(RIGHT_WIDTH, 0));
		bottom.setPreferredSize(new Dimension(0, BOTTOM_HEIGHT));

		content.add(chart, BorderLayout.CENTER);
		content.add(right, BorderLayout.EAST);
		content.add(bottom, BorderLayout.SOUTH);

		xOffset = 0;
		yOffset = 0;
		xScale = 1f;
		yScale = 1f;
		xPixels = 50;
		yPixels = 50;
		xInterval = 30;
		yInterval = 0.0001;
	}

	protected void before() {
		xPegs.clear();
		double xDistance = xPixels * xScale;
		long xCurrent = -floorDiv(xOffset, xDistance) * xInterval;
		long xEnd = (long) Math.floor(div(chart.getWidth(), xDistance) - div(xOffset, xDistance)) * xInterval;
		while (xCurrent <= xEnd) {
			xPegs.add(new Peg(xValueToPixel(xCurrent), xCurrent, true));
			xCurrent += xInterval;
		}
		FontMetrics metrics = bottom.getFontMetrics(FONT);
		int spacing = metrics.stringWidth(Collections.max(xPegs, Comparator.comparing(peg -> peg.label.length())).label) + 10;
		int interval = (int) Math.ceil((float) spacing / xDistance);
		for (int i = 0, len = xPegs.size(); i < len; i++) {
			Peg peg = xPegs.get(i);
			if (peg.value % (interval * xInterval) == 0) {
				peg.shouldLabel = true;
			}
		}

		yPegs.clear();
		double yDistance = yPixels * yScale;
		double yCurrent = -floorDiv(yOffset, yDistance) * yInterval;
		double yEnd = Math.floor(div(chart.getWidth(), yDistance) - div(yOffset, yDistance)) * yInterval;
		while (yCurrent <= yEnd) {
			yPegs.add(new Peg(yValueToPixel(yCurrent), yCurrent, false));
			yCurrent += yInterval;
		}
		interval = 2;
		for (int i = 0, len = yPegs.size(); i < len; i++) {
			Peg peg = yPegs.get(i);
			peg.shouldLabel = true;
		}
	}

	protected void drawChart(Graphics2D g) {

		int width = chart.getWidth();
		int height = chart.getHeight();

		g.setColor(CHART_COLOR);
		g.fillRect(0, 0, width, height);

		g.setColor(GRID_COLOR);
		for (Peg peg : xPegs)
			g.drawLine(peg.pixel, 0, peg.pixel, height);
		for (Peg peg : yPegs)
			g.drawLine(0, peg.pixel, width, peg.pixel);

		if (hoverChart) {
			g.setColor(PEG_COLOR);
			g.drawLine(0, cursor.y, width, cursor.y);
			g.drawLine(cursor.x, 0, cursor.x, height);
		}

	}

	private void drawBars(Graphics2D g) {
		for (Bar bar : bars) {
			bar.draw(g, this);
		}
	}

	protected void drawRight(Graphics2D g) {
		g.setColor(SIDE_COLOR);
		g.fillRect(0, 0, right.getWidth(), right.getHeight());
		g.setColor(LINE_COLOR);
		g.fillRect(0, 0, INTERNAL_BORDER_WIDTH, right.getHeight());

		g.setFont(FONT);
		g.setRenderingHint(KEY_TEXT_ANTIALIASING, VALUE_TEXT_ANTIALIAS_LCD_HRGB);
		FontMetrics metrics = g.getFontMetrics();

		for (int i = 0, len = yPegs.size(); i < len; i++) {
			Peg peg = yPegs.get(i);
			if (peg.shouldLabel) {
				g.setColor(LABEL_COLOR);
				g.drawString(peg.label, RIGHT_LABEL_MARGIN_SIDE, peg.pixel + RIGHT_LABEL_MARGIN_TOP);
				g.setColor(LINE_COLOR);
				g.fillRect(0, peg.pixel - 1, 5, 3);
			}
		}

		if (hoverChart) {
			String label = DECIMAL_4_PLACES.format(yPixelToValue(cursor.y));
			int stringWidth = metrics.stringWidth(label);
			g.setColor(LINE_COLOR);
			g.fillRect(0, cursor.y - (metrics.getHeight() + TAG_PADDING * 2) / 2, stringWidth + TAG_PADDING + RIGHT_LABEL_MARGIN_SIDE,
					metrics.getHeight() + TAG_PADDING * 2);
			g.setColor(CHART_COLOR);
			g.fillRect(0, cursor.y - (metrics.getHeight()) / 2, stringWidth + RIGHT_LABEL_MARGIN_SIDE, metrics.getHeight());
			g.setColor(LABEL_COLOR);
			g.drawString(label, RIGHT_LABEL_MARGIN_SIDE, cursor.y + metrics.getHeight() - (metrics.getHeight()) / 2 - RIGHT_LABEL_MARGIN_TOP);
		}
	}

	protected void drawBottom(Graphics2D g) {

		int width = bottom.getWidth();
		int height = bottom.getHeight();

		g.setColor(SIDE_COLOR);
		g.fillRect(0, 0, width, height);
		g.setColor(LINE_COLOR);
		g.fillRect(0, 0, INTERNAL_BORDER_WIDTH + width - right.getWidth(), INTERNAL_BORDER_WIDTH);

		g.setFont(FONT);
		g.setRenderingHint(KEY_TEXT_ANTIALIASING, VALUE_TEXT_ANTIALIAS_LCD_HRGB);
		FontMetrics metrics = g.getFontMetrics();

		for (int i = 0, len = xPegs.size(); i < len; i++) {
			Peg peg = xPegs.get(i);
			if (peg.shouldLabel) {
				g.setColor(LABEL_COLOR);
				g.drawString(peg.label, peg.pixel - metrics.stringWidth(peg.label) / 2, metrics.getHeight());
				g.setColor(LINE_COLOR);
				g.fillRect(peg.pixel - 1, 0, 3, 5);
			}
		}

		if (hoverChart) {
			String label = MIN_SEC.format(new Date(xPixelToValue(cursor.x) * 1000));
			int stringWidth = metrics.stringWidth(label);
			g.setColor(LINE_COLOR);
			g.fillRect(cursor.x - stringWidth / 2 - TAG_PADDING - INTERNAL_BORDER_WIDTH, 0, stringWidth + TAG_PADDING * 2 + INTERNAL_BORDER_WIDTH * 2,
					metrics.getHeight() + TAG_PADDING + INTERNAL_BORDER_WIDTH);
			g.setColor(TAG_COLOR);
			g.fillRect(cursor.x - stringWidth / 2 - TAG_PADDING, 0, stringWidth + TAG_PADDING * 2, metrics.getHeight() + TAG_PADDING);
			g.setColor(LABEL_COLOR);
			g.drawString(label, cursor.x - stringWidth / 2, metrics.getHeight());
		}
	}

	public long xPixelToValue(int pixel) {
		return (long) Math.round((double) (pixel - xOffset) / (xPixels * xScale) * xInterval);
	}

	public int xValueToPixel(long value) {
		return (int) Math.round(((double) value / xInterval) * (xPixels * xScale) + xOffset);
	}

	public double yPixelToValue(int pixel) {
		return (double) (pixel - yOffset) / (yPixels * yScale) * yInterval;
	}

	public int yValueToPixel(double value) {
		return (int) Math.round((value / yInterval) * (yPixels * yScale) + yOffset);
	}

	public void addBar(Bar bar) {
		bars.add(bar);
	}

	protected int barWidth() {
		return (int) (xPixels * xScale * 0.8f);
	}

	private static double div(double a, double b) {
		return a / b;
	}

	private static int floorDiv(double a, double b) {
		return (int) Math.floor(a / b);
	}

	public int getXOffset() {
		return xOffset;
	}

	public void setXOffset(int xOffset) {
		this.xOffset = xOffset;
	}

	public int getYOffset() {
		return yOffset;
	}

	public void setYOffset(int yOffset) {
		this.yOffset = yOffset;
	}

	public float getXScale() {
		return xScale;
	}

	public void setXScale(float xScale) {
		this.xScale = xScale;
	}

	public float getYScale() {
		return yScale;
	}

	public void setYScale(float yScale) {
		this.yScale = yScale;
	}

	public int getxPixels() {
		return xPixels;
	}

	public void setxPixels(int xPixels) {
		this.xPixels = xPixels;
	}

	public int getyPixels() {
		return yPixels;
	}

	public void setyPixels(int yPixels) {
		this.yPixels = yPixels;
	}

	public long getxInterval() {
		return xInterval;
	}

	public void setxInterval(long xInterval) {
		this.xInterval = xInterval;
	}

	public double getyInterval() {
		return yInterval;
	}

	public void setyInterval(double yInterval) {
		this.yInterval = yInterval;
	}

	public double getPip() {
		return pip;
	}

	public void setPip(double pip) {
		this.pip = pip;
	}

	public Point getCursor() {
		return cursor;
	}

	private class Peg {

		public int pixel;
		public double value;
		public String label;
		public boolean shouldLabel;

		public Peg(int pixel, double value, boolean x) {
			this.pixel = pixel;
			this.value = value;
			label = x ? MIN_SEC.format(new Date((long) value * 1000)) : DECIMAL_5_PLACES.format(value);
		}

	}

}
