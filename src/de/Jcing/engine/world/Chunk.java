package de.Jcing.engine.world;

import java.awt.Graphics2D;

import de.Jcing.engine.graphics.Drawable;
import de.Jcing.util.Point;

public class Chunk implements Drawable{
	
	public static final int TILE_COUNT = 16;
	
	private Tile[][] tiles;
	private int x, y;
	
	private boolean loaded;
	
	private Stage stage;
	
	public Chunk(int x, int y, Stage stage) {
		this.x = x;
		this.y = y;
		this.stage = stage;
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
		return x * TILE_COUNT * Tile.TILE_PIXELS;
	}
	
	public int getYOffset() {
		return y * TILE_COUNT * Tile.TILE_PIXELS;
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
