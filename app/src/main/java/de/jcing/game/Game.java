package de.jcing.game;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import de.jcing.controls.Binding;
import de.jcing.controls.Executable;
import de.jcing.creator.rectanglecreator.RectangleCreator;
import de.jcing.game.Entity.Entity;
import de.jcing.game.Entity.Cat;
import de.jcing.game.Entity.Player;
import de.jcing.game.world.Level;
import de.jcing.graphics.JCImage;
import de.jcing.job.Job;
import de.jcing.job.Routine;
import de.jcing.main.Main;
import de.jcing.main.Remindable;

public class Game implements Routine, Executable, Remindable<Main> {

	private Job job;

	// private Level lvl;
	private JCImage test;
	private Player player;

	public final static String tilesetPath = "gfx/tilesets";
	public final static String levelPath = "levels";

	private Binding oneBinding;
	private ArrayList<Level> levels;
	private Main main;

	private RectangleCreator rectcr;
	
	
	private Entity testent;

	public Game(Main main) {
		this.main = main;
		levels = new ArrayList<Level>();
		levels.add(new Level("main", this));
		oneBinding = new Binding(KeyEvent.VK_1,this,true);
//		main.getInputManager().addBinding(oneBinding);
		// lvl = new Level("main", this);
		test = new JCImage("gfx/player/up.png",true);
		
		test.addAnimation("gfx/player/down.png");
		test.addAnimation("gfx/player/left.png");
		test.addAnimation("gfx/player/right.png");
		
		test.addAnimation("gfx/player/up");
		test.addAnimation("gfx/player/down");
		test.addAnimation("gfx/player/left");
		test.addAnimation("gfx/player/right");
		
		player = new Player(test, levels.get(0));
		Main.mainInitialized.addRemindable(player);
		Main.mainInitialized.addRemindable(this);
		
		levels.get(0).setPlayer(player);
		testent =new Cat(1000,400,levels.get(0));
		
		levels.get(0).addEntity(testent);

	}

	public Level getLevel(String name) {
		//TODO: lvl �ber ID! id may be unsafe due to serializing of lvls
		for (int i = 0; i < levels.size(); i++) {
			//			System.out.println(name + " != " + levels.get(i).getName());
			if (levels.get(i).getName().equals(name)) {
				return levels.get(i);
			}
		}
		return null;
	}

	public Main getMain() {
		return main;
	}

	public Dimension getCanvas() {
		// TODO: win.getCanvas();
		return main.getWin().getSize();
	}

	public Level getActiveLevel() {
		return levels.get(0);
	}

	public void go() {
		//		getActiveLevel().tick();
	}

	public void finish() {
		job.finish();
		//TODO: Save binding
		getActiveLevel().save();
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public void execute(Binding binding) {
		// TODO Auto-generated method stub
		if (binding == oneBinding) {
			rectcr = new RectangleCreator("TEST");
			rectcr.start();
		}
	}

	public void remind(Main r) {
		testent.accelerateX(true);
		getActiveLevel().setSize(main.getWin().getSize());
	}
}
