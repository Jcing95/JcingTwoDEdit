package org.Jcing.GUI;

import java.awt.Dimension;

public class Layout {
	
	private Dimension size;
	private int paddingX;
	private int paddingY;
	
	public Layout(Dimension size){
		this.size = size;
	}
	
	public Layout(int width, int height){
		size = new Dimension(width,height);
	}
	
	public int getXInPercent(double percent){
		return (int)(size.width * (percent / 100));
	}
	
	public int getYInPercent(double percent){
		return (int)(size.height * (percent / 100));
	}
	
	
}
