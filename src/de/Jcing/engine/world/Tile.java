package de.Jcing.engine.world;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.LinkedList;

import de.Jcing.engine.Trigger;
import de.Jcing.engine.entity.Entity;
import de.Jcing.engine.graphics.Drawable;
import de.Jcing.image.Image;

public class Tile implements Drawable {
	
	public static final int TILE_PIXELS = 32;
	
	private LinkedList<Image> textures;
	private LinkedList<Entity> entities;
	private LinkedList<Trigger> triggers;
	
	private boolean collision;
	
	private Chunk chunk;
	private int x, y;
	
	public Tile(int x, int y, Chunk chunk) {
		this.chunk = chunk;
		this.x = x;
		this.y = y;
		textures = new LinkedList<>();
		entities = new LinkedList<>();
		triggers = new LinkedList<>();
	}
	
	public void addTexture(Image img) {
		//TODO: catch invalid images for Tiles
		textures.add(img);
	}

	@Override
	public void draw(Graphics2D g) {
		int xOff = chunk.getXOffset() + x * TILE_PIXELS;
		int yOff = chunk.getYOffset() + y * TILE_PIXELS;
		g.setColor(Color.green);
		g.fillRect(xOff, yOff, TILE_PIXELS, TILE_PIXELS);
		for(Image i : textures) {
			g.drawImage(i.get().get(), xOff, yOff, null);
		}
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
