package org.Jcing.GUI;

import java.awt.Color;
import java.awt.Graphics;

public class Line implements Paintable {

	int x, y, w, h;

	public static final Color DEFAULTCOLOR = new Color(220, 220, 220, 220);

	public Line(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return w;
	}

	public void setWidth(int w) {
		this.w = w;
	}

	public int getHeight() {
		return h;
	}

	public void setHeight(int h) {
		this.h = h;
	}

	public void paint(Graphics g) {
		g.setColor(DEFAULTCOLOR);
		g.drawLine(x, y, x+w, y+h);
	}

	public JComponent getComponent() {
		return null;
	}

	public boolean contains(int x, int y) {
		// TODO: useless
		return false;
	}

	public boolean isClickable() {
		// TODO Auto-generated method stub
		return false;
	}

}
