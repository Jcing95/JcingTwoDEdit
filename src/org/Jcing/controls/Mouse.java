package org.Jcing.controls;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Mouse implements MouseListener, MouseMotionListener {

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

	public static void update(MouseEvent e) {
		
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		pressed = true;
		button = e.getButton();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		pressed = false;
		button = NONE;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}
	
	
}
