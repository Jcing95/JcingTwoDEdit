package de.jcing.graphics;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public interface Drawable {

	public BufferedImage getImg();

	public int getX();

	public int getY();
	
	public void paint(Graphics g);
	
}
