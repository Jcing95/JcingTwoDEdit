package org.Jcing.GUI;

import java.awt.Graphics;

import org.Jcing.graphics.JCImage;

public class JCCheckbox extends Clickable {

	private boolean active;
	private static JCImage checked = new JCImage("gfx/CheckBox/CheckBoxActive.png"),
			unchecked = new JCImage("gfx/CheckBox/CheckBox.png");

	public boolean isActive() {
		o.println("" + active);
		return active;
		
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public JCCheckbox(int x, int y) {
		super(x, y);
		active = false;

		w = checked.getImg().getWidth();
		h = checked.getImg().getHeight();
		setSize(w, h);
	}

	public void paint(Graphics g) {
		if (active)
			g.drawImage(checked.getImg(), x, y, null);
		else
			g.drawImage(unchecked.getImg(), x, y, null);
	}

	public void hover(boolean hovered) {
		// TODO Auto-generated method stub

	}

	public void click() {
		if (!clicked) {
			active = !active;
			if (cm != null)
				cm.actionHappened(this);
		}
	}
}
