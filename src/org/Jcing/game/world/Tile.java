package org.Jcing.game.world;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.ArrayList;

import org.Jcing.Essentials.OutputStreamController;
import org.Jcing.main.CollectedImage;
import org.Jcing.main.Main;

public class Tile implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3671222352564494120L;

	private ArrayList<CollectedImage> img;
	private int x, y;
	private boolean collision, defined;
	private String lvl;
	public static final int SIZE = 32;
	private boolean animated;
	private ArrayList<Integer> containingEntities;

	private static OutputStreamController o = new OutputStreamController("Tile", true);

	public Tile(CollectedImage img, String lvl, boolean collision) {
		this.img = new ArrayList<CollectedImage>();
		this.img.add(img);
		animated = img.get(Main.getMain().getGame().getLevel(lvl).getImgCollector()).isAnimated();

		this.collision = collision;
		this.lvl = lvl;
		defined = false;
		containingEntities = new ArrayList<Integer>();
	}

	public Tile(Tile tile, int x, int y) {
		this.x = x;
		this.y = y;
		this.img = tile.img;
		this.collision = tile.collision;
		this.defined = tile.defined;
		this.lvl = tile.lvl;
		containingEntities = new ArrayList<Integer>();
		defined = true;
	}

	public Tile(int x, int y, String lvl) {
		this.x = x;
		this.y = y;
		this.lvl = lvl;
		// super(x, y);
		img = new ArrayList<CollectedImage>();
		img.add(Level.DEFAULTTILE);
		collision = false;
		defined = true;
		containingEntities = new ArrayList<Integer>();
	}

	public boolean isAnimated() {
		return animated;
	}

	public void addImg(CollectedImage img) {
		this.img.add(img);
		defined = true;
	}

	public void replace(CollectedImage img) {
		this.img = new ArrayList<CollectedImage>();
		this.img.add(img);
	}

	public void paint(Graphics g) {
		if (defined) {
			for (int i = 0; i < img.size(); i++) {
				if (Main.getMain().getGame().getLevel(lvl).getImgCollector().get(img.get(i)) != null)
					g.drawImage(Main.getMain().getGame().getLevel(lvl).getImgCollector().get(img.get(i)).getImg(),
							x * SIZE, y * SIZE, null);
				else {
					o.println("NULL-TILE");
					g.drawImage(
							Main.getMain().getGame().getLevel(lvl).getImgCollector().get(Level.MISSINGTILE).getImg(),
							x * SIZE, y * SIZE, null);
				}
			}
		}
	}

	public synchronized boolean hasCollision(Rectangle footPrint, Level lvl, int entityID) {
		if (collision) {
			return true;
		}
		for (int i = 0; i < containingEntities.size(); i++) {
			if (containingEntities.get(i) != entityID && containingEntities.get(i) != null
					&& lvl.getEntity(containingEntities.get(i)).checkCollision(footPrint))
				return true;
		}
		return false;
	}

	public void setCollision(boolean collision) {
		this.collision = collision;
	}

	public void paintCollision(Graphics g) {
		if (collision) {
			g.setColor(Level.COLLISION);
			g.drawRect(x * SIZE + 1, y * SIZE + 1, SIZE - 2, SIZE - 2);
			g.drawLine(x * SIZE + 1, y * SIZE + 1, x * SIZE + SIZE - 2, y * SIZE + SIZE - 2);
		}
	}

	public synchronized void enter(Level lvl, int entityID) {
		if (!containingEntities.contains(entityID)) {
			containingEntities.add(entityID);
			lvl.getEntity(entityID).addTile(this);
			//			o.println("added Entity " + entityID + " to Tile " +x + "|" + y + " size: "+ containingEntities.size());
		}
	}

	public synchronized void leave(int ID) {
		int c = 0;
		while (containingEntities.contains(ID)) {
			containingEntities.remove(new Integer(ID));
			c++;
		}
		o.println("removed " + c + " Entity " + ID + " duplicates at " + x + "|" + y + " size: "
				+ containingEntities.size());
	}

	public void removeTop() {
		if (img.size() > 1)
			img.remove(img.size() - 1);
	}

}
