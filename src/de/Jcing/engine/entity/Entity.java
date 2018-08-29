package de.Jcing.engine.entity;

import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.LinkedList;

import de.Jcing.engine.graphics.Drawable;
import de.Jcing.engine.world.Stage;
import de.Jcing.engine.world.Tile;
import de.Jcing.geometry.Rectangle;
import de.Jcing.image.Image;

public class Entity implements Drawable {
	
	public static final float DRAG = 0.25f;
	public static final float MAXSPEED = 10;
	
	protected HashMap<Integer, Image> sprite;
	
	protected Stage stage;
	
	protected Rectangle collisionBox;
	
	/**
	 *  world position in Tiles.
	 */
	protected double x, y;
	
	protected int w, h;
	
	protected float speedX, speedY;
	
	protected float accelerationX, accelerationY;
	
	protected LinkedList<Tile> occupiedTiles;
	
	
	
	
	
	public Entity(Stage stage) {
		this.stage = stage;
	}
	
	public void tick() {
		x += speedX;
		y += speedY;
		speedX = Float.max(0, speedX-DRAG);
		speedY = Float.max(0, speedY-DRAG);
		speedX = Float.min(speedX+accelerationX, MAXSPEED);
		speedY = Float.min(speedX + accelerationY, MAXSPEED);
		
//		stage.getTileAtWorldPos(x,y)
		
		if(w > Tile.TILE_PIXELS || h > Tile.TILE_PIXELS) {
			
		}
	}
	
	
	//TODO: implement Entity logics
	// register at all tiles via Stage.getTileAtWorldPos()
	// if Entity size > Tile Size go with EntitySize/TileSize steps
	// for collision go in BiggerRect/smallerRect steps;
	
	
	@Override
	public void draw(Graphics2D g) {
		
	}

}
