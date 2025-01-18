package de.jcing.game.world;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.Serializable;

import de.jcing.GUI.Paintable;

public class Grid implements Paintable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7142010651685162027L;

	private int x, y;
	private int sizeX, sizeY;
	private int w, h;
	private Color color;

	public Grid(int sizeX, int sizeY, Color color, int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
//		System.out.println("GRID INITIALIZING WITH SIZE: " + sizeX);
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.color = color;
		this.w = width;
		this.h = height;
	}

	public void paint(Graphics g) {
		g.setColor(color);
		for (int x = 0; x <= w; x += sizeX) {
			g.drawLine(x, 0, x, h);
//			System.out.println("GRID: x += " + sizeX);
		}
		for (int y = 0; y <= h; y += sizeY) {
			g.drawLine(0, y, w, y);
//			System.out.println("GRID: y += " + sizeY + " = " + y);
		}
//		g.drawLine(w, 0, w, h);
//		g.drawLine(0, h, w, h);

	}

	public int getX() {
		// TODO Auto-generated method stub
		return x;
	}

	public int getY() {
		// TODO Auto-generated method stub
		return y;
	}

	public int getWidth() {
		// TODO Auto-generated method stub
		return w;
	}

	public int getHeight() {
		// TODO Auto-generated method stub
		return h;
	}

	public int getSizeX() {
		return sizeX;
	}

	public void setSizeX(int sizeX) {
		this.sizeX = sizeX;
	}

	public int getSizeY() {
		return sizeY;
	}

	public void setSizeY(int sizeY) {
		this.sizeY = sizeY;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setWidth(int width) {
		this.w = width;
	}

	public void setHeight(int height) {
		this.h = height;
	}

	public boolean contains(int x, int y) {
		return new Rectangle(this.x, this.y, w, h).contains(x, y);
		// return false;
	}

	public boolean isClickable() {
		// TODO Auto-generated method stub
		return false;
	}

}
