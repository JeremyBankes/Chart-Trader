package com.sineshore.charts.components;

import static java.awt.Frame.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;

import com.sineshore.charts.utilities.ComponentFactory;

public class Window {

	public static final int HEADER_HEIGHT = 30;
	public static final int INTERNAL_BORDER_WIDTH = 2;

	public static final Border BORDER = BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK);

	public static final Color CONTENT_COLOR = new Color(0xEEEEEE);
	public static final Color BODY_COLOR = new Color(0xFFFFFF);
	public static final Color HEADER_COLOR = new Color(0xDDDDDD);

	public final JFrame frame;
	public final JPanel body;
	public final JPanel header;
	public final JPanel tray;
	public final JPanel content;

	public final JButton exit;
	public final JButton state;
	public final JButton shrink;
	public final JButton center;

	private Point start;
	private Point startFrame;

	public Window(String title, int width, int height) {
		frame = new JFrame(title);
		body = new JPanel(new BorderLayout());
		header = new JPanel(new BorderLayout());
		tray = new JPanel(new FlowLayout(0, 0, 0));
		content = new JPanel(new BorderLayout());

		exit = ComponentFactory.createButton("Exit", 0, HEADER_HEIGHT - INTERNAL_BORDER_WIDTH);
		state = ComponentFactory.createButton("Max", 0, HEADER_HEIGHT - INTERNAL_BORDER_WIDTH);
		shrink = ComponentFactory.createButton("Hide", 0, HEADER_HEIGHT - INTERNAL_BORDER_WIDTH);
		center = ComponentFactory.createButton("Center", 0, HEADER_HEIGHT - INTERNAL_BORDER_WIDTH);

		ComponentFactory.addAction(exit, frame::dispose);
		ComponentFactory.addAction(state, () -> {
			frame.setExtendedState(frame.getExtendedState() == NORMAL ? MAXIMIZED_BOTH : NORMAL);
			if (frame.getExtendedState() == NORMAL) {
				state.setText("Max");
			} else {
				state.setText("Min");
			}
		});
		ComponentFactory.addAction(shrink, () -> frame.setState(ICONIFIED));
		ComponentFactory.addAction(center, () -> frame.setLocationRelativeTo(null));

		body.setBackground(BODY_COLOR);
		header.setBackground(HEADER_COLOR);

		header.setPreferredSize(new Dimension(0, HEADER_HEIGHT));
		body.setPreferredSize(new Dimension(width, height));

		tray.add(center);
		tray.add(shrink);
		tray.add(state);
		tray.add(exit);
		header.add(tray, BorderLayout.EAST);

		body.setBorder(BORDER);
		header.setBorder(new MatteBorder(0, 0, INTERNAL_BORDER_WIDTH, 0, Color.BLACK));
		exit.setBorder(null);
		state.setBorder(null);
		shrink.setBorder(null);
		center.setBorder(null);

		header.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent event) {
				start = event.getLocationOnScreen();
				startFrame = frame.getLocation();
			};
		});

		header.addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent event) {
				Point location = event.getLocationOnScreen();
				frame.setLocation(location.x - start.x + startFrame.x, location.y - start.y + startFrame.y);
			};
		});

		content.setBackground(CONTENT_COLOR);
		body.add(header, BorderLayout.NORTH);
		body.add(content, BorderLayout.CENTER);
		frame.setMaximizedBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
		frame.setUndecorated(true);
		frame.setContentPane(body);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public void validate() {
		frame.validate();
		frame.repaint();
	}

}
