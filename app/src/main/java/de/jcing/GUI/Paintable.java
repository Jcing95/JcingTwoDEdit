package de.jcing.GUI;

import java.awt.Graphics;

public interface Paintable extends JComponent {
	
	
	public void paint(Graphics g);
	
	public boolean isClickable();
}
