package de.Jcing.window.gui;

import java.awt.Graphics2D;

import de.Jcing.engine.graphics.Drawable;

public abstract class Component implements Drawable {
	
	protected boolean visible;
	
	protected int x, y, w, h;
	
	protected Container parent;
	
	protected boolean hovered;
		
	public Component(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		parent = null;
	}

	protected void setParent(Container parent) {
		this.parent = parent;
	}
	
	@Override
	public void draw(Graphics2D g) {
		prepareDraw(g);
		g.translate(x, y);
		paint(g);
		g.translate(-x, -y);
	}
	
	public void handleMouse() {
		
	}
	
	public abstract void paint(Graphics2D g);

	public void prepareDraw(Graphics2D g) {}
	
	public void setWidth(int w) {
		this.w = w;
	}
	
	public void setHeight(int h) {
		this.h = h;
	}
		

}
