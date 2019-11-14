package com.sineshore.charts.utilities;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import com.sineshore.charts.Main;

public class Audio {

	public static synchronized void playSound(String path) {
		ExecutorService threadPool = Executors.newCachedThreadPool();
		threadPool.execute(() -> {
			try {
				Clip clip = AudioSystem.getClip();
				AudioInputStream inputStream = AudioSystem.getAudioInputStream(Main.class.getResourceAsStream(path));
				clip.open(inputStream);
				clip.start();
			} catch (Exception e) {
				Logger.error(e);
			}
		});
	}

}
