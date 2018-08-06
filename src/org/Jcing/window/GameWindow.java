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
import org.Jcing.job.Job;
import org.Jcing.main.Main;
import org.Jcing.main.Remindable;

public class GameWindow extends	Canvas implements WindowListener, ComponentListener, Remindable<Main> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9147088231335201118L;

	private GraphicsDevice gd;

	private int lw, lh;
	private boolean showCreator = false;
	
	private Job job;
	private Main m;
	InputManager im;
	
	private JFrame frame;

	public GameWindow(String title) {
		m = Main.getMain();
		im = m.getInputManager();
		frame = new JFrame(title);
//		setTitle(title);
		gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		setPreferredSize(new Dimension(gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight()));
		frame.setPreferredSize(new Dimension(gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight()));
		if (m.options().fullscreen) {
			gd.setFullScreenWindow(frame);
		}
		
		setSize(new Dimension(gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight()));
		lw = getWidth();
		lh = getHeight();
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		frame.addComponentListener(this);
		setBackground(new Color(5, 20, 2));
		setForeground(new Color(220, 220, 220));
		frame.add(this);
		frame.pack();
		frame.setVisible(true);
		addKeyListener(im);
		frame.addWindowListener(this);
		addMouseListener(im);
		addMouseListener(new Mouse());
		addMouseMotionListener(new Mouse());
		addMouseMotionListener(im);
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
			m.getCreator().setCreatorSize(getSize());
			addMouseListener(m.getCreator().getComponentListener());
			addMouseMotionListener(m.getCreator().getComponentListener());
			addMouseWheelListener(m.getCreator().getComponentListener());
		} else {
			removeMouseListener(m.getCreator().getComponentListener());
			removeMouseMotionListener(m.getCreator().getComponentListener());
			removeMouseWheelListener(m.getCreator().getComponentListener());
		}
	}

	public void toggleFullscreen() {
		// lvl.process();
		setFullscreen(!m.options().fullscreen);
	}

	public void setFullscreen(boolean fullscreen) {
		m.options().fullscreen = fullscreen;

		if (m.options().fullscreen) {
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
//		System.out.println("render");
		BufferStrategy bs = getBufferStrategy();
		if(bs == null){
			createBufferStrategy(3);
			return;
		}		
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
		
		m.getGame().getActiveLevel().paint(g);

		m.getGame().getActiveLevel().paintMovables(g);

		if (showCreator)
			m.getCreator().paint(g);
		
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

	public void setJob(Job job) {
		this.job = job;
	}

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
		m.finish();
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
		if (!m.options().creatorWindowed && m.getCreator() != null) {
			m.getCreator().setCreatorSize(getSize());
		}
		xBuffer -= (lw-getWidth())/2.0;
		m.getGame().getActiveLevel().addX((int)xBuffer);
		xBuffer -= (int)xBuffer;
		
		yBuffer -= (lh-getHeight())/2.0;
		m.getGame().getActiveLevel().addY((int)yBuffer);
		yBuffer -= (int)yBuffer;
		lw = getWidth();
		lh = getHeight();
		m.getGame().getActiveLevel().setSize(getSize());
	}

	public void componentShown(ComponentEvent arg0) {
		// TODO Auto-generated method stub

	}

	public Job getJob() {
		return job;
	}

	public void remind(Main r) {
		// TODO Auto-generated method stub
		
	}

}
