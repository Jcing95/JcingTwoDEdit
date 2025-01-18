package de.jcing.game.world;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.Serializable;

import de.jcing.essentials.OutputStreamController;
import de.jcing.graphics.JCImage;
import de.jcing.job.Job;
import de.jcing.job.Routine;
import de.jcing.main.CollectedImage;
import de.jcing.main.Main;

public class Chunk implements Serializable, Routine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8392107041639808413L;

	private int x, y;
	private Dimension dim;
	private Rectangle canvas;
	private Rectangle hoveredTile;
	private boolean hovered;
	private Tile[][] tiles;
	private Grid grid;
	private boolean active;
	public static final int HEIGHT = 10;
	public static final int WIDTH = 10;

	private static OutputStreamController o = new OutputStreamController("Chunk", false);

	private boolean animated;

	private CollectedImage img;

	public Chunk(int x, int y, Level lvl) {
		this.x = x;
		this.y = y;
		this.dim = new Dimension(WIDTH, HEIGHT);
		canvas = new Rectangle(x * dim.width * Tile.SIZE, y * dim.height * Tile.SIZE, dim.width * Tile.SIZE,
				dim.height * Tile.SIZE);
		tiles = new Tile[dim.width][dim.height];

		grid = new Grid(Tile.SIZE, Tile.SIZE, Color.BLACK, 0, 0, dim.width * Tile.SIZE, dim.height * Tile.SIZE);
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[0].length; j++) {
				tiles[i][j] = new Tile(i, j, lvl.getName());
			}
		}
		o.println("C added Chunk at: " + x + " " + y);
		// TODO: UNSAVE aber trotzdem immer 0 (chunkImageList wird nicht
		// gespeichert.)
		active = false;
		hovered = false;
		animated = false;
		load(lvl);
	}

	public void unload(Level lvl) {
		lvl.getImgCollector().remove(img);
		active = false;
	}

	public Tile getTile(Point relative) {
		return tiles[relative.x][relative.y];
	}

	public Tile getTile(int x, int y) {
		return tiles[x][y];
	}

	public Point getRelativeTile(int x, int y) {
		boolean yn = false;
		boolean xn = false;
		if (x < 0) {
			xn = true;
			// x *= -1;
		}
		if (y < 0) {
			yn = true;
			// y *= -1;
		}
		x /= Tile.SIZE;
		y /= Tile.SIZE;
		if (xn) {
			x--;
			x += Math.abs(this.x) * WIDTH;
		} else {
			x %= Chunk.WIDTH;
		}
		if (yn) {
			y--;
			y += Math.abs(this.y) * HEIGHT;
		} else {
			y %= Chunk.HEIGHT;
		}

		return new Point(x, y);
	}

	private Point tilept;
	private CollectedImage lastTileimg;

	public void click(int x, int y, CollectedImage img, Level lvl, boolean rightclick, boolean collision, boolean drag) {
		if (canvas.contains(x, y)) {
			if (tilept == null || !(drag && (tilept.equals(getRelativeTile(x, y)) && lastTileimg.equals(img)))) {
				tilept = getRelativeTile(x, y);
				lastTileimg = img;
				o.println("NEW TILE");
				// y = Math.abs(y);
				// x = Math.abs(x);

				// o.println(" ->" + x + " " + y);
				// hoveredTile = new Rectangle(x * Tile.SIZE, y * Tile.SIZE,
				// Tile.SIZE, Tile.SIZE);
				// hovered = true;
				o.println("click at: " + this.x + " " + this.y + "img " + img.getId());
				if (lvl.editing() && !lvl.addingChunks()) {
					if (Main.getMain().options().replaceTile) {
						if (!rightclick) {
							if (!animated && img.get(lvl.getImgCollector()).isAnimated())
								animated = true;
							set(tilept.x, tilept.y, new Tile(img, lvl.getName(), collision));
						} else
							set(tilept.x, tilept.y, new Tile(Level.DEFAULTTILE, lvl.getName(), false));
					} else {
						if (!rightclick) {
							if (!animated && img.get(lvl.getImgCollector()).isAnimated())
								animated = true;
							tiles[tilept.x][tilept.y].addImg(img);
						} else {
							tiles[tilept.x][tilept.y].removeTop();
						}
					}
					reload(lvl);
				}
			}
		}
	}

	public boolean contains(int x, int y) {
		return canvas.contains(x, y);
	}

	public void hover(int x, int y) {
		if (contains(x, y)) {
			// o.println("HOVER: " +x + " " + y);

			boolean yn = false;
			boolean xn = false;
			if (x < 0) {
				xn = true;
				// x *= -1;
			}
			if (y < 0) {
				yn = true;
				// y *= -1;
			}
			x /= Tile.SIZE;
			y /= Tile.SIZE;
			if (xn) {
				x--;
				// x *= -1;
			}
			if (yn) {
				y--;
				// y *= -1;
			}

			// o.println(" ->" + x + " " + y);
			hoveredTile = new Rectangle(x * Tile.SIZE, y * Tile.SIZE, Tile.SIZE, Tile.SIZE);
			hovered = true;
			// if(x != lastHoveredX || y != lastHoveredY){
			// tiles[x][y].hover(true);
			// tiles[lastHoveredX][lastHoveredY].hover(false);
			// }
		} else
			hovered = false;
	}

	public void load(Level lvl) {
		// TODO: import!
		img = new CollectedImage(Level.CHUNKIMAGELIST,
				lvl.getImgCollector().add(Level.CHUNKIMAGELIST, new JCImage(dim.width * Tile.SIZE, dim.height * Tile.SIZE)));
		o.println("new CI needed: " + img.getId());

		img.set(lvl.getImgCollector(), new JCImage(dim.width * Tile.SIZE, dim.height * Tile.SIZE));
		o.println("painting Chunk " + x + " " + y);
		draw(lvl);
		active = true;
		lvl.addLoaded(this);
	}
	
	public void reload(Level lvl){
		draw(lvl);
	}
	
	private void draw(Level lvl) {
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[0].length; j++) {
				tiles[i][j].paint(img.get(lvl.getImgCollector()).getGraphics());
				if (lvl.isShowCollision())
					tiles[i][j].paintCollision(img.get(lvl.getImgCollector()).getGraphics());
			}
		}
		
		if (lvl.isShowGrid()) {
			// o.println("DRAWING GRID: " + Tile.SIZE);
			grid.paint(img.get(lvl.getImgCollector()).getGraphics());
			Graphics g = img.get(lvl.getImgCollector()).getGraphics();
			g.setColor(new Color(20, 20, 245));
			g.drawRect(0, 0, dim.width * Tile.SIZE - 1, dim.height * Tile.SIZE - 1);
		}
	}

	public void paint(Graphics g, Level lvl) {
		if (active) {
			int xOffset = lvl.getxFix();
			int yOffset = lvl.getyFix();
			g.drawImage(img.get(lvl.getImgCollector()).getImg(), x * dim.width * Tile.SIZE + xOffset,
					y * dim.height * Tile.SIZE + yOffset, null);
			g.setColor(Level.HOVER);
			if (hovered)
				g.fillRect(hoveredTile.x + /* (x * dim.width * Tile.SIZE) */xOffset,
						hoveredTile.y + /* (y * dim.height * Tile.SIZE) */yOffset, hoveredTile.width,
						hoveredTile.height);
		}
	}

	// public JCImage get(int x, int y) {
	// if (tiles[x][y] == null) {
	// return new JCImage(twidth, theight);
	// }
	// return tiles[x][y].getImg();
	// }

	public void set(int x, int y, Tile tile) {
		tiles[x][y] = new Tile(tile, x, y);
	}

	// public JCImage get(int x, int y) {
	// if (tiles[x][y] == null) {
	// return new JCImage(twidth, theight);
	// }
	// return tiles[x][y].getImg();
	// }

	// public Grid getGrid() {
	// return grid;
	// }

	public int getWidth() {
		return dim.width;
	}

	public int getHeight() {
		return dim.height;
	}

	// public JCImage get(int x, int y) {
	// if (tiles[x][y] == null) {
	// return new JCImage(twidth, theight);
	// }
	// return tiles[x][y].getImg();
	// }

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isAnimated() {
		return animated;
	}

	public void setAnimated(boolean animated) {
		this.animated = animated;
	}

	public void go() {
		// TODO Auto-generated method stub

	}

	public void setJob(Job job) {
		// TODO Auto-generated method stub

	}

	public void tick(Level lvl) {
		if (animated) {
			draw(lvl);
		}
	}
}
