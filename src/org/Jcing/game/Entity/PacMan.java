package org.Jcing.game.Entity;

import java.awt.Rectangle;

import org.Jcing.game.world.Level;
import org.Jcing.graphics.JCImage;

public class PacMan extends Entity {

	public PacMan(int x, int y, Level lvl) {
		super(null, x, y, lvl);
		JCImage img = new JCImage("gfx/pacman/full.png");
		img.addAnimation("gfx/pacman/up");
		img.addAnimation("gfx/pacman/down");
		img.addAnimation("gfx/pacman/left");
		img.addAnimation("gfx/pacman/right");
		img.setFps(10);
		setImg(img);
		footPrint = new Rectangle(0, 0, img.getImg().getWidth(), img.getImg().getHeight());
	}

	protected void moveX(int x) {
		if (img != null) {
			if (x > 0) {
				img.setAnimation(4);
			}
			if (x < 0) {
				img.setAnimation(3);
			}
		}
	}

	protected void moveY(int y) {
		if (img != null) {
			if (y > 0) {
				img.setAnimation(2);
			}
			if (y < 0) {
				img.setAnimation(1);
			}
		}
	}

	protected void standStill() {
		if (img != null)
			img.setAnimation(0);
	}

	protected void atCollision(boolean left, boolean top, boolean right, boolean bottom) {
		if (left)
			accelerateX(0.5);
		if (right)
			accelerateX(-0.5);
		if (top)
			accelerateY(0.5);
		if (bottom)
			accelerateY(-0.5);
	}
}
