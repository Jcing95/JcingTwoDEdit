package de.Jcing.window.gui;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Canvas extends Container {

	protected BufferedImage canvas;
		
	public Canvas(int x, int y, int w, int h) {
		super(x,y,w,h);
		canvas = new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
	}
	
	@Override
	public void paint(Graphics2D g) {
		g.drawImage(canvas, 0, 0, null);
	}
	
	public Graphics2D getGraphics() {
		return (Graphics2D) canvas.getGraphics();
	}

}
