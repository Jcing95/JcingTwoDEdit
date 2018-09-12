package de.Jcing.game;

import java.awt.event.KeyEvent;

import de.Jcing.Main;
import de.Jcing.engine.entity.Entity;
import de.Jcing.engine.io.KeyBoard;
import de.Jcing.engine.world.Stage;
import de.Jcing.engine.world.Tile;
import de.Jcing.tasks.Task;
import de.Jcing.util.Point;
import de.Jcing.window.Window;
import de.Jcing.window.gui.Label;

public class Game {
	
	private Stage mainStage;
	
	private Point camera;

	private Entity testEntity;
		
	public Game () {
		
		mainStage = new Stage();
		camera = new Point(0,0);
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				mainStage.addChunk(i, j);
			}
		}
		Main.getWindow().addDrawable(mainStage);
		
		Label fpsLabel = new Label("FPS: ", 10, 20);
		
		Main.getWindow().addDrawable(fpsLabel);
		new Task(() -> tick(), 60);
		new Task(() -> fpsLabel.setText("FPS: " + Main.getWindow().getFPS()), 1);
		
		mainStage.getChunkAtWorldPos(-10, 10);
		mainStage.getChunkAtWorldPos(0, 0);
		mainStage.getChunkAtWorldPos(-1, 0);
		mainStage.getChunkAtWorldPos(-17, 10);
		
		testEntity = new Entity(mainStage,0,0,20,20);
		int entityID = mainStage.addEntity(testEntity);
		
		testEntity.getOntick().add(() -> {
			camera.x = testEntity.getX()*Tile.TILE_PIXELS/Main.getWindow().getPixelSize() - Window.PIXEL_WIDTH/2;
			camera.y = testEntity.getY()*Tile.TILE_PIXELS/Main.getWindow().getPixelSize() - Window.PIXEL_HEIGHT/2;
//			System.out.println("KAMERA: " + camera);
		});
	}
	
	public Point getCamera() {
		return camera;
	}
	
	public void tick() {
		if(KeyBoard.isPressed(KeyEvent.VK_W) || KeyBoard.isPressed(KeyEvent.VK_UP))
			testEntity.accelerate(0, -.05f);
		
		if(KeyBoard.isPressed(KeyEvent.VK_A) || KeyBoard.isPressed(KeyEvent.VK_LEFT))
			testEntity.accelerate(-.05f, 0);
		
		if(KeyBoard.isPressed(KeyEvent.VK_S) || KeyBoard.isPressed(KeyEvent.VK_DOWN))
			testEntity.accelerate(0, .05f);
		
		if(KeyBoard.isPressed(KeyEvent.VK_D) || KeyBoard.isPressed(KeyEvent.VK_RIGHT))
			testEntity.accelerate(.05f, 0);
		
		mainStage.tick();
	}	
	
}
