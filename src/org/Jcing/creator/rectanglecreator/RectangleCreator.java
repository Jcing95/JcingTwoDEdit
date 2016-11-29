package org.Jcing.creator.rectanglecreator;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferStrategy;

import org.Jcing.job.Job;
import org.Jcing.job.Routine;
import org.Jcing.window.Window;

public class RectangleCreator extends Window
		implements KeyListener, MouseListener, MouseMotionListener, WindowListener, Routine {

	public RectangleCreator(String title) {
		super(title);
		init(new Rectangle(10, 10, 50, 0));
	}

	boolean initialized = false;

	private void init(Rectangle def) {
		addMouseListener(this);
		addKeyListener(this);
		setPreferredSize(new Dimension(256, 256));
		frame.addWindowListener(this);
		frame.pack();
		frame.setVisible(true);
		//setVisible(true);
		requestFocus();
		setRect(def);
		job = new Job(this, 10, "RectangleCreator");
		job.start();
		initialized = true;
	}

	private Job job;
	private Point p1, p2, p3, p4;
	private boolean close = false;
	public static final int POINTSIZE = 10;

	private void setRect(Rectangle rect) {
		p1 = new Point(rect.x, rect.y);
		p2 = new Point(rect.x + rect.width, rect.y);
		p3 = new Point(rect.x, rect.y + rect.height);
		p4 = new Point(rect.x + rect.width, rect.y + rect.height);
	}

	public void mouseClicked(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			close = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			job.start();
		}
	}

	public void keyReleased(KeyEvent e) {
		if (close && e.getKeyCode() == KeyEvent.VK_ESCAPE)
			frame.dispose();
	}

	public void reload() {
		BufferStrategy bs = getBufferStrategy();

		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();

		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
		renderPoints(g);
		g.dispose();
		bs.show();
	}

	private void renderPoints(Graphics g) {
		g.fillRect(p1.x - POINTSIZE / 2, p1.y - POINTSIZE / 2, POINTSIZE, POINTSIZE);
		g.fillRect(p2.x - POINTSIZE / 2, p2.y - POINTSIZE / 2, POINTSIZE, POINTSIZE);
		g.fillRect(p3.x - POINTSIZE / 2, p3.y - POINTSIZE / 2, POINTSIZE, POINTSIZE);
		g.fillRect(p4.x - POINTSIZE / 2, p4.y - POINTSIZE / 2, POINTSIZE, POINTSIZE);

	}

	public void go() {
		if (initialized)
			reload();
	}

	public void start() {
		//		job.start();
	}

	public void setJob(Job job) {
		// TODO Auto-generated method stub

	}

}
