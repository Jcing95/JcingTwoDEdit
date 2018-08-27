package de.Jcing.window;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import javax.swing.JFrame;

import org.Jcing.main.Main;

import de.Jcing.engine.graphics.Drawable;
import de.Jcing.tasks.Task;

public class Window {
	
	public static final String TITLE = "TwoDedit";
	
	public static final int DEFAULT_WIDTH = 1280;
	public static final int DEFAULT_HEIGHT = 720;
	
	public static final Color DEFAULT_BACKGROUND = new Color(5,20,2);
	public static final Color DEFAULT_FOREGROUND = new Color(220,220,220);

	public static final int PIXEL_SIZE = 1;	
	
	private JFrame frame;
	private Canvas canvas;
	
	private LinkedList<Drawable> drawables;
	
	Task task;
	
	public Window() {
		frame = new JFrame(TITLE);
		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
		canvas.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		
		canvas.setBackground(DEFAULT_BACKGROUND);
		canvas.setForeground(DEFAULT_FOREGROUND);
		
		canvas.addKeyListener(Main.getInputManager());
		canvas.addMouseListener(Main.getInputManager());
		canvas.addMouseMotionListener(Main.getInputManager());
		
		frame.add(canvas);
		frame.addComponentListener(componentListener);
		frame.addWindowListener(windowListener);
		frame.pack();
		frame.setVisible(true);
		frame.requestFocus();
		canvas.requestFocus();
		
		drawables = new LinkedList<>();
		
		task = new Task(() -> render(), 120);
	}
	
	public void render() {

		BufferStrategy bs = canvas.getBufferStrategy();
		
		if(bs == null){
			canvas.createBufferStrategy(2);
			return;
		}		
		
		//draw everything on buffer for later scaling.
		BufferedImage buffer = new BufferedImage(getCanvasWidth(), getCanvasHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D)buffer.getGraphics();
		
		for(Drawable d : drawables) {
			d.draw(g);
		}
		
		g.dispose();
		
		g = (Graphics2D) bs.getDrawGraphics();
		g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
		g.drawImage(buffer, 0, 0, canvas.getWidth(), canvas.getHeight(), null);
				
		g.dispose();
		bs.show();
	}
	
	public void finish() {
		frame.dispose();
	}
	
	public void addDrawable(Drawable drawable) {
		drawables.add(drawable);
	}
	
	public void removeDrawable(Drawable drawable) {
		drawables.remove(drawable);
	}
	
	public int getCanvasWidth() {
		return canvas.getWidth()/PIXEL_SIZE;
	}
	
	public int getCanvasHeight() {
		return canvas.getHeight()/PIXEL_SIZE;
	}
	
	WindowListener windowListener = new WindowListener() {

		@Override
		public void windowClosing(WindowEvent arg0) {
			Main.finish();
		}
		
		@Override
		public void windowActivated(WindowEvent arg0) {}

		@Override
		public void windowClosed(WindowEvent arg0) {}

		@Override
		public void windowDeactivated(WindowEvent arg0) {}

		@Override
		public void windowDeiconified(WindowEvent arg0) {}

		@Override
		public void windowIconified(WindowEvent arg0) {}

		@Override
		public void windowOpened(WindowEvent arg0) {}
		
	};
	
	ComponentListener componentListener = new ComponentListener() {

		@Override
		public void componentResized(ComponentEvent arg0) {
			//TODO: handle window resizing
		}
		
		@Override
		public void componentHidden(ComponentEvent arg0) {}

		@Override
		public void componentMoved(ComponentEvent arg0) {}

		@Override
		public void componentShown(ComponentEvent arg0) {}
		
	};
	
}
