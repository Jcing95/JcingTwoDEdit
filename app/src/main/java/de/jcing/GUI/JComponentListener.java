package de.jcing.GUI;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;

public class JComponentListener implements MouseListener, MouseMotionListener, MouseWheelListener {

	private ArrayList<JComponent> components;

	public JComponentListener() {
		components = new ArrayList<JComponent>();
	}

	public void add(JComponent component) {
		components.add(component);
		// TODO: evtl components splitten clickable scrolable etc.
	}

	public void mouseWheelMoved(MouseWheelEvent e) {
		for (int i = 0; i < components.size(); i++) {
			((Clickable) components.get(i)).mouseWheelMoved(e.getWheelRotation());
		}
	}

	public void mouseDragged(MouseEvent e) {
		for (int i = 0; i < components.size(); i++) {
			((Clickable) components.get(i)).hover(e.getX(), e.getY());
		}
	}

	public void mouseMoved(MouseEvent e) {
		for (int i = 0; i < components.size(); i++) {
			((Clickable) components.get(i)).hover(e.getX(), e.getY());
		}
	}

	public void mouseClicked(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {
		for (int i = 0; i < components.size(); i++) {
			((Clickable) components.get(i)).click(true, e.getX(), e.getY());
		}
	}

	public void mouseReleased(MouseEvent e) {
		for (int i = 0; i < components.size(); i++) {
				((Clickable) components.get(i)).click(false, e.getX(), e.getY());
		}
	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
