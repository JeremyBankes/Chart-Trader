package com.sineshore.charts.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import com.sineshore.charts.Main;
import com.sineshore.charts.utilities.ComponentFactory;

public class Trader {

	public static final Color CONTENT_COLOR = new Color(0x9FCACC);
	public static final Color LABEL_COLOR = new Color(0xFFFFFF);
	public static final Color BUY_COLOR = new Color(0xEEFFEE);
	public static final Color SELL_COLOR = new Color(0xFFEEEE);
	public static final int WIDTH = Main.WIDTH / 5;
	public static final int BUTTON_HEIGHT = 50;
	public static final int PADDING = 8;

	public final JPanel parent;
	public final JPanel trader;
	private boolean visible;

	public Trader(JPanel parent) {
		this.parent = parent;

		trader = new JPanel(new FlowLayout(0, PADDING, PADDING));
		trader.setPreferredSize(new Dimension(WIDTH, 0));
		trader.setBackground(CONTENT_COLOR);
		trader.setBorder(new MatteBorder(0, 2, 0, 0, Color.BLACK));

		int width = WIDTH / 2 - PADDING - PADDING / 2;
		JButton buyMarket = ComponentFactory.createButton("Buy Market", width, BUTTON_HEIGHT);
		buyMarket.setBackground(BUY_COLOR);
		JButton sellMarket = ComponentFactory.createButton("Sell Market", width, BUTTON_HEIGHT);
		sellMarket.setBackground(SELL_COLOR);
		JButton buyBid = ComponentFactory.createButton("Buy Bid", width, BUTTON_HEIGHT);
		buyBid.setBackground(BUY_COLOR);
		JButton sellBid = ComponentFactory.createButton("Sell Bid", width, BUTTON_HEIGHT);
		sellBid.setBackground(SELL_COLOR);
		JLabel riskPercent = ComponentFactory.createLabel(riskPercentString(), WIDTH - PADDING * 2, BUTTON_HEIGHT);
		riskPercent.setBackground(LABEL_COLOR);
		JLabel takeProfit = ComponentFactory.createLabel(takeProfitString(), WIDTH - PADDING * 2, BUTTON_HEIGHT);
		takeProfit.setBackground(LABEL_COLOR);
		JLabel accountBalance = ComponentFactory.createLabel(balanceString(), WIDTH - PADDING * 2, BUTTON_HEIGHT);
		accountBalance.setBackground(LABEL_COLOR);

		trader.add(buyMarket);
		trader.add(sellMarket);
		trader.add(buyBid);
		trader.add(sellBid);
		trader.add(Box.createRigidArea(new Dimension(width, PADDING * 2)));
		trader.add(riskPercent);
		trader.add(takeProfit);
		trader.add(Box.createRigidArea(new Dimension(width, 538)));
		trader.add(accountBalance);

		show();
	}

	private String riskPercentString() {
		return "<html><center>Risk Percentage:<br>3.5%</center></html>";
	}

	private String takeProfitString() {
		return "<html><center>Take Profit:<br>4.6 Pips</center></html>";
	}

	private String balanceString() {
		return "<html><center>Account Balance:<br>$546,435</center></html>";
	}

	public void show() {
		visible = true;
		parent.add(trader, BorderLayout.EAST);
	}

	public void hide() {
		visible = false;
		parent.remove(trader);
	}

	public boolean isVisible() {
		return visible;
	}

}
