package org.Jcing.controls;

public class Mouse {

	public static final int LEFT = -5;
	public static final int RIGHT = -6;
	public static final int NONE = 0;
	
	private static int x, y;
	private static int lastX, lastY;
	
	private static boolean pressed;
	private static int button;
	
	
	public static int getX() {
		return x;
	}
	
	public static int getY() {
		return y;
	}
	
	public static boolean isPressed() {
		return pressed;
	}
	
	public static int getLastX() {
		return lastX;
	}
	
	public static int getLastY() {
		return lastY;
	}
	
	public static int getButton() {
		return button;
	}
	
	
}
