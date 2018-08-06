package org.Jcing.game;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import org.Jcing.controls.Binding;
import org.Jcing.controls.Executable;
import org.Jcing.creator.rectanglecreator.RectangleCreator;
import org.Jcing.game.Entity.Entity;
import org.Jcing.game.Entity.PacMan;
import org.Jcing.game.Entity.Player;
import org.Jcing.game.world.Level;
import org.Jcing.graphics.JCImage;
import org.Jcing.job.Job;
import org.Jcing.main.Main;
import org.Jcing.main.Remindable;

public class Game implements Executable, Remindable<Main> {

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
		test = new JCImage("gfx/TravelerSF/up.png",true);
		
		test.addAnimation("gfx/TravelerSF/down.png");
		test.addAnimation("gfx/TravelerSF/left.png");
		test.addAnimation("gfx/TravelerSF/right.png");
		
		test.addAnimation("gfx/TravelerSF/up");
		test.addAnimation("gfx/TravelerSF/down");
		test.addAnimation("gfx/TravelerSF/left");
		test.addAnimation("gfx/TravelerSF/right");
		
		player = new Player(test, levels.get(0));
		Main.mainInitialized.addRemindable(player);
		Main.mainInitialized.addRemindable(this);
		
		levels.get(0).setPlayer(player);
		testent =new PacMan(1000,400,levels.get(0));
		
		levels.get(0).addEntity(testent);

	}

	public Level getLevel(String name) {
		//TODO: lvl über ID! id may be unsafe due to serializing of lvls
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

	private Runnable routine = () -> {
		//		getActiveLevel().tick();
	};

	public void finish() {
//		routine.finish();
		//TODO: Save binding
		getActiveLevel().save();
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

	public Runnable getJob() {
		return routine;
	}
}
