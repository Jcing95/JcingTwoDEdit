package de.Jcing.game;

import java.awt.Color;
import java.awt.event.KeyEvent;

import de.Jcing.Main;
import de.Jcing.engine.entity.Entity;
import de.Jcing.engine.io.KeyBoard;
import de.Jcing.engine.world.Stage;
import de.Jcing.engine.world.Tile;
import de.Jcing.image.Image;
import de.Jcing.tasks.Task;
import de.Jcing.util.Point;
import de.Jcing.window.Window;
import de.Jcing.window.gui.Button;
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
		
		Label fpsLabel = new Label("FPS: ", 5, 5);
		fpsLabel.getOnClick().add(() -> System.exit(0));
		fpsLabel.listenOnMouse();
		Main.getWindow().addDrawable(fpsLabel);
		new Task(() -> tick(), 60);
		new Task(() -> fpsLabel.setText("FPS: " + Main.getWindow().getFPS()), 1);
		
		Button exit = new Button("X", Window.PIXEL_WIDTH-20, 0);
		exit.setTextColor(new Color(200,200,200));
		exit.setBackground(new Color(200,50,50), Button.DEFAULT);
		exit.setBackground(new Color(220,80,80), Button.HOVERED);
		exit.setBackground(new Color(180,10,10), Button.PRESSED);
		exit.setPosition(Window.PIXEL_WIDTH-exit.getWidth(),0);
		exit.getOnClick().add(() -> System.exit(0));
		exit.listenOnMouse();
		Main.getWindow().addDrawable(exit);
		
		testEntity = new Entity(mainStage,0,0,20,20);
		testEntity.setAnim(Entity.ON_LEFT, new Image("D:\\Bilder\\tiles\\anim\\left"));
		testEntity.setAnim(Entity.ON_RIGHT, new Image("D:\\Bilder\\tiles\\anim\\right"));
		testEntity.setAnim(Entity.ON_UP, new Image("D:\\Bilder\\tiles\\anim\\up"));
		testEntity.setAnim(Entity.ON_DOWN, new Image("D:\\Bilder\\tiles\\anim\\down"));

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
			testEntity.accelerate(0, -.025f);
		
		if(KeyBoard.isPressed(KeyEvent.VK_A) || KeyBoard.isPressed(KeyEvent.VK_LEFT))
			testEntity.accelerate(-.025f, 0);
		
		if(KeyBoard.isPressed(KeyEvent.VK_S) || KeyBoard.isPressed(KeyEvent.VK_DOWN))
			testEntity.accelerate(0, .025f);
		
		if(KeyBoard.isPressed(KeyEvent.VK_D) || KeyBoard.isPressed(KeyEvent.VK_RIGHT))
			testEntity.accelerate(.025f, 0);
		
		mainStage.tick();
	}	
	
}
