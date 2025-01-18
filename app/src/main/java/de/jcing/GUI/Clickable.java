package de.jcing.GUI;

import java.awt.Rectangle;

import de.jcing.essentials.OutputStreamController;

public abstract class Clickable implements Paintable {

	protected boolean hovered;
	protected boolean clicked;
	protected int x, y, w, h;
	protected Rectangle canvas;
	protected ClickableManager cm;
	
	public final OutputStreamController o = new OutputStreamController("Clickable " + getClass().getSimpleName(), false);
	
	public static final int DEFAULTSIZE = 10;

	public Clickable(int x, int y) {
		this.x = x;
		this.y = y;
		setSize(DEFAULTSIZE, DEFAULTSIZE);
		hovered = false;
		clicked = false;
	}
	
	public boolean isClickable(){
		return true;
	}
	
	public void setClickableManager(ClickableManager cm) {
		this.cm = cm;
	}

	public void setSize(int w, int h) {
		this.w = w;
		this.h = h;
		canvas = new Rectangle(x, y, w, h);
	}

	public boolean contains(int x, int y) {
		return canvas.contains(x, y);
	}

	public void hover(int x, int y) {
		if (!hovered && contains(x, y)) {
			hovered = true;
		}
		if (hovered && !contains(x, y)) {
			hovered = false;
		}
		hover();
	}

	public void click(boolean clicked, int x, int y) {
		if (this.clicked && !clicked) {
			this.clicked = false;
			if(contains(x, y)){
				click();
			}
		} else if (contains(x, y)) {
			this.clicked = true;
		}
	}
	
	public void click(){
		
	}
	
	public void hover(){
		
	}
	
	public void mouseWheelMoved(int movement){
		
	}
	
	public JComponent getComponent() {
		return this;
	}

	public boolean isHovered() {
		return hovered;
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
		setSize(w,h);
	}

	public void setY(int y) {
		this.y = y;
		setSize(w,h);
	}

	public void setWidth(int w) {
		this.w = w;
		setSize(w,h);
	}

	public void setHeight(int h) {
		this.h = h;
		setSize(w,h);
	}
}
