package org.Jcing.main;

import java.awt.event.KeyEvent;

import org.Jcing.controls.Executable;
import org.Jcing.controls.InputManager;
import org.Jcing.controls.KeyBinding;
import org.Jcing.creator.Creator;
import org.Jcing.game.Game;
import org.Jcing.job.JobManager;
import org.Jcing.window.GameWindow;

public class Main implements Executable {

	private static final String TITLE = "JCING Game";

	private static Main m;

	private static Game game;
	private static GameWindow win;

	private static Creator creator;

	private static JobManager jm;
	private static InputManager im;

	private static Settings options;

	private static ImageCollector mainImageCollector;
	
	
	//TODO: rework reminder as simple callback
	public static Reminder mainInitialized;

	private KeyBinding exit;
	
	
	public static void main(String[] args) {
		m = new Main();
		m.init();
	}
	
	public Main() {
	}

	public void init() {
		mainInitialized = new Reminder();
		options = Settings.load("options.Jcop");

		im = new InputManager();
		
		mainImageCollector = new ImageCollector();
		
		game = new Game();
		
		//TODO: load in game constructor?
		game.getActiveLevel().load();
		
		win = new GameWindow(TITLE);

		creator = new Creator();
		
		jm = new JobManager();
		jm.addJob(game.getJob(), 20, "mainGame");
		jm.addJob(win.getJob(), 1000, "gameWindow");
		jm.addJob(creator.getJob(), 60, "gameCreator");
		jm.startJobs();
		
		exit = new KeyBinding(KeyEvent.VK_ESCAPE, this);
//		im.addBinding(exit);
//		ImageManager im = new ImageManager("gfx/tiles");
		
		mainInitialized.remind();
	}
	
	public void execute(KeyBinding binding) {
		if (binding == exit) {
			finish();
		}
	}
	
	public static void finish() {
		game.finish();
		win.finish();
		creator.finish();

		options.save();
		if (jm.runningJobs() > 0 || jm.pausedJobs() > 0) {
			jm.finishAll();
		}
		System.exit(1000);
	}
	
	
	//static getters
	
	public static Game getGame(){
		return game;
	}
	
	public static Creator getCreator() {
		return creator;
	}

	public static Settings settings() {
		return options;
	}

	public static InputManager getInputManager() {
		return im;
	}

	public static Main getMain() {
		return m;
	}

	public static GameWindow getWin() {
		return win;
	}

	public static ImageCollector getMainImageCollector() {
		return mainImageCollector;
	}
	
}
