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

	public static Mouse mouse;
	public static KeyBoard keyboard;
	public static BindingManager bm = new BindingManager(50);

	public InputManager() {
		mouse = new Mouse();
		keyboard = new KeyBoard();
	}

	public void keyPressed(KeyEvent e) {
		// SysteMain.out.println("key " + e.getKeyCode());
		keyboard.press(e.getKeyCode());
		bm.press(e.getKeyCode());
	}

	public void keyReleased(KeyEvent e) {
		keyboard.release(e.getKeyCode());
		bm.release(e.getKeyCode());
	}

	public void mouseDragged(MouseEvent e) {
		
		if (Main.getCreator() != null && !Main.settings().creatorWindowed && Main.getWin().creatorShown()) {
			if (!Main.getCreator().hovered(e.getX(), e.getY())) {
				// SysteMain.out.println("ADD TILE!");
				Main.getGame().getActiveLevel().hover(e.getX(), e.getY());
				Main.getGame().getActiveLevel().click(true);
			} else {
				// TODO: maybe unsafe
				Main.getGame().getActiveLevel().hover(-1000, -1000);
			}
		} else {
			// SysteMain.out.println("NO CREATOR SEEN");
			Main.getGame().getActiveLevel().hover(e.getX(), e.getY());
			Main.getGame().getActiveLevel().click(true);
		}

	}

	public void mouseMoved(MouseEvent e) {
		if (Main.getCreator() != null && !Main.settings().creatorWindowed && Main.getWin().creatorShown()) {
			if (!Main.getCreator().hovered(e.getX(), e.getY())) {
				// SysteMain.out.println("ADD TILE!");
				Main.getGame().getActiveLevel().hover(e.getX(), e.getY());
			} else {
				// TODO: maybe unsafe
				Main.getGame().getActiveLevel().hover(-1000, -1000);
			}
		} else {
			// SysteMain.out.println("NO CREATOR SEEN");
			Main.getGame().getActiveLevel().hover(e.getX(), e.getY());
		}

	}

	public void mousePressed(MouseEvent e) {
		
		if (Main.getCreator() != null && !Main.settings().creatorWindowed) {
			if (!Main.getCreator().hovered(e.getX(), e.getY())) {
				System.out.println("ADD TILE!");
				Main.getGame().getActiveLevel().click(false);
			}
		} else {
			// SysteMain.out.println("NO CREATOR SEEN");
			Main.getGame().getActiveLevel().click(false);
		}

	}

	public void mouseReleased(MouseEvent e) {
		if (Main.getCreator() != null && !Main.settings().creatorWindowed) {
			if (!Main.getCreator().hovered(e.getX(), e.getY())) {
				// SysteMain.out.println("ADD TILE!");
				Main.getGame().getActiveLevel().click(false);
			}
		} else {
			// SysteMain.out.println("NO CREATOR SEEN");
			Main.getGame().getActiveLevel().click(false);
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
