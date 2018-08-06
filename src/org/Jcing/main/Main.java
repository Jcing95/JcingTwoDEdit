package org.Jcing.main;

import java.awt.event.KeyEvent;

import org.Jcing.controls.Binding;
import org.Jcing.controls.Executable;
import org.Jcing.controls.InputManager;
import org.Jcing.creator.Creator;
import org.Jcing.game.Game;
import org.Jcing.job.JobManager;
import org.Jcing.window.GameWindow;

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
		jm.addJob(game.getJob(), 20, "mainGame");
		jm.addJob(win.getJob(), 1000, "gameWindow");
		jm.addJob(creator.getJob(), 60, "gameCreator");
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
		System.exit(1000);
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
