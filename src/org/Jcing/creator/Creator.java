package org.Jcing.creator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JFileChooser;

import org.Jcing.Essentials.OutputStreamController;
import org.Jcing.GUI.JCButton;
import org.Jcing.GUI.JCCheckbox;
import org.Jcing.GUI.JCLabel;
import org.Jcing.GUI.JComponentListener;
import org.Jcing.GUI.Line;
import org.Jcing.GUI.Paintable;
import org.Jcing.GUI.Pane;
import org.Jcing.GUI.ScrollPane;
import org.Jcing.GUI.Selectable;
import org.Jcing.GUI.Selector;
import org.Jcing.controls.Binding;
import org.Jcing.controls.Executable;
import org.Jcing.game.Game;
import org.Jcing.game.world.Level;
import org.Jcing.game.world.Tile;
import org.Jcing.graphics.Resizable;
import org.Jcing.job.Job;
import org.Jcing.main.CollectedImage;
import org.Jcing.main.Main;
import org.Jcing.main.Remindable;
import org.Jcing.window.GameWindow;

public class Creator implements Executable, Selector, Remindable<Main> {

	private Main m;
	private Game g;
	private GameWindow win;
	private Job job;
	
	private DecimalFormat df = new DecimalFormat("#.###");
	
	private GraphicsDevice gd;

	private Color background;
	private Color foreground;
	private BufferedImage img;

	private Rectangle size;

	private CreatorFrame frame;

	private String title;

	private CreatorActions cm;
	private JComponentListener cl;
	
	private OutputStreamController o = new OutputStreamController("Creator", true);

	private ScrollPane tilePane;
	private Selectable selected;

	private Binding show;

	private ScrollPane leftPane;
	private Pane addTilesetPane;

	public JComponentListener getComponentListener() {
		return cl;
	}

	private ArrayList<Paintable> clickables;
	private ArrayList<Resizable> toResize;

	public final JCCheckbox addChunks;
	public final JCCheckbox deleteChunks;
	public final JCCheckbox windowCreator;
	public final JCCheckbox showGrid;
	public final JCCheckbox showCollision;

	public final JCButton addTileset;

	private JCCheckbox collision;
	public final JCCheckbox entitySize;
	public final JCCheckbox entityFootprint;

	public Creator(Game game, GameWindow win) {
		m = Main.getMain();
		g = game;
		this.win = win;
		title = "JCING Game Creator";
		background = new Color(15, 10, 50);
		foreground = new Color(200, 200, 200);
		show = new Binding(KeyEvent.VK_C, this, true);
//		window = new Binding(KeyEvent.VK_NUMBER_SIGN, this, true);
//		m.getInputManager().addBinding(show);
//		m.getInputManager().addBinding(window);

		cm = new CreatorActions(this, m);
		cl = new JComponentListener();

		clickables = new ArrayList<Paintable>();
		toResize = new ArrayList<Resizable>();

		if (GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices().length < 2) {
			gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		} else {
			gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[1];
		}
		size = gd.getDefaultConfiguration().getBounds();

		img = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);

		frame = new CreatorFrame();
		if (!m.options().creatorWindowed) {
			setCreatorSize(win.getSize());
		}

		// Panes
		tilePane = new ScrollPane(size.width - (20 + 5 * (Tile.SIZE + 10)), 50, 20 + 5 * (Tile.SIZE + 10),
				size.height - (50 + 75));
		o.println("Scrollpane: " + (size.width - (20 + 5 * (Tile.SIZE + 10))) + " w: " + size.width);
		addTilesToScrollPane();
		setResizable(tilePane, Resizable.TOPRIGHTBOUND);
		o.println(
				"Tilepane initialized with: w: " + (size.width - (20 + 5 * (Tile.SIZE + 10))) + " refw:" + size.width);
		cl.add(tilePane);
		add(tilePane);

		leftPane = new ScrollPane(0, 0, 200, size.height);
		setResizable(leftPane, Resizable.TOPLEFTBOUND);
//		leftPane.setColor(Color.black);
		leftPane.setColor(Pane.DEFAULTCOLOR);
		cl.add(leftPane);
		add(leftPane);

		addTilesetPane = new Pane(tilePane.getX(), size.height - 75, tilePane.getWidth(), 75);
		setResizable(addTilesetPane, Resizable.BOTTOMRIGHTBOUND);
		cl.add(addTilesetPane);
		add(addTilesetPane);

		// button = new JCButton(10, 10, "test");
		// cl.add(button);

		leftPane.add(new Line(0, 90, leftPane.getWidth(), 0));

		windowCreator = new JCCheckbox(15, 100);
		windowCreator.setActive(m.options().creatorWindowed);
		windowCreator.setClickableManager(cm);
		cl.add(windowCreator);
		leftPane.add(windowCreator);

		leftPane.add(new JCLabel(windowCreator.getX() + 5 + windowCreator.getWidth(), windowCreator.getY(),
				"Creator Windowed"));

		leftPane.add(new Line(0, 125, leftPane.getWidth(), 0));

		addChunks = new JCCheckbox(15, 135);
		addChunks.setClickableManager(cm);
		// cl.add(addChunks);
		leftPane.add(addChunks);
		leftPane.add(new JCLabel(addChunks.getX() + 5 + addChunks.getWidth(), addChunks.getY(), "add Chunks"));

		deleteChunks = new JCCheckbox(15, 155);
		deleteChunks.setClickableManager(cm);
		// cl.add(deleteChunks);
		leftPane.add(deleteChunks);
		leftPane.add(
				new JCLabel(deleteChunks.getX() + 5 + deleteChunks.getWidth(), deleteChunks.getY(), "delete Chunks"));

		leftPane.add(new Line(0, 180, leftPane.getWidth(), 0));

		showGrid = new JCCheckbox(15, 190);
		showGrid.setActive(m.options().showGrid);
		showGrid.setClickableManager(cm);
		// cl.add(showGrid);
		leftPane.add(showGrid);
		leftPane.add(new JCLabel(showGrid.getX() + 5 + showGrid.getWidth(), showGrid.getY(), "show Grid"));

		showCollision = new JCCheckbox(15, 210);
		showCollision.setActive(m.options().showCollision);
		showCollision.setClickableManager(cm);
		leftPane.add(showCollision);
		leftPane.add(new JCLabel(showCollision.getX() + 5 + showCollision.getWidth(), showCollision.getY(),
				"show Collision"));

		leftPane.add(new Line(0, 235, leftPane.getWidth(), 0));
		
		collision = new JCCheckbox(15, 245);
		collision.setActive(false);
		leftPane.add(collision);
		leftPane.add(new JCLabel(collision.getX() + 5 + collision.getWidth(), collision.getY(), "collision"));
		
		leftPane.add(new Line(0, 270, leftPane.getWidth(), 0));
		
		entitySize = new JCCheckbox(15,280);
		entitySize.setActive(m.options().showEntitySizes);
		entitySize.setClickableManager(cm);
		leftPane.add(entitySize);
		leftPane.add(new JCLabel(entitySize.getX() + 5 + entitySize.getWidth(), entitySize.getY(),
				"show entity sizes"));
		
		entityFootprint = new JCCheckbox(15,300);
		entityFootprint.setActive(m.options().showEntitySizes);
		entityFootprint.setClickableManager(cm);
		leftPane.add(entityFootprint);
		leftPane.add(new JCLabel(entityFootprint.getX() + 5 + entityFootprint.getWidth(), entityFootprint.getY(),
				"show entity footprints"));
		
		
		addTileset = new JCButton(15, 15, "add Tileset");
		addTileset.setClickableManager(cm);
		addTilesetPane.add(addTileset);

		// add(new Line(leftPane.getWidth(),0,0,leftPane.getHeight()));

		paint(img.getGraphics());
	}

	public void setResizable(Resizable toresize, int style) {
		toresize.setResizable(size.width, size.height, style);
		toResize.add(toresize);
	}

	public void refreshTiles(int tilesetID) {
		// TODO: implement
		addTilesToScrollPane();
	}

	public void addTileSet() {
		o.println("OPENING FILECHOOSER!");
		JFileChooser fc = new JFileChooser(new File("gfx/tilesets"));
		fc.removeChoosableFileFilter(fc.getAcceptAllFileFilter());
		fc.setFileFilter(new TilesetChooser());
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnVal = fc.showOpenDialog(new JDialog());
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File chosen = fc.getSelectedFile();
			if (chosen.isDirectory()) {
				o.println(chosen.getPath());
				m.getGame().getActiveLevel()
						.addTileSet("gfx/" + new File("gfx").toURI().relativize(chosen.toURI()).getPath());
			} else {
				System.err.println("You can only choose Directories");
			}
		}
	}

	public boolean hovered(int x, int y) {
		if (tilePane.contains(x, y) || leftPane.contains(x, y) || addTilesetPane.contains(x, y)) {
			// TODO: DONT FORGET FOR FURTHER PANES OR BACKGROUND ELEMENTS
//			o.println("CREATOR HOVERED");
			return true;
		}
		return false;
	}

	private void addTilesToScrollPane() {
		// ArrayList<JCImage> tileimgs = g.getActiveLevel().getTileImgs();
		//
		// int rowlength = (tilePane.getWidth() - 10) / (Tile.SIZE + 10);
		// o.println(tileimgs.size() + " tiles available");
		// o.println(" -> " + (double) tileimgs.size() / rowlength + "
		// rows at " + rowlength + "Tiles");
		// for (int i = 0; i < (double) tileimgs.size() / rowlength; i++) {
		// for (int j = 0; j < rowlength; j++) {
		// if (tileimgs.size() > i * rowlength + j) {
		// tilePane.add(new Selectable(10 + j * (Tile.SIZE + 10), 10 + i *
		// (Tile.SIZE + 10),
		// tileimgs.get(i * rowlength + j), this));
		// o.println("added selectable nr: " + (i * rowlength + j));
		// }
		// }
		// }

		int rowlength = (tilePane.getWidth() - 10) / (Tile.SIZE + 10);
		int y = tilePane.getyOffset() + 10;
		for (int t = g.getActiveLevel().getTileImgStartIndex(); t < g.getActiveLevel().getImgCollector().size(); t++) {
			for (int i = 0; i < (double) g.getActiveLevel().getImgCollector().size(t) / rowlength; i++) {
				for (int j = 0; j < rowlength; j++) {
					if (g.getActiveLevel().getImgCollector().size(t) > i * rowlength + j) {

						tilePane.add(new Selectable(10 + j * (Tile.SIZE + 10), y,
								g.getActiveLevel().getImgCollector().get(t, i * rowlength + j), this));
						o.println("added selectable nr: " + (i * rowlength + j) + " of tileset " + t);
					}
				}
				y += 10 + (Tile.SIZE + 10);
			}
			tilePane.add(new Line(0, y - 10, tilePane.getWidth(), 0));
		}

		// TODO: seperate tilesets
	}

	public CollectedImage getSelectedImage() {
		if (selected != null)
			return g.getActiveLevel().getImgCollector().find(selected.getImg());
		return Level.DEFAULTTILE;

		// TODO: Collision
	}
	
	public boolean collision(){
		return collision.isActive();
	}
	
	public void toggleWindow() {

		if (m.options().creatorWindowed) {
			m.options().creatorWindowed = false;
			setCreatorSize(win.getSize());
			frame.setVisible(false);
			job.pause(true);
		} else {
			if (win.creatorShown())
				win.toggleShowCreator();
			job.pause(false);
			setCreatorSize(frame.getSize());
			frame.setVisible(true);

			m.options().creatorWindowed = true;
		}
	}

	public void add(Paintable paintable) {
		clickables.add(paintable);
		// if (paintable != null) {
		// if (m.options().creatorWindowed) {
		// // frame.add(paintable.getComponent());
		// }
		//
		// }
	}

	// public void addbuttons() {
	// if (m.options().creatorWinowed) {
	// for (int i = 0; i < clickables.size(); i++) {
	// frame.add(clickables.get(i));
	// }
	// }
	// }

	public void finish() {
		if (frame != null) {
			frame.finish();
		}
	}

	public void paint(Graphics g) {
		// g.setColor(background);
		// g.fillRect(0, 0, size.width, size.height);
		for (int i = 0; i < clickables.size(); i++) {
			clickables.get(i).paint(g);
		}

		g.setColor(foreground);
		if (m.getWin().getJob() != null)
			g.drawString("FPS: " + m.getWin().getJob().getTps(), 20, 45);

		g.drawString("X: " + m.getGame().getActiveLevel().getPlayer().getX() + " Y: "
				+ m.getGame().getActiveLevel().getPlayer().getY(), 20, 65);

		g.drawString("PPT: " + df.format(Math.sqrt(m.getGame().getActiveLevel().getPlayer().getMovementSpeedX()
				* m.getGame().getActiveLevel().getPlayer().getMovementSpeedX()
				+ m.getGame().getActiveLevel().getPlayer().getMovementSpeedY()
						* m.getGame().getActiveLevel().getPlayer().getMovementSpeedY())),
				20, 85);

		if (m.options().creatorWindowed && frame != null) {
			frame.repaint();
		}
	}

	public void setCreatorSize(Dimension size) {
		this.size = new Rectangle(this.size.x, this.size.y, size.width, size.height);
		img = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);

		// TODO: RESIZABLES in list
		for (int i = 0; i < toResize.size(); i++) {
			if (toResize.get(i) != null) {
				toResize.get(i).resize(size.width, size.height);
			}
		}
		// if(tilePane != null){
		// tilePane.resize(size.width, size.height);
		// }
		paint(img.getGraphics());
	}

	private Runnable routine = () -> {
		img = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
		paint(img.getGraphics());
		if (m.options().creatorWindowed) {
			frame.repaint();
		}

		if (size.height < tilePane.getHeight() + 50) {
			o.println("HAZARD XD" + size.getHeight() + "|" + tilePane.getHeight());
		}
	};

	public void setJob(Job job) {
		this.job = job;
	}

	public void execute(Binding binding) {
		if (binding == show) {
			if (!m.options().creatorWindowed)
				win.toggleShowCreator();
		}
	}

	public BufferedImage getImg() {
		return img;
	}

	public void selection(Selectable selected) {
		o.println("selection!");
		if (selected == this.selected) {
			selected = null;
			this.selected = null;
			o.println("DESELECT!");
		}
		for (int i = 0; i < tilePane.paintablesSize(); i++) {
			if (tilePane.get(i).isClickable() && selected == (Selectable) tilePane.get(i)) {
				this.selected = selected;
				for (int j = 0; j < tilePane.paintablesSize(); j++) {
					if (selected != tilePane.get(j) && tilePane.get(j).isClickable()) {
						((Selectable) tilePane.get(j)).setSelected(false);
					}
				}
			}
		}
	}

	private class CreatorFrame extends Frame implements WindowListener, ComponentListener {

		/**
		 * 
		 */
		private static final long serialVersionUID = -3450215684785245370L;

		private Image dbi;
		private Graphics dbg;

		public CreatorFrame() {
			super(title);
			// setMaximizedBounds(new Rectangle(gd.getDisplayMode().getWidth(),
			// gd.getDisplayMode().getHeight()));
			setBounds(size);

			addComponentListener(this);
			addKeyListener(m.getInputManager());
			setExtendedState(Frame.MAXIMIZED_BOTH);
			addWindowListener(this);
			if (m.options().creatorWindowed) {
				setVisible(true);
				setCreatorSize(getSize());
			}
			addMouseListener(cl);
			addMouseMotionListener(cl);
			addMouseWheelListener(cl);
			// setBackground(Color.black);

		}

		public void finish() {
			dispose();
		}

		@Override
		public void update(Graphics g) {
			if (dbi == null || dbi.getWidth(this) != getWidth() || dbi.getHeight(this) != getHeight()) {
				dbi = createImage(getWidth(), getHeight());
				dbg = dbi.getGraphics();
			}
			dbg.setColor(background);
			dbg.fillRect(0, 0, getWidth(), getHeight());

			dbg.setColor(getForeground());
			paint(dbg);
			g.drawImage(dbi, 0, 0, null);
		}

		@Override
		public void paint(Graphics g) {
			// dbg.setColor(background);
			// g.fillRect(0, 0, getWidth(), getHeight());
			g.drawImage(img, 0, 0, null);

			g.setColor(foreground);
			if (job != null)
				g.drawString("FPS: " + job.getTps(),
						size.width - 10 - g.getFontMetrics().stringWidth("FPS: " + job.getTps()),
						size.height - g.getFontMetrics().getHeight());
		}

		public void windowActivated(WindowEvent arg0) {
			// TODO Auto-generated method stub

		}

		public void windowClosed(WindowEvent arg0) {
			// TODO Auto-generated method stub

		}

		public void windowClosing(WindowEvent arg0) {
			m.finish();
		}

		public void windowDeactivated(WindowEvent arg0) {
			// TODO Auto-generated method stub

		}

		public void windowDeiconified(WindowEvent arg0) {
			// TODO Auto-generated method stub

		}

		public void windowIconified(WindowEvent arg0) {
			// TODO Auto-generated method stub

		}

		public void windowOpened(WindowEvent arg0) {
			// TODO Auto-generated method stub

		}

		public void componentHidden(ComponentEvent arg0) {
			// TODO Auto-generated method stub

		}

		public void componentMoved(ComponentEvent arg0) {
			// TODO Auto-generated method stub

		}

		public void componentResized(ComponentEvent e) {
			setCreatorSize(getSize());
		}

		@Override
		public void validate() {

		}

		public void componentShown(ComponentEvent arg0) {
			repaint();
		}

	}

	public void remind(Main r) {
		setCreatorSize(r.getWin().getSize());
	}

	public Runnable getJob() {
		return routine;
	}
}
