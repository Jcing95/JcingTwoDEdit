package de.Jcing.world;

import java.awt.Graphics2D;

import de.Jcing.engine.Drawable;
import de.Jcing.util.Point;

public class Chunk implements Drawable{
	
	public static final int TILE_COUNT = 16;
	
	private Tile[][] tiles;
	private int x, y;
	
	boolean loaded;
	
	public Chunk(int x, int y) {
		this.x = x;
		this.y = y;
		tiles = new Tile[TILE_COUNT][TILE_COUNT];
		for (int xt = 0; xt < tiles.length; xt++) {
			for (int yt = 0; yt < tiles.length; yt++) {
				tiles[xt][yt] = new Tile(xt,yt,this);
			}
		}
	}
	
	public void load(boolean loaded) {
		//TODO: handle chunkloading logic
		this.loaded = loaded;
	}
	
	public int getXOffset() {
		return 0;
	}
	
	public int getYOffset() {
		return 0;
	}

	@Override
	public void draw(Graphics2D g) {
		for (int xt = 0; xt < tiles.length; xt++) {
			for (int yt = 0; yt < tiles.length; yt++) {
				tiles[xt][yt].draw(g);
			}
		}
	}
	
	public boolean isLoaded() {
		return loaded;
	}
	
	public Point getPoint() {
		return new Point(x,y);
	}
	
}
