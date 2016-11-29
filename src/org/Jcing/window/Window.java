package org.Jcing.window;

import java.awt.Canvas;
import java.awt.event.ComponentListener;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

public class Window extends Canvas{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8002551013297699522L;
	
	protected JFrame frame;
	protected WindowListener wl;
	protected ComponentListener cl;
	
	
	public Window(String title){
		frame = new JFrame(title);
		frame.add(this);
	}
	
}
