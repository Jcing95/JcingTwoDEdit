package de.Jcing.game;

import org.Jcing.main.Main;

import de.Jcing.engine.world.Stage;

public class Game {
	
	private Stage mainStage;
	
	public Game () {
		
		mainStage = new Stage();
		mainStage.addChunk(0, 0);
		Main.getWindow().addDrawable(mainStage);
		
	}

	public void finish() {
		//TODO: clean up gamefinish
	}
	
	
}
