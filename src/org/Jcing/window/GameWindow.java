package org.Jcing.window;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import org.Jcing.controls.InputManager;
import org.Jcing.controls.Mouse;
import org.Jcing.main.Main;

public class GameWindow extends	Canvas implements WindowListener, ComponentListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9147088231335201118L;

	private GraphicsDevice gd;

	private int lw, lh;
	private boolean showCreator = false;
	
	InputManager im;
	
	private JFrame frame;

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
		
		lw = getWidth();
		lh = getHeight();
		
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
//		routine.finish();
		this.setVisible(false);
		this.setEnabled(false);
	}

	
	public void render() {
//		SysteMain.out.println("render");
		BufferStrategy bs = getBufferStrategy();
		if(bs == null){
			createBufferStrategy(3);
			return;
		}		
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
		
		Main.getGame().getActiveLevel().paint(g);

		Main.getGame().getActiveLevel().paintMovables(g);

		if (showCreator)
			Main.getCreator().paint(g);
		
		g.dispose();
		bs.show();
	}
	
//	@Override
//	public void paint(Graphics gr) {
//		render();
//		
//	}

	private Runnable routine = () -> {
		render();
	};

//	@Override
//	public void update(Graphics g) {
//		if (dbi == null || dbi.getWidth(this) != getWidth() || dbi.getHeight(this) != getHeight()) {
//			dbi = createImage(getWidth(), getHeight());
//			dbg = dbi.getGraphics();
//		}
//		dbg.setColor(getBackground());
//		dbg.fillRect(0, 0, getWidth(), getHeight());
//
//		dbg.setColor(getForeground());
//		paint(dbg);
//		g.drawImage(dbi, 0, 0, this);
//	}

	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void windowClosing(WindowEvent arg0) {
		Main.finish();
	}

	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void componentHidden(ComponentEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void componentMoved(ComponentEvent arg0) {
		// TODO Auto-generated method stub

	}
	
	private double xBuffer, yBuffer;
	
	public void componentResized(ComponentEvent arg0) {
		if (!Main.settings().creatorWindowed && Main.getCreator() != null) {
			Main.getCreator().setCreatorSize(getSize());
		}
		xBuffer -= (lw-getWidth())/2.0;
		Main.getGame().getActiveLevel().addX((int)xBuffer);
		xBuffer -= (int)xBuffer;
		
		yBuffer -= (lh-getHeight())/2.0;
		Main.getGame().getActiveLevel().addY((int)yBuffer);
		yBuffer -= (int)yBuffer;
		lw = getWidth();
		lh = getHeight();
		Main.getGame().getActiveLevel().setSize(getSize());
	}

	public void componentShown(ComponentEvent arg0) {
		// TODO Auto-generated method stub

	}

	public Runnable getJob() {
		return routine;
	}

}
