package com.sineshore.charts.ibapi;

import com.ib.client.Bar;
import com.sineshore.charts.utilities.Logger;

public class Instructor implements Wrapper {

	public void connectAck() {
		Logger.info("Acknowledging connect attempt.");
	}

	public void error(String message) {
		Logger.error(message);
	}

	public void error(Exception e) {
		e.printStackTrace();
	}

	public void error(int id, int errorCode, String errorMsg) {
		if (errorCode > 2100 && errorCode < 2137)
			Logger.info('[', errorCode, ']', errorMsg);
		else
			Logger.error('[', errorCode, ']', errorMsg);
	}

	public void nextValidId(int orderId) {
		Logger.info("Next valid order id: " + orderId);
	}

	public void managedAccounts(String accountsList) {
		Logger.info("Managed Accounts: " + accountsList);
	}

	public void historicalData(int reqId, Bar bar) {}

	public void historicalDataEnd(int reqId, String startDateStr, String endDateStr) {

	}

}
