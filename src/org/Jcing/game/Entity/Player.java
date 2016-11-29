package org.Jcing.game.Entity;

import java.awt.event.KeyEvent;

import org.Jcing.Essentials.OutputStreamController;
import org.Jcing.controls.Binding;
import org.Jcing.controls.Executable;
import org.Jcing.controls.InputManager;
import org.Jcing.game.world.Level;
import org.Jcing.graphics.JCImage;
import org.Jcing.main.Main;
import org.Jcing.main.Remindable;

public class Player extends Entity implements Executable, Remindable<Main> {

	private Binding right, left, up, down;
	private static OutputStreamController o = new OutputStreamController("Player", true);

	private Binding last;

	public Player(JCImage img, Level lvl) {
		super(img, 0, 0, lvl);
		right = new Binding(KeyEvent.VK_RIGHT, this);
		left = new Binding(KeyEvent.VK_LEFT, this);
		up = new Binding(KeyEvent.VK_UP, this);
		down = new Binding(KeyEvent.VK_DOWN, this);
	}

	protected void moveX(int x) {
		lvl.addX(-x);
		lvl.hover(InputManager.mouse.x, InputManager.mouse.y);
	}

	protected void moveY(int y) {
		lvl.addY(-y);
		lvl.hover(InputManager.mouse.x, InputManager.mouse.y);
	}

	public void execute(Binding binding) {
		if (binding == right) {
			last = right;
			accelerateX(true);
			if (binding.isPressed()) {
				acceleratingX = true;
				img.setAnimation(7);
			} else {
				acceleratingX = false;
				calcanim();
			}
		}
		if (binding == left) {
			last = left;
			accelerateX(false);
			if (binding.isPressed()) {
				acceleratingX = true;
				img.setAnimation(6);
			} else {
				acceleratingX = false;
				calcanim();
			}
		}
		if (binding == up) {
			last = up;
			accelerateY(false);
			if (binding.isPressed()) {
				acceleratingY = true;
				img.setAnimation(4);
			} else {
				acceleratingY = false;
				calcanim();
			}
		}
		if (binding == down) {
			last = down;
			accelerateY(true);
			if (binding.isPressed()) {
				acceleratingY = true;
				img.setAnimation(5);
			} else {
				acceleratingY = false;
				calcanim();
			}
		}
	}

	protected void calcanim() {
		//		if(movementSpeedX == 0 && movementSpeedY == 0){
		if (last == up) {
			img.setAnimation(0);
		}
		if (last == down) {
			img.setAnimation(1);
		}
		if (last == left) {
			img.setAnimation(2);
		}
		if (last == right) {
			img.setAnimation(3);
		}

		//		}
	}

	public void remind(Main r) {
		x = r.getWin().getWidth() / 2 - getImg().getWidth() / 2;
		y = r.getWin().getHeight() / 2 - getImg().getHeight() / 2;
		o.println("x: " + x + " y: " + y + " fx:" + footPrint.x + " fy: " + footPrint.y);
	}

}
