package com.sineshore.charts.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.sineshore.charts.Main;
import com.sineshore.charts.utilities.ComponentFactory;

public class Breadcrumbs {

	public static final int INTERNAL_BORDER_WIDTH = 2;
	public static final Color BREADCRUMB_COLOR = new Color(0xDDDDDD);

	public JPanel breadcrumbs;

	public final JButton toggleTrader;

	public Breadcrumbs(JPanel header) {
		int width = header.getWidth();
		int height = header.getHeight() - INTERNAL_BORDER_WIDTH;
		for (Component component : header.getComponents())
			width -= component.getWidth();
		breadcrumbs = new JPanel(new FlowLayout(0, 0, 0));
		breadcrumbs.setPreferredSize(new Dimension(width, height));
		breadcrumbs.setBackground(new Color(0, 0, 0, 0));

		toggleTrader = ComponentFactory.createButton(ComponentFactory.getIcon("/toggle.png"), height, height);
		toggleTrader.setBackground(BREADCRUMB_COLOR);
		toggleTrader.setBorder(null);
		ComponentFactory.addAction(toggleTrader, () -> {
			if (Main.trader.isVisible())
				Main.trader.hide();
			else
				Main.trader.show();
			Main.window.validate();
		});
		breadcrumbs.add(toggleTrader);

		header.add(breadcrumbs, 0);
	}

}
