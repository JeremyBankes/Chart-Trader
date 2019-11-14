package com.sineshore.charts;

import static com.sineshore.charts.utilities.Utilities.*;
import static java.awt.RenderingHints.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.JPanel;

public class Welcome extends JPanel {

	private static final long serialVersionUID = 1L;

	private static final String[][] QUOTES = {
			{ "Start by doing what's necessary; then do what's possible; and suddenly you are doing the impossible.", "Francis of Assisi" },
			{ "Believe you can and you're halfway there.", "Theodore Roosevelt" },
			{ "It does not matter how slowly you go as long as you do not stop.", "Confucius" },
			{ "Our greatest weakness lies in giving up. The most certain way to succeed is always to try just one more time.", "Thomas A. Edison" },
			{ "The will to win, the desire to succeed, the urge to reach your full potential... these are the keys that will unlock the door to personal excellence.",
					"Confucius" },
			{ "Don't watch the clock; do what it does. Keep going.", "Sam Levenson" },
			{ "A creative man is motivated by the desire to achieve, not by the desire to beat others.", "Ayn Rand" },
			{ "A creative man is motivated by the desire to achieve, not by the desire to beat others.", "Ayn Rand" },
			{ "Expect problems and eat them for breakfast.", "Alfred A. Montapert" },
			{ "Start where you are. Use what you have. Do what you can.", "Arthur Ashe" },
			{ "Ever tried. Ever failed. No matter. Try Again. Fail again. Fail better.", "Samuel Beckett" },
			{ "Be yourself; everyone else is already taken.", "Oscar Wilde" },
			{ "Two things are infinite: the universe and human stupidity; and I'm not sure about the universe.", "Albert Einstein" },
			{ "Always remember that you are absolutely unique. Just like everyone else.", "Margaret Mead" },
			{ "Do not take life too seriously. You will never get out of it alive.", "Elbert Hubbard" },
			{ "People who think they know everything are a great annoyance to those of us who do.", "Isaac Asimov" },
			{ "Procrastination is the art of keeping up with yesterday.", "Don Marquis" },
			{ "Get your facts first, then you can distort them as you please.", "Mark Twain" },
			{ "A day without sunshine is like, you know, night.", "Steve Martin" },
			{ "My grandmother started walking five miles a day when she was sixty. She's ninety-seven now, and we don't know where the hell she is.",
					"Ellen DeGeneres" },
			{ "Don't sweat the petty things and don't pet the sweaty things.", "George Carlin" }, { "Always do whatever's next.", "George Carlin" },
			{ "Atheism is a non-prophet organization.", "George Carlin" },
			{ "Hapiness is not something ready made. It comes from your own actions.", "Dalai Lama" } };

	public static Font font = new Font("Consolas", Font.PLAIN, 64);

	private JDialog frame;
	private int width, height;

	private GradientPaint paint;
	private BufferedImage logo;
	private FontMetrics metrics;

	private StringBuffer text;

	public Welcome() {
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		width = screen.width;
		height = screen.height;
		try {
			logo = ImageIO.read(Welcome.class.getResourceAsStream("/sineshore.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		paint = new GradientPaint(0, 0, new Color(0xee212121, true), width, height, new Color(0xaaaaaaaa, true));
		setPreferredSize(screen);
		metrics = getFontMetrics(font);
		setFont(font);

		text = new StringBuffer();

		frame = new JDialog();
		frame.setUndecorated(true);
		frame.setBackground(new Color(0, 0, 0, 0));
		frame.setContentPane(this);
		frame.pack();
		frame.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new BufferedImage(1, 1, 2), new Point(), ""));
		frame.requestFocus();
		frame.setOpacity(0f);
		frame.setVisible(true);

		welcome();
	}

	@Override
	protected void paintComponent(Graphics graphics) {
		Graphics2D g = (Graphics2D) graphics;
		g.setPaint(paint);
		g.setBackground(new Color(0, 0, 0, 0));
		g.clearRect(0, 0, width, height);
		g.fillRect(0, 0, width, height);

		g.drawImage(logo, width / 2 - logo.getWidth() / 2, 10, null);

		renderingHints(g);

		String text = this.text.toString();
		g.setColor(Color.BLACK);
		drawWrappedString(g, text, width / 2, height / 2 + 5, width);
		g.setColor(Color.WHITE);
		drawWrappedString(g, text, width / 2, height / 2, width);
	}

	private void welcome() {
		new Thread(() -> {
			for (int i = 0, len = 60; i <= len; i++) {
				frame.setOpacity((float) i / len);
			}
		}).start();
		try {
			String welcomeMessage = "Welcome " + beautify(System.getenv("USERDOMAIN"));
			int index = new Random().nextInt(QUOTES.length);
			welcomeMessage = QUOTES[index][0] + "\n~ " + QUOTES[index][1];
			for (char character : welcomeMessage.toCharArray()) {
				text.append(character);
				repaint();
				Thread.sleep(50);
			}
			Thread.sleep(5000);
			for (int i = 0, len = 60; i <= len; i++) {
				frame.setOpacity((float) (len - i) / len);
			}
			frame.dispose();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	private void drawWrappedString(Graphics2D g, String text, int x, int y, int width) {
		ArrayList<String> words = convert(text.split("(?=\\n| )"));
		ArrayList<String> lines = new ArrayList<String>();
		String build = "";

		for (int i = 0, len = words.size(); i < len; i++) {
			if (metrics.stringWidth(build + words.get(i)) > width || words.get(i).startsWith("\n")) {
				lines.add(build);
				build = words.get(i);
			} else {
				build += words.get(i);
			}
		}
		lines.add(build);
		int yOffset = 0;
		for (String line : lines) {
			int stringWidth = metrics.stringWidth(line);
			g.drawString(line, x - stringWidth / 2, y + yOffset);
			yOffset += metrics.getHeight();
		}
	}

	private void renderingHints(Graphics2D g) {
		g.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
		g.setRenderingHint(KEY_ALPHA_INTERPOLATION, VALUE_ALPHA_INTERPOLATION_QUALITY);
	}

}
