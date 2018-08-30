package de.Jcing.geometry;

import de.Jcing.util.Point;

public class Rectangle {
	
	public double x,y;
	public double width, height;
	
	public Rectangle(double x, double y, double width, double height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public boolean collides(Rectangle r) {
		return false; //TODO: implement rectangle collision logic
	}

	public boolean contains(Point point) {
		return point.x > x && point.x < x+width && point.y > y && point.y < y + height;	
	}
}
