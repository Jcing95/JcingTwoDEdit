package org.Jcing.main;

import java.awt.event.KeyEvent;

import org.Jcing.controls.Executable;
import org.Jcing.controls.InputManager;
import org.Jcing.controls.KeyBinding;
import org.Jcing.creator.Creator;
import org.Jcing.window.GameWindow;

import de.Jcing.game.Game;
import de.Jcing.tasks.Clock;
import de.Jcing.util.Point;
import de.Jcing.window.Window;

public class Main implements Executable {

	private static Main m;

	private static Game game;
	private static GameWindow win;

	private static Creator creator;

	private static InputManager im;

	private static Settings options;

	private static ImageCollector mainImageCollector;

	private static Window window;
	
	//TODO: rework reminder as simple callback
	public static Reminder mainInitialized;

	private KeyBinding exit;
	
	private static Point camera;
	
	
	public static void main(String[] args) {
		m = new Main();
		m.init();
	}
	
	public Main() {
	}

	public void init() {
		mainInitialized = new Reminder();
		
		camera = new Point(0,0);
		camera.y = -5;
		camera.x = -5;
		im = new InputManager();
		window = new Window();
		game = new Game();
		
		exit = new KeyBinding(KeyEvent.VK_ESCAPE, this);
		Clock.start();

//		options = Settings.load("options.Jcop");
//		mainImageCollector = new ImageCollector();
//		game = new Game();
		//TODO: load in game constructor?
//		game.getActiveLevel().load();
//		win = new GameWindow(TITLE);
//		creator = new Creator();
//		im.addBinding(exit);
//		ImageManager im = new ImageManager("gfx/tiles");
//		mainInitialized.remind();
	}
	
	public void execute(KeyBinding binding) {
		if (binding == exit) {
			finish();
		}
	}
	
	public static void finish() {
		game.finish();
		window.finish();
//		creator.finish();

//		options.save();
		Clock.stop();
//		System.exit(1000);
	}
	
	
	//static getters
	
	public static Window getWindow() {
		return window;
	}
	
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

	public static Point getCamera() {
		return camera;
	}
	
}
