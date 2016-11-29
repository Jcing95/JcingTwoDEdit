package org.Jcing.GUI;

import java.awt.Color;
import java.awt.Graphics;

import org.Jcing.graphics.JCImage;

public class Selectable extends Clickable {

	private boolean selected;
	private Selector selector;
	private JCImage img;

	public Selectable(int x, int y, JCImage img) {
		super(x, y);
		this.img = img;
		selected = false;
		setSize(img.getImg().getWidth(), img.getImg().getHeight());
	}

	public Selectable(int x, int y, JCImage img, Selector selector) {
		super(x, y);
		this.img = img;
		selected = false;
		this.selector = selector;
		setSize(img.getImg().getWidth(), img.getImg().getHeight());
	}

	public JCImage getImg() {
		return img;
	}

	@Override
	public void click() {
		setSelected(!selected);
		selector.selection(this);
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
//		if (selected) {
			
//		}
	}

	public void paint(Graphics g) {
		g.drawImage(img.getImg(), x, y, null);
		g.setColor(new Color(255, 255, 255, 20));

		if (hovered) {
			g.fillRect(x, y, img.getImg().getWidth(), img.getImg().getHeight());
		}
		if (selected) {
			g.fillRect(x, y, img.getImg().getWidth(), img.getImg().getHeight());
			g.setColor(new Color(20, 220, 20, 200));
			g.drawRect(x, y, img.getImg().getWidth(), img.getImg().getHeight());
		}
	}

}
