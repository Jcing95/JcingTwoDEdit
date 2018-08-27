package org.Jcing.GUI;

import java.awt.Color;
import java.awt.Graphics;

public class JCLabel implements Paintable {

	private int x, y;
	private int w, h;
	private String label;

	public static final Color DEFAULTCOLOR = new Color(220, 220, 220);
	private Color color;

	public JCLabel(int x, int y, String label) {
		this.x = x;
		this.y = y;
		this.label = label;
		color = DEFAULTCOLOR;
	}

	public void paint(Graphics g) {
		g.setColor(color);
		g.drawString(label, x, y + g.getFontMetrics().getAscent());
		// + g.getFontMetrics().getHeight() / 2
	}

	public JComponent getComponent() {
		return null;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return w;
	}

	public int getHeight() {
		return h;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setWidth(int w) {
		this.w = w;
	}

	public void setHeight(int h) {
		this.h = h;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public boolean contains(int x, int y) {
		// TODO ey kp vllt iwie implementieren
		return false;
	}

	public boolean isClickable() {
		return false;
	}

}
