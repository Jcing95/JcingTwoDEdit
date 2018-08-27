package de.Jcing.engine.world;

import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.LinkedList;

import de.Jcing.engine.entity.Entity;
import de.Jcing.engine.graphics.Drawable;
import de.Jcing.util.Point;

public class Stage implements Drawable{

	private HashMap<Point, Chunk> chunks;
	private HashMap<Integer, Entity> entities;
	private LinkedList<Integer> loadedChunks;
	
	
	//TODO: implement and manage tilesets!
	
	public Stage() {
		chunks = new HashMap<>();
		entities = new HashMap<>();
		loadedChunks = new LinkedList<>();
	}
	
	@Override
	public void draw(Graphics2D g) {
		for(Point pt : chunks.keySet())
			chunks.get(pt).draw(g);
		for(Integer i : entities.keySet())
			entities.get(i).draw(g);
	}
	
	public void addChunk(int x, int y) {
		chunks.put(new Point(x,y), new Chunk(x,y,this));
	}
	
	public Chunk getChunk(int x, int y) {
		return getChunk(new Point(x,y));
	}
	
	public Chunk getChunk(Point point) {
		return chunks.get(point);
	}
	
	public void removeChunk(int x, int y) {
		removeChunk(new Point(x,y));
	}
	
	public void removeChunk(Point point) {
		chunks.remove(point);
	}
	
	public void handleClick() {
		//TODO: implement chunk clicks
	}
	
	public void handleMouseMovement() {
		//TODO: implement chunk mouse movement
	}
	
	public int addEntity(Entity entity) {
		entities.put(entity.hashCode(), entity);
		return entity.hashCode();
	}
	
	
	
	
}
