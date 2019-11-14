package com.sineshore.charts;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.ib.client.Contract;
import com.sineshore.charts.components.Breadcrumbs;
import com.sineshore.charts.components.ChartManager;
import com.sineshore.charts.components.Trader;
import com.sineshore.charts.components.Window;
import com.sineshore.charts.ibapi.Client;
import com.sineshore.charts.utilities.ComponentFactory;

public class Main {

	public static final int WIDTH = 1440;
	public static final int HEIGHT = 900;

	public static JFrame frame;
	public static JPanel panel;

	public static Window window;
	public static Trader trader;
	public static ChartManager manager;
	public static Breadcrumbs breadcrumbs;

	public static void main(String[] args) {
		window = new Window("Jeremy", WIDTH, HEIGHT);
		window.frame.setIconImage(ComponentFactory.getIcon("/icon.png").getImage());
		trader = new Trader(window.content);
		manager = new ChartManager(window.content);
		breadcrumbs = new Breadcrumbs(window.header);

		ComponentFactory.addToolTip(manager.newChart, "Add New Chart");
		ComponentFactory.addToolTip(breadcrumbs.toggleTrader, "Toggle Chart Trader");

		window.validate();
	}

	public static long timestamp;

	public static void setupClient() {
		Client client = new Client();
		client.connect();

		if (!client.isConnected()) {
			return;
		}

		Contract contract = new Contract();
		contract.symbol("EUR");
		contract.secType("CASH");
		contract.currency("USD");
		contract.exchange("IDEALPRO");

		System.out.println("Data requested!");
		timestamp = System.currentTimeMillis();
		client.reqHistoricalData(30, contract, "", "9 D", "5 mins", "MIDPOINT", 1, 1, true, null);
	}

}
