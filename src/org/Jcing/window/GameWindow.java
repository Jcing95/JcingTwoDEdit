package org.Jcing.window;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import org.Jcing.controls.InputManager;
import org.Jcing.controls.Mouse;
import org.Jcing.main.Main;

import de.Jcing.tasks.Task;

public class GameWindow extends	Canvas implements WindowListener, ComponentListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9147088231335201118L;

	public static final int PIXEL_SIZE = 3;
	
	private GraphicsDevice gd;

	private boolean showCreator = false;
	
	
	
	InputManager im;
	
	private JFrame frame;
	
	Task task;

	public GameWindow(String title) {
		im = Main.getInputManager();
		frame = new JFrame(title);
//		setTitle(title);
		gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		
		setPreferredSize(new Dimension(gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight()));
		
		frame.setPreferredSize(new Dimension(gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight()));
		
		if (Main.settings().fullscreen) {
			gd.setFullScreenWindow(frame);
		}
		
		setSize(new Dimension(gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight()));
		
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		frame.addComponentListener(this);
		
		setBackground(new Color(5, 20, 2));
		setForeground(new Color(220, 220, 220));
		
		addKeyListener(im);
		addMouseListener(im);
		//TODO: mouse / keyboard hooks
		addMouseListener(new Mouse());
		addMouseMotionListener(new Mouse());
		addMouseMotionListener(im);
		frame.addWindowListener(this);

		frame.add(this);
		frame.pack();
		frame.setVisible(true);
		
		frame.requestFocus();
		requestFocus();
		
		task = new Task(routine, 120);

		// lvl.process();
	}

	public void showCreator(boolean show) {
		this.showCreator = show;
	}

	public boolean creatorShown() {
		return showCreator;
	}

	public void toggleShowCreator() {
		this.showCreator = !showCreator;
		if (showCreator) {
			Main.getCreator().setCreatorSize(getSize());
			addMouseListener(Main.getCreator().getComponentListener());
			addMouseMotionListener(Main.getCreator().getComponentListener());
			addMouseWheelListener(Main.getCreator().getComponentListener());
		} else {
			removeMouseListener(Main.getCreator().getComponentListener());
			removeMouseMotionListener(Main.getCreator().getComponentListener());
			removeMouseWheelListener(Main.getCreator().getComponentListener());
		}
	}

	public void toggleFullscreen() {
		// lvl.process();
		setFullscreen(!Main.settings().fullscreen);
	}

	public void setFullscreen(boolean fullscreen) {
		Main.settings().fullscreen = fullscreen;
		if (Main.settings().fullscreen) {
			gd.setFullScreenWindow(frame);
		} else {
			gd.setFullScreenWindow(null);
		}
	}

	public void finish() {
		this.setVisible(false);
		this.setEnabled(false);
	}

	
	public int getCanvasWidth() {
		return (int)(getWidth()/PIXEL_SIZE);
	}
	
	public int getCanvasHeight() {
		return (int)(getHeight()/PIXEL_SIZE);
	}
	
	public void render() {

		BufferStrategy bs = getBufferStrategy();
		
		if(bs == null){
			createBufferStrategy(2);
			return;
		}		
		
		//draw everything on buffer for later scaling.
		BufferedImage buffer = new BufferedImage(getCanvasWidth(), getCanvasHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D)buffer.getGraphics();
		Main.getGame().getActiveLevel().draw(g);
		Main.getGame().getActiveLevel().drawEntities(g);
		g.dispose();
		
		g = (Graphics2D) bs.getDrawGraphics();
		g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
		g.drawImage(buffer, 0, 0, getWidth(), getHeight(), null);
		
		if (showCreator)
			Main.getCreator().paint(g);
		
		g.dispose();
		bs.show();
	}

	private Runnable routine = () -> {
		render();
	};


	public void windowActivated(WindowEvent arg0) {

	}

	public void windowClosed(WindowEvent arg0) {

	}

	public void windowClosing(WindowEvent arg0) {
		Main.finish();
	}

	public void windowDeactivated(WindowEvent arg0) {

	}

	public void windowDeiconified(WindowEvent arg0) {

	}

	public void windowIconified(WindowEvent arg0) {

	}

	public void windowOpened(WindowEvent arg0) {

	}

	public void componentHidden(ComponentEvent arg0) {

	}

	public void componentMoved(ComponentEvent arg0) {

	}
	
	public void componentResized(ComponentEvent arg0) {
		if (!Main.settings().creatorWindowed && Main.getCreator() != null) {
			Main.getCreator().setCreatorSize(getSize());
		}
		//TODO: this is crazy
//		xBuffer -= (lw-getWidth())/2.0;
//		Main.getGame().getActiveLevel().addX((int)xBuffer);
//		xBuffer -= (int)xBuffer;
//		yBuffer -= (lh-getHeight())/2.0;
//		Main.getGame().getActiveLevel().addY((int)yBuffer);
//		yBuffer -= (int)yBuffer;
		Main.getGame().getActiveLevel().setSize(getSize());
	}

	public void componentShown(ComponentEvent arg0) {

	}

	public int getFPS() {
		return task.tps;
	}

	public int getCenterX() {
		return (int)(getWidth()/PIXEL_SIZE/2.0);
	}
	
	public int getCenterY() {
		return (int)(getHeight()/PIXEL_SIZE/2.0);
	}
}
