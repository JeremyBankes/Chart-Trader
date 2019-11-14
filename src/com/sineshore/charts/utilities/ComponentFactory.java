package com.sineshore.charts.utilities;

import static com.sineshore.charts.utilities.Utilities.*;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.colorchooser.AbstractColorChooserPanel;

import com.sineshore.charts.Main;

public class ComponentFactory {

	public static Color backgroundColor = new Color(1f, 1f, 1f);
	public static Color selectionColor = new Color(0.8f, 0.8f, 0.8f);
	public static Color selectedColor = new Color(0.2f, 0.2f, 0.2f);
	public static Color caretColor = new Color(0f, 0f, 0f);
	public static Border border = new LineBorder(Color.BLACK, 2);
	public static Font font = new Font("Consolas", Font.PLAIN, 16);

	public static final JButton createButton(String text, int width, int height) {
		JButton button = new JButton(text) {

			private static final long serialVersionUID = 1L;

			private ButtonModel model = getModel();

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
		};
		if (width == 0)
			width = (int) button.getPreferredSize().getWidth();
		button.setPreferredSize(new Dimension(width, height));
		button.setContentAreaFilled(false);
		button.setFocusPainted(false);
		button.setBackground(backgroundColor);
		button.setBorder(border);
		button.setFont(font);
		return button;
	}

	public static final JButton createButton(ImageIcon icon, int width, int height) {
		JButton button = new JButton(icon) {

			private static final long serialVersionUID = 1L;

			private ButtonModel model = getModel();

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

		};

		button.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent event) {
				button.setIcon(getScaledIcon(icon, event.getComponent().getWidth(), event.getComponent().getHeight()));
			}
		});

		button.setPreferredSize(new Dimension(width, height));
		button.setContentAreaFilled(false);
		button.setFocusPainted(false);
		button.setBackground(backgroundColor);
		button.setBorder(border);
		button.setFont(font);
		return button;
	}

	public static final JTextField createTextField(int width, int height) {
		JTextField field = new JTextField();
		field.setBackground(tinge(backgroundColor, Color.BLACK, 0.05f));
		field.setSelectedTextColor(selectedColor);
		field.setCaretColor(caretColor);
		field.setSelectionColor(selectionColor);
		field.setPreferredSize(new Dimension(width, height));
		field.setBorder(new CompoundBorder(border, new EmptyBorder(0, 5, 0, 5)));
		field.setFont(font);
		return field;
	}

	public static final JComponent createStrut(int width, int height, Color color) {
		JComponent strut = (JComponent) Box.createRigidArea(new Dimension(width, height));
		strut.setOpaque(true);
		strut.setBackground(color);
		return strut;
	}

	public static final JTextArea createTextArea(int width, int height) {
		JTextArea field = new JTextArea();
		field.setBackground(tinge(backgroundColor, Color.BLACK, 0.05f));
		field.setSelectedTextColor(selectedColor);
		field.setCaretColor(caretColor);
		field.setSelectionColor(selectionColor);
		field.setPreferredSize(new Dimension(width, height));
		field.setBorder(new CompoundBorder(border, new EmptyBorder(5, 5, 5, 5)));
		field.setFont(font);
		return field;
	}

	public static final JLabel addToolTip(JComponent component, String text) {
		JDialog tip = new JDialog(Main.window.frame);
		tip.setAlwaysOnTop(true);
		tip.setUndecorated(true);
		tip.setModalityType(ModalityType.MODELESS);
		JLabel label = createLabel(text, 0, 0);
		label.setFont(font.deriveFont(16f));
		FontMetrics metrics = label.getFontMetrics(label.getFont());
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setPreferredSize(new Dimension(metrics.stringWidth(text) + 10, metrics.getHeight() + 10));
		label.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
		tip.setContentPane(label);
		tip.setFocusable(false);
		tip.setAutoRequestFocus(false);
		tip.pack();
		component.addMouseListener(new MouseAdapter() {

			public void mouseEntered(MouseEvent event) {
				tip.setVisible(true);
			}

			public void mouseExited(MouseEvent event) {
				tip.setVisible(false);
			}

		});

		component.addMouseMotionListener(new MouseAdapter() {
			public void mouseMoved(MouseEvent event) {
				Component component = event.getComponent();
				Point location = component.getLocationOnScreen();
				tip.setLocation(location.x + event.getX() + 3, location.y - tip.getHeight() + event.getY() - 3);
			}
		});
		return label;
	}

	public static final JFileChooser createFileChooser(File start, int width, int height) {
		JFileChooser chooser = new JFileChooser(start) {
			private static final long serialVersionUID = 1L;

		};
		chooser.setBackground(tinge(backgroundColor, Color.BLACK, 0.05f));
		chooser.setPreferredSize(new Dimension(width, height));
		chooser.setFont(font);
		return chooser;
	}

	public static final JPanel createColorChooser() {
		AbstractColorChooserPanel chooser = new AbstractColorChooserPanel() {

			private static final long serialVersionUID = 1L;

			public void updateChooser() {

			}

			public Icon getSmallDisplayIcon() {

				return null;
			}

			public Icon getLargeDisplayIcon() {

				return null;
			}

			public String getDisplayName() {

				return null;
			}

			protected void buildChooser() {

			}
		};
		return chooser;
	}

	public static final JLabel createLabel(String text, int width, int height) {
		JLabel label = new JLabel(text);
		label.setOpaque(true);
		label.setBackground(tinge(backgroundColor, Color.BLACK, 0.05f));
		label.setPreferredSize(new Dimension(width, height));
		label.setFont(font);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setBorder(ComponentFactory.border);
		return label;
	}

	public static JLabel createDropDownList(String display, String[] texts, Runnable[] actions, int width, int height) {
		JLabel label = createLabel(display, width, height);
		return label;
	}

	public static JComponent addAction(JComponent component, Runnable runnable) {
		if (component instanceof AbstractButton) {
			((AbstractButton) component).addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					runnable.run();
				}
			});
		}
		return component;
	}

	public static ImageIcon getIcon(String path) {
		try {
			return new ImageIcon(ImageIO.read(Main.class.getResourceAsStream(path)));
		} catch (IOException e) {
			Logger.error(e);
		}
		return null;
	}

	private static ImageIcon getScaledIcon(ImageIcon image, int w, int h) {
		BufferedImage result = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = result.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.drawImage(image.getImage(), 0, 0, w, h, null);
		g.dispose();
		return new ImageIcon(result);
	}

}
