package org.Jcing.game.Entity;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.Jcing.GUI.Paintable;
import org.Jcing.game.world.Level;
import org.Jcing.game.world.Tile;
import org.Jcing.graphics.JCImage;
import org.Jcing.job.Job;
import org.Jcing.main.Main;

public class Entity implements Paintable {

	public static final int MPS = 1000;
	public static final double MOVEMENTCAP = 5.0;
	public static final double ACCELERATION = 0.025;
	public static final double MINSPEED = 0.001;
	public static final double RESISTANCEFAC = 1.0 - 7.5 * 1.0 / (MPS);

	protected JCImage img;
	protected Level lvl;
	protected int ID;
	protected Rectangle footPrint;

	protected int x, y, w, h;

	protected Job job;

	protected double movementSpeedX = 0.0, movementSpeedY = 0.0;
	protected double xApproacher = 0.0, yApproacher = 0.0;

	protected int wallX;

	protected int wallY;

	protected boolean acceleratingX = false, acceleratingY = false;
	boolean defined = false;

	protected ArrayList<Tile> tiles;

	public Entity(JCImage img, int x, int y, Level lvl) {
		this.img = img;
		this.x = x;
		this.y = y;
		if(img != null){
			w = img.getImg().getWidth();
			h = img.getImg().getHeight();
		}
		//TODO: footprint
		footPrint = new Rectangle(0, 16, 24, 16);
		this.lvl = lvl;
		tiles = new ArrayList<Tile>();
		job = new Job(routine, MPS, "Player Movement");
		job.start();
	}
	
	protected void setImg(JCImage img){
		this.img = img;
		defined = true;
	}

	public void accelerateX(double amount) {
		if (Math.abs(movementSpeedX) < MOVEMENTCAP)
			movementSpeedX += amount;
		if (Math.abs(movementSpeedX) > MOVEMENTCAP)
			movementSpeedX = (int) (movementSpeedX / Math.abs(movementSpeedX)) * MOVEMENTCAP;
		job.pause(false);
	}

	public void accelerateY(double amount) {
		if (Math.abs(movementSpeedY) < MOVEMENTCAP)
			movementSpeedY += amount;
		if (Math.abs(movementSpeedY) > MOVEMENTCAP)
			movementSpeedY = (int) (movementSpeedY / Math.abs(movementSpeedY)) * MOVEMENTCAP;
		job.pause(false);
	}

	public void accelerateX(boolean positive) {
		if (positive) {
			if (wallX <= 0) {
				// positive
				if (movementSpeedX < MOVEMENTCAP)
					movementSpeedX += ACCELERATION;
				if (movementSpeedX > MOVEMENTCAP)
					movementSpeedX = MOVEMENTCAP;
			}
		} else {
			if (wallX >= 0) {
				// negative
				if (movementSpeedX > -MOVEMENTCAP)
					movementSpeedX -= ACCELERATION;
				if (movementSpeedX < -MOVEMENTCAP)
					movementSpeedX = -MOVEMENTCAP;
			}
		}
		job.pause(false);
	}

	// TODO: diagonal maxSpeed calculation? ;)
	public void accelerateY(boolean positive) {
		if (positive) {
			if (wallY <= 0) {
				// positive
				if (movementSpeedY < MOVEMENTCAP)
					movementSpeedY += ACCELERATION;
				if (movementSpeedY > MOVEMENTCAP)
					movementSpeedY = MOVEMENTCAP;
			}
		} else {
			if (wallY >= 0) {
				// negative
				if (movementSpeedY > -MOVEMENTCAP)
					movementSpeedY -= ACCELERATION;
				if (movementSpeedY < -MOVEMENTCAP)
					movementSpeedY = -MOVEMENTCAP;
			}
		}
		job.pause(false);
	}

	protected void applyMovementX() {
		if (Math.abs(movementSpeedX) < 1) {
			xApproacher += movementSpeedX;
		} else {
			xApproacher = movementSpeedX;
		}
		if (Math.abs(xApproacher) >= 1) {
			if (!lvl.checkEntry(getFootPrint(this.x + (int) (xApproacher / Math.abs(xApproacher)), y), ID)) {
				xApproacher -= (int) (xApproacher / Math.abs(xApproacher));
				// System.err.println(x + " " + xApproacher);
				this.x += (int) (xApproacher / Math.abs(xApproacher));
				moveX((int) (xApproacher / Math.abs(xApproacher)));
				// System.err.println(x);
			} else {
				wallX = (int) (xApproacher / Math.abs(xApproacher));
				movementSpeedX = 0;
				xApproacher = 0;
			}
		}
	}

	protected void applyMovementY() {
		if (Math.abs(movementSpeedY) < 1) {
			yApproacher += movementSpeedY;
		} else {
			yApproacher = movementSpeedY;
		}
		if (Math.abs(yApproacher) >= 1) {
			if (!lvl.checkEntry(getFootPrint(x, this.y + (int) (yApproacher / Math.abs(yApproacher))), ID)) {
				yApproacher -= (int) (yApproacher / Math.abs(yApproacher));
				this.y += (int) (yApproacher / Math.abs(yApproacher));
				moveY((int) (yApproacher / Math.abs(yApproacher)));
			} else {
				wallY = (int) (yApproacher / Math.abs(yApproacher));
				movementSpeedY = 0;
				yApproacher = 0;
			}
		}
	}

	protected void moveX(int x) {

	}

	protected void moveY(int y) {

	}
	
	protected void standStill() {
		
	}

	public Rectangle getRelativeFootPrint() {
		return footPrint;
	}

	public void paint(Graphics g) {
		g.drawImage(getImg(), x + Main.getMain().getGame().getActiveLevel().getxOffset(),
				y + Main.getMain().getGame().getActiveLevel().getyOffset(), null);
		if (Main.getMain().options().showEntityFootprints) {
			g.setColor(Level.COLLISION);
			g.drawRect(x + Main.getMain().getGame().getActiveLevel().getxOffset() + footPrint.x,
					y + Main.getMain().getGame().getActiveLevel().getyOffset() + footPrint.y, footPrint.width,
					footPrint.height);
		}
		if (Main.getMain().options().showEntitySizes) {
			g.setColor(Level.HOVER);
			g.drawRect(x + Main.getMain().getGame().getActiveLevel().getxOffset(),
					y + Main.getMain().getGame().getActiveLevel().getyOffset(), img.getImg().getWidth(),
					img.getImg().getHeight());
		}
	}

	public Rectangle getFootPrint(int x, int y) {
		return new Rectangle(footPrint.x + x, footPrint.y + y, footPrint.width, footPrint.height);
	}

	public Rectangle getFootPrint() {
		return new Rectangle(footPrint.x + x, footPrint.y + y, footPrint.width, footPrint.height);
	}

	public void setFootPrint(Rectangle footPrint) {
		this.footPrint = footPrint;
	}

	public Runnable routine = () -> {
		if (movementSpeedY == 0 && movementSpeedX == 0) {
			job.pause(true);
			wallX = 0;
			wallY = 0;
			standStill();
		} else {
			if (Math.abs(movementSpeedX) > 0) {
				applyMovementX();
				if (!acceleratingX)
					movementSpeedX *= RESISTANCEFAC;
				if (Math.abs(movementSpeedX) < MINSPEED) {
					movementSpeedX = 0;
				}
			} else {
				wallX = 0;
			}
			if (Math.abs(movementSpeedY) > 0) {
				applyMovementY();
				if (!acceleratingY)
					movementSpeedY *= RESISTANCEFAC;
				if (Math.abs(movementSpeedY) < MINSPEED) {
					movementSpeedY = 0;
				}
			} else {
				wallY = 0;
			}
		}
	};

	public void setJob(Job job) {
		this.job = job;
	}

	public double getMovementSpeedX() {
		return movementSpeedX;
	}

	public double getMovementSpeedY() {
		return movementSpeedY;
	}

	public int getWidth() {
		return w;
	}

	public void setWidth(int w) {
		this.w = w;
	}

	public int getHeight() {
		return h;
	}

	public void setHeight(int h) {
		this.h = h;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public BufferedImage getImg() {
		return img.getImg();
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	public boolean contains(int x, int y) {
		return new Rectangle(this.x, this.y, w, h).contains(x, y);
	}

	public boolean isClickable() {
		return false;
	}

	public void logout() {
		for (int i = 0; i < tiles.size(); i++) {
			tiles.get(i).leave(ID);
		}
		tiles = new ArrayList<Tile>();
	}

	public void addTile(Tile tile) {
		tiles.add(tile);
	}

	protected void atCollision(boolean left, boolean top, boolean right, boolean bottom) {
		
	}

	public boolean checkCollision(Rectangle footprint) {
		Rectangle newfoot = getFootPrint(x, y);
		boolean left = false, top = false, right = false, bottom = false;
		// TODO: fix
		if (newfoot.intersects(footprint)) {
			Rectangle in = newfoot.intersection(footprint);
			if (in.getCenterY() < newfoot.getCenterY() && in.getWidth() >= in.getHeight()) {
				// footprint.intersectsLine(newfoot.getX() + 1, newfoot.getY() +
				// 1,
				// newfoot.getX() + newfoot.getWidth() - 1, newfoot.getY() + 1))
				// {
				// TOP LINE OF this
				top = true;
			}
			if (in.getCenterX() < newfoot.getCenterX() && in.getHeight() >= in.getWidth()) {
				// footprint.intersectsLine(newfoot.getX() + 1, newfoot.getY() +
				// 1, newfoot.getX() + 1,
				// newfoot.getY() + newfoot.getHeight() - 1)) {
				// LEFT LINE OF this
				left = true;
			}
			if (in.getCenterY() > newfoot.getCenterY() && in.getWidth() >= in.getHeight()) {
				// footprint.intersectsLine(newfoot.getX() + 1, newfoot.getY() +
				// newfoot.getHeight() - 1,
				// newfoot.getX() + newfoot.getWidth() - 1, newfoot.getY() +
				// newfoot.getHeight() - 1)) {
				// BOTTOM LINE OF this
				bottom = true;
			}
			if (in.getCenterX() > newfoot.getCenterX() && in.getHeight() >= in.getWidth()) {
				// footprint.intersectsLine(newfoot.getX() + newfoot.getWidth()
				// - 1, newfoot.getY() + 1,
				// newfoot.getX() + newfoot.getWidth() - 1, newfoot.getY() +
				// newfoot.getHeight() - 1)) {
				// RIGHT LINE OF this
				right = true;
			}
			atCollision(left, top, right, bottom);
			return true;
		}
		// System.out.println("INTERSECTION: " + newfoot + " " + footprint);
		return false;
	}
}
