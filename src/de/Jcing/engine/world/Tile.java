package de.Jcing.engine.world;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.File;
import java.util.LinkedList;

import de.Jcing.engine.Trigger;
import de.Jcing.engine.entity.Entity;
import de.Jcing.engine.graphics.Drawable;
import de.Jcing.engine.io.Mouse;
import de.Jcing.geometry.Rectangle;
import de.Jcing.image.Image;
import de.Jcing.util.Point;
import de.Jcing.window.Window;

public class Tile implements Drawable {
	
	public static final int TILE_PIXELS = 32;
	
	private LinkedList<Image> textures;
	private LinkedList<Entity> entities;
	private LinkedList<Trigger> triggers;
	
	private Image testBack = new Image(new File("C:\\workspace\\java\\JcingWorld\\res\\terrain\\dev\\sand.png"));
	
	private boolean collision;
	
	private Chunk chunk;
	private int x, y;
	
	private Color testColor = new Color((int)(Math.random()*155),(int)(Math.random()*155),(int)(Math.random()*155));
	
	public Tile(int x, int y, Chunk chunk) {
		this.chunk = chunk;
		this.x = x;
		this.y = y;
		textures = new LinkedList<>();
		entities = new LinkedList<>();
		triggers = new LinkedList<>();
		textures.add(testBack);
	}
	
	public void addTexture(Image img) {
		//TODO: catch invalid images for Tiles
		textures.add(img);
	}

	@Override
	public void draw(Graphics2D g) {
		int xOff = getXOnScreen();
		int yOff = getYOnScreen();
		if(hovered())
			g.setColor(Color.white);
		else
			g.setColor(testColor);
		g.fillRect(xOff, yOff, TILE_PIXELS, TILE_PIXELS);
		for(Image i : textures) {
			g.drawImage(i.get().get(), xOff, yOff, null);
		}
	}
	
	public boolean hovered() {
		return new Rectangle(getXOnScreen(), getYOnScreen(),
				TILE_PIXELS, TILE_PIXELS)
				.contains(new Point(Mouse.getX()/Window.PIXEL_SIZE, Mouse.getY()/Window.PIXEL_SIZE));
	}
	
	public int getXOnScreen() {
		return x * TILE_PIXELS - chunk.getXOffset();
	}
	
	public int getYOnScreen() {
		return y * TILE_PIXELS - chunk.getYOffset();
	}
	
	public void removeFrame(boolean last) {
		if(last)
			textures.removeLast();
		else
			textures.removeFirst();
	}
	
	public LinkedList<Entity> getEntities(){
		return entities;
	}
	
	public void enter(Entity e) {
		entities.add(e);
	}
	
	public void leave(Entity e) {
		entities.remove(e);
	}
	
	public void setCollision(boolean collision) {
		this.collision = collision;
	}
	
	public boolean hasCollision() {
		return collision;
	}
	
	
}
