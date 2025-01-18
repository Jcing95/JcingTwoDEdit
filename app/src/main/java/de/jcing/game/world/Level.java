package de.jcing.game.world;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import de.jcing.essentials.OutputStreamController;
import de.jcing.controls.InputManager;
import de.jcing.files.FileLoader;
import de.jcing.files.FolderLoader;
import de.jcing.game.Game;
import de.jcing.game.Entity.Entity;
import de.jcing.game.Entity.Player;
import de.jcing.graphics.JCImage;
import de.jcing.main.CollectedImage;
import de.jcing.main.ImageCollector;
import de.jcing.main.Main;

public class Level {
	private Point offset;
	private int xfix, yfix;

	public static final int CHUNKIMAGELIST = 0;
	public static final CollectedImage DEFAULTTILE = new CollectedImage(0, 0);
	public static final CollectedImage MISSINGTILE = new CollectedImage(0, 1);
	public static final int DEFAULTMINCOLLISIONSIZE = 8;

	private int tileImgsStartIndex;

	public static final Color HOVER = new Color(240, 240, 240, 40);
	public static final Color ADD = new Color(20, 200, 20, 40);
	public static final Color DELETE = new Color(240, 40, 40, 40);
	public static final Color BORDER = new Color(240, 240, 240);
	public static final Color COLLISION = new Color(240, 40, 40, 200);

	private ImageCollector lvlimgs;

	private ArrayList<String> tilesets;

	private ArrayList<Entity> entities;
	private int playerID;

	//	private ArrayList<Chunk> chunks;
	private HashMap<Integer, HashMap<Integer, Chunk>> chunks;
	private ArrayList<Chunk> loadedChunks;

	private String name;

	private Game game;
	private boolean chunkHovered;
	private Point hoveredChunk;

	private boolean deleteChunks;
	private boolean addChunks;
	private boolean showCollision;
	private boolean showGrid;
	private boolean editing = true; // TODO: checkbox

	private OutputStreamController o;

	private BufferedImage canvas;

	private int mincollisionSize;

	public Level(String name, Game game) {
		this.game = game;
		this.name = name;
		o = new OutputStreamController("Level " + name, false);
		init();
	}

	private void init() {
		mincollisionSize = DEFAULTMINCOLLISIONSIZE;
		offset = new Point(0, 0);
		entities = new ArrayList<Entity>();
		showCollision = game.getMain().options().showCollision;
		showGrid = game.getMain().options().showGrid;
		loadedChunks = new ArrayList<Chunk>();
		//		canvas = new BufferedImage();
		//		canvas.setSize(game.getMain().getWin().getSize());
	}

	private void initImageCollector() {
		lvlimgs = new ImageCollector();

		// TODO: INSERT STATIC LISTS HERE:
		lvlimgs.addList(); // CHUNKIMAGELIST
		lvlimgs.add(0, new JCImage(Tile.SIZE, Tile.SIZE, new Color(10,10,75))); //STANDARDTILE
		lvlimgs.add(0, new JCImage("gfx/tilesets/MISSINGTILE.png")); //MISSINGTILE

		// all next Lists are Tilesets!
		tileImgsStartIndex = lvlimgs.size();
	}

	@SuppressWarnings("unchecked")
	public void load() {
		File lvlfile = new File(Game.levelPath + "/" + name);
		if (!lvlfile.exists()) {
			System.err.println("Could not load lvl - creating new one!");
			create();
		} else {
			initImageCollector();
			tilesets = FileLoader.LoadArrayList(Game.levelPath + "/" + name + "/usedTilesets.JTS");
			tilesets = tilesets == null ? new ArrayList<String>() : tilesets;
			for (int i = 0; i < tilesets.size(); i++) {
				o.println("loading Tileset nr: " + i);
				lvlimgs.addList(FolderLoader.indexedLoad((tilesets.get(i)), true));
			}
			var loaded = FileLoader.LoadFile(Game.levelPath + "/" + name + "/chunks.chnJc");
			chunks = loaded == null ? new HashMap<Integer, HashMap<Integer, Chunk>>() : (HashMap<Integer, HashMap<Integer, Chunk>>) loaded; 
			loadChunks();
			o.println("LOADED LVL " + name);
		}
		if (mincollisionSize <= 0) {
			mincollisionSize = DEFAULTMINCOLLISIONSIZE;
		}
	}

	public void create() {
		File lvlfile = new File(Game.levelPath + "/" + name);
		if (lvlfile.exists()) {
			System.err.println("Error: A level with this name already exists!");
		} else {
			lvlfile.mkdirs();
			initImageCollector();
			tilesets = new ArrayList<String>();
			chunks = new HashMap<Integer, HashMap<Integer, Chunk>>();
		}
		save();
	}

	public void tick() {
		canvas = new BufferedImage(canvas.getWidth(),canvas.getHeight(),BufferedImage.TYPE_INT_ARGB);
		Graphics g = canvas.getGraphics();
		if (g != null) {
			
		}
		g.dispose();
	}

	public void paint(Graphics g) {
		xfix = offset.x;
		yfix = offset.y;
		for (int i = 0; i < loadedChunks.size(); i++) {
			loadedChunks.get(i).tick(this);
			loadedChunks.get(i).paint(g, this);
		}
		if (hoveredChunk != null) {
			if (chunkHovered && deleteChunks) {
				g.setColor(DELETE);
				g.fillRect(hoveredChunk.x * Chunk.WIDTH * Tile.SIZE + offset.x,
						hoveredChunk.y * Chunk.WIDTH * Tile.SIZE + offset.y, Chunk.WIDTH * Tile.SIZE,
						Chunk.HEIGHT * Tile.SIZE);
				g.setColor(BORDER);
				g.drawRect(hoveredChunk.x * Chunk.WIDTH * Tile.SIZE + offset.x,
						hoveredChunk.y * Chunk.WIDTH * Tile.SIZE + offset.y, Chunk.WIDTH * Tile.SIZE,
						Chunk.HEIGHT * Tile.SIZE);
			} else if (!chunkHovered && addChunks) {
				g.setColor(ADD);
				g.fillRect(hoveredChunk.x * Chunk.WIDTH * Tile.SIZE + offset.x,
						hoveredChunk.y * Chunk.WIDTH * Tile.SIZE + offset.y, Chunk.WIDTH * Tile.SIZE,
						Chunk.HEIGHT * Tile.SIZE);
				g.setColor(BORDER);
				g.drawRect(hoveredChunk.x * Chunk.WIDTH * Tile.SIZE + offset.x,
						hoveredChunk.y * Chunk.WIDTH * Tile.SIZE + offset.y, Chunk.WIDTH * Tile.SIZE,
						Chunk.HEIGHT * Tile.SIZE);
			}
		}
		// chunks.get(0).getGrid().paint(g);
	}

	public void addNewChunk(int x, int y) {
		if (chunks.containsKey(x)) {
			chunks.get(x).put(y, new Chunk(x, y, this));
			o.println("L added Chunk at: " + x + " " + y);
		} else {
			chunks.put(x, new HashMap<Integer, Chunk>());
			chunks.get(x).put(y, new Chunk(x, y, this));
			o.println("L added Chunk at NL: " + x + " " + y);
		}
	}

	public Chunk getChunk(int x, int y) {
		if (chunks.containsKey(x)) {
			return chunks.get(x).get(y);
		} else {
			return null;
		}
	}

	public void removeChunk(int x, int y) {
		if (chunks.containsKey(x)) {
			chunks.remove(y);
		}
	}

	public Iterator<Integer> ChunkIterator() {
		return chunks.keySet().iterator();
	}

	public Iterator<Integer> ChunkIterator(int x) {
		//TODO: should be null save
		return chunks.get(x).keySet().iterator();
	}

	public void click(boolean drag) {
		int x = InputManager.mouse.x - offset.x;
		int y = InputManager.mouse.y - offset.y;
		if (hoveredChunk != null && !InputManager.mouse.pressed) {
			if (hoveredChunk != null) {
				if (chunkHovered && deleteChunks && getChunk(hoveredChunk) != null) {
					chunks.remove(getChunk(hoveredChunk));
				} else if (!chunkHovered && addChunks) {
					addNewChunk(hoveredChunk.x, hoveredChunk.y);
				}
			}
		}
		boolean rightClick = false;
		if (InputManager.mouse.button == MouseEvent.BUTTON3) {
			rightClick = true;
		}
		
		o.println("Click at Chunk " + (x/(Chunk.WIDTH*Tile.SIZE)) + " " + (y/(Chunk.WIDTH*Tile.SIZE)));
		if(getChunk(x/(Chunk.WIDTH*Tile.SIZE),y/(Chunk.HEIGHT*Tile.SIZE)) != null)
		getChunk(x/(Chunk.WIDTH*Tile.SIZE),y/(Chunk.HEIGHT*Tile.SIZE)).click(x, y, game.getMain().getCreator().getSelectedImage(), this, rightClick,
				game.getMain().getCreator().collision(), drag);
//		//TODO: refractor calculate Chunk directly
//		Iterator<Integer> itx = ChunkIterator();
//		while (itx.hasNext()) {
//			int cx = itx.next();
//			Iterator<Integer> ity = ChunkIterator(cx);
//			while (ity.hasNext()) {
//				int cy = ity.next();
//				if (getChunk(cx, cy).isActive())
//					getChunk(cx, cy);
//			}
//		}
	}

	public void paintMovables(Graphics g) {
		for (int i = 0; i < entities.size(); i++) {
			if (i != playerID)
				entities.get(i).paint(g);
		}
		entities.get(playerID).paint(g);
	}

	public void save() {
		FileLoader.saveFile(Game.levelPath + "/" + name + "/chunks.chnJc", chunks);
		FileLoader.saveArrayList(Game.levelPath + "/" + name + "/usedTilesets.JTS", tilesets);
	}

	public Point[] getChunks(int x, int y, Rectangle footPrint) {
		//		o.println(mincollisionSize);
		int i = 0, sx = x, sy = y, xpl = footPrint.width % mincollisionSize, ypl = footPrint.height % mincollisionSize;
		Point playerChunk[];
		if (xpl > 0 && ypl > 0) {
			// 4 more Collision points if w & h > n*mincollisionSize
			playerChunk = new Point[4 + 2 * (footPrint.width / mincollisionSize)
					+ 2 * (footPrint.height / mincollisionSize)];
		} else if (xpl > 0 || ypl > 0) {
			// 2 more Collision points if w xOr h > n*mincollisionSize
			playerChunk = new Point[2 + 2 * (footPrint.width / mincollisionSize)
					+ 2 * (footPrint.height / mincollisionSize)];
		} else {
			// 2*w/mincollisionSize + 2*h/mincollisionSize Collision points else
			playerChunk = new Point[2 * (footPrint.width / mincollisionSize)
					+ 2 * (footPrint.height / mincollisionSize)];
		}

		//		o.println("size: " + playerChunk.length + " ypl: " + ypl + " xpl: " + xpl + " w:"
		//				+ 2 * (footPrint.width / mincollisionSize) + " h: " + 2 * (footPrint.height / mincollisionSize));
		//TODO: MINIMIZE ARRAY
		// TOPLEFT TO TOPRIGHT (0|0) -> (n-2|0)
		for (; x < footPrint.width + sx - xpl; i++) {
			playerChunk[i] = getContainingChunk(x, y);
			x += mincollisionSize;
		}
		if (xpl > 0) {
			// TOPRIGHT CORNER n-1|0
			playerChunk[i] = getContainingChunk(x, y);
			x += xpl;
			i++;
		}
		//		o.println("i: " + i);

		// TOPRIGHT TO BOTTOMRIGHT (n|0) -> (n|n-2)
		for (; y < footPrint.height + sy - ypl; i++) {
			playerChunk[i] = getContainingChunk(x, y);
			y += mincollisionSize;
		}
		if (ypl > 0) {
			// BOTTOMRIGHT CORNER (n|n-1)
			playerChunk[i] = getContainingChunk(x, y);
			y += ypl;
			i++;
		}
		//		o.println("i: " + i);

		// BOTTOMRIGHT TO BOTTOMLEFT (n|n) -> (2|n)
		for (; x > sx + xpl; i++) {
			playerChunk[i] = getContainingChunk(x, y); // BOTTOMRIGHT TO
			x -= mincollisionSize; // BOTTOMLEFT
		}
		if (xpl > 0) {
			// BOTTOMLEFT CORNER (1|n)
			playerChunk[i] = getContainingChunk(x, y);
			x -= xpl;
			i++;
		}
		//		o.println("i: " + i);

		// BOTTOMLEFT TO TOPLEFT (0|n) -> (0|2)
		for (; y > sy + ypl; i++) {
			playerChunk[i] = getContainingChunk(x, y);
			y -= mincollisionSize; // BOTTOMRIGHT
		}
		if (ypl > 0) {
			// TOPLEFT CORNER (0|1)
			playerChunk[i] = getContainingChunk(x, y);
			y -= ypl;
			i++;
		}
		//		o.println("i: " + i);
		return playerChunk;
	}

	public Point[] getTiles(int x, int y, Rectangle footPrint) {
		//		Point playerChunk[] = getChunks(x, y, footPrint);
		int i = 0, sx = x, sy = y, xpl = footPrint.width % mincollisionSize, ypl = footPrint.height % mincollisionSize;

		Point playerTiles[];
		if (xpl > 0 && ypl > 0) {
			// 4 more Collision points if w & h > n*mincollisionSize
			playerTiles = new Point[4 + 2 * (footPrint.width / mincollisionSize)
					+ 2 * (footPrint.height / mincollisionSize)];
		} else if (xpl > 0 || ypl > 0) {
			// 2 more Collision points if w xOr h > n*mincollisionSize
			playerTiles = new Point[2 + 2 * (footPrint.width / mincollisionSize)
					+ 2 * (footPrint.height / mincollisionSize)];
		} else {
			// 2*w/mincollisionSize + 2*h/mincollisionSize Collision points else
			playerTiles = new Point[2 * (footPrint.width / mincollisionSize)
					+ 2 * (footPrint.height / mincollisionSize)];
		}

		//TODO: MINIMIZE ARRAY
		// COMMENTS SEE getChunks()
		for (; x < footPrint.width + sx - xpl; i++, x += mincollisionSize) {
			if (getChunk(getContainingChunk(x, y)) == null)
				playerTiles[i] = null;
			else
				playerTiles[i] = getChunk(getContainingChunk(x, y)).getRelativeTile(x, y);
		}
		if (xpl > 0) {
			if (getChunk(getContainingChunk(x, y)) == null)
				playerTiles[i] = null;
			else
				playerTiles[i] = getChunk(getContainingChunk(x, y)).getRelativeTile(x, y);
			x += xpl;
			i++;
		}
		for (; y < footPrint.height + sy - ypl; i++, y += mincollisionSize) {
			if (getChunk(getContainingChunk(x, y)) == null)
				playerTiles[i] = null;
			else
				playerTiles[i] = getChunk(getContainingChunk(x, y)).getRelativeTile(x, y);
		}
		if (xpl > 0) {

			if (getChunk(getContainingChunk(x, y)) == null)
				playerTiles[i] = null;
			else
				playerTiles[i] = getChunk(getContainingChunk(x, y)).getRelativeTile(x, y);
			y += ypl;
			i++;
		}
		for (; x > sx + xpl; i++, x -= mincollisionSize) {
			if (getChunk(getContainingChunk(x, y)) == null)
				playerTiles[i] = null;
			else
				playerTiles[i] = getChunk(getContainingChunk(x, y)).getRelativeTile(x, y);
		}
		if (xpl > 0) {
			if (getChunk(getContainingChunk(x, y)) == null)
				playerTiles[i] = null;
			else
				playerTiles[i] = getChunk(getContainingChunk(x, y)).getRelativeTile(x, y);
			x -= xpl;
			i++;
		}
		for (; y > sy + ypl; i++, y -= mincollisionSize) {
			if (getChunk(getContainingChunk(x, y)) == null)
				playerTiles[i] = null;
			else
				playerTiles[i] = getChunk(getContainingChunk(x, y)).getRelativeTile(x, y);
		}
		if (xpl > 0) {

			if (getChunk(getContainingChunk(x, y)) == null)
				playerTiles[i] = null;
			else
				playerTiles[i] = getChunk(getContainingChunk(x, y)).getRelativeTile(x, y);
			y -= ypl;
			i++;
		}
		return playerTiles;
	}

	public boolean checkEntry(Rectangle footPrint, int EntityID) {
		Point entityChunk[] = getChunks(footPrint.x, footPrint.y, footPrint);
		Point entityTiles[] = getTiles(footPrint.x, footPrint.y, footPrint);
		boolean collision = false;
		for (int i = 0; i < entityTiles.length; i++) {
			if (entityTiles[i] != null
					&& getChunk(entityChunk[i]).getTile(entityTiles[i]).hasCollision(footPrint, this, EntityID)) {
				return true;
			}
		}
		if (!collision) {
			entities.get(EntityID).logout();
			for (int i = 0; i < entityTiles.length; i++) {
				if (entityTiles[i] != null)
					getChunk(entityChunk[i]).getTile(entityTiles[i]).enter(this, EntityID);
			}
		}
		return collision;
	}

	public Point getContainingChunk(int x, int y) {
		boolean left = false;
		boolean top = false;
		if (x < 0) {
			left = true;
		}
		if (y < 0) {
			top = true;
		}

		x /= (Chunk.WIDTH * Tile.SIZE);
		y /= (Chunk.HEIGHT * Tile.SIZE);
		if (left) {
			x--;
		}
		if (top) {
			y--;
		}
		// o.println("Hovered new Chunk: " + x + "|"+y);
		return new Point(x, y);
	}

	public Chunk getChunk(Point chunk) {
		if (chunk == null) {
			return null;
		}
		return getChunk(chunk.x, chunk.y);
	}

	public Tile getTile(Point tile) {
		int x = tile.x;
		int y = tile.y;
		Chunk containing = getChunk(getContainingChunk(x, y));
		return containing.getTile(containing.getRelativeTile(x, y));
	}
	
	public void reload(){
		for (int i = 0; i < loadedChunks.size(); i++) {
			loadedChunks.get(i).reload(this);
		}
	}
	
	public void loadChunks() {
		// TODO: dynamically
		Iterator<Integer> itx = ChunkIterator();
		while (itx.hasNext()) {
			int x = itx.next();
			Iterator<Integer> ity = ChunkIterator(x);
			while (ity.hasNext()) {
				int y = ity.next();
				getChunk(x, y).load(this);
				addLoaded(getChunk(x, y));
			}
		}
	}

	public void addLoaded(Chunk chunk) {
		if (!loadedChunks.contains(chunk))
			loadedChunks.add(chunk);
	}

	public void hover(int x, int y) {
		x -= offset.x;
		y -= offset.y;
		chunkHovered = false;

		Iterator<Integer> itx = ChunkIterator();
		while (itx.hasNext()) {
			int cx = itx.next();
			Iterator<Integer> ity = ChunkIterator(cx);
			while (ity.hasNext()) {
				int cy = ity.next();
				if (getChunk(cx, cy).isActive()) {
					getChunk(cx, cy).hover(x, y);
					if (getChunk(cx, cy).contains(x, y)) {
						chunkHovered = true;
					}
				}

			}
		}
		//		for (int i = 0; i < chunks.size(); i++) {
		//			if (chunks.get(i).isActive()) {
		//				chunks.get(i).hover(x, y);
		//				if (chunks.get(i).contains(x, y)) {
		//					chunkHovered = true;
		//				}
		//			}
		//
		//		}
		hoveredChunk = getContainingChunk(x, y);
	}

	public void addTileSet(String path) {
		// o.println("SHOULD ADD: " + path);

		tilesets.add(path);
		int setid = lvlimgs.addList(FolderLoader.indexedLoad(path, true));
		Main.getMain().getCreator().refreshTiles(setid);
	}

	//	public void addChunk(Chunk chunk) {
	//		chunks.add(chunk);
	//	}
	//
	//	public void addChunk(int x, int y) {
	//		chunks.add(new Chunk(x, y, this));
	//	}

	public void toggleShowCollision() {
		game.getMain().options().showCollision = !game.getMain().options().showCollision;
		showCollision = game.getMain().options().showCollision;
	}

	public void toggleDeleteChunks() {
		if (deleteChunks) {
			deleteChunks = false;
		} else {
			deleteChunks = true;
			addChunks = false;
		}
	}

	public void toggleAddChunks() {
		if (addChunks) {
			addChunks = false;
		} else {
			addChunks = true;
			deleteChunks = false;
		}
	}

	public Game getGame() {
		return game;
	}

	public int getTileImgStartIndex() {
		return tileImgsStartIndex;
	}

	public String getName() {
		return name;
	}

	public Point getSurfacePosition(Point onScreen) {
		//TODO: test
		return new Point(onScreen.x - offset.x, onScreen.y - offset.y);
	}

	public Point getScreenPosition(Point onSurface) {
		//TODO: test
		return new Point(onSurface.x + offset.x, onSurface.y + offset.y);
	}

	public Point getOffset() {
		return offset;
	}

	public void setOffset(Point offset) {

	}

	public void addX(int x) {
		offset.x += x;
	}

	public void addY(int y) {
		offset.y += y;
	}

	public void addMovable(Entity obj) {
		// TODO: implement
	}

	public Player getPlayer() {
		return (Player) entities.get(playerID);
	}

	public void setPlayer(Player player) {
		playerID = entities.size();
		player.setID(playerID);
		entities.add(player);
	}

	public void addEntity(Entity entity) {
		entity.setID(entities.size());
		entities.add(entity);
	}

	public ArrayList<JCImage> getTileImgs() {
		ArrayList<JCImage> imgs = new ArrayList<JCImage>();
		for (int i = tileImgsStartIndex; i < lvlimgs.size(); i++) {
			for (int j = 0; j < lvlimgs.size(i); j++) {
				imgs.add(lvlimgs.get(i, j));
			}
		}
		return imgs;
	}

	public boolean isShowGrid() {
		return showGrid;
	}

	public void setShowGrid(boolean showGrid) {
		this.showGrid = showGrid;
	}

	public void toggleShowGrid() {
		game.getMain().options().showGrid = !game.getMain().options().showGrid;
		showGrid = game.getMain().options().showGrid;
	}

	public ImageCollector getImgCollector() {
		return lvlimgs;
	}

	public boolean isShowCollision() {
		return showCollision;
	}

	public boolean editing() {
		return editing;
	}

	public boolean addingChunks() {
		return addChunks;
	}

	public int getMincollisionSize() {
		return mincollisionSize;
	}

	public void setMincollisionSize(int mincollisionSize) {
		this.mincollisionSize = mincollisionSize;
	}

	public int getxOffset() {
		return offset.x;
	}

	public int getyOffset() {
		return offset.y;
	}

	public Entity getEntity(int entityID) {
		return entities.get(entityID);
	}

	public void setSize(Dimension size) {
		canvas = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
		tick();
	}

	public int getxFix() {
		return xfix;
	}
	
	public int getyFix() {
		return yfix;
	}
}
