package org.Jcing.creator.spectator;

import java.awt.Graphics;

import org.Jcing.GUI.Paintable;
import org.Jcing.game.world.Tile;

public class Tilespectator implements Paintable {
	Tile spectated;

	int x, y, w, h;

	
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

	
	public boolean contains(int x, int y) {
		return false;
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

	
	public void paint(Graphics g) {
		// TODO Auto-generated method stub

	}

	
	public boolean isClickable() {
		return false;
	}

}
