package de.Jcing.engine.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import de.Jcing.Main;
import de.Jcing.engine.graphics.Drawable;
import de.Jcing.engine.world.Stage;
import de.Jcing.engine.world.Tile;
import de.Jcing.geometry.Rectangle;
import de.Jcing.image.Image;
import de.Jcing.util.Point;

public class Entity implements Drawable {

	public static final float DRAG = 0.7f;
	public static final float MAXSPEED = 10;

	protected HashMap<Integer, Image> sprite;

	protected Stage stage;

	protected Rectangle collisionBox;

	/**
	 * world position in Tiles.
	 */
	protected double x, y;

	protected int w, h;

	protected float speedX, speedY;

	protected float accelerationX, accelerationY;

	protected Set<Tile> occupiedTiles;

	protected LinkedList<Point> tileOccupationMask;
	
	protected LinkedList<Runnable> onTick;
	private double nextX;
	private double nextY;

	public Entity(Stage stage, double x, double y, int w, int h) {
		this.stage = stage;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		onTick = new LinkedList<>();
		tileOccupationMask = createOccupationMask(Tile.TILE_PIXELS);
	}

	private LinkedList<Point> createOccupationMask(int MAXDELTA) {
		LinkedList<Point> occupationMask = new LinkedList<>();
		for (int y = 0; true; y += MAXDELTA) {
			if (y > h)
				y = h;
			for (int x = 0; true; x += MAXDELTA) {
				if (x > w)
					x = w;
				occupationMask.add(new Point(x, y));
				if (x == w)
					break;
			}
			if (y == h)
				break;
		}
		return occupationMask;
	}

	protected Set<Tile> checkTileOccupation() {
		HashSet<Tile> occupiedTiles = new HashSet<>();
		for (Point pt : tileOccupationMask) {
			occupiedTiles.add(stage.getTileAtWorldPos(pt.x + x, pt.y + y));
		}
		return occupiedTiles;
	}
	
	
	
	public void tick() {
		nextX = x + speedX;
		nextY = y + speedY;
		
		Set<Tile> nextOccupiedTiles = checkTileOccupation();
		
		boolean collided = checkCollision(nextOccupiedTiles) || checkCollision(occupiedTiles);
		
		if (collided) {
			correctMovement(nextOccupiedTiles);
		}

		if (occupiedTiles != null) {
			for (Tile t : occupiedTiles) {
				if (!nextOccupiedTiles.contains(t))
					t.leave(this);
			}
			
			for (Tile t : nextOccupiedTiles) {
				if (!occupiedTiles.contains(t))
					t.enter(this);
			}
		}
		
		occupiedTiles = nextOccupiedTiles;
		
		x = nextX;
		y = nextY;
		
		speedX *= DRAG;
		speedY *= DRAG;
		if(Math.abs(speedX) < 0.1)
			speedX = 0;
		if(Math.abs(speedY) < 0.1)
			speedY = 0;
		
		speedX = Float.min(speedX + accelerationX, MAXSPEED);
		speedY = Float.min(speedY + accelerationY, MAXSPEED);
		speedX = Float.max(speedX + accelerationX, -MAXSPEED);
		speedY = Float.max(speedY + accelerationY, -MAXSPEED);
		accelerationX = 0;
		accelerationY = 0;
		
		for(Runnable r : onTick) {
			r.run();
		}
	}
	
	public LinkedList<Runnable> getOntick() {
		return onTick;
	}
	
	public void accelerate(float x, float y) {
		accelerationX += x;
		accelerationY += y;
	}
	
	private boolean checkCollision(Set<Tile> nextOccupiedTiles) {
		// TODO implement collision check and movement correction
		HashSet<Tile> collisionTiles = new HashSet<>();
		HashSet<Entity> collidedEntities = new HashSet<>();
		System.out.println(nextOccupiedTiles.size());
		for(Tile t: nextOccupiedTiles) {
			if(t == null)
				continue;
			if(t.hasCollision())
				collisionTiles.add(t);
			for(Entity e : t.getEntities()) {
				if(e.getFootPrint().collides(getFootPrint()))
					collidedEntities.add(e);
			}
		}
		
		return collisionTiles.isEmpty() && collidedEntities.isEmpty();
	}
	
	public Rectangle getFootPrint() {
		return collisionBox;
	}

	private void correctMovement(Set<Tile> nextOccupiedTiles) {

	}

	// TODO: implement Entity logics
	// register at all tiles via Stage.getTileAtWorldPos()
	// if Entity size > Tile Size go with EntitySize/TileSize steps
	// for collision go in BiggerRect/smallerRect steps;

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.CYAN);
		int xPos = (int)(x*Tile.TILE_PIXELS/Main.getWindow().getPixelSize()-(Main.getGame().getCamera().x)); // (int) (stage.getCamera().x * Main.getWindow().getPixelSize() - x*Tile.TILE_PIXELS);
		int yPos = (int)(y*Tile.TILE_PIXELS/Main.getWindow().getPixelSize()-(Main.getGame().getCamera().y)); // (int) (stage.getCamera().y * Main.getWindow().getPixelSize() - y*Tile.TILE_PIXELS);
		
		// System.out.println(" cam: " + xPos + " | " + yPos);

		g.fillRect(xPos, yPos, w, h);
	}

	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}

}
