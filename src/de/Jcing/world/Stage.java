package de.Jcing.world;

import java.awt.Graphics2D;
import java.util.HashMap;

import de.Jcing.engine.Drawable;
import de.Jcing.entity.Entity;
import de.Jcing.util.Point;

public class Stage implements Drawable{

	private HashMap<Point, Chunk> chunks;
	private HashMap<Integer, Entity> entities;
	
	
	public Stage() {
		
	}
	
	@Override
	public void draw(Graphics2D g) {
		
	}
	
}
