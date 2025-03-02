package de.jcing.main;

import java.awt.event.KeyEvent;

import de.jcing.controls.Binding;
import de.jcing.controls.Executable;
import de.jcing.controls.InputManager;
import de.jcing.creator.Creator;
import de.jcing.files.FolderLoader;
import de.jcing.game.Game;
import de.jcing.job.JobManager;
import de.jcing.window.GameWindow;

public class Main implements Executable {

	private static Main m;

	private Game game;
	private GameWindow win;

	private Creator creator;

	private JobManager jm;
	private InputManager im;

	private Options options;

	private final String TITLE = "JCING Game";
	
	private ImageCollector mainImageCollector;

	private Binding exit;
	
	public static Reminder<Main> mainInitialized;
	
	public Creator getCreator() {
		return creator;
	}

	public Main() {
	}

	public void init() {
		mainInitialized = new Reminder<Main>(this);
		options = Options.load("options.Jcop");

		im = new InputManager();
		
		mainImageCollector = new ImageCollector();
		
		game = new Game(this);
		game.getActiveLevel().load();
		win = new GameWindow(TITLE);

		creator = new Creator(game, win);
		
		jm = new JobManager();
		jm.addJob(game, 20, "mainGame");
		jm.addJob(win, 1000, "gameWindow");
		jm.addJob(creator, 60, "gameCreator");
		jm.startJobs();
		
		exit = new Binding(KeyEvent.VK_ESCAPE, this);
//		im.addBinding(exit);
		
//		ImageManager im = new ImageManager("gfx/tiles");
		mainInitialized.remind();
	}
	
	public Game getGame(){
		return game;
	}
	
	public void finish() {
		game.finish();
		win.finish();
		creator.finish();

		options.save();
		if (jm.runningJobs() > 0 || jm.pausedJobs() > 0) {
			jm.finishAll();
		}
		System.exit(0);
	}

	public Options options() {
		return options;
	}

	public InputManager getInputManager() {
		return im;
	}

	public static void main(String[] args) {
		m = new Main();
		m.init();
	}

	public static Main getMain() {
		return m;
	}

	public GameWindow getWin() {
		return win;
	}

	public void execute(Binding binding) {
		if (binding == exit) {
			this.finish();
		}
	}

	public ImageCollector getMainImageCollector() {
		return mainImageCollector;
	}

	public void setMainImageCollector(ImageCollector mainImageCollector) {
		this.mainImageCollector = mainImageCollector;
	}
}
