package com.sineshore.charts.ibapi;

import java.io.IOException;

import com.ib.client.EClientSocket;
import com.ib.client.EJavaSignal;
import com.ib.client.EReader;
import com.sineshore.charts.utilities.Logger;

public class Client extends EClientSocket {

	private Thread thread;

	public Client() {
		super(new Instructor(), new EJavaSignal());
		thread = new Thread(this::run);
	}

	public void connect() {
		eConnect("192.168.0.8", 4002, 0);
		if (isConnected())
			thread.start();
	}

	private void run() {
		EReader reader = new EReader(this, m_signal);
		reader.start();
		while (isConnected()) {
			m_signal.waitForSignal();
			try {
				reader.processMsgs();
			} catch (IOException e) {
				Logger.error("Failed to process message: " + e.getMessage());
			}
		}
	}

}
