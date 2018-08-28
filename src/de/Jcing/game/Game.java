package de.Jcing.game;

import java.awt.event.KeyEvent;

import de.Jcing.Main;
import de.Jcing.engine.io.KeyBoard;
import de.Jcing.engine.world.Stage;
import de.Jcing.tasks.Task;
import de.Jcing.util.Point;

public class Game {
	
	private Stage mainStage;
	
	private Point camera;
	
	private Task tick;
	
	public Game () {
		
		mainStage = new Stage();
		mainStage.addChunk(0, 0);
		camera = new Point(0,0);
		Main.getWindow().addDrawable(mainStage);
		tick = new Task(() -> tick(), 60);
	}
	
	public Point getCamera() {
		return camera;
	}
	
	public void tick() {
		if(KeyBoard.isPressed(KeyEvent.VK_W))
			camera.y+= 1.5;
		if(KeyBoard.isPressed(KeyEvent.VK_A))
			camera.x+= 1.5;
		if(KeyBoard.isPressed(KeyEvent.VK_S))
			camera.y-= 1.5;
		if(KeyBoard.isPressed(KeyEvent.VK_D))
			camera.x-= 1.5;
	}	
	
}
