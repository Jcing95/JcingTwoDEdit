package org.Jcing.controls;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import org.Jcing.main.Main;

/**
 * InputManager manages Mouse / -motion and KeyEvents. It holds public static
 * <b>Mouse</b>, <b>KeyBoard</b> and <b>BindingManager</b>.
 * 
 * @author Jcing
 *
 */
public class InputManager implements KeyListener, MouseListener, MouseMotionListener {

	Main m;

	public static Mouse mouse;
	public static KeyBoard keyboard;
	public static BindingManager bm = new BindingManager(50);

	public InputManager() {
		m = Main.getMain();
		mouse = new Mouse();
		keyboard = new KeyBoard();
	}

	public void keyPressed(KeyEvent e) {
		// System.out.println("key " + e.getKeyCode());
		keyboard.press(e.getKeyCode());
		bm.press(e.getKeyCode());
	}

	public void keyReleased(KeyEvent e) {
		keyboard.release(e.getKeyCode());
		bm.release(e.getKeyCode());
	}

	public void mouseDragged(MouseEvent e) {
		mouse.x = e.getX();
		mouse.y = e.getY();
		
		if (m.getCreator() != null && !m.options().creatorWindowed && m.getWin().creatorShown()) {
			if (!m.getCreator().hovered(e.getX(), e.getY())) {
				// System.out.println("ADD TILE!");
				m.getGame().getActiveLevel().hover(e.getX(), e.getY());
				m.getGame().getActiveLevel().click(true);
			} else {
				// TODO: maybe unsafe
				m.getGame().getActiveLevel().hover(-1000, -1000);
			}
		} else {
			// System.out.println("NO CREATOR SEEN");
			m.getGame().getActiveLevel().hover(e.getX(), e.getY());
			m.getGame().getActiveLevel().click(true);
		}

	}

	public void mouseMoved(MouseEvent e) {
		mouse.x = e.getX();
		mouse.y = e.getY();
		if (m.getCreator() != null && !m.options().creatorWindowed && m.getWin().creatorShown()) {
			if (!m.getCreator().hovered(e.getX(), e.getY())) {
				// System.out.println("ADD TILE!");
				m.getGame().getActiveLevel().hover(e.getX(), e.getY());
			} else {
				// TODO: maybe unsafe
				m.getGame().getActiveLevel().hover(-1000, -1000);
			}
		} else {
			// System.out.println("NO CREATOR SEEN");
			m.getGame().getActiveLevel().hover(e.getX(), e.getY());
		}

	}

	public void mousePressed(MouseEvent e) {
		mouse.pressed = true;
		mouse.button = e.getButton();
		if (m.getCreator() != null && !m.options().creatorWindowed) {
			if (!m.getCreator().hovered(e.getX(), e.getY())) {
				System.out.println("ADD TILE!");
				m.getGame().getActiveLevel().click(false);
			}
		} else {
			// System.out.println("NO CREATOR SEEN");
			m.getGame().getActiveLevel().click(false);
		}

	}

	public void mouseReleased(MouseEvent e) {
		mouse.pressed = false;
		if (m.getCreator() != null && !m.options().creatorWindowed) {
			if (!m.getCreator().hovered(e.getX(), e.getY())) {
				// System.out.println("ADD TILE!");
				m.getGame().getActiveLevel().click(false);
			}
		} else {
			// System.out.println("NO CREATOR SEEN");
			m.getGame().getActiveLevel().click(false);
		}
	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
